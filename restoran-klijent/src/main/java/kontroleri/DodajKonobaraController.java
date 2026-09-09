package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import forme.DodajKonobaraForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;


public class DodajKonobaraController {

    private final DodajKonobaraForma dkf;

    public DodajKonobaraController(DodajKonobaraForma dkf) {
        this.dkf = dkf;
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        dkf.setVisible(true);
    }

    private void addActionListener() {
        // ➕ Dodaj
        dkf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj();
            }

            private void dodaj() {
                String ime = dkf.getjTextFieldIme().getText().trim();
                String prezime = dkf.getjTextFieldPrezime().getText().trim();
                String username = dkf.getjTextFieldUsername().getText().trim();
                String password = String.valueOf(dkf.getjPasswordField1().getPassword()).trim();

                Konobar k = new Konobar(-1, ime, prezime, username, password);

                try {
                    Komunikacija.getInstance().dodajKonobara(k);
                    JOptionPane.showMessageDialog(dkf, "Sistem je zapamtio konobara.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dkf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne može da zapamti konobara.", "Greška", JOptionPane.ERROR_MESSAGE);
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
                int id = Integer.parseInt(dkf.getjTextFieldId().getText());
                String ime = dkf.getjTextFieldIme().getText().trim();
                String prezime = dkf.getjTextFieldPrezime().getText().trim();
                String username = dkf.getjTextFieldUsername().getText().trim();
                String password = String.valueOf(dkf.getjPasswordField1().getPassword()).trim();

                Konobar k = new Konobar(id, ime, prezime, username, password);

                try {
                    Komunikacija.getInstance().izmeniKonobara(k);
                    JOptionPane.showMessageDialog(dkf, "Sistem je izmenio konobara.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dkf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne može da izmeni konobara.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

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
