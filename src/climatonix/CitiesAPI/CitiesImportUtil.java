/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix.CitiesAPI;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author michaelzhang
 */
public class CitiesImportUtil {

    public static Cities importCitiesFromCSV(String fileName) {

        Cities cities = new Cities();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Lire la première ligne
            String line = br.readLine();

            // Loop dans chaque ligne
            while (line != null) {

                // Prendre attributes
                String[] attributes = line.split(",");

                // Ajouter ville à villes
                cities.add(new City(attributes));

                // Lire la prochaine ligne
                line = br.readLine();
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
