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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
    private TextField searchCities;

    @FXML
    private ListView displayCities;

    @FXML
    private Button addFavourite;

    @FXML
    private Button refresh;

    @FXML
    private Button removeFavourite;

    @FXML
    private TextFlow displayWeather;

    @FXML
    private ComboBox selectCity;

    @FXML
    private ComboBox selectCityDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Create a new list of favourite cities
        List<String> favouriteCities = new ArrayList<>();
        try {
            // If xml file has favourite cities, add all to List favouriteCities
            if (XMLUtils.getFavourites() != null) {
                favouriteCities.clear();
                for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                    favouriteCities.add(XMLUtils.getFavourites().get(g));
                }
                // If there are cities saved as favourite, populate comboboxes
                if (!favouriteCities.isEmpty()) {
                    selectCity.getItems().clear();
                    selectCity.getItems().setAll(favouriteCities);
                    selectCity.getSelectionModel().selectFirst(); // Select first item in combobox

                    selectCityDelete.getItems().clear();
                    selectCityDelete.getItems().setAll(favouriteCities);

                    // Display the weather
                    try {
                        // Create and configure Text by calling API methods
                        Text temp = new Text((Double.toString(Math.round(APIUtils.getTempature(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex())))))) + "°C");
                        temp.setFill(Color.BLACK);
                        temp.setFont(Font.font("System", 100));

                        Text lnbreak = new Text("\n");

                        Text space = new Text(" ");

                        Text wind = new Text("with winds of " + (Double.toString(Math.round(APIUtils.getWindSpeed(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex())))))) + " km/h");
                        wind.setFill(Color.BLACK);
                        wind.setFont(Font.font("System", FontPosture.ITALIC, 28));

                        Text desc = new Text(APIUtils.getDescription(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex()))));
                        desc.setFill(Color.BLACK);
                        desc.setFont(Font.font("System", FontPosture.ITALIC, 40));

                        // Display text objects
                        displayWeather.getChildren().clear();
                        displayWeather.getChildren().add(temp);
                        displayWeather.getChildren().add(lnbreak);
                        displayWeather.getChildren().add(desc);
                        displayWeather.getChildren().add(space);
                        displayWeather.getChildren().add(wind);

                        // Create and play fade animation on weather load
                        FadeTransition anim = new FadeTransition(Duration.millis(700), displayWeather);
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

        searchCities.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                
                // Make sure that the characters are valid (are letters)
                if (!newValue.matches("\\sa-zA-Z*")) {
                    searchCities.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                    if (!searchCities.getText().isEmpty()) {
                        // Initialize variables
                        List<String> searched = new ArrayList();
                        AutocompleteUtil util = new AutocompleteUtil();

                        // Find searches matching cities in list using AutocompleteUtils
                        for (int q = 0; q < util.query(searchCities.getText()).size(); q++) {
                            searched.add(util.query(searchCities.getText()).get(q)); // Add them to List searched
                        }

                        // Display matching searches in city list
                        Platform.runLater(new Runnable() {
                            public void run() {
                                displayCities.getItems().clear();
                                displayCities.getItems().setAll(searched);
                            }
                        });
                    }
                }
            }
        });

        addFavourite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Adds selected city to favourites using XMLUtils
                try {
                    if (!displayCities.getSelectionModel().isEmpty()) {
                        XMLUtils.addFavourite(displayCities.getSelectionModel().getSelectedItem().toString());
                    }
                } catch (ParsingException | IOException ex) {
                }

                // Refresh favouriteCities List and comboboxes
                try {
                    favouriteCities.clear();
                    for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                        favouriteCities.add(XMLUtils.getFavourites().get(g));
                    }

                    selectCity.getItems().clear();
                    selectCity.getItems().setAll(favouriteCities);
                    selectCity.getSelectionModel().selectLast(); // Select the most recently added city by default

                    selectCityDelete.getItems().clear();
                    selectCityDelete.getItems().setAll(favouriteCities);
                } catch (IOException | ParsingException ex) {
                }
            }
        });

        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Checks to make sure you have selected a real city
                    if (selectCity.getSelectionModel().getSelectedIndex() != -1) {
                        // Create and configure Text by calling API methods
                        Text temp = new Text((Double.toString(Math.round(APIUtils.getTempature(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex())))))) + "°C");
                        temp.setFill(Color.BLACK);
                        temp.setFont(Font.font("System", 100));

                        Text lnbreak = new Text("\n");

                        Text space = new Text(" ");

                        Text wind = new Text("with winds of " + (Double.toString(Math.round(APIUtils.getWindSpeed(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex())))))) + " km/h");
                        wind.setFill(Color.BLACK);
                        wind.setFont(Font.font("System", FontPosture.ITALIC, 28));

                        Text desc = new Text(APIUtils.getDescription(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex()))));
                        desc.setFill(Color.BLACK);
                        desc.setFont(Font.font("System", FontPosture.ITALIC, 40));

                        // Display text objects
                        displayWeather.getChildren().clear();
                        displayWeather.getChildren().add(temp);
                        displayWeather.getChildren().add(lnbreak);
                        displayWeather.getChildren().add(desc);
                        displayWeather.getChildren().add(space);
                        displayWeather.getChildren().add(wind);

                        // Create and play fade animation on weather load
                        FadeTransition anim = new FadeTransition(Duration.millis(700), displayWeather);
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
        selectCity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Checks to make sure you have selected a real city
                    if (selectCity.getSelectionModel().getSelectedIndex() != -1) {
                        // Create and configure Text by calling API methods
                        Text temp = new Text((Double.toString(Math.round(APIUtils.getTempature(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex())))))) + "°C");
                        temp.setFill(Color.BLACK);
                        temp.setFont(Font.font("System", 100));

                        Text lnbreak = new Text("\n");

                        Text space = new Text(" ");

                        Text wind = new Text("with winds of " + (Double.toString(Math.round(APIUtils.getWindSpeed(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex())))))) + " km/h");
                        wind.setFill(Color.BLACK);
                        wind.setFont(Font.font("System", FontPosture.ITALIC, 28));

                        Text desc = new Text(APIUtils.getDescription(APIUtils.request("weather", favouriteCities.get(selectCity.getSelectionModel().getSelectedIndex()))));
                        desc.setFill(Color.BLACK);
                        desc.setFont(Font.font("System", FontPosture.ITALIC, 40));

                        // Display text objects
                        displayWeather.getChildren().clear();
                        displayWeather.getChildren().add(temp);
                        displayWeather.getChildren().add(lnbreak);
                        displayWeather.getChildren().add(desc);
                        displayWeather.getChildren().add(space);
                        displayWeather.getChildren().add(wind);

                        // Create and play fade animation on weather load
                        FadeTransition anim = new FadeTransition(Duration.millis(700), displayWeather);
                        anim.setFromValue(0.0);
                        anim.setToValue(1.0);
                        anim.play();
                    }
                } catch (ProtocolException ex) {
                } catch (IOException ex) {
                }
            }
        });

        removeFavourite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (!selectCityDelete.getSelectionModel().isEmpty()) {
                        // If a city is selected, delete selected city
                        XMLUtils.removeFavourite(selectCityDelete.getSelectionModel().getSelectedItem().toString());
                    }
                } catch (ParsingException | IOException ex) {
                }

                try {
                    // Repopulate favouriteCities List with new data
                    favouriteCities.clear();
                    if (XMLUtils.getFavourites() != null) {
                        for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                            favouriteCities.add(XMLUtils.getFavourites().get(g));
                        }
                    }

                    // Check if favouriteCities is empty
                    if (favouriteCities.isEmpty()) {
                        // Clear comboboxes
                        selectCityDelete.getSelectionModel().clearSelection();
                        selectCity.getItems().clear();

                        selectCityDelete.getSelectionModel().clearSelection();
                        selectCityDelete.getItems().clear();
                    } else {
                        // Repopulate comboboxes
                        selectCity.getItems().clear();
                        selectCityDelete.getItems().clear();

                        selectCity.getItems().setAll(favouriteCities);
                        selectCityDelete.getItems().setAll(favouriteCities);

                        selectCity.getSelectionModel().selectLast(); // Select last by default
                    }
                } catch (IOException | ParsingException ex) {
                }
            }
        });
    }

}
