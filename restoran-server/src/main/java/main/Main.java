package main;

import forme.ServerskaForma;

/**
 * Ulazna tacka serverske aplikacije.
 * Pokrece glavnu {@link ServerskaForma} i prikazuje je korisniku.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Main {

    /**
     * Pokretacka metoda – kreira i prikazuje serversku formu.
     *
     * @param args argumenti komandne linije (ne koriste se)
     */
    public static void main(String[] args) {
        ServerskaForma sf = new ServerskaForma();
        sf.setVisible(true);
    }

}
