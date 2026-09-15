package komunikacija;

import java.io.Serializable;

/**
 * Predstavlja odgovor koji server salje klijentu nakon obrade zahteva.
 * Sadrzi rezultat operacije i indikator da li je operacija uspesno izvrsena.
 * Objekat se serijalizuje i prenosi kao deo klijent-server komunikacije.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Odgovor implements Serializable {

    /** Rezultat obrade zahteva (podaci ili poruka o gresci). */
    private Object odgovor;
    /** Indikator da li je operacija uspesno izvrsena. */
    private boolean uspeh;

    /**
     * Kreira prazan odgovor bez podataka.
     */
    public Odgovor() {
    }

    /**
     * Kreira odgovor sa zadatim rezultatom obrade.
     *
     * @param odgovor rezultat operacije; moze biti {@code null}
     */
    public Odgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

    /**
     * Vraca rezultat obrade zahteva.
     *
     * @return objekat rezultata; moze biti {@code null}
     */
    public Object getOdgovor() {
        return odgovor;
    }

    /**
     * Postavlja rezultat obrade zahteva.
     *
     * @param odgovor novi rezultat; moze biti {@code null}
     */
    public void setOdgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

    /**
     * Vraca indikator uspesnosti operacije.
     *
     * @return {@code true} ako je operacija uspesna, inace {@code false}
     */
    public boolean getUspeh() {
        return uspeh;
    }

    /**
     * Postavlja indikator uspesnosti operacije.
     *
     * @param uspeh {@code true} ako je operacija uspesna, inace {@code false}
     */
    public void setUspeh(boolean uspeh) {
        this.uspeh = uspeh;
    }

}
