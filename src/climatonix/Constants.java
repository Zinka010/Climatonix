/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

/**
 *
 * @author Justin Toft
 */
public final class Constants {
    
    private static Constants currentIntance;
    
    // Class statique qui ne devrait pas être instantiée
    private Constants() {
        
    }
    
    public static Constants getInstance() {
        init();
        return currentIntance;
    }
    
    public static void init() {
        if(currentIntance == null) {
            currentIntance = new Constants();
        }
    }
    
    public final String API_KEY = "54d83a3134fc8396ccb7e043d2b3b7de";
    public final String BASE_URL = "api.openweathermap.org/data/2.5/";
}
