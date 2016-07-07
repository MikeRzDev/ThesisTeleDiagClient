package co.edu.unab.gti.thesistelediagclient.ui.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

public class MeasureAdapter extends BaseAdapter {

    private String[] measureNames = {"Pulso y Oxigeno en Sangre","Electrocardiograma","Temperatura Corporal","Presion Arterial",
    "Prueba de Exhalacion"};
    private int[] measureResId = {R.drawable.oxygen,R.drawable.cardiogram,R.drawable.temp,R.drawable.blood_pressure
    ,R.drawable.airflow};

    private Context context;
    private LayoutInflater layoutInflater;

    public MeasureAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return measureNames.length;
    }

    @Override
    public Integer getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_measure, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(position, (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(int position, ViewHolder holder) {
        holder.imageViewMeasureIcon.setImageResource(measureResId[position]);
        holder.textViewMeasureType.setText(measureNames[position]);
    }

    protected class ViewHolder {
        private ImageView imageViewMeasureIcon;
        private TextView textViewMeasureType;

        public ViewHolder(View view) {
            imageViewMeasureIcon = (ImageView) view.findViewById(R.id.imageView_measure_icon);
            textViewMeasureType = (TextView) view.findViewById(R.id.textView_measureType);
        }
    }
}
