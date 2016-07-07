package co.edu.unab.gti.thesistelediagclient.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import co.edu.unab.gti.thesistelediagclient.util.AppContext;


/**
 * Created by user on 10/03/2016.
 */
public class ServiceHandler {
    private static TelediagService mService;
    private static Boolean mBound = false;
    private static ServiceConnection mConnection;

    public static boolean isServiceBinded(){
        return mBound;
    }

    public static TelediagService getService(){
        return mService;
    }


    //analog to start service
    public static void bindService() {
        if (!AppContext.isServiceRunning(TelediagService.class)) {
            mConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName className,
                                               IBinder service) {
                    // We've bound to LocalService, cast the IBinder and get LocalService instance
                    TelediagService.TelediagServiceBinder binder = (TelediagService.TelediagServiceBinder) service;
                    mService = binder.getService();
                    mBound = true;
                    Log.d("ServiceHandler","service started!");
                }


                @Override
                public void onServiceDisconnected(ComponentName arg0) {
                    mBound = false;
                }
            };
            Intent intent = new Intent(AppContext.getContext(), TelediagService.class);
            AppContext.getContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    //analog to stop service
    public static void unBindService() {
        if (mBound) {
            Log.d("ServiceHandler","service stopped!");
            AppContext.getContext().unbindService(mConnection);
            mBound = false;
        }
    }
}
