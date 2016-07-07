package co.edu.unab.gti.thesistelediagclient.device;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import co.edu.unab.gti.thesistelediagclient.util.PermissionHelper;

/**
 * Created by user on 31/05/2016.
 */
public class PhoneService {
    public static final void makeCall(final Activity activity, final String phone){
        PermissionHelper.requestPhonePermission(activity,
                new PermissionHelper.PermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phone));
                activity.startActivity(callIntent);
            }
        });

    }
}
