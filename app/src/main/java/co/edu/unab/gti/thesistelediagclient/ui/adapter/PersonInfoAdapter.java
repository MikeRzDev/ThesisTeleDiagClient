package co.edu.unab.gti.thesistelediagclient.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;

/**
 * Created by user on 1/06/2016.
 */
public class PersonInfoAdapter extends RecyclerView.Adapter<PersonInfoAdapter.PersonDetailViewHolder> {

    Person person;

    public PersonInfoAdapter(Person person) {
        this.person = person;
    }

    @Override
    public PersonDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_person_info, parent, false);

        return new PersonDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonDetailViewHolder holder, int position) {
        if (position == 0){
            holder.textViewPersonInfoTitle1.setText("Doc. de Identidad");
            holder.textViewPersonInfoValue1.setText(person.getCitizenId() + " " +getDocumentAbreviation(person.getCitizenIdType()));
            holder.textViewPersonInfoTitle2.setText("Nombres");
            holder.textViewPersonInfoValue2.setText(person.getFirstName() + " " +person.getSecondName());
            holder.textViewPersonInfoTitle3.setText("Apellidos");
            holder.textViewPersonInfoValue3.setText(person.getFirstSurname() + " " +person.getSecondSurname());
            holder.textViewPersonInfoTitle4.setText("Condicion Especial");
            holder.textViewPersonInfoValue4.setText(person.getSpecialCondition());
            holder.textViewPersonInfoTitle5.setText("Condicion Cronica");
            holder.textViewPersonInfoValue5.setText(person.getCronicCondition());
            holder.textViewPersonInfoTitle6.setText("Edad");
            holder.textViewPersonInfoValue6.setText(getAge(person.getBirthdate(), new Date())+" Años");
        } else if (position == 1) {
            holder.textViewPersonInfoTitle1.setText("Genero");
            holder.textViewPersonInfoValue1.setText(person.getGender());
            holder.textViewPersonInfoTitle2.setText("Regimen de Salud");
            holder.textViewPersonInfoValue2.setText(person.getHealthProviderType());
            holder.textViewPersonInfoTitle3.setText("Proveedor de Salud");
            holder.textViewPersonInfoValue3.setText(person.getHealthProvider());
            holder.textViewPersonInfoTitle4.setText("Nivel de Escolaridad");
            holder.textViewPersonInfoValue4.setText(person.getScholarityLevel());
            holder.textViewPersonInfoTitle5.setText("Actividad Economica");
            holder.textViewPersonInfoValue5.setText(person.getBusinessActivity());
            holder.textViewPersonInfoTitle6.setText("");
            holder.textViewPersonInfoValue6.setText("");
        }

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

    @Override
    public int getItemCount() {
        return 2;
    }

    private String getDocumentAbreviation(String documentType){
        if (documentType.equals("Cedula de ciudadania")){
            return "C.C";
        } else  if (documentType.equals("Tarjeta de identidad")){
            return "T.I";
        } else  if (documentType.equals("Cedula de extrangeria")){
            return "C.E";
        } else  if (documentType.equals("Pasaporte")){
            return "P.";
        } else  if (documentType.equals("Registro civil")){
            return "R.C";

        } else  if (documentType.equals("Sin identificacion")) {
            return "N.N";

        }
        return null;

    }

    public class PersonDetailViewHolder extends RecyclerView.ViewHolder   {
        private TextView textViewPersonInfoTitle1;
        private TextView textViewPersonInfoTitle2;
        private TextView textViewPersonInfoTitle3;
        private TextView textViewPersonInfoTitle4;
        private TextView textViewPersonInfoTitle5;
        private TextView textViewPersonInfoTitle6;
        private TextView textViewPersonInfoValue1;
        private TextView textViewPersonInfoValue2;
        private TextView textViewPersonInfoValue3;
        private TextView textViewPersonInfoValue4;
        private TextView textViewPersonInfoValue5;
        private TextView textViewPersonInfoValue6;
        private ImageView imageViewPersonInfoEdit;

        public PersonDetailViewHolder(View view) {
            super(view);
            textViewPersonInfoTitle1 = (TextView) view.findViewById(R.id.textView_personInfo_title1);
            textViewPersonInfoTitle2 = (TextView) view.findViewById(R.id.textView_personInfo_title2);
            textViewPersonInfoTitle3 = (TextView) view.findViewById(R.id.textView_personInfo_title3);
            textViewPersonInfoTitle4 = (TextView) view.findViewById(R.id.textView_personInfo_title4);
            textViewPersonInfoTitle5 = (TextView) view.findViewById(R.id.textView_personInfo_title5);
            textViewPersonInfoTitle6 = (TextView) view.findViewById(R.id.textView_personInfo_title6);
            textViewPersonInfoValue1 = (TextView) view.findViewById(R.id.textView_personInfo_value1);
            textViewPersonInfoValue2 = (TextView) view.findViewById(R.id.textView_personInfo_value2);
            textViewPersonInfoValue3 = (TextView) view.findViewById(R.id.textView_personInfo_value3);
            textViewPersonInfoValue4 = (TextView) view.findViewById(R.id.textView_personInfo_value4);
            textViewPersonInfoValue5 = (TextView) view.findViewById(R.id.textView_personInfo_value5);
            textViewPersonInfoValue6 = (TextView) view.findViewById(R.id.textView_personInfo_value6);
            imageViewPersonInfoEdit = (ImageView) view.findViewById(R.id.imageView_personInfo_edit);
        }
    }


}
