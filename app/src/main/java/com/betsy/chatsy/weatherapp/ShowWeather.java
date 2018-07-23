package com.betsy.chatsy.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.betsy.chatsy.weatherapp.enumaration.CallOrder;
import com.betsy.chatsy.weatherapp.json.JsonManager;
import com.betsy.chatsy.weatherapp.model.Weather;
import com.betsy.chatsy.weatherapp.security.DeveloperKey;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class ShowWeather extends AppCompatActivity {

    private static final String DESCRIPTION_QUERY = "description";
    private static final String DATE_QUERY = "dt_txt";
    private static final String HUMIDITY_QUERY = "humidity";
    private static final String TEMPERATURE_QUERY = "temp";
    private static final String ERROR_MESSAGE = "Something went wrong.Please try again";
    List<Weather> forecast;

    private TextView tvDescription;
    private TextView tvTemperature;
    private TextView tvDate;
    private TextView tvHumidity;

    private ListView lvWeathers;

    private WeatherAdapter taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        String cityName = intent.getStringExtra(getString(R.string.cityNameIntent));

        initialize();


        getValuesFromKey(cityName);


    }

    private void initialize() {
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvDescription = findViewById(R.id.tvDescription);
        tvDate = findViewById(R.id.tvDate);

        lvWeathers = findViewById(R.id.lvWeathers);
    }

    private void setAdapter(List<Weather> forecast) {

        taskAdapter = new WeatherAdapter(this, R.layout.weather, forecast);
        lvWeathers.setAdapter(taskAdapter);
    }

    private Hashtable<Integer, String> initDictionary() {

        Hashtable<Integer, String> dictionarySearch = new Hashtable<>();

        dictionarySearch.put(CallOrder.DESCRIPTION.getOrder(), DESCRIPTION_QUERY);
        dictionarySearch.put(CallOrder.DATE.getOrder(), DATE_QUERY);
        dictionarySearch.put(CallOrder.HUMIDITY.getOrder(), HUMIDITY_QUERY);
        dictionarySearch.put(CallOrder.TEMPERATURE.getOrder(), TEMPERATURE_QUERY);

        return dictionarySearch;

    }


    private void getValuesFromKey(final String cityName) {

        RequestQueue requestQueue = Volley.newRequestQueue(ShowWeather.this);


        String url = getString(R.string.forecastWeather) + cityName + getString(R.string.query) + DeveloperKey.WEATHER_KEY_API;


        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        forecast = new ArrayList<>();
                        JsonManager jsonManager = new JsonManager();

                        Hashtable<Integer, String> hashMap = initDictionary();

                        forecast = jsonManager.listIterator(response, hashMap);

                        setAdapter(forecast);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowWeather.this, ERROR_MESSAGE, Toast.LENGTH_LONG).show();

                    }
                }

        );

        requestQueue.add(objectRequest);

    }


}
