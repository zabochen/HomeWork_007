package com.tzabochen.homework_007;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherHttp
{
    // CONFIG
    private static final String START_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";

    // List of city ID - http://bulk.openweathermap.org/sample/
    private static final String CITY = "Cherkasy";

    // Kelvin is used by default, Fahrenheit use units=imperial, Celsius use units=metric
    private static final String UNIT_FORMAT = "&units=metric";

    private static final String API_ID = "&appid=03385a242c9e1c316b7c9fd1e70679c8";

    private static final String BASE_URL = START_URL + CITY + UNIT_FORMAT + API_ID;


    public static String getWeatherDate()
    {
        StringBuffer stringBuffer = new StringBuffer("");

        try
        {
            URL baseUrl = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) baseUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String getDataString = "";

            while((getDataString = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(getDataString);
            }

            inputStream.close();
            connection.disconnect();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }
}
