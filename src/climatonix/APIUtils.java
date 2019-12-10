/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author justi
 */
public class APIUtils {
    
    private APIUtils() { }

    public static JSONObject request(String requestType) throws MalformedURLException, ProtocolException, IOException {
        
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", "Ottawa");
        parameters.put("appid", Constants.getInstance().API_KEY);
        
        URL url = new URL(Constants.getInstance().BASE_URL + requestType + "?" + ParamaterStringBuilder.getParamsString(parameters));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

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
        
        return new JSONObject(content.toString());
    }

    public static double kelvinToCelcius(double kelvin) {
        return kelvin - 273.15;
    }
    // https://www.baeldung.com/java-http-request
}
