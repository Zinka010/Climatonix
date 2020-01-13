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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import climatonix.xml.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.TextFlow;
import nu.xom.ParsingException;

/**
 * FXML Controller class
 *
 * @author cecce
 */
public class ClimatonixController implements Initializable {

    @FXML
    private AutoCompleteTextField<String> searchcities;
    
    @FXML
    private ListView displaycities;
    
    @FXML
    private Button addfavourite;
    
    @FXML
    private TextFlow displayweather;

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
            List<String> searched = new ArrayList();
            for (int x = 0; x < citiesbystring.size(); x++) {
                if (citiesbystring.get(x).startsWith(s)) {
                    searched.add(citiesbystring.get(x));
                }
            }
            
            displaycities.getItems().clear();
            
            for (int z = 0; z < searched.size(); z++) {
                displaycities.getItems().add(searched.get(z));
            }
            
            return searched;
        });
        
        addfavourite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    XMLUtils.addFavourite(displaycities.getSelectionModel().getSelectedItem().toString());
                } catch (ParsingException | IOException ex) {
                    Logger.getLogger(ClimatonixController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
