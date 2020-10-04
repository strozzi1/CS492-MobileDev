package com.example.android.lifecycleweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.lifecycleweather.data.ForecastItem;
import com.example.android.lifecycleweather.data.Status;
import com.example.android.lifecycleweather.data.WeatherPreferences;
import com.example.android.lifecycleweather.utils.NetworkUtils;
import com.example.android.lifecycleweather.utils.OpenWeatherMapUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String WEATHER_RESULTS_LIST_KEY = "weatherResultsList";

    private TextView mForecastLocationTV;
    private RecyclerView mForecastItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private ForecastAdapter mForecastAdapter;

    private WeatherSearchViewModel mWeatherSearchViewModel;

    private ArrayList<ForecastItem> mWeatherResultsList; //from class

    String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherResultsList = null;
        Log.d(TAG, "onCreate()");
        // Remove shadow under action bar.
        getSupportActionBar().setElevation(0);

        mForecastLocationTV = findViewById(R.id.tv_forecast_location);
        mForecastLocationTV.setText(WeatherPreferences.getDefaultForecastLocation());  //update to current location.




        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mForecastItemsRV = findViewById(R.id.rv_forecast_items);

        mForecastAdapter = new ForecastAdapter(this);
        mForecastItemsRV.setAdapter(mForecastAdapter);
        mForecastItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mForecastItemsRV.setHasFixedSize(true);

        mWeatherSearchViewModel = new ViewModelProvider(this).get(WeatherSearchViewModel.class);

        mWeatherSearchViewModel.getWeatherResults().observe(this, new Observer<List<ForecastItem>>() {
            @Override
            public void onChanged(List<ForecastItem> forecastItems) {
                mForecastAdapter.updateForecastItems(forecastItems);
            }
        });

        /*if(savedInstanceState != null && savedInstanceState.containsKey(WEATHER_RESULTS_LIST_KEY)) {
            mWeatherResultsList = (ArrayList)savedInstanceState.getSerializable(WEATHER_RESULTS_LIST_KEY);
            mForecastAdapter.updateForecastItems(mWeatherResultsList);
        }*/

        mWeatherSearchViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.ERROR){
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                }
                else if (status == Status.SUCCESS){
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                    mForecastItemsRV.setVisibility(View.VISIBLE);
                }
                else{
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
                    mForecastItemsRV.setVisibility(View.INVISIBLE);
                }

            }
        });

        loadForecast();
    }

    @Override
    public void onForecastItemClick(ForecastItem forecastItem) {
        Intent intent = new Intent(this, ForecastItemDetailActivity.class);
        intent.putExtra(OpenWeatherMapUtils.EXTRA_FORECAST_ITEM, forecastItem);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_location:
                showForecastLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadForecast() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String units = preferences.getString(
                getString(R.string.pref_temperature_units_key),
                getString(R.string.pref_temperature_units_default)
        );

        String location = preferences.getString(
                getString(R.string.pref_weather_location_key), WeatherPreferences.getDefaultForecastLocation()
        );

        //WeatherPreferences.setTempuratureUnitsAbbr(units);

        mWeatherSearchViewModel.loadWeatherResults(location, units);
        mForecastLocationTV.setText(location);
    }

    public void showForecastLocation() {
        Uri geoUri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", WeatherPreferences.getDefaultForecastLocation())
                .build();
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
        if (mWeatherResultsList != null){
            outState.putSerializable(WEATHER_RESULTS_LIST_KEY, mWeatherResultsList);
        }
    }


}
