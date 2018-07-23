package com.betsy.chatsy.weatherapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.betsy.chatsy.weatherapp.date.DateParser;
import com.betsy.chatsy.weatherapp.model.Weather;

import java.util.List;


public class WeatherAdapter extends ArrayAdapter<Weather> {

    private List<Weather> items;

    private LayoutInflater layoutInflater;

    public WeatherAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Weather> objects) {
        super(context, resource, objects);

        items = objects;
        layoutInflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.weather, parent, false);

            TextView tvDescription = convertView.findViewById(R.id.tvDescription);
            TextView tvHumidity = convertView.findViewById(R.id.tvHumidity);
            TextView tvTemperature = convertView.findViewById(R.id.tvTemperature);
            TextView tvDate = convertView.findViewById(R.id.tvDate);

            vh = new ViewHolder();


            vh.dateText = tvDate;
            vh.temperatureText = tvTemperature;
            vh.humidityText = tvHumidity;
            vh.descriptionText = tvDescription;


            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.position = position;
        Weather weather = items.get(vh.position);


        vh.dateText.setText(DateParser.dateToString(weather.getDate()));
        vh.temperatureText.setText(weather.toCelsiusString());
        vh.humidityText.setText(weather.toHumidityString());
        vh.descriptionText.setText(weather.getDescription());

        return convertView;

    }

    static class ViewHolder {
        private int position;
        private TextView dateText;
        private TextView temperatureText;
        private TextView humidityText;
        private TextView descriptionText;

    }
}
