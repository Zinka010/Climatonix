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
     * Faire un GET request à l'API
     *
     * @param requestType Le type de get à faire (weather, etc.)
     * @param cityName Le nom de la ville voulu
     * @return Le JSON obtenu dans le format JSONObject
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static JSONObject request(String requestType, String cityName) throws MalformedURLException, ProtocolException, IOException {

        // Indiqué les paramètres du GET request
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", cityName);
        parameters.put("appid", Constants.getInstance().API_KEY);

        // Créer l'URL
        URL url = new URL(Constants.getInstance().BASE_URL + requestType + "?" + ParamaterStringBuilder.getParamsString(parameters));

        // Ouvrir la connection
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Sauver les données obtenus
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // Retourner le JSONObject créer du texte obtenu
        return new JSONObject(content.toString());
    }

    /**
     * Convertir de Kelvin en degrées Celcius
     *
     * @param kelvin La température en kelvin
     * @return La température en degrées Celcius
     */
    private static double kelvinToCelcius(double kelvin) {
        return kelvin - 273.15;
    }

    /**
     * Retourné la température courrante des données JSON format "weather"
     *
     * @param jSONObject L'objet json avc les données
     * @return La température en celcius
     * @throws JSONException Le JSON est invalide
     */
    public static Double getTempature(JSONObject jSONObject) throws JSONException{

        // Obtenir les données sous "main"
        JSONObject main = jSONObject.getJSONObject("main");

        // Obtenir la température
        double tempature = main.getDouble("temp");

        // Convertir de kelvin à celcius
        return kelvinToCelcius(tempature);

    }

    private static double msToKmH(double ms) {
        return ms * 3.6;
    }

    /**
     * Retourné la vitesse du vent des données JSON format "weather"
     * 
     * @param jSONObject L'objet json avc les données
     * @return La vitesse du vent en km/h
     * @throws JSONException Le JSON est invalide
     */
    public static Double getWindSpeed(JSONObject jSONObject) throws JSONException {

        // Obtenir les données sous "wind"
        JSONObject wind = jSONObject.getJSONObject("wind");

        // Obtenir la vitesse du vent
        double speed = wind.getDouble("speed");

        // Convertir de m/s à km/h
        return msToKmH(speed);

    }
}
