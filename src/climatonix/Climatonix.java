/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package climatonix;

import java.util.ArrayList;
import climatonix.API.APIUtils;
import climatonix.xml.XMLUtils;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Benjamin
 */
public class Climatonix {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InterfaceClimatonix interfaceClimatonix = new InterfaceClimatonix();
        interfaceClimatonix.setVisible(true);
    }
}
