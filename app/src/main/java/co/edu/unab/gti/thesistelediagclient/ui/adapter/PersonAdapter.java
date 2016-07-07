package co.edu.unab.gti.thesistelediagclient.ui.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

public class PersonAdapter extends BaseAdapter {

    private List<Person> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public PersonAdapter(Context context) {
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
    public Person getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_person, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Person object, ViewHolder holder) {
       if (object.getPictureURL() == null){
           UIHelper.loadImageRounded(holder.imageViewAdapterPersonPicture,R.drawable.person_placeholder);
       } else {
           UIHelper.loadImageRounded(holder.imageViewAdapterPersonPicture,
                   object.getPictureURL());
       }


        holder.textViewAdapterPersonRelationship.setText(object.getFamilyRelationship());
        holder.textViewAdapterPersonAge.setText(getAge(object.getBirthdate(),new Date())+" Años");

        if (object.getCronicCondition().toLowerCase().equals("no aplica")) {
            holder.textViewAdapterPersonCronicCondition.setVisibility(View.INVISIBLE);
        } else {
            String condition = object.getCronicCondition().replace("Enfermedad","E.");
            condition.replace("Cardiovascular","Cardio.");
            condition.replace("Psiquiatrica","Psiq.");
            holder.textViewAdapterPersonCronicCondition.setText(condition);
        }

        if (object.getSpecialCondition().toLowerCase().equals("no aplica")) {
            holder.textViewAdapterPersonSpecialCondition.setVisibility(View.INVISIBLE);
        } else {
            String condition = object.getSpecialCondition().replace("Discapacidad","d.");
            holder.textViewAdapterPersonSpecialCondition.setText(condition);
        }

        holder.textViewAdapterPersonName.setText(object.getFirstName()+ " "
        +object.getSecondName()+" " +object.getFirstSurname()+" " +object.getSecondSurname());



    }

    public String getAge(Date first, Date last) {
        if (first == null || last == null){
            return "";
        }
        Calendar a = Calendar.getInstance();
        a.setTime(first);
        Calendar b = Calendar.getInstance();
        b.setTime(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) >
                        b.get(Calendar.DATE))) {
            diff--;
        }
        return Integer.toString(diff);
    }

    protected class ViewHolder {
        private ImageView imageViewAdapterPersonPicture;
        private TextView textViewAdapterPersonName;
        private TextView textViewAdapterPersonAge;
        private TextView textViewAdapterPersonRelationship;
        private TextView textViewAdapterPersonCronicCondition;
        private TextView textViewAdapterPersonSpecialCondition;

        public ViewHolder(View view) {
            imageViewAdapterPersonPicture = (ImageView) view.findViewById(R.id.imageView_adapterPerson_picture);
            textViewAdapterPersonName = (TextView) view.findViewById(R.id.textView_adapterPerson_name);
            textViewAdapterPersonAge = (TextView) view.findViewById(R.id.textView_adapterPerson_age);
            textViewAdapterPersonCronicCondition = (TextView) view.findViewById(R.id.textView_adapterPerson_cronicCondition);
            textViewAdapterPersonSpecialCondition = (TextView) view.findViewById(R.id.textView_adapterPerson_specialCondition);
            textViewAdapterPersonRelationship = (TextView) view.findViewById(R.id.textView_adapterPerson_relationship);

        }
    }

    public void setObjects(List<Person> objects){
        this.objects = objects;
        notifyDataSetChanged();
    }

    public List<Person> getObjects(){
        return objects;
    }
}
