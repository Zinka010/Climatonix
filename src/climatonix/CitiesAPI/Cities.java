/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix.CitiesAPI;

import java.util.ArrayList;

/**
 *
 * @author michaelzhang
 */
public class Cities {

    ArrayList<City>[] cities = new ArrayList[26];

    Cities() {
        // Initialize list of objects
        for (int i = 0; i < 26; i++) {
            cities[i] = new ArrayList();
        }
    }

    // Function to add cities
    public void add(City city) {
        try {
            // Add city to appropriate letter A-Z array
            int charAscii = (int) city.name.toUpperCase().charAt(0) - 65;
            if (0 <= charAscii && charAscii <= 25) {
                cities[charAscii].add(city);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<City> getCitiesThatBeginWith(String query, int maxNumberOfCities) {
        // Convert query to upper case
        query = query.toUpperCase();

        assert maxNumberOfCities > 0 : "maxNumberOfCities must be positive int";

        // Create matched and searched lists
        ArrayList<City> matched = new ArrayList<City>();
        ArrayList<City> searchedList = cities[query.charAt(0) - 65];

        // Loop over cities in list
        String cityName;
        for (int i = 0; i < searchedList.size(); i++) {
            City city = searchedList.get(i);
            cityName = city.name.toUpperCase();
            if (cityName.startsWith(query)) {

                // If max sized reach, break
                if (matched.size() == maxNumberOfCities) {
                    break;
                }

                // Add to matched cities list
                matched.add(city);
            }
        }
        return matched;
    }

}
