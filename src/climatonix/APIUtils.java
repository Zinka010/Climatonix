/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author justi
 */
public class APIUtils {
    
    public void request(String requestType) throws MalformedURLException, ProtocolException, IOException  {
        URL url = new URL(Constants.getInstance().BASE_URL + requestType + "?");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
    }
    
    
}
