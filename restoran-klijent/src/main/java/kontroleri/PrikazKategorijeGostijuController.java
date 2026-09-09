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
 *
 * @author Cofara
 */
public class PrikazKategorijeGostijuController {

    private final PrikazKategorijeGostijuForma pkf;

    public PrikazKategorijeGostijuController(PrikazKategorijeGostijuForma pkf) {
        this.pkf = pkf;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pkf.setVisible(true);
        pkf.setLocationRelativeTo(null);
    }

    private void pripremiFormu() {
        List<KategorijaGosta> kategorije = Komunikacija.getInstance().ucitajKategorijeGostiju();
        ModelTabeleKategorijaGosta mtk = new ModelTabeleKategorijaGosta(kategorije);
        pkf.getjTableKategorijaGostiju().setModel(mtk);
    }

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

    public void osveziPrikazKategorijaGostijuFormu() {
        pripremiFormu();
    }
}
