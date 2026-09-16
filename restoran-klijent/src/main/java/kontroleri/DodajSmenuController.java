package kontroleri;

import cordinator.Cordinator;
import domen.Smena;
import forme.DodajSmenuForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za dodavanje i izmenu smene ({@link forme.DodajSmenuForma}).
 * U klijentskom MVC-u validira naziv i vremena pocetka/kraja, zatim salje
 * zahteve serveru preko {@link komunikacija.Komunikacija}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajSmenuController {

    /** Forma za dodavanje/izmenu smene kojom ovaj kontroler upravlja. */
    private final DodajSmenuForma dsf;

    /**
     * Kreira kontroler i registruje listenere za dodavanje i izmenu.
     *
     * @param dsf forma za smenu
     */
    public DodajSmenuController(DodajSmenuForma dsf) {
        this.dsf = dsf;
        addActionListeners();
    }

    /**
     * Priprema formu prema datom modu i prikazuje je.
     *
     * @param mod rezim rada forme (dodavanje ili izmena)
     */
    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        dsf.setVisible(true);
        //dsf.setLocationRelativeTo(null);
    }

    /**
     * Registruje listenere za dugmad Dodaj i Izmeni.
     */
    private void addActionListeners() {

        // 🔹 DODAJ SMENU
        dsf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = dsf.getjTextFieldNaziv().getText().trim();
                String pocetakTxt = dsf.getjTextFieldPocetak().getText().trim();
                String krajTxt = dsf.getjTextFieldKraj().getText().trim();

                if (naziv.isEmpty() || pocetakTxt.isEmpty() || krajTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(dsf, "Sva polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    LocalTime pocetak = LocalTime.parse(pocetakTxt);
                    LocalTime kraj = LocalTime.parse(krajTxt);

                    Smena s = new Smena(0, naziv, pocetak, kraj);

                    Komunikacija.getInstance().dodajSmenu(s);
                    JOptionPane.showMessageDialog(dsf, "Sistem je zapamtio smenu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dsf.dispose();

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dsf,
                            "Format vremena nije ispravan. Unesite vreme u formatu HH:mm (npr. 08:00).",
                            "Greska",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dsf, ex.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dsf, "Sistem ne može da zapamti smenu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 🔹 IZMENI SMENU
        dsf.izmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(dsf.getjTextFieldId().getText());
                    String naziv = dsf.getjTextFieldNaziv().getText().trim();
                    String pocetakTxt = dsf.getjTextFieldPocetak().getText().trim();
                    String krajTxt = dsf.getjTextFieldKraj().getText().trim();

                    if (naziv.isEmpty() || pocetakTxt.isEmpty() || krajTxt.isEmpty()) {
                        JOptionPane.showMessageDialog(dsf, "Sva polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    LocalTime pocetak = LocalTime.parse(pocetakTxt);
                    LocalTime kraj = LocalTime.parse(krajTxt);

                    Smena s = new Smena(id, naziv, pocetak, kraj);

                    Komunikacija.getInstance().izmeniSmenu(s);
                    JOptionPane.showMessageDialog(dsf, "Sistem je izmenio smenu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dsf.dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dsf, "Id mora biti broj.", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dsf,
                            "Format vremena nije ispravan. Unesite vreme u formatu HH:mm (npr. 08:00).",
                            "Greska",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dsf, ex.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dsf, "Sistem ne može da izmeni smenu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Podesava vidljivost dugmadi i popunjava polja u zavisnosti od moda.
     * Za izmenu smena se cita iz parametra koordinatora <code>smena</code>.
     *
     * @param mod rezim rada forme
     */
    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                dsf.getjTextFieldId().setEditable(false);
                dsf.getjButtonIzmeni().setVisible(false);
                dsf.getjButtonDodaj().setVisible(true);
                dsf.getjButtonDodaj().setEnabled(true);
                break;

            case IZMENI:
                dsf.getjButtonDodaj().setVisible(false);
                dsf.getjButtonIzmeni().setVisible(true);
                dsf.getjButtonIzmeni().setEnabled(true);

                Smena s = (Smena) Cordinator.getInstance().vratiParam("smena");

                dsf.getjTextFieldId().setText(String.valueOf(s.getIdSmena()));
                dsf.getjTextFieldNaziv().setText(s.getNaziv());
                dsf.getjTextFieldPocetak().setText(s.getVremePocetka().toString());
                dsf.getjTextFieldKraj().setText(s.getVremeKraja().toString());
                break;

            default:
                throw new AssertionError();
        }
    }
}
