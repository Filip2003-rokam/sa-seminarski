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
 * Kontroler forme za dodavanje i izmenu artikla ({@link forme.DodajArtikalForma}).
 * U klijentskom MVC-u prikuplja podatke sa forme, salje zahteve serveru
 * preko {@link komunikacija.Komunikacija} i prilagodjava UI prema modu
 * ({@link forme.FormaMod#DODAJ} ili {@link forme.FormaMod#IZMENI}).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajArtikalController {
    
    /** Forma za dodavanje/izmenu artikla kojom ovaj kontroler upravlja. */
    private final DodajArtikalForma djpf;

    /**
     * Kreira kontroler i registruje listenere za dodavanje i izmenu.
     *
     * @param djpf forma za artikal
     */
    public DodajArtikalController(DodajArtikalForma djpf) {
        this.djpf = djpf;
        addActionListener();
    }

    /**
     * Priprema formu prema datom modu i prikazuje je.
     *
     * @param mod rezim rada forme (dodavanje ili izmena)
     */
    public void otvoriFormu(FormaMod mod){
        pripremiFormu(mod); 
        djpf.setVisible(true);
    }
    
    /**
     * Registruje listenere za dugmad Dodaj i Izmeni.
     */
    private void addActionListener() {
        
        djpf.dodajAddActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                try {
                    String naziv = djpf.getjTextFieldNaziv().getText().trim();
                    String tip = djpf.getjTextFieldTip().getText().trim();
                    double cena = Double.parseDouble(djpf.getjTextFieldCena().getText().trim());

                    Komunikacija.getInstance().konekcija();
                    Artikal artikal = new Artikal(0, naziv, cena, tip);
                    Komunikacija.getInstance().dodajArtikal(artikal);
                    JOptionPane.showMessageDialog(djpf, "Sistem je zapamtio artikal.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    djpf.dispose();
                } catch (NumberFormatException exc) {
                    JOptionPane.showMessageDialog(djpf, "Cena mora biti broj.", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException exc) {
                    JOptionPane.showMessageDialog(djpf, exc.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(djpf, "Sistem ne može da zapamti artikal.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        djpf.izmeniAddActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                try {
                    int id = Integer.parseInt(djpf.getjTextFieldId().getText());
                    String naziv = djpf.getjTextFieldNaziv().getText().trim();
                    String tip = djpf.getjTextFieldTip().getText().trim();
                    double cena = Double.parseDouble(djpf.getjTextFieldCena().getText().trim());

                    Komunikacija.getInstance().konekcija();
                    Artikal artikal = new Artikal(id, naziv, cena, tip);
                    Komunikacija.getInstance().izmeniArtikal(artikal);
                    JOptionPane.showMessageDialog(djpf, "Sistem je izmenio artikal.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    djpf.dispose();
                } catch (NumberFormatException exc) {
                    JOptionPane.showMessageDialog(djpf, "Id i cena moraju biti brojevi.", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException exc) {
                    JOptionPane.showMessageDialog(djpf, exc.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(djpf, "Sistem ne može da izmeni artikal.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Podesava vidljivost dugmadi i popunjava polja u zavisnosti od moda.
     * Za izmenu artikal se cita iz parametra koordinatora <code>artikal</code>.
     *
     * @param mod rezim rada forme
     */
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
