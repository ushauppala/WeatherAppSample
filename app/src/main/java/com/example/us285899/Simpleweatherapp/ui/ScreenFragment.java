package com.example.us285899.Simpleweatherapp.ui;

/**
 * Created by US285899 on 3/10/2017.
 */

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.us285899.Simpleweatherapp.R;
import com.example.us285899.Simpleweatherapp.model.WeatherInfo;
import com.example.us285899.Simpleweatherapp.service.HttpsServiceListener;
import com.example.us285899.Simpleweatherapp.service.HttpsServiceLoader;
import com.example.us285899.Simpleweatherapp.utils.Constants;
import com.example.us285899.Simpleweatherapp.utils.GPSLocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.us285899.Simpleweatherapp.utils.Constants.PRES_UNITS;
import static com.example.us285899.Simpleweatherapp.utils.Constants.SPEED_UNITS;

public class ScreenFragment extends Fragment {
    private static final String LOGGER_CLASS = "SCREEN_FRAGMENT";

    TextView mCityView, mDescView, mHumidView, mPressureView, mWindView, mCurrentDateView,
            mTempMinView, mTempMaxView, mTempView,mToDayView, mDay2View, mDay3View, mDay4View, mDay5View;
    HttpsServiceListener mListener;
    ArrayList<WeatherInfo> mWeatherData = new ArrayList<WeatherInfo>();
    private int mSelectedDay = 0;
    private double mLatitude, mLongitude;
    private Location mLocation = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = new GPSLocationManager(getActivity()).getLocation();
        if(mLocation != null) {
            mLatitude =mLocation.getLatitude();
            mLongitude =mLocation.getLongitude();
        }
        mListener = new HttpsServiceListener() {
            @Override
            public void onResponse(String httpResponse) {
                try {
                    JSONObject responseJson = new JSONObject(httpResponse);
                    mWeatherData = parseWeatherData(responseJson);
                    updateViews();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            String url = String.format(Constants.OPEN_WEATHERMAP_URL, mLatitude, mLongitude);
            HttpsServiceLoader getWeatherDetails = new HttpsServiceLoader(url, mListener, getActivity());
        } catch(Exception e) {
            Log.d(LOGGER_CLASS, "Exception in Loader"+e);
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_screen, container, false);

        mCityView = (TextView) rootview.findViewById(R.id.cityView);
        mCurrentDateView = (TextView) rootview.findViewById(R.id.dateView);
        mTempView = (TextView) rootview.findViewById(R.id.tempView);
        mTempMinView = (TextView) rootview.findViewById(R.id.tempMinView);
        mTempMaxView = (TextView) rootview.findViewById(R.id.tempMaxView);

        mPressureView = (TextView) rootview.findViewById(R.id.presView);
        mDescView = (TextView) rootview.findViewById(R.id.descView);
        mWindView = (TextView) rootview.findViewById(R.id.windView);
        mHumidView = (TextView) rootview.findViewById(R.id.humView);

        mToDayView = (TextView) rootview.findViewById(R.id.todayView);
        mToDayView.setBackgroundResource(R.color.wallet_highlighted_text_holo_dark);
        mToDayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDay = 1;
                mToDayView.setBackgroundResource(R.color.wallet_highlighted_text_holo_dark);
                mDay2View.setBackgroundResource(R.color.black_overlay);
                mDay3View.setBackgroundResource(R.color.black_overlay);
                mDay4View.setBackgroundResource(R.color.black_overlay);
                mDay5View.setBackgroundResource(R.color.black_overlay);
                updateViews();
            }
        });

        mDay2View = (TextView) rootview.findViewById(R.id.day2View);
        mDay2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDay = 1;
                mDay2View.setBackgroundResource(R.color.wallet_highlighted_text_holo_dark);
                mToDayView.setBackgroundResource(R.color.black_overlay);
                mDay3View.setBackgroundResource(R.color.black_overlay);
                mDay5View.setBackgroundResource(R.color.black_overlay);
                mDay4View.setBackgroundResource(R.color.black_overlay);
                updateViews();

            }
        });
        mDay3View = (TextView) rootview.findViewById(R.id.day3View);
        mDay3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDay = 2;
                mDay3View.setBackgroundResource(R.color.wallet_highlighted_text_holo_dark);
                mToDayView.setBackgroundResource(R.color.black_overlay);
                mDay2View.setBackgroundResource(R.color.black_overlay);
                mDay5View.setBackgroundResource(R.color.black_overlay);
                mDay4View.setBackgroundResource(R.color.black_overlay);
                updateViews();
            }
        });
        mDay4View = (TextView) rootview.findViewById(R.id.day4View);
        mDay4View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDay = 3;
                mDay4View.setBackgroundResource(R.color.wallet_highlighted_text_holo_dark);
                mToDayView.setBackgroundResource(R.color.black_overlay);
                mDay2View.setBackgroundResource(R.color.black_overlay);
                mDay3View.setBackgroundResource(R.color.black_overlay);
                mDay5View.setBackgroundResource(R.color.black_overlay);
                updateViews();
            }
        });
        mDay5View = (TextView) rootview.findViewById(R.id.day5View);
        mDay5View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDay = 4;
                mDay5View.setBackgroundResource(R.color.wallet_highlighted_text_holo_dark);
                mToDayView.setBackgroundResource(R.color.black_overlay);
                mDay2View.setBackgroundResource(R.color.black_overlay);
                mDay3View.setBackgroundResource(R.color.black_overlay);
                mDay4View.setBackgroundResource(R.color.black_overlay);

                updateViews();
            }
        });
        return rootview;
    }

    private void updateViews(){
        if(mWeatherData.size() > 0) {
            mTempView.setText(getString(R.string.temp) + mWeatherData.get(mSelectedDay).mTempday+ Constants.CELCIUS);
            mCityView.setText(mWeatherData.get(mSelectedDay).mCity);
            mCurrentDateView.setText("updated on:" + mWeatherData.get(mSelectedDay).mDay);
            mTempMinView.setText("Min: " + mWeatherData.get(mSelectedDay).mTempMin+ Constants.CELCIUS);
            mTempMaxView.setText("Max: " + mWeatherData.get(mSelectedDay).mTempMax+ Constants.CELCIUS);

            mPressureView.setText(getString(R.string.Pressure) + mWeatherData.get(mSelectedDay).mPressure+PRES_UNITS);
            mDescView.setText(mWeatherData.get(mSelectedDay).mMainDes);
            mWindView.setText(getString(R.string.windspeed) + mWeatherData.get(mSelectedDay).mWindSpeed+SPEED_UNITS);
            mHumidView.setText(getString(R.string.humiditylabel) + mWeatherData.get(mSelectedDay).mHumidity+Constants.HUM_UNITS);

            mToDayView.setText(mWeatherData.get(0).mDayOfWeek + System.getProperty (getString(R.string.line_sep)) + mWeatherData.get(0).mTempMin +Constants.CELCIUS+ "/" + mWeatherData.get(0).mTempMax+Constants.CELCIUS);
            mDay2View.setText(mWeatherData.get(1).mDayOfWeek +  System.getProperty (getString(R.string.line_sep))  + mWeatherData.get(1).mTempMin +Constants.CELCIUS+ "/" + mWeatherData.get(1).mTempMax+Constants.CELCIUS);
            mDay3View.setText(mWeatherData.get(2).mDayOfWeek +  System.getProperty (getString(R.string.line_sep))  + mWeatherData.get(2).mTempMin +Constants.CELCIUS+ "/" + mWeatherData.get(2).mTempMax+Constants.CELCIUS);
            mDay4View.setText(mWeatherData.get(3).mDayOfWeek +  System.getProperty (getString(R.string.line_sep))  + mWeatherData.get(3).mTempMin +Constants.CELCIUS+ "/" + mWeatherData.get(3).mTempMax+Constants.CELCIUS);
            mDay5View.setText(mWeatherData.get(4).mDayOfWeek +  System.getProperty (getString(R.string.line_sep))  + mWeatherData.get(4).mTempMin +Constants.CELCIUS+ "/" + mWeatherData.get(4).mTempMax+Constants.CELCIUS);

        }
    }

    public ArrayList<WeatherInfo> parseWeatherData(JSONObject jsonObject) {
        ArrayList<WeatherInfo> weatherList = new ArrayList<WeatherInfo>();
        if (jsonObject != null) {
            JSONArray list = null;
            try {
                DateFormat df = DateFormat.getDateTimeInstance();

                String city = jsonObject.getJSONObject("city").getString("name");
                list = jsonObject.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    WeatherInfo weatherInfoPerDay = new WeatherInfo();
                    JSONObject weatherObj = list.getJSONObject(i);
                    weatherInfoPerDay.mCity = city;
                    weatherInfoPerDay.mPressure = (int)weatherObj.getDouble("pressure");
                    weatherInfoPerDay.mHumidity = (int)weatherObj.getDouble("humidity");
                    Date date=new Date(weatherObj.getLong("dt")*1000);

                   weatherInfoPerDay.mDay =date;
                   SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
                    weatherInfoPerDay.mDayOfWeek = sdf_.format(date);

                    JSONObject weatherTemp = weatherObj.getJSONObject("temp");
                    weatherInfoPerDay.mTempday = (int) weatherTemp.getDouble("day");
                    weatherInfoPerDay.mTempEve = (int)weatherTemp.getDouble("eve");
                    weatherInfoPerDay.mTempMorn = (int)weatherTemp.getDouble("morn");
                    weatherInfoPerDay.mTempNight = (int)weatherTemp.getDouble("night");
                    weatherInfoPerDay.mTempMin = (int)weatherTemp.getDouble("min");
                    weatherInfoPerDay.mTempMax = (int)weatherTemp.getDouble("max");
                    weatherInfoPerDay.mWindSpeed =  (int)weatherObj.getDouble("speed");
                    JSONObject weatherDetObj = weatherObj.getJSONArray("weather").getJSONObject(0);
                    weatherInfoPerDay.mDescription = weatherDetObj.getString("description");
                    weatherInfoPerDay.mMainDes = weatherDetObj.getString("main");

                    weatherInfoPerDay.mCurIcon = ""+weatherDetObj.getInt("id");
                    weatherList.add(weatherInfoPerDay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return weatherList;
        }
        return weatherList;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
