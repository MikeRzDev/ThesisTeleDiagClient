package co.edu.unab.gti.thesistelediagclient.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.model.RawData;
import co.edu.unab.gti.thesistelediagclient.device.CameraService;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;
import co.edu.unab.gti.thesistelediagclient.ui.fragment.AboutFragment;
import co.edu.unab.gti.thesistelediagclient.ui.fragment.FamilyMap;
import co.edu.unab.gti.thesistelediagclient.ui.fragment.FamilyRecordFragment;
import co.edu.unab.gti.thesistelediagclient.ui.fragment.UserSettingsFragment;
import co.edu.unab.gti.thesistelediagclient.ui.util.MCoordinatorLayout;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;
import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;
import co.edu.unab.gti.thesistelediagclient.util.Parameters;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private boolean sendingData = false;
    private SmoothBluetooth sInstance;
    private StringBuilder buffer;
    private int currentSensor = Parameters.NO_SENSOR;
    private boolean deviceWasFound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupToolbarController();
        ServiceHandler.getService().requestUpload();
        setupSession();
        setupBT();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //selects first option
        navigationView.setCheckedItem(0);
        UIHelper.changeRootFragment(R.id.container,getSupportFragmentManager(),new FamilyRecordFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else  if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CameraService.handleTakePictureIntent(requestCode, resultCode);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_family_record) {
            UIHelper.changeRootFragment(R.id.container,getSupportFragmentManager(),new FamilyRecordFragment());

        } else if (id == R.id.nav_family_map) {
            UIHelper.changeRootFragment(R.id.container,getSupportFragmentManager(),new FamilyMap());

        } else if (id == R.id.nav_user_config) {
            UIHelper.changeRootFragment(R.id.container,getSupportFragmentManager(),new UserSettingsFragment());

        } else if (id == R.id.nav_about) {
            UIHelper.changeRootFragment(R.id.container,getSupportFragmentManager(),new AboutFragment());

        } else if (id == R.id.nav_close_session) {
            UIHelper.showInformationDialog(this, "Informacion", "Desear Cerrar Sesion?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            },null);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupToolbarController(){
        MCoordinatorLayout coordinatorLayout = (MCoordinatorLayout) findViewById(R.id.toolbar_root);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        FloatingActionButton mainFAB = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton scrollFAB = (FloatingActionButton) findViewById(R.id.fabScroll);
        ImageView toolbarImage = (ImageView) findViewById(R.id.imageView_toolBar_image);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        UIHelper.setupToolbarController(coordinatorLayout,appBarLayout,mainFAB,scrollFAB,collapsingToolbarLayout,toolbarImage);
    }


    //TODO: setupSession
    private void setupSession(){

    }


    private void setupBT(){
        buffer = new StringBuilder();
        sInstance = new SmoothBluetooth(this,new SmoothBluetooth.Listener() {
            @Override
            public void onBluetoothNotSupported() {

            }

            @Override
            public void onBluetoothNotEnabled() {
                UIHelper.showInformationDialog(MainActivity.this, "Informacion",
                        "Activa el bluetooth para poder iniciar la comunicacion con el dispostivo",
                        null);
            }

            @Override
            public void onConnecting(Device device) {
                deviceWasFound = true;
                UIHelper.showProgressDialog(MainActivity.this,"Conectando con dispositivo");
            }

            @Override
            public void onConnected(Device device) {
                UIHelper.hideProgressDialog();
                captureSensorData(currentSensor);
            }

            @Override
            public void onDisconnected() {
                deviceWasFound = false;
            }

            @Override
            public void onConnectionFailed(Device device) {
                UIHelper.showInformationDialog(MainActivity.this, "Informacion",
                        "Conexion fallida con el dispositivo, vuelve a intentar",
                        null);
            }

            @Override
            public void onDiscoveryStarted() {
                UIHelper.showProgressDialog(MainActivity.this, "Buscando Dispositivo...");
            }

            @Override
            public void onDiscoveryFinished() {
                UIHelper.hideProgressDialog();


            }

            @Override
            public void onNoDevicesFound() {

                UIHelper.showInformationDialog(MainActivity.this, "Informacion",
                        "No se encontro el dispositivo.",
                        null);
            }

            @Override
            public void onDevicesFound(List<Device> deviceList, SmoothBluetooth.ConnectionCallback connectionCallback) {
                if (deviceList != null) {
                    Device telediagDevice = null;
                    for (Device device : deviceList) {
                        if (device.getAddress().substring(0,8).equals("00:07:80")) {
                            telediagDevice = device;
                            break;
                        }
                    }
                    if (telediagDevice != null){
                        connectionCallback.connectTo(telediagDevice);
                    }
                }
            }

            @Override
            public void onDataReceived(int data) {
                char recvChar = (char) data;
                if (recvChar != '\n'){
                    if (recvChar != '\r')
                        buffer.append(recvChar);
                } else {

                    EventBus.getDefault().post(
                            new RawData(buffer.toString(),currentSensor));
                    buffer.setLength(0);
                }
            }
        });

    }

    public final void stopSensorCapture(){
        switch (currentSensor) {
            case Parameters.PULSIOXIMETER_SENSOR: {
                sInstance.send("spo2_off",true);
                break;
            }
            case Parameters.ELECTROCARDIOGRAM_SENSOR: {
                sInstance.send("ecg_off",true);
                break;
            }
            case Parameters.TEMPERATURE_SENSOR: {
                sInstance.send("temp_off",true);
                break;
            }
            case Parameters.BLOOD_PRESSURE_SENSOR: {
                sInstance.send("bp_off",true);
                break;
            }
            case Parameters.AIRFLOW_SENSOR: {
                sInstance.send("air_off",true);
                break;
            }
        }

        currentSensor = Parameters.NO_SENSOR;
        sendingData = false;

    }
    public final boolean isSendingData() {
        return sendingData && sInstance.isConnected();
    }
    public final boolean isConnecting(){
        return sInstance.isDiscovery();
    }
    public final void captureSensorData(int sensorType){
        if (!sInstance.isConnected()){
            currentSensor = sensorType;
            sInstance.doDiscovery();
        } else {
            if (currentSensor == Parameters.NO_SENSOR) {
                currentSensor = sensorType;
            }
            switch (currentSensor) {

                case Parameters.PULSIOXIMETER_SENSOR : {
                    sInstance.send("spo2_on",true);
                    break;
                }
                case Parameters.ELECTROCARDIOGRAM_SENSOR : {
                    sInstance.send("ecg_on",true);
                    break;
                }
                case Parameters.TEMPERATURE_SENSOR : {
                    sInstance.send("temp_on",true);
                    break;
                }
                case Parameters.BLOOD_PRESSURE_SENSOR : {
                    sInstance.send("bp_on",true);
                    break;
                }
                case Parameters.AIRFLOW_SENSOR : {
                    sInstance.send("air_on",true);
                    break;
                }
            }
            sendingData = true;
        }

    }

}
