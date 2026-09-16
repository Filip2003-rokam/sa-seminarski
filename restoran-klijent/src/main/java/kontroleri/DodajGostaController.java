package kontroleri;

import cordinator.Cordinator;
import domen.Gost;
import domen.KategorijaGosta;
import domen.Konobar;
import forme.DodajGostaForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za dodavanje i izmenu gosta ({@link forme.DodajGostaForma}).
 * U klijentskom MVC-u povezuje formu sa serverom: ucitava kategorije gostiju,
 * salje zahteve za dodavanje/izmenu i osvezava tabelu gostiju preko koordinatora.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajGostaController {
    
    /** Forma za dodavanje/izmenu gosta kojom ovaj kontroler upravlja. */
    private final DodajGostaForma dpf;

    /**
     * Kreira kontroler i registruje listenere za dodavanje i izmenu.
     *
     * @param dpf forma za gosta
     */
    public DodajGostaController(DodajGostaForma dpf) {
        this.dpf = dpf;
        addActionListener();
    }

    /**
     * Priprema formu prema modu, popunjava combo box kategorija i prikazuje formu.
     *
     * @param mod rezim rada forme (dodavanje ili izmena)
     */
    public void otvoriFormu(FormaMod mod){
        pripremiFormu(mod);
        
        List<KategorijaGosta> kategorije = Komunikacija.getInstance().ucitajKategorijeGostiju();
            dpf.getjComboBoxKategorijaGosta().removeAllItems();
            dpf.getjComboBoxKategorijaGosta().addItem(null);
            for (KategorijaGosta kg : kategorije) {
                dpf.getjComboBoxKategorijaGosta().addItem(kg);
            }
        
        dpf.setVisible(true);
    }
    
    /**
     * Registruje listenere za dugmad Dodaj i Izmeni.
     */
    private void addActionListener() {
        
         // 🔹 DODAJ GOSTA
        dpf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String ime = dpf.getjTextFieldIme().getText().trim();
                String prezime = dpf.getjTextFieldPrezime().getText().trim();
                KategorijaGosta izabranaKategorija = (KategorijaGosta) dpf.getjComboBoxKategorijaGosta().getSelectedItem();

                if (izabranaKategorija == null) {
                    JOptionPane.showMessageDialog(dpf, "Morate izabrati kategoriju gosta!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Komunikacija.getInstance().konekcija();
                    Gost g = new Gost(0, ime, prezime, izabranaKategorija);
                    Komunikacija.getInstance().dodajGosta(g);
                    JOptionPane.showMessageDialog(dpf, "Gost je uspešno dodat!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    Cordinator.getInstance().osveziTabeluGostiju();
                    dpf.dispose();
                } catch (IllegalArgumentException exc) {
                    JOptionPane.showMessageDialog(dpf, exc.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(dpf, "Greška prilikom dodavanja gosta.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 🔹 IZMENI GOSTA
        dpf.izmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                String ime = dpf.getjTextFieldIme().getText().trim();
                String prezime = dpf.getjTextFieldPrezime().getText().trim();
                KategorijaGosta izabranaKategorija = (KategorijaGosta) dpf.getjComboBoxKategorijaGosta().getSelectedItem();

                if (izabranaKategorija == null) {
                    JOptionPane.showMessageDialog(dpf, "Morate izabrati kategoriju gosta!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(dpf.getjTextFieldId().getText());
                    Komunikacija.getInstance().konekcija();
                    Gost g = new Gost(id, ime, prezime, izabranaKategorija);
                    Komunikacija.getInstance().izmaniGosta(g);
                    JOptionPane.showMessageDialog(dpf, "Gost je uspešno izmenjen!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    Cordinator.getInstance().osveziTabeluGostiju();
                    dpf.dispose();
                } catch (NumberFormatException exc) {
                    JOptionPane.showMessageDialog(dpf, "Id mora biti broj.", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException exc) {
                    JOptionPane.showMessageDialog(dpf, exc.getMessage(), "Neispravan unos", JOptionPane.WARNING_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(dpf, "Greška prilikom izmene gosta.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }

    /**
     * Podesava vidljivost dugmadi i popunjava polja u zavisnosti od moda.
     * Za izmenu gost se cita iz parametra koordinatora <code>gost</code>.
     *
     * @param mod rezim rada forme
     */
    private void pripremiFormu(FormaMod mod) {
        
        switch (mod) {
            case DODAJ:
                dpf.getjTextFieldId().setEditable(false);
                dpf.getjButtonIzmeni().setVisible(false);
                dpf.getjButtonDodaj().setVisible(true);
                dpf.getjButtonDodaj().setEnabled(true);
                
                break;

            case IZMENI:
                dpf.getjButtonDodaj().setVisible(false);
                dpf.getjButtonIzmeni().setVisible(true);
                dpf.getjButtonIzmeni().setEnabled(true);
                
                Gost g = (Gost) Cordinator.getInstance().vratiParam("gost");
                
                dpf.getjTextFieldIme().setText(g.getIme());
                dpf.getjTextFieldPrezime().setText(g.getPrezime());
                dpf.getjTextFieldId().setText(g.getIdGost() + "");
                
                dpf.getjComboBoxKategorijaGosta().setSelectedItem(g.getKategorijaGosta());

                
                break;

            default:
                throw new AssertionError();
        }

        
    }
    
    
    
}
