package co.edu.unab.gti.thesistelediagclient.device;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.util.AppContext;
import co.edu.unab.gti.thesistelediagclient.util.ServiceTextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by user on 30/05/2016.
 */
public class HttpClientService {
    private final static AsyncHttpClient httpClient;
    private final static Gson gson;

    static {
        httpClient = new AsyncHttpClient();
        gson =new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        httpClient.setMaxRetriesAndTimeout(0,10);
    }

    public static void get(String url, TextHttpResponseHandler responseHandler){
        httpClient.get(url,responseHandler);
    }

    public static void get(String url, FileAsyncHttpResponseHandler responseHandler){
        httpClient.get(url, responseHandler);

    }

    public static void post(String url,RequestParams params, TextHttpResponseHandler responseHandler){

        httpClient.post(AppContext.getContext(),url,params,responseHandler);
    }

    public static void post(String url,Object pojoObject, TextHttpResponseHandler responseHandler){
        StringEntity jsonStringEntity = getJsonStringEntity(pojoObject);
        httpClient.post(AppContext.getContext(),url,jsonStringEntity,"application/json",responseHandler);
    }

    public static void servicePost(String url,Object pojoObject, ServiceTextHttpResponseHandler responseHandler){
        StringEntity jsonStringEntity = getJsonStringEntity(pojoObject);
        httpClient.post(AppContext.getContext(),url,jsonStringEntity,"application/json",responseHandler);
    }

    public static void post(String url, File file, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        try {
            params.put("profile_picture", file);
        } catch(FileNotFoundException e) {
            return;
        }

        httpClient.post(url,params,responseHandler);
    }


    public static Gson getGsonService(){
        return gson;
    }

    private static StringEntity getJsonStringEntity(Object postObject) {
        String jsonString = gson.toJson(postObject);
        StringEntity jsonStringEntity = null;
        try {
            jsonStringEntity =
                    new StringEntity(jsonString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (jsonStringEntity != null)
            jsonStringEntity.setContentType("application/json");

        return jsonStringEntity;
    }
}
