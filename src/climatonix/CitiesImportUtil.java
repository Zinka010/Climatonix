/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

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

            // Read first line
            String line = br.readLine();

            // Loop each line
            while (line != null) {

                // Take attributes
                String[] attributes = line.split(",");

                // Add City to Cities
                cities.add(new City(attributes));

                // Read the next line
                line = br.readLine();
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
