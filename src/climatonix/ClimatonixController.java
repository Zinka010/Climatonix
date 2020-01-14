/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import climatonix.API.APIUtils;
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
import java.net.ProtocolException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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

        List<String> favouritecities = new ArrayList<>();
        try {
            if (XMLUtils.getFavourites() != null) {
                favouritecities.clear();
                for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                    favouritecities.add(XMLUtils.getFavourites().get(g));
                }
                if (!favouritecities.isEmpty()) {
                    selectcity.getItems().clear();
                    selectcity.getItems().setAll(favouritecities);
                    selectcity.getSelectionModel().selectFirst();

                    selectcitydelete.getItems().clear();
                    selectcitydelete.getItems().setAll(favouritecities);

                    try {
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

                        displayweather.getChildren().clear();
                        displayweather.getChildren().add(temp);
                        displayweather.getChildren().add(lnbreak);
                        displayweather.getChildren().add(desc);
                        displayweather.getChildren().add(space);
                        displayweather.getChildren().add(wind);

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

        Cities listofcities = CitiesImportUtil.importCitiesFromCSV("src/climatonix/citiesListSorted.csv");
        List<String> citiesbystring = new ArrayList();
        for (int j = 0; j < 26; j++) {
            for (int i = 0; i < listofcities.cities[j].size(); i++) {
                citiesbystring.add(listofcities.cities[j].get(i).name);
            }
        }

        searchcities.setCompleter(s -> {
            List<String> searched = new ArrayList();

//            for (int x = 0; x < citiesbystring.size(); x++) {
//                if (citiesbystring.get(x).startsWith(s)) {
//                    searched.add(citiesbystring.get(x));
//                }
//            }
            AutocompleteUtil util = new AutocompleteUtil();

            for (int q = 0; q < util.query(s).size(); q++) {
                searched.add(util.query(s).get(q));
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
                }
                try {
                    favouritecities.clear();
                    for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                        favouritecities.add(XMLUtils.getFavourites().get(g));
                    }
                    selectcity.getItems().clear();
                    selectcity.getItems().setAll(favouritecities);

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

                    displayweather.getChildren().clear();
                    displayweather.getChildren().add(temp);
                    displayweather.getChildren().add(lnbreak);
                    displayweather.getChildren().add(desc);
                    displayweather.getChildren().add(space);
                    displayweather.getChildren().add(wind);

                    FadeTransition anim = new FadeTransition(Duration.millis(700), displayweather);
                    anim.setFromValue(0.0);
                    anim.setToValue(1.0);
                    anim.play();
                } catch (ProtocolException ex) {
                } catch (IOException ex) {
                }
            }
        });

        selectcity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
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

                    displayweather.getChildren().clear();
                    displayweather.getChildren().add(temp);
                    displayweather.getChildren().add(lnbreak);
                    displayweather.getChildren().add(desc);
                    displayweather.getChildren().add(space);
                    displayweather.getChildren().add(wind);

                    FadeTransition anim = new FadeTransition(Duration.millis(700), displayweather);
                    anim.setFromValue(0.0);
                    anim.setToValue(1.0);
                    anim.play();
                } catch (ProtocolException ex) {
                } catch (IOException ex) {
                }
            }
        });

        removefavourite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    XMLUtils.removeFavourite(selectcitydelete.getSelectionModel().getSelectedItem().toString());
                } catch (ParsingException | IOException ex) {
                }
                try {
                    favouritecities.clear();
                    for (int g = 0; g < XMLUtils.getFavourites().size(); g++) {
                        favouritecities.add(XMLUtils.getFavourites().get(g));
                    }
                    selectcity.getItems().clear();
                    selectcity.getItems().setAll(favouritecities);

                    selectcitydelete.getItems().clear();
                    selectcitydelete.getItems().setAll(favouritecities);
                } catch (IOException | ParsingException ex) {
                }
            }
        });
    }

}
