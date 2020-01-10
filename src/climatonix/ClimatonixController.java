/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author cecce
 */
public class ClimatonixController implements Initializable {

    private AutoCompleteTextField searchcities;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Cities listofcities = CitiesImportUtil.importCitiesFromCSV("src/climatonix/citiesListSorted.csv");
        List<String> citiesbystring = new ArrayList();
        for (int j = 0; j < 26; j++) {
            for (int i = 0; i < listofcities.cities[j].size(); i++) {
                citiesbystring.add(listofcities.cities[j].get(i).name);
            }
        }
        
        searchcities.setCompleter(s -> {
            return citiesbystring;
        });
    }

}
