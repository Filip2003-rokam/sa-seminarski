package kontroleri;

import cordinator.Cordinator;
import domen.Gost;
import domen.Racun;
import forme.DetaljiGostaForma;
import forme.DodajGostaForma;
import forme.FormaMod;
import static forme.FormaMod.DODAJ;
import static forme.FormaMod.IZMENI;
import forme.model.ModelTabeleRacuni;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za prikaz detalja gosta ({@link forme.DetaljiGostaForma}).
 * U klijentskom MVC-u ucitava gosta iz parametara koordinatora, prikazuje
 * njegove podatke (samo za citanje) i statistiku racuna (placeni, neplaceni, dug, popust).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DetaljiGostaController {
    
    /** Forma za detalje gosta kojom ovaj kontroler upravlja. */
     private final DetaljiGostaForma dgf;

    /**
     * Kreira kontroler za formu detalja gosta.
     *
     * @param dgf forma za detalje gosta
     */
    public DetaljiGostaController(DetaljiGostaForma dgf) {
        this.dgf = dgf;
    }
    
    /**
     * Priprema i prikazuje formu sa detaljima izabranog gosta.
     */
    public void otvoriFormu() {
        pripremiFormu();
        //ppf.show(true);
        dgf.setVisible(true);
    }
    
    /**
     * Popunjava polja forme podacima o gostu i filtriranim racunima.
     * Gost se cita iz parametra koordinatora <code>gost_za_detalje</code>.
     */
    private void pripremiFormu() {

    Gost g = (Gost) Cordinator.getInstance().vratiParam("gost_za_detalje");

    // 🔹 Onemogući ručno menjanje polja
    dgf.getjTextFieldId().setEditable(false);
    dgf.getjTextFieldIme().setEditable(false);
    dgf.getjTextFieldPrezime().setEditable(false);
    dgf.getjTextFieldKategorija().setEnabled(false);
    dgf.getjTextFieldDug().setEditable(false);
    dgf.getjTextFieldPlaceni().setEditable(false);
    dgf.getjTextFieldNeplacenih().setEditable(false);
    dgf.getjTextFieldUkupno().setEnabled(false);
    dgf.getjTextFieldPopust().setEnabled(false);

    // 🔹 Popuni osnovne informacije o gostu
    dgf.getjTextFieldIme().setText(g.getIme());
    dgf.getjTextFieldPrezime().setText(g.getPrezime());
    dgf.getjTextFieldId().setText(String.valueOf(g.getIdGost()));
    dgf.getjTextFieldKategorija().setText(g.getKategorijaGosta().getOpis());

    try {
        // 🔹 Učitaj sve račune sa servera
        List<Racun> racuni = Komunikacija.getInstance().ucitajRacune();

        // 🔹 Napravi model i postavi ga na tabelu
        ModelTabeleRacuni mtr = new ModelTabeleRacuni(racuni);
        dgf.getjTableRacuni().setModel(mtr);

        // 🔹 Filtriraj račune samo za izabranog gosta
        mtr.pretrazi(null, g, null, null);

        // 🔹 Izračunaj statistiku na osnovu filtriranih računa
        int brojPlaceni = mtr.getBrojPlaceniRacuna();
        int brojNeplaceni = mtr.getBrojNeplaceniRacuna();
        int ukupanBroj = mtr.getUkupanBrojRacuna();
        double ukupanDug = mtr.getUkupanPreostaliDug();

        // 🔹 Popuni polja na formi
        dgf.getjTextFieldPlaceni().setText(String.valueOf(brojPlaceni));
        dgf.getjTextFieldNeplacenih().setText(String.valueOf(brojNeplaceni));
        dgf.getjTextFieldUkupno().setText(String.valueOf(ukupanBroj));
        dgf.getjTextFieldDug().setText(String.format("%.2f", ukupanDug));
        dgf.getjTextFieldPopust().setText(g.getKategorijaGosta().getPopust() + "%");

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(dgf, "Greška pri učitavanju računa: " + ex.getMessage(),
                "Greška", JOptionPane.ERROR_MESSAGE);
    }
}


    
}
