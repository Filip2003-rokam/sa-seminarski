package kontroleri;

import cordinator.Cordinator;
import domen.KategorijaGosta;
import forme.PrikazKategorijeGostijuForma;
import forme.model.ModelTabeleKategorijaGosta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za prikaz kategorija gostiju
 * ({@link forme.PrikazKategorijeGostijuForma}).
 * U klijentskom MVC-u ucitava kategorije sa servera i omogucava pretragu,
 * brisanje i otvaranje forme za izmenu.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class PrikazKategorijeGostijuController {

    /** Forma za prikaz kategorija gostiju kojom ovaj kontroler upravlja. */
    private final PrikazKategorijeGostijuForma pkf;

    /**
     * Kreira kontroler i registruje listenere korisnickih akcija.
     *
     * @param pkf forma za prikaz kategorija gostiju
     */
    public PrikazKategorijeGostijuController(PrikazKategorijeGostijuForma pkf) {
        this.pkf = pkf;
        addActionListeners();
    }

    /**
     * Priprema podatke, centrira i prikazuje formu.
     */
    public void otvoriFormu() {
        pripremiFormu();
        pkf.setVisible(true);
        pkf.setLocationRelativeTo(null);
    }

    /**
     * Ucitava kategorije gostiju sa servera i postavlja model tabele.
     */
    private void pripremiFormu() {
        List<KategorijaGosta> kategorije = Komunikacija.getInstance().ucitajKategorijeGostiju();
        ModelTabeleKategorijaGosta mtk = new ModelTabeleKategorijaGosta(kategorije);
        pkf.getjTableKategorijaGostiju().setModel(mtk);
    }

    /**
     * Registruje listenere: obrisi, izmeni, pretrazi, resetuj.
     */
    private void addActionListeners() {

        
        // 🔹 Obrisi
        pkf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getjTableKategorijaGostiju().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkf, "Označite kategoriju koju želite da obrišete", "GREŠKA", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleKategorijaGosta mtk = (ModelTabeleKategorijaGosta) pkf.getjTableKategorijaGostiju().getModel();
                    KategorijaGosta kg = mtk.getLista().get(red);

                    try {
                        Komunikacija.getInstance().obrisiKategorijuGosta(kg);
                        JOptionPane.showMessageDialog(pkf, "Sistem je obrisao kategoriju gosta.", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(pkf, "Sistem ne može da obriše kategoriju gosta.", "GREŠKA", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        
        // 🔹 Azuriraj
        pkf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getjTableKategorijaGostiju().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkf, "Označite kategoriju koju želite da izmenite", "GREŠKA", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleKategorijaGosta mtk = (ModelTabeleKategorijaGosta) pkf.getjTableKategorijaGostiju().getModel();
                    KategorijaGosta kg = mtk.getLista().get(red);

                    Cordinator.getInstance().dodajParam("kategorijaGosta", kg);
                    Cordinator.getInstance().otvoriIzmeniKategorijuGostaFormu();
                }
            }
        });

        // 🔹 Pretrazi
        pkf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = pkf.getjTextFieldOpis().getText().trim();

                Double popust = null;
                if (!pkf.getjTextFieldPopust().getText().trim().isEmpty()) {
                    try {
                        popust = Double.parseDouble(pkf.getjTextFieldPopust().getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(pkf, "Popust mora biti broj!", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                ModelTabeleKategorijaGosta mtk = (ModelTabeleKategorijaGosta) pkf.getjTableKategorijaGostiju().getModel();
                mtk.pretrazi(naziv, popust);
            }
        });


        // 🔹 Resetuj
        pkf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
                pkf.getjTextFieldOpis().setText("");
            }
        });
        
        
    }

    /**
     * Osvezava prikaz kategorija gostiju (ponovo ucitava podatke sa servera).
     */
    public void osveziPrikazKategorijaGostijuFormu() {
        pripremiFormu();
    }
}
