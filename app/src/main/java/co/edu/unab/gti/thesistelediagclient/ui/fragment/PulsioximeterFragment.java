package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class PulsioximeterFragment extends MeasureBaseFragment {

    private TextView textPulseoxygPersonData;
    private TextView textPulseoxygDateData;
    private TextView textPulseoxygBeatsPerMinute;
    private TextView textPulseoxygMaxBpm;
    private TextView textPulseoxygMinBpm;
    private TextView textPulseoxygAvgBpm;
    private TextView textPulseoxygO2concentration;
    private TextView textPulseoxygMaxO2concentrarion;
    private TextView textPulseoxygMinO2concentrarion;
    private TextView textPulseoxygAvgO2concentrarion;
    private Button buttonPulseoxygInitmeasure;
    private boolean measureStart = false;

    float bpmMax = 0;
    float bpmMin = 399;
    float bpmSum = 0;

    float o2Max = 0;
    float o2Min = 100;
    float o2Sum = 0;

    int measures = 0;

    public PulsioximeterFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_pulsioximeter, container, false);
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIHelper.getToolbarController().setTitle("Medir Pulso y Oxigeno");
        textPulseoxygPersonData = (TextView) view.findViewById(R.id.text_pulseoxyg_person_data);
        textPulseoxygDateData = (TextView) view.findViewById(R.id.text_pulseoxyg_date_data);
        textPulseoxygBeatsPerMinute = (TextView) view.findViewById(R.id.text_pulseoxyg_beats_per_minute);
        textPulseoxygMaxBpm = (TextView) view.findViewById(R.id.text_pulseoxyg_max_bpm);
        textPulseoxygMinBpm = (TextView) view.findViewById(R.id.text_pulseoxyg_min_bpm);
        textPulseoxygAvgBpm = (TextView) view.findViewById(R.id.text_pulseoxyg_avg_bpm);
        textPulseoxygO2concentration = (TextView) view.findViewById(R.id.text_pulseoxyg_o2concentration);
        textPulseoxygMaxO2concentrarion = (TextView) view.findViewById(R.id.text_pulseoxyg_max_o2concentrarion);
        textPulseoxygMinO2concentrarion = (TextView) view.findViewById(R.id.text_pulseoxyg_min_o2concentrarion);
        textPulseoxygAvgO2concentrarion = (TextView) view.findViewById(R.id.text_pulseoxyg_avg_o2concentrarion);
        buttonPulseoxygInitmeasure = (Button) view.findViewById(R.id.button_pulseoxyg_initmeasure);
        buttonPulseoxygInitmeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeasure();
            }

        });

        setupFragmentCommonUI(
                (TextView) view.findViewById(R.id.text_pulseoxyg_date_label),
                textPulseoxygPersonData,
                textPulseoxygDateData,
                buttonPulseoxygInitmeasure);
    }


    private void fillMeters(String rawData){
        String[] values = rawData.split(",");

        if (values.length == 2){

            try {
                float bpm = Float.parseFloat(values[0]);
                float o2 = Float.parseFloat(values[1]);

                textPulseoxygBeatsPerMinute.setText(values[0] );
                textPulseoxygO2concentration.setText(values[1] +"%");


                if (bpm > 10 && o2 > 10 && bpm < 400  && o2 <= 100 ) {
                    if (bpm > bpmMax) {
                        bpmMax = bpm;
                    }

                    if (bpm < bpmMin ){
                        bpmMin = bpm;
                    }

                    if ( o2 > o2Max) {
                        o2Max = o2;
                    }

                    if (o2 < o2Min ){
                        o2Min = o2;
                    }

                    bpmSum += bpm;
                    o2Sum += o2;
                    measures++;

                }


                int bpmAvg = (int) (bpmSum / measures);
                int o2Avg = (int) (o2Sum / measures);

                textPulseoxygAvgBpm.setText(Integer.toString(bpmAvg));
                textPulseoxygMaxBpm.setText(Float.toString(bpmMax));
                textPulseoxygMinBpm.setText(Float.toString(bpmMin));

                textPulseoxygAvgO2concentrarion.setText(Integer.toString(o2Avg));
                textPulseoxygMaxO2concentrarion.setText(Float.toString(o2Max));
                textPulseoxygMinO2concentrarion.setText(Float.toString(o2Min));


            } catch (NumberFormatException ex) {}

        }

    }

    @Override
    protected void onBeginMeasure() {
        measureStart = false;
        saveItem.setVisible(false);
        textPulseoxygO2concentration.setText("0%");
        textPulseoxygBeatsPerMinute.setText("0");
        textPulseoxygMaxBpm.setText("0");
        textPulseoxygMinBpm.setText("0");
        textPulseoxygAvgBpm.setText("0");
        textPulseoxygMaxO2concentrarion.setText("0");
        textPulseoxygMinO2concentrarion.setText("0");
        textPulseoxygAvgO2concentrarion.setText("0");

         bpmMax = 0;
         bpmMin = 399;
         bpmSum = 0;

         o2Max = 0;
         o2Min = 100;
         o2Sum = 0;

         measures = 0;
        btActivity.captureSensorData(Parameters.PULSIOXIMETER_SENSOR);
    }

    @Override
    protected void onStopMeasure() {
        saveItem.setVisible(true);
        measureStart = false;
        buttonPulseoxygInitmeasure.setText("Iniciar Medicion");
    }

    @Override
    protected void onSaveMeasure() {
        if (currentPerson != null){
            int bpmAvg = (int) (bpmSum / measures);
            int o2Avg = (int) (o2Sum / measures);
            String measureData = bpmAvg + "," + bpmMax + "," + bpmMin
                    + "," + o2Avg+ "," +o2Max+ "," +o2Min;

            if (measureData != null ) {

                Measure measure = new Measure(currentPerson.getFamilyId(),
                        currentPerson.getId(),
                        Parameters.getSensorName(Parameters.PULSIOXIMETER_SENSOR),
                        measureData,
                        new Date());

                measure.save();
            }


        }


    }

    @Override
    protected void onDisplayData() {

        String[] values = currentMeasure.getValue().split(",");

        if (values != null ) {
            textPulseoxygO2concentration.setText(values[3]);
            textPulseoxygBeatsPerMinute.setText(values[0]);
            textPulseoxygMaxBpm.setText(values[1]);
            textPulseoxygMinBpm.setText(values[2]);
            textPulseoxygAvgBpm.setText(values[0]);
            textPulseoxygMaxO2concentrarion.setText(values[4]);
            textPulseoxygMinO2concentrarion.setText(values[5]);
            textPulseoxygAvgO2concentrarion.setText(values[3]);
        }


    }



    @Subscribe
    public void onMessageEvent(RawData rawData){
        if (rawData.getSensorType() == Parameters.PULSIOXIMETER_SENSOR) {
            if (measureStart == false){
                measureStart = true;
                buttonPulseoxygInitmeasure.setText("Detener Medicion");
            }
            fillMeters(rawData.getValue());
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
