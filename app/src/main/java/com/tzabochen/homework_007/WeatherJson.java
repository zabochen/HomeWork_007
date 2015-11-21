package com.tzabochen.homework_007;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherJson
{
    public static ArrayList<HashMap<String, String>> getData(String weatherData)
    {
        try
        {
            ArrayList<HashMap<String, String>> date = new ArrayList<>();

            JSONObject rootObject = new JSONObject(weatherData);
            JSONArray rootArray = rootObject.getJSONArray("list");

            for (int i = 0; i < rootArray.length(); i++)
            {
                HashMap<String, String> buffer = new HashMap<>();

                JSONObject selectObject = rootArray.getJSONObject(i);
                JSONObject objectMain = selectObject.getJSONObject("main");
                JSONObject objectClouds = selectObject.getJSONObject("clouds");
                JSONObject objectWind = selectObject.getJSONObject("wind");

                // DATE
                String dt_txt = selectObject.getString("dt_txt");
                WeatherDate weatherDate = new WeatherDate(dt_txt);
                buffer.put("day_of_week", weatherDate.getDayOfWeek());
                buffer.put("day_of_month", weatherDate.getDayOfMonth());
                buffer.put("month", weatherDate.getMonth());
                buffer.put("time", weatherDate.getTime());

                // TEMPERATURE
                Integer temp = objectMain.getInt("temp");
                buffer.put("temp", String.valueOf(temp) + "°C");
                // TEMPERATURE MIN
                Integer temp_min = objectMain.getInt("temp_min");
                buffer.put("temp_min", String.valueOf(temp_min) + "°C");
                // TEMPERATURE MAX
                Integer temp_max = objectMain.getInt("temp_max");
                buffer.put("temp_max", String.valueOf(temp_max) + "°C");

                // PRESSURE
                Double pressure = objectMain.getDouble("pressure");
                buffer.put("pressure", String.valueOf(pressure) + " hpa");

                // HUMIDITY
                Integer humidity = objectMain.getInt("humidity");
                buffer.put("humidity", String.valueOf(humidity) + " %");

                // WEATHER ARRAY
                JSONArray weatherArray = selectObject.getJSONArray("weather");
                JSONObject weatherArrayObject = weatherArray.getJSONObject(0);
                // MAIN
                String weather_main = weatherArrayObject.getString("main");
                buffer.put("weather_main", String.valueOf(weather_main));
                // DESCRIPTION
                String weather_description = weatherArrayObject.getString("description");
                buffer.put("weather_description", String.valueOf(weather_description));
                // ICON
                String weather_icon = weatherArrayObject.getString("icon");
                buffer.put("weather_icon", String.valueOf(weather_icon));

                // CLOUDS
                Integer clouds_all = objectClouds.getInt("all");
                buffer.put("clouds_all", String.valueOf(clouds_all) + " %");

                // WIND
                Double wind_speed = objectWind.getDouble("speed");
                buffer.put("wind_speed", String.valueOf(wind_speed) + " meter/sec");

                // COMMIT
                date.add(buffer);
            }
            return date;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
