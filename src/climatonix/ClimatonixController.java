/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import climatonix.CitiesAPI.AutocompleteUtil;
import climatonix.API.APIUtils;
import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import climatonix.xml.*;
import java.io.IOException;
import java.net.ProtocolException;
import javafx.animation.FadeTransition;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import nu.xom.ParsingException;

/**
 * FXML Controller class
 *
 * @author cecce
 */
public class ClimatonixController implements Initializable {

    // Declare JavaFX fx:ids
    @FXML
    private AutoCompleteTextField<String> searchcities;

    @FXML
    private ListView displaycities;

    @FXML
    private Button addfavourite;

    @FXML
    private Button refresh;

    @FXML
    private Button removefavourite;

    @FXML
    private TextFlow displayweather;

    @FXML
    private ComboBox selectcity;

    @FXML
    private ComboBox selectcitydelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a new list of favourite cities
        List<String> favouritecities = new ArrayList<>();
        try {
            // If xml file has favourite cities, add all to List favouritecities
            if (XMLUtils.getFavourites() != null) {
                favouritecities.clear();
                for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                    favouritecities.add(XMLUtils.getFavourites().get(g));
                }
                // If there are cities saved as favourite, populate comboboxes
                if (!favouritecities.isEmpty()) {
                    selectcity.getItems().clear();
                    selectcity.getItems().setAll(favouritecities);
                    selectcity.getSelectionModel().selectFirst(); // Select first item in combobox

                    selectcitydelete.getItems().clear();
                    selectcitydelete.getItems().setAll(favouritecities);

                    // Display the weather
                    try {
                        // Create and configure Text by calling API methods
                        Text temp = new Text((Double.toString(Math.round(APIUtils.getTempature(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex())))))) + "°C");
                        temp.setFill(Color.BLACK);
                        temp.setFont(Font.font("System", 100));

                        Text lnbreak = new Text("\n");

                        Text space = new Text(" ");

                        Text wind = new Text("with winds of " + (Double.toString(Math.round(APIUtils.getWindSpeed(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex())))))) + " km/h");
                        wind.setFill(Color.BLACK);
                        wind.setFont(Font.font("System", FontPosture.ITALIC, 28));

                        Text desc = new Text(APIUtils.getDescription(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex()))));
                        desc.setFill(Color.BLACK);
                        desc.setFont(Font.font("System", FontPosture.ITALIC, 40));

                        // Display text objects
                        displayweather.getChildren().clear();
                        displayweather.getChildren().add(temp);
                        displayweather.getChildren().add(lnbreak);
                        displayweather.getChildren().add(desc);
                        displayweather.getChildren().add(space);
                        displayweather.getChildren().add(wind);

                        // Create and play fade animation on weather load
                        FadeTransition anim = new FadeTransition(Duration.millis(700), displayweather);
                        anim.setFromValue(0.0);
                        anim.setToValue(1.0);
                        anim.play();
                    } catch (ProtocolException ex) {
                    } catch (IOException ex) {
                    }
                }
            }
        } catch (IOException | ParsingException ex) {
        }

        // Set the completer of the AutoCompleteTextField with lambda
        searchcities.setCompleter(s -> {
            // Initialize variables
            List<String> searched = new ArrayList();
            AutocompleteUtil util = new AutocompleteUtil();

            // Find searches matching cities in list using AutocompleteUtils
            for (int q = 0; q < util.query(s).size(); q++) {
                searched.add(util.query(s).get(q)); // Add them to List searched
            }

            // Display matching searches in city list
            displaycities.getItems().clear();
            try {
                displaycities.getItems().setAll(searched);
            } catch (IllegalStateException e) {
            }

            return searched;
        });

        addfavourite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Adds selected city to favourites using XMLUtils
                try {
                    XMLUtils.addFavourite(displaycities.getSelectionModel().getSelectedItem().toString());
                } catch (ParsingException | IOException ex) {
                }

                // Refresh favouritecities List and comboboxes
                try {
                    favouritecities.clear();
                    for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                        favouritecities.add(XMLUtils.getFavourites().get(g));
                    }

                    selectcity.getItems().clear();
                    selectcity.getItems().setAll(favouritecities);
                    selectcity.getSelectionModel().selectLast(); // Select the most recently added city by default

                    selectcitydelete.getItems().clear();
                    selectcitydelete.getItems().setAll(favouritecities);
                } catch (IOException | ParsingException ex) {
                }
            }
        });

        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Checks to make sure you have selected a real city
                    if (selectcity.getSelectionModel().getSelectedIndex() != -1) {
                        // Create and configure Text by calling API methods
                        Text temp = new Text((Double.toString(Math.round(APIUtils.getTempature(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex())))))) + "°C");
                        temp.setFill(Color.BLACK);
                        temp.setFont(Font.font("System", 100));

                        Text lnbreak = new Text("\n");

                        Text space = new Text(" ");

                        Text wind = new Text("with winds of " + (Double.toString(Math.round(APIUtils.getWindSpeed(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex())))))) + " km/h");
                        wind.setFill(Color.BLACK);
                        wind.setFont(Font.font("System", FontPosture.ITALIC, 28));

                        Text desc = new Text(APIUtils.getDescription(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex()))));
                        desc.setFill(Color.BLACK);
                        desc.setFont(Font.font("System", FontPosture.ITALIC, 40));

                        // Display text objects
                        displayweather.getChildren().clear();
                        displayweather.getChildren().add(temp);
                        displayweather.getChildren().add(lnbreak);
                        displayweather.getChildren().add(desc);
                        displayweather.getChildren().add(space);
                        displayweather.getChildren().add(wind);

                        // Create and play fade animation on weather load
                        FadeTransition anim = new FadeTransition(Duration.millis(700), displayweather);
                        anim.setFromValue(0.0);
                        anim.setToValue(1.0);
                        anim.play();
                    }
                } catch (ProtocolException ex) {
                } catch (IOException ex) {
                }
            }
        });

        // Same as refresh button and program launch, but triggers upon selecting a new city
        selectcity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Checks to make sure you have selected a real city
                    if (selectcity.getSelectionModel().getSelectedIndex() != -1) {
                        // Create and configure Text by calling API methods
                        Text temp = new Text((Double.toString(Math.round(APIUtils.getTempature(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex())))))) + "°C");
                        temp.setFill(Color.BLACK);
                        temp.setFont(Font.font("System", 100));

                        Text lnbreak = new Text("\n");

                        Text space = new Text(" ");

                        Text wind = new Text("with winds of " + (Double.toString(Math.round(APIUtils.getWindSpeed(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex())))))) + " km/h");
                        wind.setFill(Color.BLACK);
                        wind.setFont(Font.font("System", FontPosture.ITALIC, 28));

                        Text desc = new Text(APIUtils.getDescription(APIUtils.request("weather", favouritecities.get(selectcity.getSelectionModel().getSelectedIndex()))));
                        desc.setFill(Color.BLACK);
                        desc.setFont(Font.font("System", FontPosture.ITALIC, 40));

                        // Display text objects
                        displayweather.getChildren().clear();
                        displayweather.getChildren().add(temp);
                        displayweather.getChildren().add(lnbreak);
                        displayweather.getChildren().add(desc);
                        displayweather.getChildren().add(space);
                        displayweather.getChildren().add(wind);

                        // Create and play fade animation on weather load
                        FadeTransition anim = new FadeTransition(Duration.millis(700), displayweather);
                        anim.setFromValue(0.0);
                        anim.setToValue(1.0);
                        anim.play();
                    }
                } catch (ProtocolException ex) {
                } catch (IOException ex) {
                }
            }
        });

        removefavourite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Deletes selected city
                    XMLUtils.removeFavourite(selectcitydelete.getSelectionModel().getSelectedItem().toString());
                } catch (ParsingException | IOException ex) {
                }
                try {
                    // Repopulate favouritecities List with new data
                    favouritecities.clear();
                    if (XMLUtils.getFavourites() != null) {
                        for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                            favouritecities.add(XMLUtils.getFavourites().get(g));
                        }
                    }
                    
                    // Check if favouritecities is empty
                    if (favouritecities.isEmpty()) {
                        // Clear comboboxes
                        selectcitydelete.getSelectionModel().clearSelection();
                        selectcity.getItems().clear();
                        
                        selectcitydelete.getSelectionModel().clearSelection();
                        selectcitydelete.getItems().clear();
                    } else {
                        // Repopulate comboboxes
                        selectcity.getItems().clear();
                        selectcitydelete.getItems().clear();
                        
                        selectcity.getItems().setAll(favouritecities);
                        selectcitydelete.getItems().setAll(favouritecities);
                        
                        selectcity.getSelectionModel().selectLast(); // Select last by default
                    }
                } catch (IOException | ParsingException ex) {
                }
            }
        });
    }

}
