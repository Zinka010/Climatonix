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
public class City {

    String id;
    String name;
    String country;
    double lat;
    double lon;

    City(String[] data) {
        try {
            id = data[0];
            name = data[1];
            country = data[2];
            lat = Double.parseDouble(data[3]);
            lon = Double.parseDouble(data[4]);
        } catch (Exception e) {
        }
    }
}
