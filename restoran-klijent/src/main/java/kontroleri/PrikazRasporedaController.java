package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import domen.KonobarSmena;
import domen.Smena;
import forme.PrikazRasporedaForma;
import forme.model.ModelTabeleRaspored;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler za prikaz rasporeda (KonobarSmena)
 * koji povezuje konobare, smene i datume rada.
 * @author Cofara
 */
public class PrikazRasporedaController {

    private final PrikazRasporedaForma prf;

    public PrikazRasporedaController(PrikazRasporedaForma prf) {
        this.prf = prf;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        prf.setVisible(true);
        prf.setLocationRelativeTo(null);
    }

    private void pripremiFormu() {
        try {
            // 🔹 Učitaj listu konobara
            List<Konobar> konobari = Komunikacija.getInstance().ucitajKonobare();
            prf.getjComboBoxKonobar().removeAllItems();
            prf.getjComboBoxKonobar().addItem(null);
            // ubaci sve objekte u ComboBox
            for (Konobar k : konobari) {
                prf.getjComboBoxKonobar().addItem(k);
            }


            // 🔹 Učitaj listu smena
            List<Smena> smene = Komunikacija.getInstance().ucitajSmene();
            //DefaultComboBoxModel<Smena> modelSmene = new DefaultComboBoxModel<>();
            prf.getjComboBoxSmena().removeAllItems();
            prf.getjComboBoxSmena().addItem(null);
            for (Smena s : smene) {
                prf.getjComboBoxSmena().addItem(s);
            }
            

            // 🔹 Učitaj ceo raspored (KonobarSmena)
            List<KonobarSmena> raspored = Komunikacija.getInstance().ucitajRaspored();
            ModelTabeleRaspored mtr = new ModelTabeleRaspored(raspored);
            prf.getjTableRaspored().setModel(mtr);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(prf, "Sistem ne može da učita podatke za prikaz rasporeda.", "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addActionListeners() {

        
        // 🔹 Pretraga pomoću combo boxeva i datuma
        prf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Konobar izabraniKonobar = (Konobar) prf.getjComboBoxKonobar().getSelectedItem();
                Smena izabranaSmena = (Smena) prf.getjComboBoxSmena().getSelectedItem();
                String datumTxt = prf.getjTextFieldDatum().getText().trim();

                LocalDate datum = null;
                if (!datumTxt.isEmpty()) {
                    try {
                        datum = LocalDate.parse(datumTxt);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(prf, "Format datuma nije ispravan (koristite yyyy-MM-dd).", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                ModelTabeleRaspored mtr = (ModelTabeleRaspored) prf.getjTableRaspored().getModel();
                mtr.pretrazi(izabraniKonobar, izabranaSmena, datum);
            }
        });

        // 🔹 Reset
        prf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prf.getjComboBoxKonobar().setSelectedItem(null);
                prf.getjComboBoxSmena().setSelectedItem(null);
                prf.getjTextFieldDatum().setText("");
                pripremiFormu();
            }
        });
        
        

        // 🔹 Obrisi
        prf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = prf.getjTableRaspored().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(prf, "Označite raspored koji želite da obrišete.", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleRaspored mtr = (ModelTabeleRaspored) prf.getjTableRaspored().getModel();
                    KonobarSmena ks = mtr.getLista().get(red);

                    try {
                        Komunikacija.getInstance().obrisiRaspored(ks);
                        JOptionPane.showMessageDialog(prf, "Sistem je obrisao raspored.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(prf, "Sistem ne može da obriše raspored.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 🔹 Azuriraj
        prf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = prf.getjTableRaspored().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(prf, "Označite raspored koji želite da izmenite.", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleRaspored mtr = (ModelTabeleRaspored) prf.getjTableRaspored().getModel();
                    KonobarSmena ks = mtr.getLista().get(red);
                    Cordinator.getInstance().dodajParam("raspored", ks);
                    Cordinator.getInstance().otvoriIzmeniRasporedFormu();
                }
            }
        });

        
    }

    public void osveziPrikazRasporedaFormu() {
        pripremiFormu();
    }
}
