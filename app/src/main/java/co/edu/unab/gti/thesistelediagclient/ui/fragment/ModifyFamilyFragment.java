package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.unab.gti.thesistelediagclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyFamilyFragment extends Fragment {


    public ModifyFamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_family, container, false);
    }

}
