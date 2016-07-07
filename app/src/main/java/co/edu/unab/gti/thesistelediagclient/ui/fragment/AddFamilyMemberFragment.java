package co.edu.unab.gti.thesistelediagclient.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.device.CameraService;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFamilyMemberFragment extends Fragment {


    String familyId;
    String picturePath;
    private Spinner spinnerAddMemberDocumentType;
    private EditText editTextAddMemberDocument;
    private Spinner spinnerAddMemberEthnicGroup;
    private Spinner spinnerAddMemberFamilyRelationship;
    private EditText editTextAddMemberFirstName;
    private EditText editTextAddMemberSecondName;
    private EditText editTextAddMemberFirstSurname;
    private EditText editTextAddMemberSecondSurname;
    private Spinner spinnerAddMemberGender;
    private TextView textViewAddMemberBirthDate;
    private Spinner spinnerAddMemberScholarLevel;
    private EditText editTextAddMemberEconomicActivity;
    private EditText editTextAddMemberProfession;
    private Spinner spinnerAddMemberHealthProviderType;
    private Spinner spinnerAddMemberHealthProvider;
    private Spinner spinnerAddMemberSpecialCondition;
    private Button buttonAddMemberSaveMember;
    private Spinner spinnerAddMemberCronicCondition;
    private ImageView imageViewAddMemberPicture;
    private Button buttonAddMemberTakePicture;


    public AddFamilyMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_family_member, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        familyId = getArguments().getString("familyId");

        if (familyId != null) {
            spinnerAddMemberDocumentType = (Spinner) view.findViewById(R.id.spinner_addMember_documentType);
            editTextAddMemberDocument = (EditText) view.findViewById(R.id.editText_addMember_document);
            spinnerAddMemberEthnicGroup = (Spinner) view.findViewById(R.id.spinner_addMember_ethnicGroup);
            spinnerAddMemberFamilyRelationship = (Spinner) view.findViewById(R.id.spinner_addMember_familyRelationship);
            editTextAddMemberFirstName = (EditText) view.findViewById(R.id.editText_addMember_firstName);
            editTextAddMemberSecondName = (EditText) view.findViewById(R.id.editText_addMember_secondName);
            editTextAddMemberFirstSurname = (EditText) view.findViewById(R.id.editText_addMember_firstSurname);
            editTextAddMemberSecondSurname = (EditText) view.findViewById(R.id.editText_addMember_secondSurname);
            spinnerAddMemberGender = (Spinner) view.findViewById(R.id.spinner_addMember_gender);
            textViewAddMemberBirthDate = (TextView) view.findViewById(R.id.textView_addMember_birthDate);
            spinnerAddMemberScholarLevel = (Spinner) view.findViewById(R.id.spinner_addMember_scholarLevel);
            editTextAddMemberEconomicActivity = (EditText) view.findViewById(R.id.editText_addMember_economicActivity);
            editTextAddMemberProfession = (EditText) view.findViewById(R.id.editText_addMember_profession);
            spinnerAddMemberHealthProviderType = (Spinner) view.findViewById(R.id.spinner_addMember_healthProviderType);
            spinnerAddMemberHealthProvider = (Spinner) view.findViewById(R.id.spinner_addMember_healthProvider);
            spinnerAddMemberSpecialCondition = (Spinner) view.findViewById(R.id.spinner_addMember_specialCondition);
            buttonAddMemberSaveMember = (Button) view.findViewById(R.id.button_addMember_saveMember);
            spinnerAddMemberCronicCondition = (Spinner) view.findViewById(R.id.spinner_addMember_cronicCondition);
            imageViewAddMemberPicture = (ImageView) view.findViewById(R.id.imageView_addMember_picture);
            buttonAddMemberTakePicture = (Button) view.findViewById(R.id.button_addMember_takePicture);

            spinnerAddMemberHealthProviderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (spinnerAddMemberHealthProviderType.getSelectedItem().toString().equals("Contributivo")) {
                        String[] array = getActivity().getResources().getStringArray(R.array.health_provider_private);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, array);
                        spinnerAddMemberHealthProvider.setAdapter(adapter);
                    } else {
                        String[] array = getActivity().getResources().getStringArray(R.array.health_provider_public);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, array);
                        spinnerAddMemberHealthProvider.setAdapter(adapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            textViewAddMemberBirthDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.DatePickerFragment dialog = new UIHelper.DatePickerFragment();
                    dialog.setViewToDisplay(textViewAddMemberBirthDate);
                    dialog.show(getActivity().getFragmentManager(), "datePicker");
                }
            });

            buttonAddMemberTakePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CameraService.takePicture(getActivity(), new CameraService.PictureTakenListener() {
                        @Override
                        public void onPictureTaken(String pictureTakenPath) {
                            picturePath = pictureTakenPath;
                            UIHelper.loadImage(imageViewAddMemberPicture, pictureTakenPath);
                        }
                    });
                }
            });

            buttonAddMemberSaveMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (formIsValid()) {

                        final Person person = new Person(
                                familyId,
                                editTextAddMemberDocument.getText().toString(),
                                spinnerAddMemberDocumentType.getSelectedItem().toString(),
                                spinnerAddMemberEthnicGroup.getSelectedItem().toString(),
                                spinnerAddMemberFamilyRelationship.getSelectedItem().toString(),
                                editTextAddMemberFirstName.getText().toString(),
                                editTextAddMemberSecondName.getText().toString(),
                                editTextAddMemberFirstSurname.getText().toString(),
                                editTextAddMemberSecondSurname.getText().toString(),
                                spinnerAddMemberGender.getSelectedItem().toString(),
                                dateFromString(textViewAddMemberBirthDate.getText().toString()),
                                spinnerAddMemberScholarLevel.getSelectedItem().toString(),
                                editTextAddMemberEconomicActivity.getText().toString(),
                                editTextAddMemberProfession.getText().toString(),
                                spinnerAddMemberHealthProviderType.getSelectedItem().toString(),
                                spinnerAddMemberHealthProvider.getSelectedItem().toString(),
                                spinnerAddMemberSpecialCondition.getSelectedItem().toString(),
                                spinnerAddMemberCronicCondition.getSelectedItem().toString(),
                                0,
                                picturePath,
                                new Date()
                        );

                        UIHelper.showInformationDialog(getActivity(), "Informacion",
                                "Deseas guardar este registro?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        person.save();
                                        UIHelper.navigateToBackFragment(getFragmentManager());

                                    }
                                }, null);
                    } else {
                        UIHelper.showInformationDialog(getActivity(), "Informacion",
                                "Debes llenar todos los campos del formulario para poder guardar", null);
                    }
                }
            });
        }


    }

    public boolean formIsValid(){
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
            UIHelper.getToolbarController().setTitle("Añadir Miembro");
            UIHelper.getToolbarController().setActionBarMode(true);
        }

    private Date dateFromString(String stringDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {

             date = formatter.parse(stringDate);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }




}
