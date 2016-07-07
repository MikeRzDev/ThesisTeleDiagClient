package co.edu.unab.gti.thesistelediagclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Family_Table;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure_Table;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person_Table;
import co.edu.unab.gti.thesistelediagclient.data.database.query.FamilyQuery;
import co.edu.unab.gti.thesistelediagclient.data.database.query.MeasureQuery;
import co.edu.unab.gti.thesistelediagclient.data.database.query.PersonQuery;
import co.edu.unab.gti.thesistelediagclient.data.model.MeasurePK;
import co.edu.unab.gti.thesistelediagclient.data.model.PersonPK;
import co.edu.unab.gti.thesistelediagclient.device.HttpClientService;
import co.edu.unab.gti.thesistelediagclient.device.NetworkService;
import co.edu.unab.gti.thesistelediagclient.util.Parameters;
import co.edu.unab.gti.thesistelediagclient.util.ServiceTextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.concurrent.FutureCallback;

public class TelediagService extends Service {

    private ScheduledThreadPoolExecutor scheduledPoolExecutor;
    private ScheduledFuture threadHandler;

    private final IBinder mBinder = new TelediagServiceBinder();
    public class TelediagServiceBinder extends Binder {
        public TelediagService getService() {
            // Return this instance of LocalService so clients can call public methods
            return TelediagService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        scheduledPoolExecutor
                = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void requestUpload(){
        long familyCount  =  SQLite.selectCountOf().from(Family.class)
                .where(Family_Table.isUploaded.eq(false))
                .count();
        long personCount = SQLite.selectCountOf().from(Person.class)
                .where(Person_Table.isUploaded.eq(false))
                .count();
        long measureCount = SQLite.selectCountOf().from(Measure.class)
                .where(Measure_Table.isUploaded.eq(false))
                .count();

        long notUploaded = measureCount + personCount + familyCount;

        if (notUploaded > 0){
            if  (threadHandler == null || threadHandler.isDone())
            threadHandler = scheduledPoolExecutor.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    uploadUnsent();
                }
            },0,5,TimeUnit.SECONDS);
        }
    }


    private void uploadUnsent(){
        if (!NetworkService.hasInternetAccess()){
            return;
        }
        List<Family> unsentFamilyList = FamilyQuery.getUnsendFamilies();
        for (final Family family : unsentFamilyList){
            HttpClientService.servicePost(Parameters.FAMILY_ENDPOINT,
                    family, new ServiceTextHttpResponseHandler(getMainLooper()) {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("Service","family send fail");
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            family.setUploaded(true);
                            family.update();
                            Log.d("Service","family send success");
                        }
                    });
        }

        if (unsentFamilyList.size() == 0){
            List<Person> unsentPersonList = PersonQuery.getUnsendPersons();

            for (final Person person: unsentPersonList){
                person.setPersonPK(new PersonPK(person.getFamilyId(),person.getId()));
                HttpClientService.servicePost(Parameters.PERSON_ENDPOINT,
                        person, new ServiceTextHttpResponseHandler(getMainLooper()) {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("Service","person send fail");
                            }
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                person.setUploaded(true);
                                person.update();
                                Log.d("Service","person send success");
                            }
                        });
            }

            if (unsentPersonList.size() == 0){
                List<Measure> unsentMeasuresList = MeasureQuery.getUnsendMeasures();


                for (final Measure measure: unsentMeasuresList){
                    measure.setMeasurePK(new MeasurePK(measure.getFamilyId(),measure.getId(),measure.getPersonId()));
                    HttpClientService.servicePost(Parameters.MEASURE_ENDPOINT,
                            measure, new ServiceTextHttpResponseHandler(getMainLooper()) {
                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    Log.d("Service","measure send fail");
                                }
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                    measure.setUploaded(true);
                                    measure.update();
                                    Log.d("Service","measure send success");
                                }
                            });
                }

                if (unsentFamilyList.size() == 0 && unsentMeasuresList.size() == 0
                        && unsentMeasuresList.size() == 0){
                    threadHandler.cancel(false);
                }
            }
        }


    }
}
