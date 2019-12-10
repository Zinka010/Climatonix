/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import org.json.JSONObject;

/**
 *
 * @author justi
 */
public class Climatonix {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        JSONObject jSONObject;
        
        
        try {
            jSONObject = APIUtils.request("weather");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        int h = 0;
    }
    
    public double kelvinToCelcius(double kelvin) {
        return kelvin - 273.15;
    }
    
}
