package com.tzabochen.homework_007;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FragmentContent extends Fragment
{
    public static FragmentContent newInstance(int itemPosition)
    {
        Bundle args = new Bundle();
        args.putInt("itemPosition", itemPosition);
        FragmentContent fragmentContent = new FragmentContent();
        fragmentContent.setArguments(args);
        return fragmentContent;
    }

    public int getPosition()
    {
        return getArguments().getInt("itemPosition", 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentContentView = inflater.inflate(R.layout.fragment_content, container, false);

        if (AppMain.data != null)
        {
            // DAY & MONTH
            TextView dayMonth = (TextView) fragmentContentView.findViewById(R.id.item_day_month);
            dayMonth.setText(AppMain.data.get(getPosition()).get("day_of_month") + " "
                            + AppMain.data.get(getPosition()).get("month") + ", "
                            + AppMain.data.get(getPosition()).get("day_of_week"));

            // ICON
            ImageView imageView = (ImageView) fragmentContentView.findViewById(R.id.item_icon);
            String icon = AppMain.data.get(getPosition()).get("weather_icon");
            String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
            Picasso.with(getActivity()).load(iconUrl).into(imageView);

            // ICON DESCRIPTION
            TextView iconDescription = (TextView) fragmentContentView.findViewById(R.id.item_icon_description);
            iconDescription.setText(AppMain.data.get(getPosition()).get("time") + ", "
                                    + AppMain.data.get(getPosition()).get("weather_description"));

            // TEMP MIN
            TextView tempMin = (TextView) fragmentContentView.findViewById(R.id.item_temp_min);
            tempMin.setText("Temp Min: " + AppMain.data.get(getPosition()).get("temp_min"));

            // TEMP MAX
            TextView tempMax = (TextView) fragmentContentView.findViewById(R.id.item_temp_max);
            tempMax.setText("Temp Max: " + AppMain.data.get(getPosition()).get("temp_max"));

            // WIND SPEED
            TextView windSpeed = (TextView) fragmentContentView.findViewById(R.id.item_wind_speed);
            windSpeed.setText("Wind Speed: " + AppMain.data.get(getPosition()).get("wind_speed"));

            // CLOUDS
            TextView cloudsAll = (TextView) fragmentContentView.findViewById(R.id.item_clouds_all);
            cloudsAll.setText("Cloudiness: " + AppMain.data.get(getPosition()).get("clouds_all"));

            // HUMIDITY
            TextView humidity = (TextView) fragmentContentView.findViewById(R.id.item_humidity);
            humidity.setText("Humidity: " + AppMain.data.get(getPosition()).get("humidity"));

            // PRESSURE
            TextView pressure = (TextView) fragmentContentView.findViewById(R.id.item_pressure);
            pressure.setText("Pressure: " + AppMain.data.get(getPosition()).get("pressure"));
        }

        return fragmentContentView;
    }
}