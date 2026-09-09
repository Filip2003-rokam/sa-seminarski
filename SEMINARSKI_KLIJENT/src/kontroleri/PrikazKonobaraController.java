package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import forme.PrikazKonobaraForma;
import forme.model.ModelTabeleKonobar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Cofara
 */
public class PrikazKonobaraController {

    private final PrikazKonobaraForma pkf;

    public PrikazKonobaraController(PrikazKonobaraForma pkf) {
        this.pkf = pkf;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pkf.setVisible(true);
        pkf.setLocationRelativeTo(null);
    }

    private void pripremiFormu() {
        List<Konobar> konobari = Komunikacija.getInstance().ucitajKonobare();
        ModelTabeleKonobar mtk = new ModelTabeleKonobar(konobari);
        pkf.getjTableKonobari().setModel(mtk);
    }

    private void addActionListeners() {

        // 🔹 Obrisi
        pkf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getjTableKonobari().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkf, "Označite konobara koga želite da obrišete", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleKonobar mtk = (ModelTabeleKonobar) pkf.getjTableKonobari().getModel();
                    Konobar k = mtk.getLista().get(red);

                    try {
                        Komunikacija.getInstance().obrisiKonobara(k);
                        JOptionPane.showMessageDialog(pkf, "Sistem je obrisao konobara.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(pkf, "Sistem ne može da obriše konobara.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 🔹 Azuriraj
        pkf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getjTableKonobari().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkf, "Označite konobara koga želite da izmenite", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleKonobar mtk = (ModelTabeleKonobar) pkf.getjTableKonobari().getModel();
                    Konobar k = mtk.getLista().get(red);

                    Cordinator.getInstance().dodajParam("konobar", k);
                    Cordinator.getInstance().otvoriIzmeniKonobaraFormu();
                }
            }
        });

        // 🔹 Pretrazi
        pkf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = pkf.getjTextFieldIme().getText().trim();
                String prezime = pkf.getjTextFieldPrezime().getText().trim();
                String username = pkf.getjTextFieldUsername().getText().trim();

                ModelTabeleKonobar mtk = (ModelTabeleKonobar) pkf.getjTableKonobari().getModel();
                mtk.pretrazi(ime, prezime, username);
            }
        });

        // 🔹 Resetuj
        pkf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
                pkf.getjTextFieldIme().setText("");
                pkf.getjTextFieldPrezime().setText("");
                pkf.getjTextFieldUsername().setText("");
            }
        });
    }

    public void osveziPrikazKonobaraFormu() {
        pripremiFormu();
    }
}
