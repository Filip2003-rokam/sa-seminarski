package forme.model;

import domen.Gost;
import domen.KategorijaGosta;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz gostiju u Swing {@code JTable} komponenti.
 * Prikazuje kolone: Ime, Prezime, Kategorija.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleGosti extends AbstractTableModel {

    private List<Gost> gosti;
    private final String[] kolone = {"Ime", "Prezime", "Kategorija"};

    /**
     * Kreira model tabele na osnovu prosledjene liste gostiju.
     *
     * @param gosti lista gostiju za prikaz
     */
    public ModelTabeleGosti(List<Gost> gosti) {
        this.gosti = gosti;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj gostiju u listi, ili 0 ako je lista null
     */
    @Override
    public int getRowCount() {
        return gosti == null ? 0 : gosti.size();
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
     * Kolone: 0 - Ime, 1 - Prezime, 2 - opis kategorije gosta.
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Gost g = gosti.get(rowIndex);
        switch (columnIndex) {
            //case 0: return g.getIdGost();
            case 0: return g.getIme();
            case 1: return g.getPrezime();
            case 2: 
                return g.getKategorijaGosta() != null 
                        ? g.getKategorijaGosta().getOpis() 
                        : "N/A";
            default: return "n/a";
        }
    }

    /**
     * Postavlja novu listu gostiju i obavestava tabelu o promeni podataka.
     *
     * @param gosti nova lista gostiju
     */
    public void setGosti(List<Gost> gosti) {
        this.gosti = gosti;
        fireTableDataChanged();
    }

    /**
     * Vraca gosta na zadatom redu tabele.
     *
     * @param rowIndex indeks reda
     * @return gost na datom redu
     */
    public Gost getGostAt(int rowIndex) {
        return gosti.get(rowIndex);
    }

    /**
     * Vraca listu gostiju koja se prikazuje u tabeli.
     *
     * @return lista gostiju
     */
    public List<Gost> getGosti() {
        return gosti;
    }

    /**
     * Filtrira listu gostiju po imenu, prezimenu i kategoriji.
     * Prazni ili null kriterijumi se ignorisu.
     *
     * @param ime deo imena gosta (moze biti null ili prazan)
     * @param prezime deo prezimena gosta (moze biti null ili prazan)
     * @param izabranaKategorija kategorija gosta (moze biti null)
     */
    public void pretrazi(String ime, String prezime, KategorijaGosta izabranaKategorija) {
    List<Gost> filteredList = gosti.stream()
        // filtriranje po imenu
        .filter(p -> (ime == null || ime.isEmpty() ||
                      p.getIme().toLowerCase().contains(ime.toLowerCase())))
        // filtriranje po prezimenu
        .filter(p -> (prezime == null || prezime.isEmpty() ||
                      p.getPrezime().toLowerCase().contains(prezime.toLowerCase())))
        // filtriranje po kategoriji gosta
        .filter(p -> (izabranaKategorija == null ||
                      p.getKategorijaGosta() == null ||
                      p.getKategorijaGosta().getIdKategorijaGosta() == izabranaKategorija.getIdKategorijaGosta()))
        .collect(Collectors.toList());

    this.gosti = filteredList;
    fireTableDataChanged();
}

    
    
}
