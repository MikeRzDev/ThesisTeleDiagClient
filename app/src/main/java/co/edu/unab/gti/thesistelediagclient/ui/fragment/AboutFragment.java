package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        UIHelper.getToolbarController().setActionBarMode(false);
        UIHelper.getToolbarController().isMainFABVisible(false);
        UIHelper.getToolbarController().setTitle("Acerca de");

        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
