package forme.model;

import domen.Gost;
import domen.Konobar;
import domen.Racun;
import domen.Smena;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import komunikacija.Komunikacija;

/**
 * Model tabele za prikaz racuna u Swing {@code JTable} komponenti.
 * Prikazuje kolone: Datum, Vreme, Ukupan iznos, Izdat, Gost, Konobar.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleRacuni extends AbstractTableModel {

    private List<Racun> racuni;
    //private final String[] kolone = {"ID", "Datum", "Vreme", "Ukupan iznos", "Izdat", "Gost", "Konobar"};

    private final String[] kolone = {"Datum", "Vreme", "Ukupan iznos", "Izdat", "Gost", "Konobar"};

    
    /**
     * Kreira model tabele na osnovu prosledjene liste racuna.
     *
     * @param racuni lista racuna za prikaz
     */
    public ModelTabeleRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj racuna u listi, ili 0 ako je lista null
     */
    @Override
    public int getRowCount() {
        return racuni == null ? 0 : racuni.size();
    }

    /**
     * Vraca broj kolona u tabeli.
     *
     * @return broj kolona
     */
    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    /**
     * Vraca naziv kolone za zadati indeks.
     *
     * @param column indeks kolone
     * @return naziv kolone
     */
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    

    /**
     * Vraca vrednost celije na zadatoj poziciji.
     * Kolone: 0 - Datum, 1 - Vreme, 2 - Ukupan iznos, 3 - Izdat (DA/NE),
     * 4 - Gost (ime i prezime), 5 - Konobar (ime i prezime).
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Racun r = racuni.get(rowIndex);
        switch (columnIndex) {
            //case 0: return r.getIdRacun();
            case 0: return r.getDatumIzdavanja();
            case 1: return r.getVremeIzdavanja();
            case 2: return r.getUkupanIznos();
            case 3: return r.isJeIzdat() ? "DA" : "NE";
            case 4: return r.getGost() != null ? r.getGost().getIme() + " " + r.getGost().getPrezime() : "N/A";
            case 5: return r.getKonobar() != null ? r.getKonobar().getIme() + " " + r.getKonobar().getPrezime() : "N/A";
            default: return "n/a";
        }
    }

    /**
     * Postavlja novu listu racuna i obavestava tabelu o promeni podataka.
     *
     * @param racuni nova lista racuna
     */
    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
        fireTableDataChanged();
    }

    /**
     * Vraca racun na zadatom redu tabele.
     *
     * @param rowIndex indeks reda
     * @return racun na datom redu
     */
    public Racun getRacunAt(int rowIndex) {
        return racuni.get(rowIndex);
    }

    /**
     * Vraca listu racuna koja se prikazuje u tabeli.
     *
     * @return lista racuna
     */
    public List<Racun> getRacuni() {
        return racuni;
    }
    
    /**
     * Filtrira listu racuna po konobaru, gostu, datumu i smeni.
     * Null kriterijumi se ignorisu. Filter po smeni proverava da li
     * vreme izdavanja racuna upada u interval smene.
     *
     * @param konobar izabrani konobar (moze biti null)
     * @param gost izabrani gost (moze biti null)
     * @param datum datum izdavanja (moze biti null)
     * @param smena smena za filtriranje po vremenu (moze biti null)
     */
    public void pretrazi(Konobar konobar, Gost gost, LocalDate datum, Smena smena) {
        List<Racun> filtrirani = new ArrayList<>();

        for (Racun r : racuni) {
            boolean odgovara = true;

            // 🔸 Filter po konobaru
            if (konobar != null && (r.getKonobar() == null || r.getKonobar().getIdKonobar() != konobar.getIdKonobar())) {
                odgovara = false;
            }

            // 🔸 Filter po gostu
            if (gost != null && (r.getGost() == null || r.getGost().getIdGost() != gost.getIdGost())) {
                odgovara = false;
            }

            // 🔸 Filter po datumu
            if (datum != null && (r.getDatumIzdavanja() == null || !r.getDatumIzdavanja().equals(datum))) {
                odgovara = false;
            }

            // 🔸 Filter po smeni (proverava vreme)
            if (smena != null) {
                if (r.getVremeIzdavanja() == null) {
                    odgovara = false;
                } else {
                    // ako vreme nije u okviru smene, isključi
                    if (r.getVremeIzdavanja().isBefore(smena.getVremePocetka()) || r.getVremeIzdavanja().isAfter(smena.getVremeKraja())) {
                        odgovara = false;
                    }
                }
            }

            if (odgovara) {
                filtrirani.add(r);
            }
        }

        this.racuni = filtrirani;
        fireTableDataChanged();
    }

    
    /**
     * Vraca broj placenih racuna (jeIzdat == true).
     *
     * @return broj placenih racuna
     */
    public int getBrojPlaceniRacuna() {
        int count = 0;
        for (Racun r : racuni) {
            if (r.isJeIzdat()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Vraca broj neplacenih racuna (jeIzdat == false).
     *
     * @return broj neplacenih racuna
     */
    public int getBrojNeplaceniRacuna() {
        int count = 0;
        for (Racun r : racuni) {
            if (!r.isJeIzdat()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Vraca ukupan broj racuna u trenutnoj listi.
     *
     * @return ukupan broj racuna, ili 0 ako je lista null
     */
    public int getUkupanBrojRacuna() {
        return racuni != null ? racuni.size() : 0;
    }

    /**
     * Vraca ukupan preostali dug kao sumu iznosa svih neplacenih racuna.
     *
     * @return suma iznosa neplacenih racuna
     */
    public double getUkupanPreostaliDug() {
        double suma = 0;
        for (Racun r : racuni) {
            if (!r.isJeIzdat()) {
                suma += r.getUkupanIznos();
            }
        }
        return suma;
    }


    
}
