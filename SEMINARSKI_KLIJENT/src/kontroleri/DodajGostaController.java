/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Cofara
 */
public class DodajGostaController {
    
    private final DodajGostaForma dpf;

    public DodajGostaController(DodajGostaForma dpf) {
        this.dpf = dpf;
        addActionListener();
    }

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

                Komunikacija.getInstance().konekcija();
                Gost g = new Gost(-1, ime, prezime, izabranaKategorija);

                try {
                    Komunikacija.getInstance().dodajGosta(g);
                    JOptionPane.showMessageDialog(dpf, "Gost je uspešno dodat!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    Cordinator.getInstance().osveziTabeluGostiju();
                    dpf.dispose();
                } catch (Exception exc) {
                    //JOptionPane.showMessageDialog(dpf, "Greška prilikom dodavanja gosta.", "Greška", JOptionPane.ERROR_MESSAGE);
                    dpf.dispose();
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
                int id = Integer.parseInt(dpf.getjTextFieldId().getText());
                String ime = dpf.getjTextFieldIme().getText().trim();
                String prezime = dpf.getjTextFieldPrezime().getText().trim();
                KategorijaGosta izabranaKategorija = (KategorijaGosta) dpf.getjComboBoxKategorijaGosta().getSelectedItem();

                if (izabranaKategorija == null) {
                    JOptionPane.showMessageDialog(dpf, "Morate izabrati kategoriju gosta!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Komunikacija.getInstance().konekcija();
                Gost g = new Gost(id, ime, prezime, izabranaKategorija);

                try {
                    Komunikacija.getInstance().izmaniGosta(g);
                    JOptionPane.showMessageDialog(dpf, "Gost je uspešno izmenjen!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    Cordinator.getInstance().osveziTabeluGostiju();
                    dpf.dispose();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(dpf, "Greška prilikom izmene gosta.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }

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
