package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.model.RawData;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;
import co.edu.unab.gti.thesistelediagclient.util.Parameters;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirflowFragment extends MeasureBaseFragment {

    private int upperTime = 0;

    String familyId;
    String personId;


    private CardView card_time;
    private TextView textPruebaexhPersonData;
    private TextView textPruebaexhDateData;
    private LineChart chartAirflow;
    private Chronometer chronoPruebaexhTimeintest;
    private Button buttonPruebaexhInitmeasure;
    private List<String> measuresList;
    private boolean measureStart = false;
    public AirflowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_airflow, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIHelper.getToolbarController().setTitle("Prueba de Exhalacion");
        textPruebaexhPersonData = (TextView) getActivity().findViewById(R.id.text_pruebaexh_person_data);
        textPruebaexhDateData = (TextView) getActivity().findViewById(R.id.text_pruebaexh_date_data);
        chronoPruebaexhTimeintest = (Chronometer) getActivity().findViewById(R.id.chrono_pruebaexh_timeintest);
        card_time = (CardView) getActivity().findViewById(R.id.card_timeAirflow);
        buttonPruebaexhInitmeasure = (Button) getActivity().findViewById(R.id.button_pruebaexh_initmeasure);
        buttonPruebaexhInitmeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeasure();
            }
        });

        setupFragmentCommonUI(
                (TextView) view.findViewById(R.id.text_pruebaexh_date_label),
                textPruebaexhPersonData,
                textPruebaexhDateData,
                buttonPruebaexhInitmeasure);

        measuresList = new ArrayList<>();
        chartAirflow = (LineChart) getActivity().findViewById(R.id.chart_airflow);
        configChart();
        chronoPruebaexhTimeintest.setText("00:00");

    }


    private void addDataToChart(String data) {
        if (data != null) {
            String[] values = data.split(",");
                if (values.length == 2) {
                    try {
                        float time = Float.parseFloat(values[0]) / 1000f;
                        float voltage = Float.parseFloat(values[1]);
                        StringBuilder builder = new StringBuilder(Float.toString(time));
                        builder.append(",");
                        builder.append(Float.toString(voltage));
                        measuresList.add(builder.toString());

                        LineData lineData = chartAirflow.getLineData();
                        if (lineData != null) {
                            ILineDataSet set = lineData.getDataSetByIndex(0);
                            if (set == null) {
                                set = createDynamicDataSet();
                                lineData.addDataSet(set);
                            }
                            lineData.addXValue(Float.toString(time));
                            lineData.addEntry(new Entry(voltage, set.getEntryCount())
                                    , 0);
                            if (time >= upperTime) {
                                upperTime = upperTime + 1;
                                chartAirflow.notifyDataSetChanged();
                                chartAirflow.moveViewToX(lineData.getXValCount());
                            }
                        }
                    } catch (NumberFormatException ex) {

                    }

                }
        }
    }



    private void configChart() {
        chartAirflow.setDrawGridBackground(false);
        chartAirflow.setTouchEnabled(true);
        chartAirflow.setDragEnabled(true);
        chartAirflow.setScaleEnabled(true);
        chartAirflow.setDescription("");
        chartAirflow.setHighlightPerTapEnabled(false);
        chartAirflow.setHighlightPerDragEnabled(false);
        chartAirflow.getAxisLeft().setDrawGridLines(false);
        chartAirflow.getAxisRight().setEnabled(false);
        chartAirflow.getXAxis().setDrawGridLines(true);
        chartAirflow.getXAxis().setDrawAxisLine(false);


    }


    private LineDataSet createDynamicDataSet() {
        LineDataSet set = new LineDataSet(null, "Voltaje");
        set.setColor(Color.RED);
        set.setLineWidth(0.5f);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setDrawFilled(false);
        return set;
    }


    @Override
    protected void onBeginMeasure() {
        measuresList.clear();
        measureStart = false;
        saveItem.setVisible(false);
        upperTime = 0;
        LineData data = new LineData();
        chartAirflow.setData(data);
        chartAirflow.invalidate();
        btActivity.captureSensorData(Parameters.AIRFLOW_SENSOR);
    }


    @Override
    protected void onStopMeasure() {
        saveItem.setVisible(true);
        chronoPruebaexhTimeintest.stop();
        buttonPruebaexhInitmeasure.setText("Iniciar Medicion");
    }

    @Override
    protected void onSaveMeasure(){

        if (currentPerson != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < measuresList.size(); i++){
                stringBuilder.append(measuresList.get(i));
                if (i != measuresList.size()-1) {
                    stringBuilder.append(":");
                }
            }


            Measure measure = new Measure(currentPerson.getFamilyId(),
                    currentPerson.getId(),
                    Parameters.getSensorName(Parameters.AIRFLOW_SENSOR),
                    stringBuilder.toString(),
                    new Date());

            measure.save();
        }
    }

    @Override
    protected void onDisplayData() {
        card_time.setVisibility(View.GONE);
        if (currentMeasure != null){

            List<String> xVals = new ArrayList<>();
            List<Entry> yVals = new ArrayList<>();

            String[] measures = currentMeasure.getValue().split(":");
            for (int i = 0; i < measures.length; i++){
                String[] values = measures[i].split(",");
                xVals.add(values[0]);
                yVals.add(new Entry(
                        Float.parseFloat(values[1]),
                        i));

            }

            LineDataSet lineDataSet = new LineDataSet(yVals,"Voltaje");
            lineDataSet.setColor(Color.RED);
            lineDataSet.setLineWidth(0.5f);
            lineDataSet.setDrawValues(false);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            LineData lineData = new LineData(xVals,lineDataSet);
            chartAirflow.setData(lineData);
            chartAirflow.invalidate();
        }

    }

    @Subscribe
    public void onMessageEvent(RawData rawData){
        if (rawData.getSensorType() == Parameters.AIRFLOW_SENSOR) {
            if (measureStart == false) {
                measureStart = true;
                buttonPruebaexhInitmeasure.setText("Detener Medicion");
                chronoPruebaexhTimeintest.setBase(SystemClock.elapsedRealtime());
                chronoPruebaexhTimeintest.start();
            }
            addDataToChart(rawData.getValue());
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
