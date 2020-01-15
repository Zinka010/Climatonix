/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix.API;

import climatonix.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author justi
 */
public class APIUtils {

    // Ceci est une class statique et ne devrait pas être instantié
    private APIUtils() {

    }

    /**
     * Make a GET request to the API
     *
     * @param requestType The type of data (weather, etc.)
     * @param cityName The name of the desired city
     * @return The JSON data in the JSONObject
     * @throws MalformedURLException The url was malformed and returned an error
     * @throws ProtocolException Invalid protocol
     * @throws IOException Error with reading the page
     */
    public static JSONObject request(String requestType, String cityName) throws MalformedURLException, ProtocolException, IOException {

        // The paramaters of the GET request
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", cityName);
        parameters.put("appid", Constants.getInstance().API_KEY);

        // Create the URL to the API
        URL url = new URL(Constants.getInstance().BASE_URL + requestType + "?" + ParamaterStringBuilder.getParamsString(parameters));

        // Open the connection
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Save the found JSON data
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        // Close all reading utilities
        in.close();
        con.disconnect();

        // Retourner the data in the JSONObject format
        return new JSONObject(content.toString());
    }

    /**
     * Convert Kelvin to Celsius
     *
     * @param kelvin The temperature in Kelvin
     * @return La temperature in degrees Celsius
     */
    private static double kelvinToCelcius(double kelvin) {
        return kelvin - 273.15;
    }

    /**
     * Return the weather of the JSON obtained from the "weather" request
     *
     * @param jSONObject The JSON data
     * @return The temperature in Celsius
     * @throws JSONException The JSON is invalid
     */
    public static Double getTempature(JSONObject jSONObject) throws JSONException {

        // Get the data under "main"
        JSONObject main = jSONObject.getJSONObject("main");

        // Get the tempature
        double temperature = main.getDouble("temp");

        // Convert from kelvin to celcius
        return kelvinToCelcius(temperature);

    }

    private static double msToKmH(double ms) {
        return ms * 3.6;
    }

    /**
     * Return the wind speed of the JSON obtained from the "weather" request
     *
     * @param jSONObject The JSON data from the weather request
     * @return The speed of the wind in km/h
     * @throws JSONException The JSON is invalid
     */
    public static Double getWindSpeed(JSONObject jSONObject) throws JSONException {

        // Get the data under "wind"
        JSONObject wind = jSONObject.getJSONObject("wind");

        // Get the wind speed
        double speed = wind.getDouble("speed");

        // Convert from m/s to km/h
        return msToKmH(speed);

    }

    /**
     * Return the description of the weather of the JSON obtained from the
     * "weather" request
     *
     * @param jSONObject The JSON data from the weather request
     * @return The description of the weather
     * @throws JSONException The JSON is invalid
     */
    public static String getDescription(JSONObject jSONObject) throws JSONException {
        // Get the Weather in the JSONArray format
        JSONArray weather = jSONObject.getJSONArray("weather");

        // Get the first item in the JSONArray
        JSONObject weatherObj = weather.getJSONObject(0);

        // Return the provided description
        return weatherObj.getString("description");
    }
}
