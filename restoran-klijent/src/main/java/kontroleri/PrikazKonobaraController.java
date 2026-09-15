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
 * Kontroler forme za prikaz konobara ({@link forme.PrikazKonobaraForma}).
 * U klijentskom MVC-u ucitava listu konobara sa servera, omogucava pretragu,
 * brisanje i otvaranje forme za izmenu preko {@link cordinator.Cordinator}-a.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class PrikazKonobaraController {

    /** Forma za prikaz konobara kojom ovaj kontroler upravlja. */
    private final PrikazKonobaraForma pkf;

    /**
     * Kreira kontroler i registruje listenere korisnickih akcija.
     *
     * @param pkf forma za prikaz konobara
     */
    public PrikazKonobaraController(PrikazKonobaraForma pkf) {
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
     * Ucitava konobare sa servera i postavlja model tabele.
     */
    private void pripremiFormu() {
        List<Konobar> konobari = Komunikacija.getInstance().ucitajKonobare();
        ModelTabeleKonobar mtk = new ModelTabeleKonobar(konobari);
        pkf.getjTableKonobari().setModel(mtk);
    }

    /**
     * Registruje listenere: obrisi, izmeni, pretrazi, resetuj.
     */
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

    /**
     * Osvezava prikaz konobara (ponovo ucitava podatke sa servera).
     */
    public void osveziPrikazKonobaraFormu() {
        pripremiFormu();
    }
}
