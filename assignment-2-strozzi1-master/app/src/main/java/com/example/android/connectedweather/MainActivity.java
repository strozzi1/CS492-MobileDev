package com.example.android.connectedweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.connectedweather.Utils.NetworkUtils;
import com.example.android.connectedweather.Utils.WeatherUtils;
import com.example.android.connectedweather.data.WeatherData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mForecastListRV;
    private ForecastAdapter mForecastAdapter;
    private Toast mToast;

    private ProgressBar mLoadingIndicatorPB;
    private TextView mErrorMessageTV;


    private static final String[] dummyForecastData = {
            "Sunny and Warm - 75F",
            "Partly Cloudy - 72F",
            "Mostly Sunny - 73F",
            "Partly Cloudy - 70F",
            "Occasional Showers - 65F",
            "Showers - 63F",
            "Occasional Showers - 64F",
            "Rainy - 62F",
            "Rainy - 61F",
            "Hurricane - 65F",
            "Windy and Clear - 70F",
            "Sunny and Warm - 77F",
            "Sunny and Warm - 81F"
    };

    private static final String[] dummyDetailedForecastData = {
            "Not a cloud in the sky today, with lows around 52F and highs near 75F.",
            "Clouds gathering in the late afternoon and slightly cooler than the day before, with lows around 49F and highs around 72F",
            "Scattered clouds all day with lows near 52F and highs near 73F",
            "Increasing cloudiness as the day goes on with some cooling; lows near 48F and highs near 70F",
            "Showers beginning in the morning and popping up intermittently throughout the day; lows near 46F and highs near 65F",
            "Showers scattered throughout the day, with lows near 46F and highs of 63F",
            "Showers increasing in intensity towards evening, with lows near 46F and highs near 64F",
            "Steady rain all day; lows near 47F and highs near 62F",
            "More steady rain, building in intensity towards evening; lows near 47F and highs near 61F",
            "Very, very strong winds and heavy rain; make sure you're wearing your raincoat today; lows near 50F and highs near 65F",
            "Rain ending in the very early morning, then clearing, with residual strong winds; lows near 61F and highs around 70F",
            "Beautiful day, with nothing but sunshine; lows near 55F and highs around 77F",
            "Another gorgeous day; lows near 56F and highs around 81F"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mForecastListRV = findViewById(R.id.rv_forecast_list);

        mForecastListRV.setLayoutManager(new LinearLayoutManager(this));
        mForecastListRV.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this);
        mForecastListRV.setAdapter(mForecastAdapter);

        mErrorMessageTV = findViewById(R.id.tv_error_message);
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);

        doWeatherSearch("dummy");
        /*mForecastAdapter.updateForecastData(
                new ArrayList<String>(Arrays.asList(dummyForecastData)),
                new ArrayList<String>(Arrays.asList(dummyDetailedForecastData))
        );*/
    }


    private void doWeatherSearch(String searchQuery) {
        String url = WeatherUtils.buildWeatherURL("q");
        Log.d(TAG, "querying url: " + url);
        new WeatherTask().execute(url);
    }




    @Override
    public void onForecastItemClick(WeatherData weather) {

        //mToast = Toast.makeText(this, weather.weather.get(0).main + weather.main.temp, Toast.LENGTH_LONG);

        Intent weatherDetailActivityIntent = new Intent(this, WeatherDetailActivity.class);
        weatherDetailActivityIntent.putExtra(WeatherDetailActivity.EXTRA_WEATHER_REPO, weather);
        startActivity(weatherDetailActivityIntent);
    }
/*******************************************

********************************************/
    public class WeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            //String url = WeatherUtils.buildWeatherURL("q");
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);

            if (s != null) {
                mErrorMessageTV.setVisibility(View.INVISIBLE);
                //mSearchResultsRV.setVisibility(View.VISIBLE);
                mForecastListRV.setVisibility(View.VISIBLE);

                ArrayList<WeatherData> searchResultsList = WeatherUtils.parseWeatherResults(s);         //here

                //mGitHubSearchAdapter.updateSearchResults(searchResultsList);
                mForecastAdapter.updateForecastData(searchResultsList);

            } else {
                mErrorMessageTV.setVisibility(View.VISIBLE);
                //mSearchResultsRV.setVisibility(View.INVISIBLE);
                mForecastListRV.setVisibility(View.INVISIBLE);
            }

        }
    }

}
