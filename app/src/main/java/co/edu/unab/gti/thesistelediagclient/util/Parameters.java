package co.edu.unab.gti.thesistelediagclient.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 30/05/2016.
 */
public class Parameters {


    public static final int NO_SENSOR = -1;
    public static final int PULSIOXIMETER_SENSOR = 0;
    public static final int ELECTROCARDIOGRAM_SENSOR = 1;
    public static final int TEMPERATURE_SENSOR = 2;
    public static final int BLOOD_PRESSURE_SENSOR = 3;
    public static final int AIRFLOW_SENSOR = 4;

    public static final Map<Integer,String> sensorTypeMap =
            new HashMap<>();

    public static final int MEASURE_FRAGMENT_MODE_CAPTURE = 0;
    public static final int MEASURE_FRAGMENT_MODE_DISPLAY = 1;

    static {
        sensorTypeMap.put(PULSIOXIMETER_SENSOR,"pulsioximeter");
        sensorTypeMap.put(ELECTROCARDIOGRAM_SENSOR,"electrocardiogram");
        sensorTypeMap.put(TEMPERATURE_SENSOR,"temperature");
        sensorTypeMap.put(BLOOD_PRESSURE_SENSOR,"blood_pressure");
        sensorTypeMap.put(AIRFLOW_SENSOR,"airflow");
    }


   public static int getSensorId(String sensorName){
       for (Map.Entry<Integer, String> entry : sensorTypeMap.entrySet()){
           if(sensorName.equals(entry.getValue())){
               return entry.getKey();
           }
       }

       return -1;
    }


    public static String getSensorName(int sensorType){
       return sensorTypeMap.get(sensorType);
    }

    private static final String PROTOCOL = "http://";
    private static final String SERVER_ADDRESS = "192.168.1.101:44795";
    private static final String API_ENDPOINT = "/telediag/api/v1/";

    public static final String FAMILY_ENDPOINT = PROTOCOL+SERVER_ADDRESS+API_ENDPOINT+"family";
    public static final String PERSON_ENDPOINT = PROTOCOL+SERVER_ADDRESS+API_ENDPOINT+"person";
    public static final String MEASURE_ENDPOINT = PROTOCOL+SERVER_ADDRESS+API_ENDPOINT+"measure";
    public static final String LOGIN_ENDPOINT = PROTOCOL+SERVER_ADDRESS+API_ENDPOINT+"login";
    public static final String REGISTRY_ENDPOINT = PROTOCOL+SERVER_ADDRESS+API_ENDPOINT+"registry";
}
