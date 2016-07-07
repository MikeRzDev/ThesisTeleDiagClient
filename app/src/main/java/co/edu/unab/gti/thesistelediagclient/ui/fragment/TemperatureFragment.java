package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.model.RawData;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;
import co.edu.unab.gti.thesistelediagclient.util.Parameters;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends MeasureBaseFragment {


    public TemperatureFragment() {
        // Required empty public constructor
    }


    private CardView card_time;
    private TextView textTempcorpPersonData;
    private TextView textTempcorpDateData;
    private TextView textTempcorpTemp;
    private Chronometer chronoTempcorpTimeintest;
    private Button buttonTempcorpInitmeasure;
    private boolean measureStart = false;
    float temp = 0;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UIHelper.getToolbarController().setTitle("Medir Temperatura");
        textTempcorpPersonData = (TextView) view.findViewById(R.id.text_tempcorp_person_data);
        textTempcorpDateData = (TextView) view.findViewById(R.id.text_tempcorp_date_data);
        textTempcorpTemp = (TextView) view.findViewById(R.id.text_tempcorp_temp);
        chronoTempcorpTimeintest = (Chronometer) view.findViewById(R.id.chrono_tempcorp_timeintest);
        buttonTempcorpInitmeasure = (Button) view.findViewById(R.id.button_tempcorp_initmeasure);
        card_time = (CardView) getActivity().findViewById(R.id.card_timeTemp);

        buttonTempcorpInitmeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeasure();
            }
        });

        setupFragmentCommonUI(
                (TextView) view.findViewById(R.id.text_tempcorp_date_label),
                textTempcorpPersonData,
                textTempcorpDateData,
                buttonTempcorpInitmeasure);

        chronoTempcorpTimeintest.setText("00:00");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_temperature, container, false);
    }


    private void fillMeter(String data){
        if (data != null){
           textTempcorpTemp.setText(data+" ºC");
            try {
                temp = Float.parseFloat(data);
            } catch (NumberFormatException ex) {}
        }

    }


    @Override
    protected void onBeginMeasure() {
        measureStart = false;
        textTempcorpTemp.setText("0 ºC");
        btActivity.captureSensorData(Parameters.TEMPERATURE_SENSOR);
    }

    @Override
    protected void onStopMeasure() {
        saveItem.setVisible(true);
        chronoTempcorpTimeintest.stop();
        buttonTempcorpInitmeasure.setText("Iniciar Medicion");
    }

    @Override
    protected void onSaveMeasure() {
        if (currentPerson != null) {
            Measure measure = new Measure(currentPerson.getFamilyId(),
                    currentPerson.getId(),
                    Parameters.getSensorName(Parameters.TEMPERATURE_SENSOR),
                    Float.toString(temp),
                    new Date());

            measure.save();
        }

    }

    @Override
    protected void onDisplayData() {
        card_time.setVisibility(View.GONE);
        if (currentMeasure != null){
            textTempcorpTemp.setText(currentMeasure.getValue());
        }

    }

    @Subscribe
    public void onMessageEvent(RawData rawData){
        Log.d("data",rawData.getValue());
        if (rawData.getSensorType() == Parameters.TEMPERATURE_SENSOR) {
            if (measureStart == false) {
                measureStart = true;
                buttonTempcorpInitmeasure.setText("Detener Medicion");
                chronoTempcorpTimeintest.setBase(SystemClock.elapsedRealtime());
                chronoTempcorpTimeintest.start();
            }
            fillMeter(rawData.getValue());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        btActivity.stopSensorCapture();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
