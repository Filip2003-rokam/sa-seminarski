package main;

import cordinator.Cordinator;

/**
 * Ulazna tacka klijentske aplikacije restoran sistema.
 * Pokrece aplikaciju otvaranjem login forme preko {@link Cordinator}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Main {

    /**
     * Kreira ulaznu tacku klijentske aplikacije.
     */
    public Main() {
    }
    
    /**
     * Pokrece klijentsku aplikaciju.
     *
     * @param args argumenti komandne linije (ne koriste se)
     */
    public static void main(String[] args) {
        Cordinator.getInstance().otvoriLoginFormu();
    }
    
}
