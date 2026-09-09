package konfiguracija;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton klasa koja upravlja konfiguracijom klijentske aplikacije.
 * Parametri se ucitavaju iz classpath resursa <code>config.properties</code>.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Konfiguracija {

    private static Konfiguracija instanca;

    private final Properties konfiguracija;

    private static final String RESURS = "config.properties";

    private Konfiguracija() {
        konfiguracija = new Properties();
        try (InputStream in = Konfiguracija.class.getClassLoader().getResourceAsStream(RESURS)) {
            if (in != null) {
                konfiguracija.load(in);
            } else {
                Logger.getLogger(Konfiguracija.class.getName())
                        .log(Level.WARNING, "Resurs {0} nije pronadjen u classpath-u.", RESURS);
            }
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Vraca jedinu instancu klase Konfiguracija.
     *
     * @return instanca klase Konfiguracija
     */
    public static Konfiguracija getInstanca() {
        if (instanca == null) {
            instanca = new Konfiguracija();
        }
        return instanca;
    }

    /**
     * Vraca vrednost konfiguracionog parametra.
     *
     * @param key naziv parametra
     * @return vrednost parametra, ili "n/a" ako parametar ne postoji
     */
    public String getProperty(String key) {
        return konfiguracija.getProperty(key, "n/a");
    }

    /**
     * Postavlja vrednost konfiguracionog parametra u memoriji.
     *
     * @param key naziv parametra
     * @param value nova vrednost parametra
     */
    public void setProperty(String key, String value) {
        konfiguracija.setProperty(key, value);
    }
}