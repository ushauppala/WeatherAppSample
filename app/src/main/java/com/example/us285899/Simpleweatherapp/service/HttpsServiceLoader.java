package com.example.us285899.Simpleweatherapp.service;

/**
 * Created by US285899 on 3/9/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpsServiceLoader {

    WeakReference<Activity> mActivityContext;
    GetResponse task;
    private static final String LOGGER_CLASS = "LOADER";
    public HttpsServiceLoader(String serviceUrl, HttpsServiceListener _listener,
                              Activity act) {
        mActivityContext =  new WeakReference<Activity>(act);
        task = new GetResponse(_listener, act);
        task.execute(serviceUrl);
    }
    private static class GetResponse extends AsyncTask<String, Void, String> {

        WeakReference<HttpsServiceListener> mHttpResponseListener;
        WeakReference<Activity> mActivityContext;
        ProgressDialog asyncDialog;
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                asyncDialog.setMessage("Loading");
                asyncDialog.setCancelable(false);
                asyncDialog.show();
            } catch (Exception exc) {
                Log.d(LOGGER_CLASS, "Exc in onPreExecute"+exc);
            }
        }

        GetResponse(HttpsServiceListener _listener,
                           Activity _mContext) {
            mHttpResponseListener = new WeakReference<HttpsServiceListener>(_listener);
            mActivityContext = new WeakReference<Activity>(_mContext);
            asyncDialog = new ProgressDialog(_mContext);
        }

        @Override
        public String doInBackground(String... value) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(value[0]);
                Log.d("SERVICE LOADER", "URL =="+url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setConnectTimeout(20 * 1000);
                urlConnection.setReadTimeout(20 * 1000);

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                }
            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            if (asyncDialog.isShowing())
                asyncDialog.dismiss();

            if (result != null) {
                Log.d(LOGGER_CLASS, "Response="+result);
                if (mHttpResponseListener.get() != null) {
                    mHttpResponseListener.get().onResponse(result);
                }
            }
        }
    }


}

