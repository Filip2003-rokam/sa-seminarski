package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import domen.KonobarSmena;
import domen.Smena;
import forme.DodajRasporedForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za dodavanje i izmenu rasporeda rada
 * ({@link forme.DodajRasporedForma}, entitet {@link domen.KonobarSmena}).
 * U klijentskom MVC-u povezuje konobara, smenu i datum i salje zahteve serveru.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajRasporedController {

    /** Forma za dodavanje/izmenu rasporeda kojom ovaj kontroler upravlja. */
    private final DodajRasporedForma drf;

    /**
     * Kreira kontroler i registruje listenere za dodavanje i izmenu.
     *
     * @param drf forma za raspored
     */
    public DodajRasporedController(DodajRasporedForma drf) {
        this.drf = drf;
        addActionListeners();
    }

    /**
     * Priprema formu prema datom modu, centrira je i prikazuje.
     *
     * @param mod rezim rada forme (dodavanje ili izmena)
     */
    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        drf.setVisible(true);
        drf.setLocationRelativeTo(null);
    }

    /**
     * Popunjava combo boxeve konobara i smena; za izmenu ucitava
     * raspored iz parametra koordinatora <code>raspored</code>.
     *
     * @param mod rezim rada forme
     */
    private void pripremiFormu(FormaMod mod) {
        try {
            // popuni combo boxeve
            List<Konobar> konobari = Komunikacija.getInstance().ucitajKonobare();
            drf.getjComboBoxKonobar().removeAllItems();
            for (Konobar k : konobari) drf.getjComboBoxKonobar().addItem(k);

            List<Smena> smene = Komunikacija.getInstance().ucitajSmene();
            drf.getjComboBoxSmena().removeAllItems();
            for (Smena s : smene) drf.getjComboBoxSmena().addItem(s);

            switch (mod) {
                case DODAJ:
                    drf.getjButtonDodaj().setVisible(true);
                    drf.getjButtonIzmeni().setVisible(false);
                    break;
                case IZMENI:
                    drf.getjButtonDodaj().setVisible(false);
                    drf.getjButtonIzmeni().setVisible(true);

                    KonobarSmena ks = (KonobarSmena) Cordinator.getInstance().vratiParam("raspored");
                    drf.getjComboBoxKonobar().setSelectedItem(ks.getKonobar());
                    drf.getjComboBoxSmena().setSelectedItem(ks.getSmena());
                    drf.getjTextFieldDatum().setText(ks.getDatumSmene().toString());
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(drf, "Greška pri učitavanju podataka sa servera.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Registruje listenere za dugmad Dodaj i Izmeni.
     */
    private void addActionListeners() {
        drf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Konobar konobar = (Konobar) drf.getjComboBoxKonobar().getSelectedItem();
                Smena smena = (Smena) drf.getjComboBoxSmena().getSelectedItem();
                String datumTxt = drf.getjTextFieldDatum().getText().trim();

                if (konobar == null || smena == null || datumTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(drf, "Sva polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    LocalDate datum = LocalDate.parse(datumTxt);
                    KonobarSmena ks = new KonobarSmena(konobar, smena, datum);
                    Komunikacija.getInstance().dodajRaspored(ks);
                    drf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(drf, "Greška pri dodavanju rasporeda.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        drf.izmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Konobar konobar = (Konobar) drf.getjComboBoxKonobar().getSelectedItem();
                Smena smena = (Smena) drf.getjComboBoxSmena().getSelectedItem();
                String datumTxt = drf.getjTextFieldDatum().getText().trim();

                if (konobar == null || smena == null || datumTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(drf, "Sva polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    LocalDate datum = LocalDate.parse(datumTxt);
                    KonobarSmena ks = (KonobarSmena) Cordinator.getInstance().vratiParam("raspored");
                    ks.setKonobar(konobar);
                    ks.setSmena(smena);
                    ks.setDatumSmene(datum);

                    Komunikacija.getInstance().izmeniRaspored(ks);
                    drf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(drf, "Greška pri izmeni rasporeda.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
