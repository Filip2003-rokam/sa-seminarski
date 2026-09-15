package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import forme.GlavnaForma;

/**
 * Kontroler glavne forme klijentske aplikacije ({@link forme.GlavnaForma}).
 * U klijentskom MVC-u prikazuje ulogovanog konobara i sluzi kao ulazna
 * tacka nakon uspesne prijave; navigaciju ka ostalim formama vodi
 * {@link cordinator.Cordinator}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class GlavnaFormaController {

    /** Glavna forma kojom ovaj kontroler upravlja. */
    private final GlavnaForma gf;

    /**
     * Kreira kontroler i priprema listenere na formi.
     *
     * @param gf glavna forma
     */
    public GlavnaFormaController(GlavnaForma gf) {
        this.gf = gf;
        addActionListeners();
    }

    /**
     * Registruje action listenere (trenutno prazno; navigacija je u formi/koordinatoru).
     */
    private void addActionListeners() {
        
    }

    /**
     * Prikazuje glavnu formu i postavlja ime i prezime ulogovanog konobara.
     */
    public void otvoriFormu() {
        Konobar ulogovani = Cordinator.getInstance().getUlogovani();
        String imePrezime = ulogovani.getIme() + " " + ulogovani.getPrezime();
        gf.setVisible(true);
        gf.getjLabelUlogovani().setText(imePrezime);
    }

}
