package com.velaphimathebula.weather;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView dateTime, currentMin, currentMax, currentLocation, icon;
    MyLocation myLocation;
    Forecast forecast;
    String latitude, longitude;
    Typeface weatherFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        dateTime = (TextView) findViewById(R.id.tv_currentDate);
        currentMin = (TextView) findViewById(R.id.tv_currentMin);
        currentMax = (TextView) findViewById(R.id.tv_currentMax);
        currentLocation = (TextView) findViewById(R.id.tv_currentLoc);
        icon = (TextView) findViewById(R.id.tv_icon);
        icon.setTypeface(weatherFont);

        myLocation = new MyLocation(MainActivity.this);
        forecast = new Forecast();
        if (myLocation.canGetLocation) {
            latitude = String.valueOf(myLocation.getLatitude());
            longitude = String.valueOf(myLocation.getLongitude());
        }
        dateTime.setText("TODAY, " + forecast.getDate().toUpperCase());
        setLocation();

        Forecast.placeIdTask asyncTask = new Forecast.placeIdTask(new Forecast.AsyncResponse() {
            @Override
            public void processFinish(String weatherCity, String weatherMax, String weatherMin, String weatherIcon) {
                currentMax.setText(weatherMax);
                currentMin.setText(weatherMin);
                currentLocation.setText(weatherCity);
                icon.setText(Html.fromHtml(weatherIcon));

            }
        });
        asyncTask.execute(latitude, longitude);
    }


    private void setLocation() {
        latitude = String.valueOf(myLocation.getLatitude());
        longitude = String.valueOf(myLocation.getLongitude());
        forecast.setLongitude(longitude);
        forecast.setLatitude(latitude);
    }
}
