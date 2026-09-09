package konfiguracija;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton klasa koja upravlja konfiguracijom serverske aplikacije.
 *
 * Podrazumevane vrednosti se ucitavaju iz classpath resursa
 * <code>config.properties</code>. Ako pored aplikacije postoji spoljni fajl
 * <code>config/config.properties</code>, njegove vrednosti imaju prednost.
 * Izmene koje korisnik napravi kroz forme se cuvaju u taj spoljni fajl.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Konfiguracija {

    private static Konfiguracija instanca;

    private final Properties konfiguracija;

    private static final String RESURS = "config.properties";
    private static final String SPOLJNI_FAJL = "config/config.properties";

    private Konfiguracija() {
        konfiguracija = new Properties();
        ucitajIzResursa();
        ucitajIzSpoljnogFajla();
    }

    private void ucitajIzResursa() {
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

    private void ucitajIzSpoljnogFajla() {
        File fajl = new File(SPOLJNI_FAJL);
        if (!fajl.exists()) {
            return;
        }
        try (InputStream in = new FileInputStream(fajl)) {
            konfiguracija.load(in);
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
     * Izmena se trajno cuva tek pozivom metode sacuvajIzmene.
     *
     * @param key naziv parametra
     * @param value nova vrednost parametra
     */
    public void setProperty(String key, String value) {
        konfiguracija.setProperty(key, value);
    }

    /**
     * Trajno cuva sve izmene u spoljni konfiguracioni fajl.
     */
    public void sacuvajIzmene() {
        File fajl = new File(SPOLJNI_FAJL);
        File folder = fajl.getParentFile();
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(fajl)) {
            konfiguracija.store(out, "Konfiguracija servera");
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}