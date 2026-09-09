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
 * Kontroler za dodavanje i izmenu smene.
 * @author Cofara
 */
public class DodajSmenuController {

    private final DodajSmenuForma dsf;

    public DodajSmenuController(DodajSmenuForma dsf) {
        this.dsf = dsf;
        addActionListeners();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        dsf.setVisible(true);
        //dsf.setLocationRelativeTo(null);
    }

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

                    Smena s = new Smena(-1, naziv, pocetak, kraj);

                    Komunikacija.getInstance().dodajSmenu(s);
                    JOptionPane.showMessageDialog(dsf, "Sistem je zapamtio smenu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dsf.dispose();

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dsf,
                            "Format vremena nije ispravan. Unesite vreme u formatu HH:mm (npr. 08:00).",
                            "Greška",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dsf, "Sistem ne može da zapamti smenu.", "Greška", JOptionPane.ERROR_MESSAGE);
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

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dsf,
                            "Format vremena nije ispravan. Unesite vreme u formatu HH:mm (npr. 08:00).",
                            "Greška",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dsf, "Sistem ne može da izmeni smenu.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

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
