package com.example.us285899.Simpleweatherapp.ui;

/**
 * Created by US285899 on 3/10/2017.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.us285899.Simpleweatherapp.utils.Constants.PRES_UNITS;
import static com.example.us285899.Simpleweatherapp.utils.Constants.SPEED_UNITS;

public class ScreenFragment extends Fragment {
    private static final String LOGGER_CLASS = "SCREEN_FRAGMENT";

    TextView mCityView, mDescView, mHumidView, mPressureView, mWindView, mCurrentDateView,
            mTempMinView, mTempMaxView, mTempView,mToDayView, mDay2View, mDay3View, mDay4View, mDay5View;
    ImageView mIconView;
    HttpsServiceListener mListener;
    ArrayList<WeatherInfo> mWeatherData = new ArrayList<WeatherInfo>();
    private int mSelectedDay = 0;
    private double mLatitude, mLongitude;
    private GPSLocationManager mLocationListener;

    public static int getFeaturedWeatherIcon(String iconID) {
        switch (iconID) {
            case "01d":
                return R.drawable.ic_clear;
            case "01n":
                return R.drawable.ic_clear;
            case "02d":
            case "02n":
                return R.drawable.art_light_clouds;
            case "03d":
            case "03n":
                return R.drawable.art_clouds;
            case "04d":
            case "04n":
                return R.drawable.art_light_clouds;
            case "09d":
            case "09n":
                return R.drawable.art_rain;
            case "10d":
            case "10n":
                return R.drawable.art_light_rain;
            case "11d":
            case "11n":
                return R.drawable.art_storm;
            case "13d":
            case "13n":
                return R.drawable.art_snow;
            case "50d":
            case "50n":
                return R.drawable.art_fog;
            default:
                return R.drawable.art_clear;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationListener = new GPSLocationManager(getActivity());
        Location location = mLocationListener.getLocation();
        if (location != null) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
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
        mIconView = (ImageView) rootview.findViewById(R.id.iconView);
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
        mToDayView.setBackgroundResource(R.color.day_selected);
        mToDayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDay = 0;
                mToDayView.setBackgroundResource(R.color.day_selected);
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
                mDay2View.setBackgroundResource(R.color.day_selected);
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
                mDay3View.setBackgroundResource(R.color.day_selected);
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
                mDay4View.setBackgroundResource(R.color.day_selected);
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
                mDay5View.setBackgroundResource(R.color.day_selected);
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
            mTempView.setText((int) mWeatherData.get(mSelectedDay).mTempday + Constants.CELCIUS);
            mCityView.setText(mWeatherData.get(mSelectedDay).mCity);

            Date date = getDate(mWeatherData.get(mSelectedDay).mDateInMillis).getTime();
            String stringDate = DateFormat.getDateTimeInstance().format(date);
            mCurrentDateView.setText("" + stringDate);
            mTempMinView.setText(getString(R.string.min) + (int) (mWeatherData.get(mSelectedDay).mTempMin) + "" + Constants.CELCIUS);
            mTempMaxView.setText(getString(R.string.max) + (int) (mWeatherData.get(mSelectedDay).mTempMax) + "" + Constants.CELCIUS);

            mPressureView.setText(getString(R.string.Pressure) + mWeatherData.get(mSelectedDay).mPressure + "" + PRES_UNITS);
            mDescView.setText(mWeatherData.get(mSelectedDay).mMainDes);
            mWindView.setText(getString(R.string.windspeed) + mWeatherData.get(mSelectedDay).mWindSpeed + "" + SPEED_UNITS);
            mHumidView.setText(getString(R.string.humiditylabel) + mWeatherData.get(mSelectedDay).mHumidity + "" + Constants.HUM_UNITS);
            mIconView.setImageResource(getFeaturedWeatherIcon(mWeatherData.get(mSelectedDay).mCurIcon));
            mToDayView.setText((getString(R.string.today)) + System.getProperty(getString(R.string.line_sep)) + (int) mWeatherData.get(0).mTempMin + Constants.CELCIUS + "/" + (int) (mWeatherData.get(0).mTempMax) + Constants.CELCIUS);
            mDay2View.setText(getDay(getDate((mWeatherData.get(1).mDateInMillis)).get(Calendar.DAY_OF_WEEK)) + System.getProperty(getString(R.string.line_sep)) + (int) (mWeatherData.get(1).mTempMin) + Constants.CELCIUS + "/" + (int) mWeatherData.get(1).mTempMax + Constants.CELCIUS);
            mDay3View.setText(getDay(getDate((mWeatherData.get(2).mDateInMillis)).get(Calendar.DAY_OF_WEEK)) + System.getProperty(getString(R.string.line_sep)) + (int) (mWeatherData.get(2).mTempMin) + Constants.CELCIUS + "/" + (int) mWeatherData.get(2).mTempMax + Constants.CELCIUS);
            mDay4View.setText(getDay(getDate((mWeatherData.get(3).mDateInMillis)).get(Calendar.DAY_OF_WEEK)) + System.getProperty(getString(R.string.line_sep)) + (int) (mWeatherData.get(3).mTempMin) + Constants.CELCIUS + "/" + (int) mWeatherData.get(3).mTempMax + Constants.CELCIUS);
            mDay5View.setText(getDay(getDate((mWeatherData.get(4).mDateInMillis)).get(Calendar.DAY_OF_WEEK)) + System.getProperty(getString(R.string.line_sep)) + (int) (mWeatherData.get(4).mTempMin) + Constants.CELCIUS + "/" + (int) mWeatherData.get(4).mTempMax + Constants.CELCIUS);
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
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.add(Calendar.DAY_OF_MONTH, i);

                    WeatherInfo weatherInfoPerDay = new WeatherInfo();
                    JSONObject weatherObj = list.getJSONObject(i);
                    weatherInfoPerDay.mCity = city;
                    weatherInfoPerDay.mPressure = weatherObj.getDouble("pressure");
                    weatherInfoPerDay.mHumidity = weatherObj.getDouble("humidity");

                    weatherInfoPerDay.mDateInMillis = gc.getTimeInMillis();
                    JSONObject weatherTemp = weatherObj.getJSONObject("temp");
                    weatherInfoPerDay.mTempday = weatherTemp.getDouble("day");
                    weatherInfoPerDay.mTempEve = weatherTemp.getDouble("eve");
                    weatherInfoPerDay.mTempMorn = weatherTemp.getDouble("morn");
                    weatherInfoPerDay.mTempNight = weatherTemp.getDouble("night");
                    weatherInfoPerDay.mTempMin = weatherTemp.getDouble("min");
                    weatherInfoPerDay.mTempMax = weatherTemp.getDouble("max");
                    weatherInfoPerDay.mWindSpeed = weatherObj.getDouble("speed");
                    JSONObject weatherDetObj = weatherObj.getJSONArray("weather").getJSONObject(0);
                    weatherInfoPerDay.mDescription = weatherDetObj.getString("description");
                    weatherInfoPerDay.mMainDes = weatherDetObj.getString("main");
                    weatherInfoPerDay.mCurIcon = weatherDetObj.getString("icon");
                    weatherList.add(weatherInfoPerDay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return weatherList;
        }
        return weatherList;
    }

    public Calendar getDate(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }

    public String getDay(int daynum) {
        String day = "";
        switch (daynum) {
            case 1:
                day = "SUN";
                break;
            case 2:
                day = "MON";
                break;
            case 3:
                day = "TUE";
                break;
            case 4:
                day = "WED";
                break;
            case 5:
                day = "THU";
                break;
            case 6:
                day = "FRI";
                break;
            case 7:
                day = "SAT";
                break;
            default:
                day = "";
        }
        return day;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationListener.getLocationManager().removeUpdates(mLocationListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
