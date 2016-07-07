package co.edu.unab.gti.thesistelediagclient.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import co.edu.unab.gti.thesistelediagclient.util.AppContext;


/**
 * Created by oscar on 15/10/15.
 */
public class NetworkService {

    public static boolean hasInternetAccess() {
        ConnectivityManager connMgr = (ConnectivityManager) AppContext.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();
    }

}
