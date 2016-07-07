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
public class EcgFragment extends MeasureBaseFragment {


    private CardView card_time;
    private int upperTime = 0;
    private TextView textPruebaecgPersonData;
    private TextView textPruebaecgDateData;
    private LineChart chartEcg;
    private Chronometer chronoPruebaecgTimeintest;
    private Button buttonPruebaecgInitmeasure;
    private List<String> measuresList;
    private boolean measureStart = false;
    public EcgFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_ecg, null);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIHelper.getToolbarController().setTitle("Electrocardiograma");
        textPruebaecgPersonData = (TextView) getActivity().findViewById(R.id.text_pruebaecg_person_data);
        textPruebaecgDateData = (TextView) getActivity().findViewById(R.id.text_pruebaecg_date_data);
        chronoPruebaecgTimeintest = (Chronometer) getActivity().findViewById(R.id.chrono_pruebaecg_timeintest);
        buttonPruebaecgInitmeasure = (Button) getActivity().findViewById(R.id.button_pruebaecg_initmeasure);
        card_time = (CardView) getActivity().findViewById(R.id.card_timeEcg);

        buttonPruebaecgInitmeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeasure();
            }

        });

        setupFragmentCommonUI(
                (TextView) view.findViewById(R.id.text_pruebaecg_date_label),
                textPruebaecgPersonData,
                textPruebaecgDateData,
                buttonPruebaecgInitmeasure);

        measuresList = new ArrayList<>();
        chartEcg = (LineChart) getActivity().findViewById(R.id.chart_ecg);
        configChart();
        chronoPruebaecgTimeintest.setText("00:00");
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

                    LineData lineData = chartEcg.getLineData();
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
                            chartEcg.notifyDataSetChanged();
                            chartEcg.moveViewToX(lineData.getXValCount());
                        }
                    }
                } catch (NumberFormatException ex) {

                }

            }
        }
    }



    private void configChart() {
        chartEcg.setDrawGridBackground(false);
        chartEcg.setTouchEnabled(true);
        chartEcg.setDragEnabled(true);
        chartEcg.setScaleEnabled(true);
        chartEcg.setDescription("");
        chartEcg.setHighlightPerTapEnabled(false);
        chartEcg.setHighlightPerDragEnabled(false);
        chartEcg.getAxisLeft().setDrawGridLines(false);
        chartEcg.getAxisRight().setEnabled(false);
        chartEcg.getXAxis().setDrawGridLines(true);
        chartEcg.getXAxis().setDrawAxisLine(false);


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
        chartEcg.setData(data);
        chartEcg.invalidate();
        btActivity.captureSensorData(Parameters.ELECTROCARDIOGRAM_SENSOR);
    }

    @Override
    protected void onStopMeasure() {
        saveItem.setVisible(true);
        chronoPruebaecgTimeintest.stop();
        buttonPruebaecgInitmeasure.setText("Iniciar Medicion");
    }


    @Override
    protected void onSaveMeasure() {
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
                    Parameters.getSensorName(Parameters.ELECTROCARDIOGRAM_SENSOR),
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
            chartEcg.setData(lineData);
            chartEcg.invalidate();
        }
    }


    @Subscribe
    public void onMessageEvent(RawData rawData){
        if (rawData.getSensorType() == Parameters.ELECTROCARDIOGRAM_SENSOR) {
            if (measureStart == false) {
                measureStart = true;
                buttonPruebaecgInitmeasure.setText("Detener Medicion");
                chronoPruebaecgTimeintest.setBase(SystemClock.elapsedRealtime());
                chronoPruebaecgTimeintest.start();
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
