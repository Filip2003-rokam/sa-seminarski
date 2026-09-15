package kontroleri;

import cordinator.Cordinator;
import domen.Gost;
import domen.Konobar;
import domen.Racun;
import domen.Smena;
import domen.StavkaRacuna;
import forme.FormaMod;
import forme.PrikazRacunaForma;
import forme.model.ModelTabeleRacuni;
import forme.model.ModelTabeleStavkeRacuna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za prikaz racuna ({@link forme.PrikazRacunaForma}).
 * U klijentskom MVC-u ucitava racune, filtere (konobar, gost, smena, datum),
 * omogucava brisanje i otvaranje forme za izmenu, kao i rad sa stavkama.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class PrikazRacunaController {

    /** Forma za prikaz racuna kojom ovaj kontroler upravlja. */
    private final PrikazRacunaForma prf;

    /**
     * Kreira kontroler i registruje listenere korisnickih akcija.
     *
     * @param prf forma za prikaz racuna
     */
    public PrikazRacunaController(PrikazRacunaForma prf) {
        this.prf = prf;
        addActionListener();
    }

    /**
     * Priprema podatke, centrira i prikazuje formu.
     */
    public void otvoriFormu() {
        pripremiFormu();
        prf.setVisible(true);
        prf.setLocationRelativeTo(null);
    }

    /**
     * Ucitava konobare, goste, smene i racune; postavlja modele tabela
     * i sakriva dugmad za izmenu/brisanje stavke (izmena ide zasebnom formom).
     */
    public void pripremiFormu() {
        try {
            // 🔹 Učitaj konobare
            List<Konobar> konobari = Komunikacija.getInstance().ucitajKonobare();
            prf.getjComboBoxKonobar().removeAllItems();
            //prf.getjComboBoxKonobar().addItem(null);
            for (Konobar k : konobari) {
                prf.getjComboBoxKonobar().addItem(k);
            }

            // 🔹 Učitaj goste
            List<Gost> gosti = Komunikacija.getInstance().ucitajGoste();
            prf.getjComboBoxGost().removeAllItems();
            prf.getjComboBoxGost().addItem(null);
            for (Gost g : gosti) {
                prf.getjComboBoxGost().addItem(g);
            }

            // 🔹 Učitaj smene
            List<Smena> smene = Komunikacija.getInstance().ucitajSmene();
            prf.getjComboBoxSmena().removeAllItems();
            prf.getjComboBoxSmena().addItem(null);
            for (Smena s : smene) {
                prf.getjComboBoxSmena().addItem(s);
            }

            // 🔹 Učitaj sve račune
            List<Racun> racuni = Komunikacija.getInstance().ucitajRacune();
            ModelTabeleRacuni mtr = new ModelTabeleRacuni(racuni);
            prf.getjTableRacuni().setModel(mtr);
            
            ModelTabeleStavkeRacuna mts = new ModelTabeleStavkeRacuna(new ArrayList<>());
            prf.getjTableStavkaRacuna().setModel(mts);
            
            // uklanjanje ona dva buttona sto nisu potrebna jer postoji izmena racuna zasebno
            prf.getjButtonIzmeniStavku().setVisible(false);
            prf.getjButtonObrisiStavku().setVisible(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(prf,
                    "Sistem ne može da učita podatke za prikaz računa.",
                    "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Registruje listenere: pretrazi, resetuj, obrisi racun, izmeni racun,
     * izvezi u JSON, obrisi stavku.
     */
    private void addActionListener() {

        // 🔹 Pretraga (po konobaru, gostu, datumu i smeni)
        prf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Konobar izabraniKonobar = (Konobar) prf.getjComboBoxKonobar().getSelectedItem();
                Gost izabraniGost = (Gost) prf.getjComboBoxGost().getSelectedItem();
                Smena izabranaSmena = (Smena) prf.getjComboBoxSmena().getSelectedItem();
                String datumTxt = prf.getjTextFieldDatum().getText().trim();

                LocalDate datum = null;
                if (!datumTxt.isEmpty()) {
                    try {
                        datum = LocalDate.parse(datumTxt);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(prf,
                                "Format datuma nije ispravan (koristite yyyy-MM-dd).",
                                "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getjTableRacuni().getModel();
                mtr.pretrazi(izabraniKonobar, izabraniGost, datum, izabranaSmena);
            }
        });

        // 🔹 Resetuj filtere
        prf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prf.getjComboBoxKonobar().setSelectedItem(null);
                prf.getjComboBoxGost().setSelectedItem(null);
                prf.getjComboBoxSmena().setSelectedItem(null);
                prf.getjTextFieldDatum().setText("");
                pripremiFormu();
            }
        });

        
        // 🔹 Obrisi račun
        prf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = prf.getjTableRacuni().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(prf,
                            "Morate izabrati račun koji želite da obrišete.",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getjTableRacuni().getModel();
                    Racun r = mtr.getRacuni().get(red);
                    
                    List<StavkaRacuna> stavke = Komunikacija.getInstance().ucitajStavke(r);
                    r.setStavke(stavke);

                    try {
                        Komunikacija.getInstance().obrisiRacun(r);
                        JOptionPane.showMessageDialog(prf,
                                "Sistem je uspešno obrisao račun.",
                                "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu(); // osveži tabelu
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(prf,
                                "Sistem ne može da obriše račun.",
                                "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        
        // 🔹 Izmeni račun
        prf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = prf.getjTableRacuni().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(prf,
                            "Morate izabrati račun koji želite da izmenite.",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getjTableRacuni().getModel();
                    Racun r = mtr.getRacunAt(red);

                    List<StavkaRacuna> stavke = Komunikacija.getInstance().ucitajStavke(r);
                    r.setStavke(stavke);

                    Cordinator.getInstance().dodajParam("racun_za_izmenu", r);
                    Cordinator.getInstance().otvoriIzmeniRacunFormu();
                }
            }
        });

        prf.addBtnIzveziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getjTableRacuni().getModel();
                List<Racun> racuni = mtr.getRacuni();
                if (racuni == null || racuni.isEmpty()) {
                    JOptionPane.showMessageDialog(prf,
                            "Nema računa za izvoz.",
                            "Upozorenje", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String putanja = Komunikacija.getInstance().izveziRacune(racuni);
                    JOptionPane.showMessageDialog(prf,
                            "Računi su uspešno izvezeni u fajl:\n" + putanja,
                            "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(prf,
                            "Sistem ne može da izveze račune u JSON.\n" + ex.getMessage(),
                            "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        prf.addBtnObrisiStavkuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = prf.getjTableStavkaRacuna().getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(prf, "Morate izabrati stavku koju želite da obrišete.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Dohvati selektovanu stavku iz modela
                    ModelTabeleStavkeRacuna mts = (ModelTabeleStavkeRacuna) prf.getjTableStavkaRacuna().getModel();
                    StavkaRacuna izabranaStavka = mts.getStavke().get(selectedRow);

                    // Potvrda
                    int potvrda = JOptionPane.showConfirmDialog(
                        prf,
                        "Da li ste sigurni da želite da obrišete ovu stavku?",
                        "Potvrda brisanja",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (potvrda != JOptionPane.YES_OPTION) return;

                    // Poziv serveru
                    Komunikacija.getInstance().obrisiStavkuRacuna(izabranaStavka);

                    // Ako je uspešno obrisana u bazi — obriši i lokalno
                    mts.obrisiStavku(selectedRow);
                    //prf.azurirajUkupanIznos();
                    JOptionPane.showMessageDialog(prf, "Stavka je uspešno obrisana iz baze!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    pripremiFormu();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(prf, "Greška prilikom brisanja stavke iz baze: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        
    }

    

    /**
     * Osvezava prikaz racuna (ponovo ucitava podatke sa servera).
     */
    public void osveziGlavnuFormu() {
        pripremiFormu();
    }
}
