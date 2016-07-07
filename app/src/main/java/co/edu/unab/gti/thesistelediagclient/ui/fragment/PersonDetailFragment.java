package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.data.database.query.MeasureQuery;
import co.edu.unab.gti.thesistelediagclient.data.database.query.PersonQuery;
import co.edu.unab.gti.thesistelediagclient.ui.adapter.MeasureAdapter;
import co.edu.unab.gti.thesistelediagclient.ui.adapter.PersonInfoAdapter;
import co.edu.unab.gti.thesistelediagclient.ui.adapter.PersonMeasureAdapter;
import co.edu.unab.gti.thesistelediagclient.util.Parameters;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonDetailFragment extends Fragment {

    Person selectedPerson;
    MeasureAdapter measureAdapter;
    PersonMeasureAdapter personMeasureAdapter;


    public PersonDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        measureAdapter = new MeasureAdapter(getContext());
        String personId = getArguments().getString("personId");
        selectedPerson = PersonQuery.getPerson(personId);
        if (selectedPerson != null){
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            PersonInfoAdapter adapter = new PersonInfoAdapter(selectedPerson);
            RecyclerViewPager personViewPager = (RecyclerViewPager) getActivity()
                    .findViewById(R.id.RViewPager_personDetail);
            personViewPager.setLayoutManager(layoutManager);
            personViewPager.setAdapter(adapter);

            ListView personMeasuresList = (ListView) getActivity().findViewById(R.id.listView_personDetail_measures);
            ViewCompat.setNestedScrollingEnabled(personMeasuresList, true);

            personMeasureAdapter = new PersonMeasureAdapter(getActivity());
            personMeasureAdapter.setObjects(MeasureQuery.getPersonMeasures(selectedPerson.getId()));

            personMeasuresList.setAdapter(personMeasureAdapter);
            personMeasuresList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Bundle bundle = new Bundle();

                    bundle.putInt("fragmentMode", Parameters.MEASURE_FRAGMENT_MODE_DISPLAY);
                    Measure measureSelected = personMeasureAdapter
                            .getItem(position);


                    switch (Parameters.getSensorId(measureSelected.getType())){
                        case Parameters.PULSIOXIMETER_SENSOR: {
                            bundle.putString("measureId",measureSelected.getId());
                            UIHelper.changeFragment(R.id.container,
                                    getFragmentManager(),
                                    new PulsioximeterFragment(),
                                    bundle);
                            break;
                        }
                        case Parameters.ELECTROCARDIOGRAM_SENSOR: {
                            bundle.putString("measureId",measureSelected.getId());
                            UIHelper.changeFragment(R.id.container,
                                    getFragmentManager(),
                                    new EcgFragment(),
                                    bundle);
                            break;
                        }
                        case Parameters.TEMPERATURE_SENSOR: {
                            bundle.putString("measureId",measureSelected.getId());
                            UIHelper.changeFragment(R.id.container,
                                    getFragmentManager(),
                                    new TemperatureFragment(),
                                    bundle);
                            break;
                        }
                        case Parameters.BLOOD_PRESSURE_SENSOR: {
                            bundle.putString("measureId",measureSelected.getId());
                            UIHelper.changeFragment(R.id.container,
                                    getFragmentManager(),
                                    new BloodPressureFragment(),
                                    bundle);
                            break;
                        }
                        case Parameters.AIRFLOW_SENSOR: {
                            bundle.putString("measureId",measureSelected.getId());
                            UIHelper.changeFragment(R.id.container,
                                    getFragmentManager(),
                                    new AirflowFragment(),
                                    bundle);
                            break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (selectedPerson != null){

            if (personMeasureAdapter != null) {
                personMeasureAdapter.setObjects(MeasureQuery
                        .getPersonMeasures(selectedPerson.getId()));
            }
            UIHelper.getToolbarController().setTitle(selectedPerson.getFirstName() + " "
            +selectedPerson.getFirstSurname());
            UIHelper.getToolbarController().setScrollBarMode(true);
            UIHelper.getToolbarController().setScrollToolbarFABIcon(R.drawable.ic_add_black_24dp);
            UIHelper.getToolbarController()
                    .setScrollToolbarFABOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Bundle bundle = new Bundle();
                            bundle.putString("personId",selectedPerson.getId());
                            bundle.putInt("fragmentMode", Parameters.MEASURE_FRAGMENT_MODE_CAPTURE);
                            UIHelper.showCustomListDialog(getActivity(), "Realizar Medicion",
                                    measureAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which){
                                                case 0: {
                                                    UIHelper.changeFragment(R.id.container,
                                                            getFragmentManager(),
                                                            new PulsioximeterFragment(),
                                                            bundle);
                                                    break;
                                                }
                                                case 1: {
                                                    UIHelper.changeFragment(R.id.container,
                                                            getFragmentManager(),
                                                            new EcgFragment(),
                                                            bundle);
                                                    break;
                                                }
                                                case 2: {
                                                    UIHelper.changeFragment(R.id.container,
                                                            getFragmentManager(),
                                                            new TemperatureFragment(),
                                                            bundle);
                                                    break;
                                                }
                                                case 3: {
                                                    UIHelper.changeFragment(R.id.container,
                                                            getFragmentManager(),
                                                            new BloodPressureFragment(),
                                                            bundle);
                                                    break;
                                                }
                                                case 4: {
                                                    UIHelper.changeFragment(R.id.container,
                                                            getFragmentManager(),
                                                            new AirflowFragment(),
                                                            bundle);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                            );

                        }
                    });
            UIHelper.getToolbarController().setImage(selectedPerson.getPictureURL());
        }

    }

}
