package kontroleri;

import cordinator.Cordinator;
import domen.Artikal;
import forme.PrikazArtikalaForma;
import forme.model.ModelTabeleArtikal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;



public class PrikazArtikalaController {
    
    private final PrikazArtikalaForma pjf;

    public PrikazArtikalaController(PrikazArtikalaForma pjf) {
        this.pjf = pjf;
        addActionListeners();
    }
    
    public void otvoriFormu() {
        pripremiFormu();
        pjf.setVisible(true);
        pjf.setLocationRelativeTo(null);
    }
    
    private void pripremiFormu() {
        List<Artikal> artikli = Komunikacija.getInstance().ucitajArtikle();
        ModelTabeleArtikal model = new ModelTabeleArtikal(artikli);
        pjf.getjTableArtikal().setModel(model);
    }
    
    private void addActionListeners() {
        
        // Obrisi dugme
        pjf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pjf.getjTableArtikal().getSelectedRow();
                if(red == -1){
                    JOptionPane.showMessageDialog(pjf, "Označite artikal koji želite da obrišete", "GREŠKA", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleArtikal model = (ModelTabeleArtikal) pjf.getjTableArtikal().getModel();
                    Artikal artikal = model.getLista().get(red);
                    try {
                        Komunikacija.getInstance().obrisiArtikal(artikal);
                        JOptionPane.showMessageDialog(pjf, "Sistem je obrisao artikal", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch(Exception exc) {
                        JOptionPane.showMessageDialog(pjf, "Sistem ne može da obriše artikal", "GREŠKA", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Azuriraj dugme
        pjf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pjf.getjTableArtikal().getSelectedRow();
                if(red == -1){
                    JOptionPane.showMessageDialog(pjf, "Označite artikal koji želite da izmenite", "GREŠKA", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleArtikal model = (ModelTabeleArtikal) pjf.getjTableArtikal().getModel();
                    Artikal artikal = model.getLista().get(red);
                    
                    Cordinator.getInstance().dodajParam("artikal", artikal);
                    Cordinator.getInstance().otvoriIzmeniArtikalFormu();
                }
            }
        });
        
        // Pretrazi dugme
        pjf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = pjf.getjTextFieldNaziv().getText().trim();
                String cena = pjf.getjTextFieldCena().getText().trim();
                
                ModelTabeleArtikal model = (ModelTabeleArtikal) pjf.getjTableArtikal().getModel();
                model.pretrazi(naziv, cena);
            }
        });
        
        // Resetuj dugme
        pjf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
                pjf.getjTextFieldNaziv().setText("");
                pjf.getjTextFieldCena().setText("");
                //pjf.getjTextField().setText("");
            }
        });
    }

    public void osveziPrikazArtikalaFormu() {
        pripremiFormu();
    }
    
    
}
