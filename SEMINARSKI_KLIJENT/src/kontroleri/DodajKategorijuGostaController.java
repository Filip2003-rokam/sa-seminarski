package kontroleri;

import cordinator.Cordinator;
import domen.KategorijaGosta;
import forme.DodajKategorijuGostaForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Cofara
 */
public class DodajKategorijuGostaController {

    private final DodajKategorijuGostaForma dkf;

    public DodajKategorijuGostaController(DodajKategorijuGostaForma dkf) {
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
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String naziv = dkf.getjTextFieldNaziv().getText().trim();
                double popust;

                try {
                    popust = Double.parseDouble(dkf.getjTextFieldPopust().getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dkf, "Popust mora biti broj!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Komunikacija.getInstance().konekcija();
                KategorijaGosta kg = new KategorijaGosta(-1, naziv, popust, true);

                try {
                    Komunikacija.getInstance().dodajKategorijuGosta(kg);
                    JOptionPane.showMessageDialog(dkf, "Sistem je zapamtio kategoriju gosta.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dkf.dispose();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne može da zapamti kategoriju gosta.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ✏ Izmeni
        dkf.izmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                int id = Integer.parseInt(dkf.getjTextFieldId().getText());
                String naziv = dkf.getjTextFieldNaziv().getText().trim();
                double popust;

                try {
                    popust = Double.parseDouble(dkf.getjTextFieldPopust().getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dkf, "Popust mora biti broj!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Komunikacija.getInstance().konekcija();
                
                // ovo ima popust cemo postaviti da uvek bude true jer je beskoristan parametar
                KategorijaGosta kg = new KategorijaGosta(id, naziv, popust,true);

                try {
                    Komunikacija.getInstance().izmeniKategorijuGosta(kg);
                    JOptionPane.showMessageDialog(dkf, "Sistem je izmenio kategoriju gosta.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dkf.dispose();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne može da izmeni kategoriju gosta.", "Greška", JOptionPane.ERROR_MESSAGE);
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

                KategorijaGosta kg = (KategorijaGosta) Cordinator.getInstance().vratiParam("kategorijaGosta");

                dkf.getjTextFieldId().setText(String.valueOf(kg.getIdKategorijaGosta()));
                dkf.getjTextFieldNaziv().setText(kg.getOpis());
                dkf.getjTextFieldPopust().setText(String.valueOf(kg.getPopust()));
                break;

            default:
                throw new AssertionError();
        }
    }
}
