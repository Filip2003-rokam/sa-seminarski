package kontroleri;

import cordinator.Cordinator;
import domen.Smena;
import forme.PrikazSmenaForma;
import forme.model.ModelTabeleSmena;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za prikaz smena ({@link forme.PrikazSmenaForma}).
 * U klijentskom MVC-u ucitava listu smena sa servera, omogucava pretragu,
 * brisanje i otvaranje forme za izmenu preko {@link cordinator.Cordinator}-a.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class PrikazSmenaController {

    /** Forma za prikaz smena kojom ovaj kontroler upravlja. */
    private final PrikazSmenaForma psf;

    /**
     * Kreira kontroler i registruje listenere korisnickih akcija.
     *
     * @param psf forma za prikaz smena
     */
    public PrikazSmenaController(PrikazSmenaForma psf) {
        this.psf = psf;
        addActionListeners();
    }

    /**
     * Priprema podatke, centrira i prikazuje formu.
     */
    public void otvoriFormu() {
        pripremiFormu();
        psf.setVisible(true);
        psf.setLocationRelativeTo(null);
    }

    /**
     * Ucitava smene sa servera i postavlja model tabele.
     */
    private void pripremiFormu() {
        List<Smena> smene = Komunikacija.getInstance().ucitajSmene();
        ModelTabeleSmena mts = new ModelTabeleSmena(smene);
        psf.getjTableSmene().setModel(mts);
    }

    /**
     * Registruje listenere: obrisi, izmeni, pretrazi, resetuj.
     */
    private void addActionListeners() {

        
        // 🔹 Obrisi
        psf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = psf.getjTableSmene().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(psf, "Označite smenu koju želite da obrišete", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleSmena mts = (ModelTabeleSmena) psf.getjTableSmene().getModel();
                    Smena s = mts.getSmene().get(red);

                    try {
                        Komunikacija.getInstance().obrisiSmenu(s);
                        JOptionPane.showMessageDialog(psf, "Sistem je obrisao smenu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(psf, "Sistem ne može da obriše smenu.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        // 🔹 Azuriraj
        psf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = psf.getjTableSmene().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(psf, "Označite smenu koju želite da izmenite", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleSmena mts = (ModelTabeleSmena) psf.getjTableSmene().getModel();
                    Smena s = mts.getSmene().get(red);

                    Cordinator.getInstance().dodajParam("smena", s);
                    Cordinator.getInstance().otvoriIzmeniSmenuFormu();
                }
            }
        });



        // 🔹 Pretrazi
        psf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = psf.getjTextFieldNaziv().getText().trim();
                String pocetak = psf.getjTextFieldPocetak().getText().trim();
                String kraj = psf.getjTextFieldKraj().getText().trim();

                ModelTabeleSmena mts = (ModelTabeleSmena) psf.getjTableSmene().getModel();
                mts.pretrazi(naziv, pocetak, kraj);
            }
        });
        
        

        // 🔹 Resetuj
        psf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
                psf.getjTextFieldNaziv().setText("");
                psf.getjTextFieldPocetak().setText("");
                psf.getjTextFieldKraj().setText("");
            }
        });
    }

    /**
     * Osvezava prikaz smena (ponovo ucitava podatke sa servera).
     */
    public void osveziPrikazSmenaFormu() {
        pripremiFormu();
    }
}
