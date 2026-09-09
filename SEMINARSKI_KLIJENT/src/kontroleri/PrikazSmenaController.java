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
 *
 * @author Cofara
 */
public class PrikazSmenaController {

    private final PrikazSmenaForma psf;

    public PrikazSmenaController(PrikazSmenaForma psf) {
        this.psf = psf;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        psf.setVisible(true);
        psf.setLocationRelativeTo(null);
    }

    private void pripremiFormu() {
        List<Smena> smene = Komunikacija.getInstance().ucitajSmene();
        ModelTabeleSmena mts = new ModelTabeleSmena(smene);
        psf.getjTableSmene().setModel(mts);
    }

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

    public void osveziPrikazSmenaFormu() {
        pripremiFormu();
    }
}
