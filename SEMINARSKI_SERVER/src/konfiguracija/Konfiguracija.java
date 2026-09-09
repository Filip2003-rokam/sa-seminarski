/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package konfiguracija;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cofara
 */
public class Konfiguracija {
    
    private static Konfiguracija instanca;
    private Properties konfiguracija;
   
    private Konfiguracija() {
        
        konfiguracija = new Properties();
        try {
            konfiguracija.load(new FileInputStream("C:\\Users\\Cofara\\Desktop\\Folders\\Faks\\Projektovanje Softvera\\NetBeansProjekti\\0_SEMINARSKI_SERVER\\config\\config.properties"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    // 3. Javna metoda za pristup instanci
    public static Konfiguracija getInstanca() {
        if (instanca == null) {
            instanca = new Konfiguracija();
        }
        return instanca;
    }


    public String getProperty(String key) {
        return konfiguracija.getProperty(key, "n/a");
    }

    public void setProperty(String key, String value) {
        konfiguracija.setProperty(key, value);
    }

    public void sacuvajIzmene() {
    try {
        konfiguracija.store(new FileOutputStream("C:\\Users\\Cofara\\Desktop\\Folders\\Faks\\Projektovanje Softvera\\NetBeansProjekti\\0_SEMINARSKI_SERVER\\config\\config.properties"), "");
    } catch (IOException ex) {
        ex.printStackTrace();
        Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        //ServerskaForma.fajlFajl();
    }
}

    
    
}
