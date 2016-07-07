package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyMap extends Fragment {


    public FamilyMap() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        UIHelper.getToolbarController().setActionBarMode(false);
        UIHelper.getToolbarController().isMainFABVisible(false);
        UIHelper.getToolbarController().setTitle("Mapa Familiar");

        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_family_map, container, false);
    }

}
