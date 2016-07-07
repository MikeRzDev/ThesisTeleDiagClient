package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.data.database.query.FamilyQuery;
import co.edu.unab.gti.thesistelediagclient.data.database.query.PersonQuery;
import co.edu.unab.gti.thesistelediagclient.device.PhoneService;
import co.edu.unab.gti.thesistelediagclient.ui.adapter.PersonAdapter;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyDetailFragment extends Fragment {



    Family selectedFamily;

    private TextView textViewFamilyDetailType;
    private TextView textViewFamilyDetailDepartment;
    private TextView textViewFamilyDetailMunicipality;
    private TextView textViewFamilyDetailAddress;
    private TextView textViewFamilyDetailEmail;
    private TextView textViewFamilyDetailPhone;
    private TextView textViewFamilyDetailStratification;

    private ImageView imageViewFamilyDetailEdit;

    private ListView listViewFamilyDetailMembers;
    private PersonAdapter adapter;

    public FamilyDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_family_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String familyId = getArguments().getString("familyId");

        if (familyId != null){
            selectedFamily = FamilyQuery.findFamilyById(familyId);
            if (selectedFamily != null){

                textViewFamilyDetailType = (TextView) getActivity().findViewById(R.id.textView_familyDetail_type);
                textViewFamilyDetailDepartment = (TextView) getActivity().findViewById(R.id.textView_personInfo_value2);
                textViewFamilyDetailMunicipality = (TextView) getActivity().findViewById(R.id.textView_familyDetail_municipality);
                textViewFamilyDetailAddress = (TextView) getActivity().findViewById(R.id.textView_personInfo_value3);
                textViewFamilyDetailEmail = (TextView) getActivity().findViewById(R.id.textView_personInfo_value4);
                textViewFamilyDetailPhone = (TextView) getActivity().findViewById(R.id.textView_personInfo_value5);
                textViewFamilyDetailStratification = (TextView) getActivity().findViewById(R.id.textView_personInfo_value6);
                imageViewFamilyDetailEdit = (ImageView) getActivity().findViewById(R.id.imageView_familyDetail_edit);
                listViewFamilyDetailMembers = (ListView) getActivity().findViewById(R.id.listView_familyDetail_members);
                ViewCompat.setNestedScrollingEnabled(listViewFamilyDetailMembers, true);

                textViewFamilyDetailType.setText(selectedFamily.getType());
                textViewFamilyDetailDepartment.setText(selectedFamily.getDepartment());
                textViewFamilyDetailMunicipality.setText(selectedFamily.getMunicipality());
                textViewFamilyDetailAddress.setText(selectedFamily.getAddress());
                textViewFamilyDetailEmail.setText(selectedFamily.getContactEmail());
                textViewFamilyDetailPhone.setText(selectedFamily.getContactPhone());
                textViewFamilyDetailPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhoneService.makeCall(getActivity(),
                                selectedFamily.getContactPhone());
                    }
                });
                textViewFamilyDetailStratification.setText(selectedFamily.getPublicStratification());

                adapter = new PersonAdapter(getActivity());
                listViewFamilyDetailMembers.setAdapter(adapter);

                listViewFamilyDetailMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("personId",adapter.
                                getObjects().get(position).getId());
                        UIHelper.changeFragment(R.id.container,
                                getFragmentManager(),
                                new PersonDetailFragment(),
                                bundle);
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (selectedFamily != null){


            adapter.setObjects(PersonQuery.getPersonsByFamilyId(selectedFamily.getId()));
            UIHelper.getToolbarController().setTitle(selectedFamily.getName());
            UIHelper.getToolbarController().setScrollBarMode(true);
            UIHelper.getToolbarController().isMainFABVisible(false);
            UIHelper.getToolbarController().setScrollToolbarFABIcon(R.drawable.ic_add_black_24dp);
            UIHelper.getToolbarController()
                    .setScrollToolbarFABOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("familyId",selectedFamily.getId());
                            UIHelper.changeFragment(R.id.container,getFragmentManager()
                            ,new AddFamilyMemberFragment(), bundle);
                        }
                    });
            UIHelper.getToolbarController().setImage(selectedFamily.getPictureUrl());
        }

    }
}
