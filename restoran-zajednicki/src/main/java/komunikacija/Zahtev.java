package komunikacija;

import java.io.Serializable;

/**
 * Predstavlja zahtev koji klijent salje serveru preko mreze.
 * Sadrzi tip operacije i opcioni parametar potreban za izvrsavanje te operacije.
 * Objekat se serijalizuje i prenosi kao deo klijent-server komunikacije.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Zahtev implements Serializable {

    /** Operacija koju klijent zahteva od servera. */
    private Operacija operacija;
    /** Parametar operacije (entitet ili podaci potrebni za obradu). */
    private Object parametar;

    /**
     * Kreira zahtev sa zadatom operacijom i parametrom.
     *
     * @param operacija operacija koja se zahteva
     * @param parametar parametar operacije; moze biti {@code null}
     */
    public Zahtev(Operacija operacija, Object parametar) {
        this.operacija = operacija;
        this.parametar = parametar;
    }

    /**
     * Kreira prazan zahtev bez operacije i parametra.
     */
    public Zahtev() {
    }

    /**
     * Vraca operaciju koju zahtev nosi.
     *
     * @return operacija zahteva
     */
    public Operacija getOperacija() {
        return operacija;
    }

    /**
     * Postavlja operaciju zahteva.
     *
     * @param operacija nova operacija
     */
    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }

    /**
     * Vraca parametar zahteva.
     *
     * @return parametar operacije; moze biti {@code null}
     */
    public Object getParametar() {
        return parametar;
    }

    /**
     * Postavlja parametar zahteva.
     *
     * @param parametar novi parametar; moze biti {@code null}
     */
    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }

}
