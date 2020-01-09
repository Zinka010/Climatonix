/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/**
 *
 * @author Justin
 */
public class XMLUtils {

    // Static class which should not be instantiated
    private XMLUtils() {

    }

    /**
     *
     * @param cityName The desired city
     * @throws ParsingException There was an error parsing the XML
     * @throws IOException There was an error reading or writing to the file
     */
    public static void addFavourite(String cityName) throws ParsingException, IOException {
        // Create a new File
        File favouritesFile = new File("favourites.xml");
        Builder builder = new Builder();

        // Initialize 
        Document document;
        Element root;

        // If the file exists, build it from the existing XML
        if (favouritesFile.exists()) {

            document = builder.build(favouritesFile);

            root = document.getRootElement();

        } else {
            // Otherwise, build a new instance of it
            root = new Element("cities");
            document = new Document(root);
        }

        // As a default, it is not a duplicate
        Boolean duplicate = false;

        // Search for the desired element
        for (int i = 0; i < root.getChildElements().size(); i++) {

            // The city is a duplicate and should therefore not be added again
            if (root.getChild(i).getChild(0).getValue().equalsIgnoreCase(cityName)) {
                duplicate = true;
                break;
            }
        }

        if (!duplicate) {

            // Create a new element with the desired city name
            Element city = new Element("city");
            city.appendChild(cityName);

            // Append it to the root element
            root.appendChild(city);

            // Save the file, clobbering the existing file
            FileWriter fileWriter = new FileWriter(favouritesFile);

            // Write to the file
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(document.toXML());
            bufferedWriter.close();
        }

    }

    /**
     *
     * @param cityName The name of the city to be removed
     * @throws IOException There was an error reading writing to the file
     * @throws ParsingException There was an error reading the XML
     */
    public static void removeFavourite(String cityName) throws IOException, ParsingException {

        // Create a new File
        File favouritesFile = new File("favourites.xml");
        Builder builder = new Builder();

        // Initialize 
        Document document;
        Element root;

        // If the file exists, build it from the existing XML
        if (favouritesFile.exists()) {

            document = builder.build(favouritesFile);

            root = document.getRootElement();

        } else {
            // Otherwise, build a new instance of it
            root = new Element("cities");
            document = new Document(root);
        }

        // Search for the desired element
        for (int i = 0; i < root.getChildElements().size(); i++) {

            // Remove all instances of the city the favourites.xml file
            if (root.getChild(i).getChild(0).getValue().equalsIgnoreCase(cityName)) {
                root.removeChild(i);
            }
        }

        // Save the file, clobbering the existing file
        FileWriter fileWriter = new FileWriter(favouritesFile);

        // Write to the file
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(document.toXML());
        bufferedWriter.close();

    }

    /**
     *
     * @return An ArrayList of favourite cities
     * @throws IOException There was an error reading and writing to file
     * @throws ParsingException There was an error reading the XML
     */
    public static ArrayList<String> getFavourites() throws IOException, ParsingException {

        // Create a new file
        File favouritesFile = new File("favourites.xml");
        Builder builder = new Builder();

        if (favouritesFile.exists()) {
            // Build the XML from the document
            Document document = builder.build(favouritesFile);

            // Get the cities from the XML
            Element root = document.getRootElement();
            Elements cities = root.getChildElements();

            // Convert the favourite citites to an Array List
            ArrayList<String> favourites = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                favourites.add(cities.get(i).getValue());
            }

            // If the ArrayList is empty, return null for uniformity
            if (favourites.isEmpty()) {
                return null;
            }

            // Return the populated ArrayList
            return favourites;

        }

        // If the file doesn't exist, return null
        return null;
    }

}
