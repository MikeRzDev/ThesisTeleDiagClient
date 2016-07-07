package co.edu.unab.gti.thesistelediagclient.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.data.database.query.FamilyQuery;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

public class FamilyAdapter extends BaseAdapter {

    private List<Family> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public FamilyAdapter(Context context) {
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
    public Family getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_family, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Family object, ViewHolder holder) {
        holder.textViewFamilyAdapterName.setText(object.getName());
        if (object.getPictureUrl() == null ){
            UIHelper.loadImage(holder.imageViewFamilyAdapterPicture,R.drawable.picture_placeholder);
        } else {
            UIHelper.loadImage(holder.imageViewFamilyAdapterPicture,object.getPictureUrl());
        }

        holder.textViewFamilyAdapterAddress.setText(object.getAddress());
        holder.textViewFamilyAdapterLocation.setText(object.getMunicipality()
        +" ("+object.getDepartment()+")");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss a");
        holder.textViewFamilyAdapterModifyDate.setText( sdf.format(object.getDateModified()));
        if (FamilyQuery.isAllFamilyDataUploaded(object.getId())){
            holder.textViewFamilyAdapterModifyDate.setBackgroundResource(R.color.colorUploaded);
        } else {
            holder.textViewFamilyAdapterModifyDate.setBackgroundResource(R.color.colorNotUploaded);
        }

    }

    protected class ViewHolder {
        private ImageView imageViewFamilyAdapterPicture;
        private TextView textViewFamilyAdapterName;
        private TextView textViewFamilyAdapterAddress;
        private TextView textViewFamilyAdapterLocation;
        private TextView textViewFamilyAdapterModifyDate;

        public ViewHolder(View view) {
            imageViewFamilyAdapterPicture = (ImageView) view.findViewById(R.id.imageView_familyAdapter_picture);
            textViewFamilyAdapterName = (TextView) view.findViewById(R.id.textView_familyAdapter_name);
            textViewFamilyAdapterAddress = (TextView) view.findViewById(R.id.textView_familyAdapter_address);
            textViewFamilyAdapterLocation = (TextView) view.findViewById(R.id.textView_familyAdapter_location);
            textViewFamilyAdapterModifyDate = (TextView) view.findViewById(R.id.textView_familyAdapter_modifyDate);

        }
    }

    public void setItems(List<Family> objects){
        this.objects = objects;
        notifyDataSetChanged();
    }
}
