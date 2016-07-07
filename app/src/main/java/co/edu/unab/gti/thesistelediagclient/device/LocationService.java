package co.edu.unab.gti.thesistelediagclient.device;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import co.edu.unab.gti.thesistelediagclient.util.AppContext;
import co.edu.unab.gti.thesistelediagclient.util.PermissionHelper;

/**
 * Created by user on 28/05/2016.
 */
public class LocationService {

    public interface LocationRequestListener{
        void onNetworkLocationObtanied(Location location);
        void onGPSLocationObtanied(Location location);
    }

    public static void requestLocation(final Activity activity, final LocationRequestListener listener) {
        PermissionHelper.requestLocationPermission(activity, new PermissionHelper.PermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                getCurrentLocation(listener);
            }
        });
    }


    private static void getCurrentLocation(final LocationRequestListener listener){
        final LocationManager locationManager = (LocationManager) AppContext.getContext()
                .getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        final LocationListener gpsLocationListener = new LocationListener() {
            public void onLocationChanged(final Location location) {
                // Called when a new location is found by the network location provider.
                listener.onGPSLocationObtanied(location);
                Log.d("LocationService","gps location obtained!");

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        final LocationListener networkLocationListener = new LocationListener() {
            public void onLocationChanged(final Location location) {
                // Called when a new location is found by the network location provider.
                listener.onNetworkLocationObtanied(location);
                Log.d("LocationService","network location obtained!");

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };



// Register the listener with the Location Manager to receive location updates
        try {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, networkLocationListener,null);
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, gpsLocationListener,null);

        } catch (SecurityException e){
            Log.e("LocationService","location permission has not been granted");
        }

    }

    public static String getFormattedLocationInDegree(double latitude, double longitude) {
        try {
            int latSeconds = (int) Math.round(latitude * 3600);
            int latDegrees = latSeconds / 3600;
            latSeconds = Math.abs(latSeconds % 3600);
            int latMinutes = latSeconds / 60;
            latSeconds %= 60;

            int longSeconds = (int) Math.round(longitude * 3600);
            int longDegrees = longSeconds / 3600;
            longSeconds = Math.abs(longSeconds % 3600);
            int longMinutes = longSeconds / 60;
            longSeconds %= 60;
            String latDegree = latDegrees >= 0 ? "N" : "S";
            String lonDegrees = longDegrees >= 0 ? "E" : "O";

            return  Math.abs(latDegrees) + "°" + latMinutes + "'" + latSeconds
                    + "\"" + latDegree +" "+ Math.abs(longDegrees) + "°" + longMinutes
                    + "'" + longSeconds + "\"" + lonDegrees;
        } catch (Exception e) {
            return ""+ String.format("%8.5f", latitude) + "  "
                    + String.format("%8.5f", longitude) ;
        }
    }


}
