package com.betsy.chatsy.weatherapp.json;

import com.betsy.chatsy.weatherapp.date.DateParser;
import com.betsy.chatsy.weatherapp.enumaration.CallOrder;
import com.betsy.chatsy.weatherapp.model.Weather;
import com.betsy.chatsy.weatherapp.unitconversion.Conversion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class JsonManager {

    List<String> temp;


    public String getValueFromKey(JSONObject jsonResponse, String searchedKey) {

        String itemFound = "";

        Iterator<?> keys = jsonResponse.keys();

        while (keys.hasNext()) {

            String key = (String) keys.next();

            try {

                if (key.equals(searchedKey)) {

                    itemFound = jsonResponse.getString(searchedKey);

                    return itemFound;


                }

                if (jsonResponse.get(key) instanceof JSONObject) {

                    itemFound = getValueFromKey((JSONObject) jsonResponse.get(key), searchedKey);

                    if (!itemFound.equals("")) {
                        return itemFound;
                    }


                } else if (jsonResponse.get(key) instanceof JSONArray) {


                    JSONArray array = ((JSONArray) jsonResponse.get(key));

                    for (int i = 0; i < array.length(); i++) {

                        itemFound = getValueFromKey(array.getJSONObject(i), searchedKey);

                        if (!itemFound.equals("")) {
                            return itemFound;
                        }
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return itemFound;

    }

    public List<Weather> listIterator(JSONObject jsonResponse, Hashtable<Integer, String> dictionary) {

        List<List<String>> listOfLists = new ArrayList<>();
        List<Weather> forecast = new ArrayList<>();

        for (int i = 0; i < dictionary.size(); i++) {

            temp = new ArrayList<>();


            getListValuesFromKey(jsonResponse, dictionary.get(i));


            listOfLists.add(temp);

        }


        return toForecast(forecast, listOfLists);
    }


    private List<Weather> toForecast(List<Weather> forecast, List<List<String>> listOfLists) {

        for (int i = 0; i < listOfLists.get(0).size(); i++) {

            Weather weather = new Weather();


            weather.setHumidity(Integer.parseInt(listOfLists.get(CallOrder.HUMIDITY.getOrder()).get(i)));
            weather.setTemperature(Conversion.kelvinToCelsius(Double.parseDouble(listOfLists.get(CallOrder.TEMPERATURE.getOrder()).get(i))));
            weather.setDate(DateParser.stringToDate(listOfLists.get(CallOrder.DATE.getOrder()).get(i)));
            weather.setDescription(listOfLists.get(CallOrder.DESCRIPTION.getOrder()).get(i));

            forecast.add(weather);

        }

        return forecast;

    }

    private void getListValuesFromKey(JSONObject jsonResponse, String searchedKey) {

        String itemFound;


        Iterator<?> keys = jsonResponse.keys();

        while (keys.hasNext()) {

            String key = (String) keys.next();

            try {

                if (key.equals(searchedKey)) {


                    itemFound = jsonResponse.getString(searchedKey);
                    temp.add(itemFound);


                }

                if (jsonResponse.get(key) instanceof JSONObject) {

                    getListValuesFromKey((JSONObject) jsonResponse.get(key), searchedKey);


                } else if (jsonResponse.get(key) instanceof JSONArray) {

                    JSONArray array = ((JSONArray) jsonResponse.get(key));

                    for (int i = 0; i < array.length(); i++) {

                        getListValuesFromKey(array.getJSONObject(i), searchedKey);
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return;

    }

}


