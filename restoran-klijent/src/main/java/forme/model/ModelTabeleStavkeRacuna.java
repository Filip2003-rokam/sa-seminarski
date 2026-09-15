package forme.model;

import domen.StavkaRacuna;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz stavki racuna u Swing {@code JTable} komponenti.
 * Prikazuje kolone: RB, Naziv, Kolicina, Cena, Ukupan iznos.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleStavkeRacuna extends AbstractTableModel {

    private List<StavkaRacuna> stavke;
    private final String[] kolone = {"RB", "Naziv", "Količina", "Cena", "Ukupan iznos"};

    /**
     * Kreira model tabele na osnovu prosledjene liste stavki racuna.
     *
     * @param stavke lista stavki racuna za prikaz
     */
    public ModelTabeleStavkeRacuna(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj stavki u listi, ili 0 ako je lista null
     */
    @Override
    public int getRowCount() {
        return stavke == null ? 0 : stavke.size();
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
     * Kolone: 0 - RB, 1 - Naziv artikla, 2 - Kolicina, 3 - Cena, 4 - Ukupan iznos.
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaRacuna sr = stavke.get(rowIndex);
        switch (columnIndex) {
            case 0: return sr.getRb();
            case 1: return sr.getArtikal() != null ? sr.getArtikal().getNaziv() : "N/A";
            case 2: return sr.getKolicina();
            case 3: return sr.getCena();
            case 4: return sr.getUkupanIznos();
            default: return "n/a";
        }
    }

    /**
     * Postavlja novu listu stavki i obavestava tabelu o promeni podataka.
     *
     * @param stavke nova lista stavki racuna
     */
    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
        fireTableDataChanged();
    }

    /**
     * Vraca stavku racuna na zadatom redu tabele.
     *
     * @param rowIndex indeks reda
     * @return stavka racuna na datom redu
     */
    public StavkaRacuna getStavkaAt(int rowIndex) {
        return stavke.get(rowIndex);
    }

    /**
     * Dodaje stavku u listu i obavestava tabelu o umetanju reda.
     *
     * @param sr stavka racuna koja se dodaje
     */
    public void dodajStavku(StavkaRacuna sr) {
        stavke.add(sr);
        fireTableRowsInserted(stavke.size() - 1, stavke.size() - 1);
    }

    /**
     * Brise stavku sa zadatog reda i obavestava tabelu o brisanju.
     *
     * @param rowIndex indeks reda koji se brise
     */
    public void obrisiStavku(int rowIndex) {
        stavke.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * Vraca listu stavki racuna koja se prikazuje u tabeli.
     *
     * @return lista stavki racuna
     */
    public List<StavkaRacuna> getStavke() {
        return stavke;
    }
    
    /**
     * Racuna ukupan iznos svih stavki u listi.
     *
     * @return suma ukupnih iznosa svih stavki
     */
    public double getUkupanIznosRacuna() {
        double suma = 0;
        for (StavkaRacuna sr : stavke) {
            suma += sr.getUkupanIznos();
        }
        
        
        return suma;
    }

    
    
}
