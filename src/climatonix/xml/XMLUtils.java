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
import nu.xom.Serializer;

/**
 *
 * @author justi
 */
public class XMLUtils {

    // Static class which should not be instantiated
    private XMLUtils() {

    }

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

            Elements childrenElements = root.getChildElements();

        } else {
            // Otherwise, build a new instance of it
            root = new Element("cities");
            document = new Document(root);
        }

        // Create a new element with the desired city name
        Element city = new Element("city");
        city.appendChild(cityName);

        // Append it to the root element
        root.appendChild(city);

        // Save the file, clobbering the existing file
        Serializer serialisation = new Serializer(System.out);
        serialisation.setIndent(4);
        serialisation.setMaxLength(64);
        serialisation.write(document);

        FileWriter fileWriter = new FileWriter(favouritesFile);

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        System.out.println(document.toXML());
        bufferedWriter.write(document.toXML());
        bufferedWriter.close();

    }

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
            if(favourites.isEmpty()) {
                return null;
            }
            
            // Return the populated ArrayList
            return favourites;

        }

        // If the file doesn't exist, return null
        return null;
    }

}
