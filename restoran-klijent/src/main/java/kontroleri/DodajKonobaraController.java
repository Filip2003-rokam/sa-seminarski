package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import forme.DodajKonobaraForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za dodavanje i izmenu konobara ({@link forme.DodajKonobaraForma}).
 * U klijentskom MVC-u prikuplja ime, prezime, korisnicko ime i lozinku sa forme
 * i salje zahteve serveru preko {@link komunikacija.Komunikacija}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajKonobaraController {

    /** Forma za dodavanje/izmenu konobara kojom ovaj kontroler upravlja. */
    private final DodajKonobaraForma dkf;

    /**
     * Kreira kontroler i registruje listenere za dodavanje i izmenu.
     *
     * @param dkf forma za konobara
     */
    public DodajKonobaraController(DodajKonobaraForma dkf) {
        this.dkf = dkf;
        addActionListener();
    }

    /**
     * Priprema formu prema datom modu i prikazuje je.
     *
     * @param mod rezim rada forme (dodavanje ili izmena)
     */
    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        dkf.setVisible(true);
    }

    /**
     * Registruje listenere za dugmad Dodaj i Izmeni.
     */
    private void addActionListener() {
        // ➕ Dodaj
        dkf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj();
            }

            private void dodaj() {
                try {
                    String ime = dkf.getjTextFieldIme().getText().trim();
                    String prezime = dkf.getjTextFieldPrezime().getText().trim();
                    String username = dkf.getjTextFieldUsername().getText().trim();
                    String password = String.valueOf(dkf.getjPasswordField1().getPassword()).trim();

                    Konobar k = new Konobar(0, ime, prezime, username, password);
                    Komunikacija.getInstance().dodajKonobara(k);
                    JOptionPane.showMessageDialog(dkf, "Sistem je zapamtio konobara.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dkf.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dkf, ex.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne može da zapamti konobara.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ✏ Izmeni
        dkf.izmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni();
            }

            private void izmeni() {
                try {
                    int id = Integer.parseInt(dkf.getjTextFieldId().getText());
                    String ime = dkf.getjTextFieldIme().getText().trim();
                    String prezime = dkf.getjTextFieldPrezime().getText().trim();
                    String username = dkf.getjTextFieldUsername().getText().trim();
                    String password = String.valueOf(dkf.getjPasswordField1().getPassword()).trim();

                    Konobar k = new Konobar(id, ime, prezime, username, password);
                    Komunikacija.getInstance().izmeniKonobara(k);
                    JOptionPane.showMessageDialog(dkf, "Sistem je izmenio konobara.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dkf.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dkf, "Id mora biti broj.", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dkf, ex.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne može da izmeni konobara.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Podesava vidljivost dugmadi i popunjava polja u zavisnosti od moda.
     * Za izmenu konobar se cita iz parametra koordinatora <code>konobar</code>.
     *
     * @param mod rezim rada forme
     */
    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                dkf.getjTextFieldId().setEditable(false);
                dkf.getjButtonIzmeni().setVisible(false);
                dkf.getjButtonDodaj().setVisible(true);
                dkf.getjButtonDodaj().setEnabled(true);
                break;

            case IZMENI:
                dkf.getjButtonDodaj().setVisible(false);
                dkf.getjButtonIzmeni().setVisible(true);
                dkf.getjButtonIzmeni().setEnabled(true);

                Konobar k = (Konobar) Cordinator.getInstance().vratiParam("konobar");

                dkf.getjTextFieldId().setText(String.valueOf(k.getIdKonobar()));
                dkf.getjTextFieldIme().setText(k.getIme());
                dkf.getjTextFieldPrezime().setText(k.getPrezime());
                dkf.getjTextFieldUsername().setText(k.getKorisnickoIme());
                //dkf.getjTextFieldPassword().setText(k.getPassword());
                break;

            default:
                throw new AssertionError();
        }
    }
}
