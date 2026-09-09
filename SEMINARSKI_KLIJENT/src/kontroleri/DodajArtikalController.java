package kontroleri;

import cordinator.Cordinator;
import domen.Artikal;
import forme.DodajArtikalForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Cofara
 */
public class DodajArtikalController {
    
    private final DodajArtikalForma djpf;

    public DodajArtikalController(DodajArtikalForma djpf) {
        this.djpf = djpf;
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod){
        pripremiFormu(mod); 
        djpf.setVisible(true);
    }
    
    private void addActionListener() {
        
        djpf.dodajAddActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                
                String naziv = djpf.getjTextFieldNaziv().getText().trim();
                String tip = djpf.getjTextFieldTip().getText().trim();
                double cena = Double.parseDouble(djpf.getjTextFieldCena().getText().trim());

                Komunikacija.getInstance().konekcija();
                
                Artikal artikal = new Artikal(-1, naziv,cena, tip);
                
                try {
                   Komunikacija.getInstance().dodajArtikal(artikal); 
                   JOptionPane.showMessageDialog(djpf, "Sistem je zapamtio artikal.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                   djpf.dispose();
                } catch (Exception exc) {
                   JOptionPane.showMessageDialog(djpf, "Sistem ne može da zapamti artikal.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        djpf.izmeniAddActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                
                int id = Integer.parseInt(djpf.getjTextFieldId().getText());
                String naziv = djpf.getjTextFieldNaziv().getText().trim();
                String tip = djpf.getjTextFieldTip().getText().trim();
                double cena = Double.parseDouble(djpf.getjTextFieldCena().getText().trim());

                Komunikacija.getInstance().konekcija();
                
                Artikal artikal = new Artikal(id, naziv,cena, tip);
                
                try {
                   Komunikacija.getInstance().izmeniArtikal(artikal); 
                   JOptionPane.showMessageDialog(djpf, "Sistem je izmenio artikal.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                   djpf.dispose();
                } catch (Exception exc) {
                   JOptionPane.showMessageDialog(djpf, "Sistem ne može da izmeni artikal.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void pripremiFormu(FormaMod mod) {
        
        switch (mod) {
            case DODAJ:
                djpf.getjTextFieldId().setEditable(false);
                djpf.getjButtonIzmeni().setVisible(false);
                djpf.getjButtonDodaj().setVisible(true);
                djpf.getjButtonDodaj().setEnabled(true);
                break;

            case IZMENI:
                djpf.getjButtonDodaj().setVisible(false);
                djpf.getjButtonIzmeni().setVisible(true);
                djpf.getjButtonIzmeni().setEnabled(true);
                
                Artikal artikal = (Artikal) Cordinator.getInstance().vratiParam("artikal");
                
                djpf.getjTextFieldNaziv().setText(artikal.getNaziv());
                djpf.getjTextFieldTip().setText(artikal.getTip());
                djpf.getjTextFieldCena().setText(artikal.getCena() + "");
                djpf.getjTextFieldId().setText(artikal.getIdArtikal()+ "");
                break;

            default:
                throw new AssertionError();
        }
    }
}
