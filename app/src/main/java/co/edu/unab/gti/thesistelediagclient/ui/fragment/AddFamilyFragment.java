package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.device.CameraService;
import co.edu.unab.gti.thesistelediagclient.device.LocationService;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;
import co.edu.unab.gti.thesistelediagclient.service.TelediagService;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFamilyFragment extends Fragment {

    private EditText editTextAddFamilyName;
    private Spinner spinnerAddFamilyType;
    private Spinner spinnerAddFamilyStratification;
    private EditText editTextAddFamilyDepartment;
    private EditText editTextAddFamilyMunicipality;
    private EditText editTextAddFamilyAddress;
    private EditText editTextAddFamilyEmail;
    private EditText editTextAddFamilyPhone;
    private ImageView imageViewAddFamilyHomePicture;
    private Button buttonAddFamilyTakePicture;
    private Button buttonAddFamilySave;
    private TextView textViewAddFamilyLatitude;
    private TextView textViewAddFamilyLongitude;
    private Button buttonAddFamilyGetLocation;
    private Location currentLocation;
    private String imagePath;
    private boolean isGPSLocationAvailable;

    public AddFamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UIHelper.getToolbarController().setTitle("Agregar Familia");
    }

    @Override
    public void onResume() {
        UIHelper.getToolbarController().isMainFABVisible(false);
        isGPSLocationAvailable = false;
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_family, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextAddFamilyName = (EditText) view.findViewById(R.id.editText_addFamily_name);
        spinnerAddFamilyType = (Spinner) view.findViewById(R.id.spinner_addFamily_type);
        spinnerAddFamilyStratification = (Spinner) view.findViewById(R.id.spinner_addFamily_stratification);
        editTextAddFamilyDepartment = (EditText) view.findViewById(R.id.editText_addFamily_department);
        editTextAddFamilyMunicipality = (EditText) view.findViewById(R.id.editText_addFamily_municipality);
        editTextAddFamilyAddress = (EditText) view.findViewById(R.id.editText_addFamily_address);
        editTextAddFamilyEmail = (EditText) view.findViewById(R.id.editText_addFamily_email);
        editTextAddFamilyPhone = (EditText) view.findViewById(R.id.editText_addFamily_phone);
        imageViewAddFamilyHomePicture = (ImageView) view.findViewById(R.id.imageView_addFamily_homePicture);
        buttonAddFamilyTakePicture = (Button) view.findViewById(R.id.button_addFamily_takePicture);
        buttonAddFamilySave = (Button) view.findViewById(R.id.button_addFamily_save);
        textViewAddFamilyLatitude = (TextView) view.findViewById(R.id.textView_addFamily_latitude);
        textViewAddFamilyLongitude = (TextView) view.findViewById(R.id.textView_addFamily_longitude);
        buttonAddFamilyGetLocation = (Button) view.findViewById(R.id.button_addFamily_getLocation);

        buttonAddFamilyGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationService.requestLocation(getActivity(),
                        new LocationService.LocationRequestListener() {
                            @Override
                            public void onNetworkLocationObtanied(Location location) {
                                if (!isGPSLocationAvailable) {
                                    currentLocation = location;
                                    String[] latLongString = LocationService.getFormattedLocationInDegree(location.getLatitude(),
                                            location.getLongitude()).split(" ");
                                    textViewAddFamilyLatitude.setText(latLongString[0]);
                                    textViewAddFamilyLongitude.setText(latLongString[1]);
                                }
                            }

                            @Override
                            public void onGPSLocationObtanied(Location location) {
                                isGPSLocationAvailable = true;
                                currentLocation = location;
                                String[] latLongString = LocationService.getFormattedLocationInDegree(location.getLatitude(),
                                        location.getLongitude()).split(" ");
                                textViewAddFamilyLatitude.setText(latLongString[0]);
                                textViewAddFamilyLongitude.setText(latLongString[1]);
                            }
                        });
            }
        });

        buttonAddFamilyTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraService.discardLastPictureTaken();
                CameraService.takePicture(getActivity(), new CameraService.PictureTakenListener() {
                    @Override
                    public void onPictureTaken(String pictureTakenPath) {
                        imagePath = pictureTakenPath;
                        UIHelper.loadImage(imageViewAddFamilyHomePicture,pictureTakenPath);
                    }
                });
            }
        });

        buttonAddFamilySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formIsValid()){

                    final Family family = new Family(
                            editTextAddFamilyName.getText().toString(),
                            spinnerAddFamilyType.getSelectedItem().toString(),
                            editTextAddFamilyDepartment.getText().toString(),
                            editTextAddFamilyMunicipality.getText().toString(),
                            editTextAddFamilyAddress.getText().toString(),
                            spinnerAddFamilyStratification.getSelectedItem().toString(),
                            editTextAddFamilyEmail.getText().toString(),
                            editTextAddFamilyPhone.getText().toString(),
                            imagePath,
                            currentLocation != null ? currentLocation.getLatitude() : 0,
                            currentLocation != null ? currentLocation.getLongitude() : 0,
                            new Date()
                    );

                    UIHelper.showInformationDialog(getActivity(), "Informacion",
                            "Deseas guardar este registro?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    family.save();
                                    UIHelper.navigateToBackFragment(getFragmentManager());

                                }
                            },null);
                } else {
                    UIHelper.showInformationDialog(getActivity(),"Informacion",
                            "Debes llenar todos los campos del formulario para poder guardar",null);
                }
            }
        });
    }

    public boolean formIsValid(){
        return true;
    }



}
