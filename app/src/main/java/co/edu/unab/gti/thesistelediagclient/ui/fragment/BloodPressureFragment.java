package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class BloodPressureFragment extends MeasureBaseFragment {


    public BloodPressureFragment() {
        // Required empty public constructor
    }

    private TextView textPresartPersonData;
    private TextView textPresartDateData;
    private TextView textPresartSystolicPressure;
    private TextView textPresartDiastolicPressure;
    private Button buttonPresartReadtension;
    private boolean measureStart = false;
    private String bpData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_blood_pressure, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIHelper.getToolbarController().setTitle("Tomar Presion Sanguinea");

        textPresartPersonData = (TextView) view.findViewById(R.id.text_presart_person_data);
        textPresartDateData = (TextView) view.findViewById(R.id.text_presart_date_data);
        textPresartSystolicPressure = (TextView) view.findViewById(R.id.text_presart_systolic_pressure);
        textPresartDiastolicPressure = (TextView) view.findViewById(R.id.text_presart_diastolic_pressure);
        buttonPresartReadtension = (Button) view.findViewById(R.id.button_presart_readtension);
        buttonPresartReadtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeasure();
            }
        });

        setupFragmentCommonUI(
                (TextView) view.findViewById(R.id.text_presart_date_label),
                textPresartPersonData,
                textPresartDateData,
                buttonPresartReadtension);
    }

    private void fillMeters(String data){
        if (data != null){
            String[] values = data.split(",");
            textPresartSystolicPressure.setText(values[0]);
            textPresartDiastolicPressure.setText(values[1]);
            bpData = data;
        }

    }


    @Override
    protected void onBeginMeasure() {
        measureStart = false;
        textPresartSystolicPressure.setText("0");
        textPresartDiastolicPressure.setText("0");
        btActivity.captureSensorData(Parameters.BLOOD_PRESSURE_SENSOR);

    }

    @Override
    protected void onStopMeasure() {
        saveItem.setVisible(true);
        buttonPresartReadtension.setText("Iniciar Medicion");
    }

    @Override
    protected void onSaveMeasure() {

        if (currentPerson != null && bpData != null) {
            Measure measure = new Measure(currentPerson.getFamilyId(),
                    currentPerson.getId(),
                    Parameters.getSensorName(Parameters.BLOOD_PRESSURE_SENSOR),
                    bpData,
                    new Date());

            measure.save();
        }
    }

    @Override
    protected void onDisplayData() {
        if (currentMeasure != null){
            String[] values = currentMeasure.getValue().split(",");
            textPresartSystolicPressure.setText(values[0]);
            textPresartDiastolicPressure.setText(values[1]);
        }

    }

    @Subscribe
    public void onMessageEvent(RawData rawData){
        Log.d("data",rawData.getValue());
        if (rawData.getSensorType() == Parameters.BLOOD_PRESSURE_SENSOR) {
            if (measureStart == false){
                measureStart = true;
                buttonPresartReadtension.setText("Detener Medicion");
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
