package forme.model;

import domen.Artikal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz artikala u Swing {@code JTable} komponenti.
 * Prikazuje kolone: ID, Naziv, Tip, Cena.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleArtikal extends AbstractTableModel {
    
    /** Lista artikala prikazanih u tabeli. */
    private List<Artikal> lista;
    /** Nazivi kolona tabele. */
    private final String[] kolone = {"ID", "Naziv", "Tip", "Cena"};

    /**
     * Kreira model tabele na osnovu prosledjene liste artikala.
     *
     * @param lista lista artikala za prikaz
     */
    public ModelTabeleArtikal(List<Artikal> lista) {
        this.lista = lista;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj artikala u listi, ili 0 ako je lista null
     */
    @Override
    public int getRowCount() {
        return lista == null ? 0 : lista.size();
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
     * Vraca vrednost celije na zadatoj poziciji.
     * Kolone: 0 - ID, 1 - Naziv, 2 - Tip, 3 - Cena.
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Artikal a = lista.get(rowIndex);
        switch(columnIndex) {
            case 0: return a.getIdArtikal();
            case 1: return a.getNaziv();
            case 2: return a.getTip();
            case 3: return a.getCena();
            default: return "N/A";
        }
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
     * Vraca listu artikala koja se prikazuje u tabeli.
     *
     * @return lista artikala
     */
    public List<Artikal> getLista() {
        return lista;
    }

    /**
     * Postavlja novu listu artikala i obavestava tabelu o promeni podataka.
     *
     * @param lista nova lista artikala
     */
    public void setLista(List<Artikal> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    /**
     * Filtrira listu artikala po nazivu i tipu.
     * Ako nijedan kriterijum nije unet, lista se ne menja.
     *
     * @param naziv deo naziva artikla (moze biti null ili prazan)
     * @param tip deo tipa artikla (moze biti null ili prazan)
     */
    public void pretrazi(String naziv, String tip) {
        if((naziv == null || naziv.isEmpty()) && (tip == null || tip.isEmpty())) {
            return; // ništa nije uneto
        }

        List<Artikal> filtrirana = new ArrayList<>();
        for(Artikal a : lista) {
            boolean ok = true;
            if(naziv != null && !naziv.isEmpty() && !a.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
                ok = false;
            }
            if(tip != null && !tip.isEmpty() && !a.getTip().toLowerCase().contains(tip.toLowerCase())) {
                ok = false;
            }
            if(ok) filtrirana.add(a);
        }
        this.lista = filtrirana;
        fireTableDataChanged();
    }
}
