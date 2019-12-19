/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import climatonix.API.APIUtils;
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
//        InterfaceClimatonix weather = new InterfaceClimatonix();
//        weather.setVisible(true);
        
        try {
            JSONObject jSONObject = APIUtils.request("weather", "Ottawa");
            System.out.println(APIUtils.getDescription(jSONObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
