package com.example.android.connectedweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.connectedweather.data.WeatherData;

import java.util.List;

public class WeatherDetailActivity extends AppCompatActivity {
    public static final String EXTRA_WEATHER_REPO = "WeatherData";

    private WeatherData mRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra(EXTRA_WEATHER_REPO)) {
            mRepo = (WeatherData)intent.getSerializableExtra(EXTRA_WEATHER_REPO);

            TextView repoNameTV = findViewById(R.id.tv_repo_name);
            repoNameTV.setText(mRepo.main.temp);

            TextView repoDescriptionTV = findViewById(R.id.tv_repo_description);
            repoDescriptionTV.setText(mRepo.weather.get(0).description);

        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_view_repo:
                viewLocationOnWeb();
                return true;
            case R.id.action_share:
                shareWeather();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_detail, menu);
        return true;
    }

    private void viewLocationOnWeb(){
        if(mRepo != null){
            //Uri repoUri = Uri.parse(mRepo.html_url);
            Uri repoUri = Uri.parse("https://www.google.com/maps/place/Seaside,+OR+97138/@45.9819496,-123.9621425,13z/data=!3m1!4b1!4m5!3m4!1s0x5493612cb60e6667:0x8d640cf4541bfd22!8m2!3d45.9931636!4d-123.9226385");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, repoUri);

            PackageManager pm = getPackageManager();

            List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if(activities.size()>0){
                startActivity(webIntent);
            }
        }

    }


    private void shareWeather() {
        if(mRepo!=null){
            String shareText = "The temperature in Seaside is " + mRepo.main.temp + " and it is " + mRepo.weather.get(0).main;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(shareIntent, null);
            startActivity(chooserIntent);
        }
    }
}
