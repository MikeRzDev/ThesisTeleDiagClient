package co.edu.unab.gti.thesistelediagclient.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;


import com.karumi.dexter.Dexter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;

import co.edu.unab.gti.thesistelediagclient.data.database.SQLCipherHelperImpl;
import co.edu.unab.gti.thesistelediagclient.data.database.TelediagDB;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;

/**
 * Created by user on 18/04/2016.
 */

//public class AppContext extends MultiDexApplication
public class AppContext extends MultiDexApplication {

    private static Context context;
    private static DisplayImageOptions imageOptions;
    private static DisplayImageOptions imageOptionRounded;


    @Override
    public void onCreate() {
        super.onCreate();
        if (imageOptions == null) {
            imageOptions = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(false)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .cacheInMemory(false)
                    .cacheOnDisk(false)
                    .build();
        }

        if (imageOptionRounded == null){
            imageOptionRounded = new DisplayImageOptions.Builder()
                    .displayer(new RoundedBitmapDisplayer(1000))
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(false)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .cacheInMemory(false)
                    .cacheOnDisk(false)
                    .build();
        }

        if (context == null){
            context = getApplicationContext();
        }

        FlowManager.init(new FlowConfig.Builder(this)
                .addDatabaseConfig(
                        new DatabaseConfig.Builder(TelediagDB.class)
                                .openHelper(new DatabaseConfig.OpenHelperCreator() {
                                    @Override
                                    public OpenHelper createHelper(DatabaseDefinition databaseDefinition,
                                                                   DatabaseHelperListener helperListener) {
                                        return new SQLCipherHelperImpl(databaseDefinition, helperListener);
                                    }
                                })
                                .build())
                .build());

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(conf);

        Dexter.initialize(this);
        ServiceHandler.bindService();
    }

    public static Context getContext(){
        return context;
    }

    public static DisplayImageOptions getImageOptions(){
        return imageOptions;
    }

    public static DisplayImageOptions getRoundedOptions(){
        return imageOptionRounded;
    }

    public static boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
