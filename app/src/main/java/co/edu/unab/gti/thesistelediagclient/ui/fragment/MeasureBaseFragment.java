package co.edu.unab.gti.thesistelediagclient.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.data.database.query.MeasureQuery;
import co.edu.unab.gti.thesistelediagclient.data.database.query.PersonQuery;
import co.edu.unab.gti.thesistelediagclient.ui.activity.MainActivity;
import co.edu.unab.gti.thesistelediagclient.util.Parameters;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

/**
 * Created by user on 3/06/2016.
 */
public abstract class MeasureBaseFragment extends Fragment {
    protected MainActivity btActivity;
    protected Person currentPerson;
    protected Measure currentMeasure;
    protected int mode = -1;

    private Button button_measure;
    private TextView textView_name;
    private TextView textView_date;
    private TextView textView_label;
    protected MenuItem saveItem;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        btActivity = (MainActivity) activity;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String personId = getArguments().getString("personId");
        String measureId = getArguments().getString("measureId");
        mode = getArguments().getInt("fragmentMode");

        if (mode == Parameters.MEASURE_FRAGMENT_MODE_CAPTURE){
            currentPerson = PersonQuery.getPerson(personId);
            textView_label.setVisibility(View.GONE);
            textView_date.setVisibility(View.GONE);

            if (currentPerson != null) {
                textView_name.setText(currentPerson.getFirstName() + " "
                        + currentPerson.getSecondName()+" "
                        +currentPerson.getFirstSurname()+ " "
                        +currentPerson.getSecondSurname());
            }

        } else if (mode == Parameters.MEASURE_FRAGMENT_MODE_DISPLAY){
            currentMeasure = MeasureQuery.getMeasure(measureId);
            currentPerson = PersonQuery.getPerson(currentMeasure.getPersonId());

            if (currentPerson != null) {
                textView_name.setText(currentPerson.getFirstName() + " "
                        +currentPerson.getSecondName()+" "
                        +currentPerson.getFirstSurname()+ " "
                        +currentPerson.getSecondSurname());
            }

            if (currentMeasure != null){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                textView_date.setText(sdf.format(currentMeasure.getDate()));
                button_measure.setVisibility(View.GONE);
            }

            onDisplayData();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.measure_menu, menu);
        saveItem = menu.findItem(R.id.action_save_measure);
        saveItem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_measure) {
            UIHelper.showInformationDialog(getActivity(), "Informacion",
                    "deseas guardar esta medicion?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onSaveMeasure();
                        }
                    }, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startMeasure(){
        if (btActivity.isConnecting()) {
            return;
        }

        if (btActivity.isSendingData()) {
            UIHelper.showInformationDialog(getActivity(),
                    "Informacion", "deseas detener esta medicion?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btActivity.stopSensorCapture();
                            onStopMeasure();
                        }
                    }, null);
        } else {
            UIHelper.showInformationDialog(getActivity(), "Informacion",
                    "deseas iniciar una nueva medicion?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBeginMeasure();
                        }
                    }, null);

        }
    }

    protected void setupFragmentCommonUI(TextView textView_label,
                                         TextView textView_name,
                                         TextView textView_date,
                                         Button button_measure){
        this.textView_label = textView_label;
        this.textView_name = textView_name;
        this.textView_date = textView_date;
        this.button_measure = button_measure;
    }

    protected abstract void onBeginMeasure();
    protected abstract void onStopMeasure();
    protected abstract void onSaveMeasure();
    protected abstract void onDisplayData();

    @Override
    public void onResume() {
        super.onResume();
        UIHelper.getToolbarController().setActionBarMode(true);
    }


}
