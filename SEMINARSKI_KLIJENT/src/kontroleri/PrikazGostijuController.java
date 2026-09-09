/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Gost;
import domen.KategorijaGosta;
import forme.PrikazGostijuForma;
import forme.model.ModelTabeleGosti;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Cofara
 */
public class PrikazGostijuController {
    
    private final PrikazGostijuForma ppf;

    public PrikazGostijuController(PrikazGostijuForma ppf) {
        this.ppf = ppf;
        addActionListener();
    }

    public void otvoriFormu() {
        pripremiFormu();
        //ppf.show(true);
        ppf.setVisible(true);
    }

    public void pripremiFormu() {
        
        List<Gost> gosti = komunikacija.Komunikacija.getInstance().ucitajGoste();
        

            List<KategorijaGosta> kategorije = Komunikacija.getInstance().ucitajKategorijeGostiju();
            ppf.getjComboBoxKategorijaGosta().removeAllItems();
            ppf.getjComboBoxKategorijaGosta().addItem(null);
            for (KategorijaGosta kg : kategorije) {
                ppf.getjComboBoxKategorijaGosta().addItem(kg);
            }
        
        ModelTabeleGosti mtg = new ModelTabeleGosti(gosti);
        ppf.getjTableGosti().setModel(mtg);
    }

        private void addActionListener() {
            ppf.addBtnObrisiActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int red = ppf.getjTableGosti().getSelectedRow();
                    if (red == -1) {
                        JOptionPane.showMessageDialog(ppf,
                                "Morate izabrati gosta kojeg želite da obrišete.",
                                "Greška", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ModelTabeleGosti mtg = (ModelTabeleGosti) ppf.getjTableGosti().getModel();

                        // uzmi baš jednog gosta iz liste prema selektovanom redu
                        Gost g = mtg.getGosti().get(red);

                        try {
                            Komunikacija.getInstance().obrisiGosta(g);
                            JOptionPane.showMessageDialog(ppf,
                                    "Sistem je uspešno obrisao gosta.",
                                    "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                            // osveži tabelu da više ne prikazuje obrisanog gosta
                            pripremiFormu();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ppf,
                                    "Sistem ne može da obriše gosta.",
                                    "Greška", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            
            ppf.addBtnIzmeniActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int red = ppf.getjTableGosti().getSelectedRow();
                    if (red == -1) {
                        JOptionPane.showMessageDialog(ppf,
                                "Morate izabrati gosta kojeg želite da obrišete.",
                                "Greška", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ModelTabeleGosti mtg = (ModelTabeleGosti) ppf.getjTableGosti().getModel();

                        // uzmi baš jednog gosta iz liste prema selektovanom redu
                        Gost g = mtg.getGosti().get(red);
                        
                        cordinator.Cordinator.getInstance().dodajParam("gost", g);
                        
                        cordinator.Cordinator.getInstance().otvoriIzmeniGostaFormu();

                        
                    }
                }
            });
            
            ppf.addBtnPretraziActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    String ime = ppf.getjTextFieldIme().getText().trim();
                    String prezime = ppf.getjTextFieldPrezime().getText().trim();
                    KategorijaGosta kg = (KategorijaGosta)ppf.getjComboBoxKategorijaGosta().getSelectedItem();

                    ModelTabeleGosti mtp = (ModelTabeleGosti) ppf.getjTableGosti().getModel();
                    mtp.pretrazi(ime, prezime, kg);

                    
                }
            });
            
            ppf.addBtnResetujActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pripremiFormu();         
                }
            });
            
            ppf.addBtnDetaljiActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int red = ppf.getjTableGosti().getSelectedRow();
                    if (red == -1) {
                        JOptionPane.showMessageDialog(ppf,
                                "Morate izabrati gosta.",
                                "Greška", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ModelTabeleGosti mtg = (ModelTabeleGosti) ppf.getjTableGosti().getModel();

                        // uzmi baš jednog gosta iz liste prema selektovanom redu
                        Gost g = mtg.getGosti().get(red);
                        
                        cordinator.Cordinator.getInstance().dodajParam("gost_za_detalje", g);
                        
                        cordinator.Cordinator.getInstance().otvoriDetaljiGostaFormu();

                        
                    }
                }
            });
            
    }

    public void osveziPrikazGostijuFormu() {
        
        pripremiFormu();
        
    }


        
        
    
}
