/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author michaelzhang
 */
public class AutocompleteUtil {

    private Cities cities;
    private ArrayList<City> selectedCities;
    
    AutocompleteUtil() {
        // Initialize cities
        String fileName = "src/climatonix/citiesListSorted.csv";
        cities = CitiesImportUtil.importCitiesFromCSV(fileName);
    }

    public List<String> query(String query) {
        
        if (query.isEmpty()) {
            return null;
        }
        
        if (query.toUpperCase().charAt(0) - 65 > 26 || query.toUpperCase().charAt(0) - 65 < 0) {
            return null;
        }
        
        selectedCities = cities.getCitiesThatBeginWith(query, 10);
        return selectedCities.stream().map(x->x.name).distinct().collect(Collectors.toList());
    }
}
