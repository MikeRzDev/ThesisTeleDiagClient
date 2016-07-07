package co.edu.unab.gti.thesistelediagclient.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by user on 29/05/2016.
 */
public class PermissionHelper {

    public interface PermissionGrantedListener{
        void onPermissionGranted();
    }

    private static final void requestPermission(final Activity activity,
                                                final PermissionGrantedListener listener,
                                                final String permission,
                                                final String rationaleMsg,
                                                final String permanentDeniedMsg
                                                ){
        if (Dexter.isRequestOngoing()) {
            return;
        }


        Dexter.checkPermission(new PermissionListener() {
            @Override public void onPermissionGranted(PermissionGrantedResponse response) {
             listener.onPermissionGranted();
            }
            @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                if (response.isPermanentlyDenied()) {
                    UIHelper.showInformationDialog(activity, "Informacion",
                            permanentDeniedMsg
                            , null);
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
                UIHelper.showInformationDialog(activity, "Informacion",
                        rationaleMsg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                token.continuePermissionRequest();
                            }
                        });

            }
        }, permission);
    }

    public static final void requestCameraPermission(Activity activity, PermissionGrantedListener listener){
        requestPermission(activity,listener,
                Manifest.permission.CAMERA,
                "Para poder usar la camara debes aceptar este permiso",
                "Para poder usar la camara debes habilitar este permiso en" +
                        " Configuracion > Apps > Telediag > Permisos > Camara");
    }

    public static final void requestStoragePermission(Activity activity, PermissionGrantedListener listener){
        requestPermission(activity,listener,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "Para poder usar la camara debes aceptar este permiso",
                "Para poder usar la camara debes habilitar este permiso en" +
                        " Configuracion > Apps > Telediag > Permisos > Almacenamiento");
    }

    public static final void requestPhonePermission(Activity activity, PermissionGrantedListener listener){
        requestPermission(activity,listener,
                Manifest.permission.CALL_PHONE,
                "Para poder llamar a este numero debes aceptar este permiso",
                "Para poder llamar a este numero debes habilitar este permiso en" +
                        " Configuracion > Apps > Telediag > Permisos > Telefono");
    }

    public static final void requestLocationPermission(Activity activity, PermissionGrantedListener listener){
        requestPermission(activity,listener,
                Manifest.permission.ACCESS_FINE_LOCATION,
                "Para poder obtener tu ubicacion debes aceptar este permiso",
                "Para poder obtener tu ubicacion debes habilitar este permiso en" +
                        " Configuracion > Apps > Telediag > Permisos > Ubicacion");
    }
}
