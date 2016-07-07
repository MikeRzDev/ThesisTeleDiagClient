package co.edu.unab.gti.thesistelediagclient.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

public class PersonMeasureAdapter extends BaseAdapter {

    private List<Measure> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public PersonMeasureAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (objects == null){
            return 0;
        } else {
            return objects.size();
        }
    }

    @Override
    public Measure getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_measure_person, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Measure object, ViewHolder holder) {
       if (object.getType().equals("pulsioximeter")){
           holder.imageViewPersonMeasureIcon.setImageResource(R.drawable.oxygen);
           holder.textViewPersonMeasureType.setText("Pulso y Oxigeno en Sangre");
           String[] values = object.getValue().split(",");

           if (values.length == 6){
               holder.textViewPersonMeasureValue.setText(values[0] + " lpm | "+values[1]+" %");
           }


       } else if (object.getType().equals("electrocardiogram")){
           holder.imageViewPersonMeasureIcon.setImageResource(R.drawable.cardiogram);
           holder.textViewPersonMeasureType.setText("Electrocardiograma");
           holder.textViewPersonMeasureValue.setVisibility(View.GONE);

       } else if (object.getType().equals("temperature")){
           holder.imageViewPersonMeasureIcon.setImageResource(R.drawable.temp);
           holder.textViewPersonMeasureType.setText("Temperatura");
           holder.textViewPersonMeasureValue.setText(object.getValue() +" °C");


       } else if (object.getType().equals("blood_pressure")){
           holder.imageViewPersonMeasureIcon.setImageResource(R.drawable.blood_pressure);
           holder.textViewPersonMeasureType.setText("Presion sanguinea");
           String[] values = object.getValue().split(",");

           if (values.length == 2){
               holder.textViewPersonMeasureValue.setText(values[0] + " sis | "+values[1]+" dia");
           }

       } else if (object.getType().equals("airflow")){
           holder.imageViewPersonMeasureIcon.setImageResource(R.drawable.airflow);
           holder.textViewPersonMeasureType.setText("Flujo de respiracion");
           holder.textViewPersonMeasureValue.setVisibility(View.GONE);
       }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        holder.textViewPersonMeasureDate.setText(sdf.format(object.getDate()));


    }

    protected class ViewHolder {
        private ImageView imageViewPersonMeasureIcon;
        private TextView textViewPersonMeasureType;
        private TextView textViewPersonMeasureDate;
        private TextView textViewPersonMeasureValue;

        public ViewHolder(View view) {
            imageViewPersonMeasureIcon = (ImageView) view.findViewById(R.id.imageView_personMeasure_icon);
            textViewPersonMeasureType = (TextView) view.findViewById(R.id.textView_personMeasure_type);
            textViewPersonMeasureDate = (TextView) view.findViewById(R.id.textView_personMeasure_date);
            textViewPersonMeasureValue = (TextView) view.findViewById(R.id.textView_personMeasure_value);
        }
    }

    public void setObjects(List<Measure> objects){
        this.objects = objects;
        notifyDataSetChanged();
    }

    public List<Measure> getObjects(){
        return objects;
    }
}
