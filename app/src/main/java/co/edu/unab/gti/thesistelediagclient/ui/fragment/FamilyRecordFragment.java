package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.query.FamilyQuery;
import co.edu.unab.gti.thesistelediagclient.ui.adapter.FamilyAdapter;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyRecordFragment extends Fragment implements SearchView.OnQueryTextListener {


    ListView listView_familyDetail_list;
    FamilyAdapter adapter;

    public FamilyRecordFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_family_record, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView_familyDetail_list = (ListView) getActivity().findViewById(R.id.listView_familyRecord);
        adapter = new FamilyAdapter(getContext());
        listView_familyDetail_list.setAdapter(adapter);
        listView_familyDetail_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("familyId",adapter.getItem(position).getId());
                UIHelper.changeFragment(R.id.container,
                        getFragmentManager(),
                        new FamilyDetailFragment(),
                        bundle);
            }
        });

    }

    @Override
    public void onResume() {
        UIHelper.getToolbarController().setTitle("Ficha Familiar");
        UIHelper.getToolbarController().setActionBarMode(false);
        UIHelper.getToolbarController().setMainFABIcon(R.drawable.ic_add_black_24dp);
        UIHelper.getToolbarController().isMainFABVisible(true);
        UIHelper.getToolbarController().setMainFABOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.changeFragment(R.id.container,getFragmentManager(), new AddFamilyFragment());
            }
        });


        adapter.setItems(FamilyQuery.findAllFamilies());
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.family_record_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_family_record_search);
        SearchView  searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.setItems(FamilyQuery.findAllFamiliesByCriteria(query));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("text",newText);
        if (newText.equals("")){
            adapter.setItems(FamilyQuery.findAllFamilies());
        }
        return true;
    }
}
