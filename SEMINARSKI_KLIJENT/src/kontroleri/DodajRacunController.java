package kontroleri;

import cordinator.Cordinator;
import domen.Gost;
import domen.Artikal;
import domen.KategorijaGosta;
import domen.Konobar;
import domen.Racun;
import domen.StavkaRacuna;
import forme.DodajRacunForma;
import forme.FormaMod;
import forme.model.ModelTabeleStavkeRacuna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

public class DodajRacunController {
    
    private final DodajRacunForma drf;

    public DodajRacunController(DodajRacunForma drf) {
        this.drf = drf;
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod){
        pripremiFormu(mod);
        
        /*
        popuniComboBoxeve();
        ModelTabeleStavkeRacuna mts = new ModelTabeleStavkeRacuna(new ArrayList<>());
        drf.getjTableStavke().setModel(mts);
        
        // Formatteri
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Postavljanje trenutnog datuma i vremena
        drf.getjTextFieldDatum().setText(LocalDate.now().format(dateFormatter));
        drf.getjTextFieldVreme().setText(LocalTime.now().withNano(0).format(timeFormatter));
        */

        
        drf.setVisible(true);
    }
    
    private void addActionListener() {
        
        
       drf.addDodajStavkuActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Izabran artikal
                Artikal izabraniArtikal = (Artikal) drf.getjComboBoxArtikal().getSelectedItem();
                if (izabraniArtikal == null) {
                    JOptionPane.showMessageDialog(drf, "Morate izabrati artikal!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 2️⃣ Proveri količinu
                String kolText = drf.getjTextFieldKolicina().getText().trim();
                if (kolText.isEmpty()) {
                    JOptionPane.showMessageDialog(drf, "Unesite količinu!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int kolicina = Integer.parseInt(kolText);
                if (kolicina <= 0) {
                    JOptionPane.showMessageDialog(drf, "Količina mora biti veća od nule!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 3️⃣ Cena i ukupan iznos
                double cena = izabraniArtikal.getCena();
                
                // TODO
                double ukupanIznos = cena * kolicina;
                
                //double ukupanIznos = (cena * kolicina) * (1 - (Integer.parseInt(drf.getjTextFieldPopust().getText()) / 100));

                /*
                // 4️⃣ Uzimamo trenutno selektovani račun
                Racun trenutniRacun = drf.getTrenutniRacun();
                if (trenutniRacun == null) {
                    JOptionPane.showMessageDialog(drf, "Prvo kreirajte račun!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                */

                // 5️⃣ Redni broj stavke = broj postojećih + 1
                ModelTabeleStavkeRacuna mts = (ModelTabeleStavkeRacuna) drf.getjTableStavke().getModel();
                int rb = mts.getRowCount() + 1;

                // 6️⃣ Kreiraj stavku
                StavkaRacuna novaStavka = new StavkaRacuna(
                    -1,
                    rb,
                    kolicina,
                    ukupanIznos,
                    cena,
                    izabraniArtikal
                );

                // 7️⃣ Dodaj u tabelu (model)
                mts.dodajStavku(novaStavka);

                // 8️⃣ Očisti unos
                drf.getjTextFieldKolicina().setText("");
                drf.getjTextFieldUkupanIznos().setText(String.format("%.2f", ukupanIznos));

                // 9️⃣ Osveži ukupan iznos računa
                drf.getjTextFieldUkupanIznos().setText(mts.getUkupanIznosRacuna()+"");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(drf, "Količina mora biti ceo broj!", "Greška", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                System.out.println("GRESKAA: ");
                ex.printStackTrace();
                JOptionPane.showMessageDialog(drf, "Došlo je do greške prilikom dodavanja stavke.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
       
       drf.addObrisiStavkuActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int selectedRow = drf.getjTableStavke().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(drf, "Morate izabrati stavku koju želite da obrišete.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Uzimamo model tabele i brišemo selektovanu stavku
                ModelTabeleStavkeRacuna mts = (ModelTabeleStavkeRacuna) drf.getjTableStavke().getModel();
                mts.obrisiStavku(selectedRow);

                // Osveži ukupan iznos računa
                drf.getjTextFieldUkupanIznos().setText(mts.getUkupanIznosRacuna()+"");

                JOptionPane.showMessageDialog(drf, "Stavka je uspešno obrisana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(drf, "Greška prilikom brisanja stavke.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    });


        
        
        // 🔹 DODAJ RAČUN
        drf.addKreirajRacunActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                Gost izabraniGost = (Gost) drf.getjComboBoxGost().getSelectedItem();
                Konobar izabraniKonobar = (Konobar) drf.getjComboBoxKonobar().getSelectedItem();

                if (izabraniGost == null || izabraniKonobar == null) {
                    JOptionPane.showMessageDialog(drf, "Morate izabrati gosta i konobara!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    LocalDate datum = LocalDate.parse(drf.getjTextFieldDatum().getText().trim());
                    LocalTime vreme = LocalTime.parse(drf.getjTextFieldVreme().getText().trim());
                    double ukupanIznos = Double.parseDouble(drf.getjTextFieldUkupanIznos().getText().trim());
                    boolean jeIzdat = drf.getjCheckBoxJeIzdat().isSelected();

                    
                    // 🔹 Uzimanje svih stavki iz tabele
                    ModelTabeleStavkeRacuna mts = (ModelTabeleStavkeRacuna) drf.getjTableStavke().getModel();
                    List<StavkaRacuna> stavke = mts.getStavke();

                    if (stavke.isEmpty()) {
                        JOptionPane.showMessageDialog(drf, "Račun mora imati barem jednu stavku!", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // UKUPAN IZNOS
                    if (izabraniGost != null && izabraniGost.getKategorijaGosta() != null) {
                        KategorijaGosta kg = izabraniGost.getKategorijaGosta();

                        if (kg.isImaPopust()) { // proveri da li ta kategorija ima popust
                            double popustProcenat = kg.getPopust(); // npr. 10 za 10%
                            double iznosPopusta = ukupanIznos * (popustProcenat / 100);
                            ukupanIznos -= iznosPopusta; // umanji ukupan iznos

                            System.out.println("Primijenjen popust od " + popustProcenat + "% (" + iznosPopusta + " RSD)");
                        }
                    }

                    // 🔹 Zaokruži na 2 decimale
                    ukupanIznos = Math.round(ukupanIznos * 100.0) / 100.0;

                    // 🔹 Kreiranje objekta Racun sa svim stavkama
                    Racun r = new Racun(-1, datum, vreme, ukupanIznos, jeIzdat, izabraniKonobar, izabraniGost, stavke);

                    // 🔹 Slanje na server (trenutno zakomentarisano)
                    Komunikacija.getInstance().dodajRacun(r);

                    JOptionPane.showMessageDialog(drf, "Račun je uspešno dodat sa " + stavke.size() + " stavki!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                    // 🔹 Eventualno osveži tabele ili zatvori formu
                    //Cordinator.getInstance().osveziTabeluRacuna();
                    drf.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(drf, "Greška prilikom dodavanja računa! Proverite format datuma/vremena.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        

        drf.addIzmeniRacunActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                try {
                    int id = Integer.parseInt(drf.getjTextFieldId().getText());
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                    LocalDate datum = LocalDate.parse(drf.getjTextFieldDatum().getText().trim(), dateFormatter);
                    LocalTime vreme = LocalTime.parse(drf.getjTextFieldVreme().getText().trim(), timeFormatter);
                    double ukupanIznos = Double.parseDouble(drf.getjTextFieldUkupanIznos().getText().trim());
                    boolean jeIzdat = drf.getjCheckBoxJeIzdat().isSelected();


                    Gost izabraniGost = (Gost) drf.getjComboBoxGost().getSelectedItem();
                    Konobar izabraniKonobar = (Konobar) drf.getjComboBoxKonobar().getSelectedItem();

                    if (izabraniGost == null || izabraniKonobar == null) {
                        JOptionPane.showMessageDialog(drf, "Morate izabrati gosta i konobara!", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ModelTabeleStavkeRacuna mts = (ModelTabeleStavkeRacuna) drf.getjTableStavke().getModel();
                    
                    // UKUPAN IZNOS
                    if (izabraniGost != null && izabraniGost.getKategorijaGosta() != null) {
                        KategorijaGosta kg = izabraniGost.getKategorijaGosta();

                        if (kg.isImaPopust()) { // proveri da li ta kategorija ima popust
                            double popustProcenat = kg.getPopust(); // npr. 10 za 10%
                            double iznosPopusta = ukupanIznos * (popustProcenat / 100);
                            ukupanIznos -= iznosPopusta; // umanji ukupan iznos

                            System.out.println("Primijenjen popust od " + popustProcenat + "% (" + iznosPopusta + " RSD)");
                        }
                    }

                    // 🔹 Zaokruži na 2 decimale
                    ukupanIznos = Math.round(ukupanIznos * 100.0) / 100.0;

                    Racun r = new Racun(id, datum, vreme, ukupanIznos, jeIzdat, izabraniKonobar, izabraniGost, mts.getStavke());

                    Komunikacija.getInstance().izmeniRacun(r);

                    JOptionPane.showMessageDialog(drf, "Račun je uspešno izmenjen!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    // TODO - DAL OVO ZAPRAVO RIFRESUJE FORMU
                    //Cordinator.getInstance().osveziFormu();
                    drf.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(drf, "Greška prilikom izmene računa!", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private void pripremiFormu(FormaMod mod) {
        
        switch (mod) {
            case DODAJ:
                // ID se ne prikazuje (generiše se automatski)
                drf.getjTextFieldId().setVisible(false);

                popuniComboBoxeve();
                // Postavi trenutni datum i vreme
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                drf.getjTextFieldDatum().setText(LocalDate.now().format(dateFormatter));
                drf.getjTextFieldVreme().setText(LocalTime.now().withNano(0).format(timeFormatter));

                // Poništi sve selekcije
                drf.getjComboBoxKonobar().setSelectedIndex(-1);
                drf.getjComboBoxGost().setSelectedIndex(-1);

                // Poništi checkbox
                drf.getjCheckBoxJeIzdat().setSelected(false);

                // Poništi polja za stavke
                drf.getjComboBoxArtikal().setSelectedIndex(-1);
                drf.getjTextFieldKolicina().setText("");
                drf.getjTextFieldUkupanIznos().setText("");

                // Isprazni tabelu (ako ima model)
                //((ModelTabeleStavkeRacuna) drf.getjTableStavke().getModel()).ocistiTabelu();

                List<StavkaRacuna> lista = new ArrayList<>();
                
                ModelTabeleStavkeRacuna model1 = new ModelTabeleStavkeRacuna(lista);
                drf.getjTableStavke().setModel(model1);
                
                // Aktiviraj dugme "Kreiraj račun", onemogući "Izmeni"
                drf.getjButtonKreirajRacun().setVisible(true);
                drf.getjButtonIzmeniRacun().setVisible(false);
                break;


            case IZMENI:
                
                Racun r = (Racun) Cordinator.getInstance().vratiParam("racun_za_izmenu");
                
                popuniComboBoxeve();
                
                drf.getjTextFieldId().setText(String.valueOf(r.getIdRacun()));
                drf.getjTextFieldId().setEditable(false);

                DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");

                
                drf.getjTextFieldDatum().setText(r.getDatumIzdavanja().format(dateFormatter1));
                drf.getjTextFieldVreme().setText(r.getVremeIzdavanja().format(timeFormatter1));

                drf.getjComboBoxKonobar().setSelectedItem(r.getKonobar());
                drf.getjComboBoxGost().setSelectedItem(r.getGost());
                drf.getjCheckBoxJeIzdat().setSelected(r.isJeIzdat());

                drf.getjTextFieldUkupanIznos().setText(String.valueOf(r.getUkupanIznos()));

                // 🔹 Postavi STAVKE računa u tabelu:
                ModelTabeleStavkeRacuna model = new ModelTabeleStavkeRacuna(r.getStavke());
                drf.getjTableStavke().setModel(model);

                // 🔹 Omogući izmene
                drf.getjButtonIzmeniRacun().setVisible(true);
                drf.getjButtonKreirajRacun().setVisible(false);
                break;


            default:
                throw new AssertionError();
        }
    }

    private void popuniComboBoxeve() {
        
        // 🔹 Popunjavanje ComboBoxeva za gosta i konobara
        List<Gost> gosti = Komunikacija.getInstance().ucitajGoste();
        drf.getjComboBoxGost().removeAllItems();
        drf.getjComboBoxGost().addItem(null);
        for (Gost g : gosti) {
            drf.getjComboBoxGost().addItem(g);
        }

        List<Konobar> konobari = Komunikacija.getInstance().ucitajKonobare();
        drf.getjComboBoxKonobar().removeAllItems();
        drf.getjComboBoxKonobar().addItem(null);
        for (Konobar k : konobari) {
            drf.getjComboBoxKonobar().addItem(k);
        }
        
         List<Artikal> artikli = Komunikacija.getInstance().ucitajArtikle();
        drf.getjComboBoxArtikal().removeAllItems();
        drf.getjComboBoxArtikal().addItem(null);
        for (Artikal artikal : artikli) {
            drf.getjComboBoxArtikal().addItem(artikal);
        }
        
        // 🔹 Listener za prikaz popusta kad se promeni izabrani gost
        drf.getjComboBoxGost().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gost izabraniGost = (Gost) drf.getjComboBoxGost().getSelectedItem();

                if (izabraniGost != null && izabraniGost.getKategorijaGosta() != null) {
                    KategorijaGosta kg = izabraniGost.getKategorijaGosta();

                    if (kg.isImaPopust()) {
                        drf.getjTextFieldPopust().setText(kg.getPopust() + " %");
                    } else {
                        drf.getjTextFieldPopust().setText("0 %");
                    }
                } else {
                    drf.getjTextFieldPopust().setText("");
                }
            }
        });

        drf.getjTextFieldPopust().setEnabled(false);
        
    }
}
