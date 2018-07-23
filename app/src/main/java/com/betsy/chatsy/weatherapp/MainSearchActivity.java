package com.betsy.chatsy.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.betsy.chatsy.weatherapp.json.JsonManager;
import com.betsy.chatsy.weatherapp.network.Connection;
import com.betsy.chatsy.weatherapp.notificationsystem.Dialogs;
import com.betsy.chatsy.weatherapp.security.DeveloperKey;

import org.json.JSONObject;


public class MainSearchActivity extends AppCompatActivity {

    Button btnWeather;
    Button btnYoutube;
    EditText etCity;

    private static final String ERROR_MESSAGE = "You entered the wrong city name. Please try again";
    private static final String QUERY_DESCRIPTION = "description";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Connection.isConnected(MainSearchActivity.this)) {
            noConnectionDialog();
        }

        setContentView(R.layout.activity_search);


        initialize();

        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Connection.isConnected(MainSearchActivity.this)) {
                    noConnectionDialog();
                } else {
                    checkIfCityExists(YouTubeSearch.class, getString(R.string.youtubeSearchIntent));
                }
            }
        });


        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Connection.isConnected(MainSearchActivity.this)) {
                    noConnectionDialog();
                } else {
                    checkIfCityExists(ShowWeather.class, getString(R.string.cityNameIntent));
                }
            }
        });


    }

    private void noConnectionDialog() {

        Dialogs.alertDialog(
                MainSearchActivity.this,
                getString(R.string.noInternetConnectionTitle),
                getString(R.string.noInternetConnectionMessage))
                .show();


    }

    private void checkIfCityExists(final Class<?> classCalling, final String key) {

        RequestQueue requestQueue = Volley.newRequestQueue(MainSearchActivity.this);


        String url = getString(R.string.todayWeather) + etCity.getText().toString() + getString(R.string.query) + DeveloperKey.WEATHER_KEY_API;


        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String value;
                        JsonManager jsonManager = new JsonManager();

                        if (key.equals(getString(R.string.youtubeSearchIntent))) {
                            value = String.format("%s %s", jsonManager.getValueFromKey(response, QUERY_DESCRIPTION), etCity.getText().toString());
                        } else {
                            value = etCity.getText().toString();
                        }

                        intentHandler(classCalling, key, value);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainSearchActivity.this, ERROR_MESSAGE, Toast.LENGTH_LONG).show();

                    }
                }

        );

        requestQueue.add(objectRequest);


    }

    private void intentHandler(Class<?> newIntentClass, String key, String value) {


        Intent intent = new Intent(MainSearchActivity.this, newIntentClass);
        intent.putExtra(key, value);

        startActivity(intent);


    }

    private void initialize() {

        btnWeather = findViewById(R.id.btnWeather);
        btnYoutube = findViewById(R.id.btnYoutube);
        etCity = findViewById(R.id.etCity);

    }
}
