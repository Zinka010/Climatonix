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
public class Constants {
    
    private static Constants currentIntance;
    
    // Classe singleton qui ne devrait pas être instantiée
    private Constants() {
        
    }
    
    // Retourner l'instance de Constants
    public static Constants getInstance() {
        init();
        return currentIntance;
    }
    
    // Seulement un instance peut exister à la foi
    public static void init() {
        if(currentIntance == null) {
            currentIntance = new Constants();
        }
    }
    
    // La clé pour le API
    public final String API_KEY = "54d83a3134fc8396ccb7e043d2b3b7de";
    
    // L'URL de base
    public final String BASE_URL = "https://api.openweathermap.org//data//2.5//";
}
