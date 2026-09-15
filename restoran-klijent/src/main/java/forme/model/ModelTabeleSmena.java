package forme.model;

import domen.Smena;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz smena u Swing {@code JTable} komponenti.
 * Prikazuje kolone: Naziv, Pocetak, Kraj.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleSmena extends AbstractTableModel {

    private List<Smena> smene;
    private final String[] kolone = {"Naziv", "Početak", "Kraj"};
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Kreira model tabele na osnovu prosledjene liste smena.
     *
     * @param smene lista smena za prikaz
     */
    public ModelTabeleSmena(List<Smena> smene) {
        this.smene = smene;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj smena u listi, ili 0 ako je lista null
     */
    @Override
    public int getRowCount() {
        return smene == null ? 0 : smene.size();
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
     * Kolone: 0 - Naziv, 1 - Pocetak (HH:mm), 2 - Kraj (HH:mm).
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Smena s = smene.get(rowIndex);
        switch (columnIndex) {
            //case 0: return s.getIdSmena();
            case 0: return s.getNaziv();
            case 1: return s.getVremePocetka()!= null ? s.getVremePocetka().format(formatter) : "";
            case 2: return s.getVremeKraja()!= null ? s.getVremeKraja().format(formatter) : "";
            default: return null;
        }
    }

    /**
     * Vraca smenu na zadatom redu tabele.
     *
     * @param rowIndex indeks reda
     * @return smena na datom redu
     */
    public Smena getSmena(int rowIndex) {
        return smene.get(rowIndex);
    }

    /**
     * Postavlja novu listu smena i obavestava tabelu o promeni podataka.
     *
     * @param smene nova lista smena
     */
    public void setSmene(List<Smena> smene) {
        this.smene = smene;
        fireTableDataChanged();
    }

    /**
     * Vraca listu smena koja se prikazuje u tabeli.
     *
     * @return lista smena
     */
    public List<Smena> getSmene() {
        return smene;
    }
    
    

    /**
     * Dodaje smenu u listu i obavestava tabelu o umetanju reda.
     *
     * @param s smena koja se dodaje
     */
    public void dodajSmenu(Smena s) {
        smene.add(s);
        fireTableRowsInserted(smene.size() - 1, smene.size() - 1);
    }

    /**
     * Brise smenu sa zadatog reda i obavestava tabelu o brisanju.
     *
     * @param rowIndex indeks reda koji se brise
     */
    public void obrisiSmenu(int rowIndex) {
        smene.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * Filtrira listu smena po nazivu, vremenu pocetka i vremenu kraja.
     * Ako nijedan kriterijum nije unet, lista se ne menja.
     *
     * @param naziv deo naziva smene (moze biti null ili prazan)
     * @param pocetak deo string reprezentacije vremena pocetka (moze biti null ili prazan)
     * @param kraj deo string reprezentacije vremena kraja (moze biti null ili prazan)
     */
    public void pretrazi(String naziv, String pocetak, String kraj) {
        // ako ništa nije uneto — ne filtriraj
        if ((naziv == null || naziv.isEmpty()) &&
            (pocetak == null || pocetak.isEmpty()) &&
            (kraj == null || kraj.isEmpty())) {
            return;
        }

        List<Smena> filtrirane = new ArrayList<>();

        for (Smena s : smene) {
            boolean odgovara = true;

            // 🔹 po nazivu (case-insensitive)
            if (naziv != null && !naziv.isEmpty()) {
                if (s.getNaziv() == null || !s.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
                    odgovara = false;
                }
            }

            // 🔹 po početku (ako je unet kao string)
            if (pocetak != null && !pocetak.isEmpty()) {
                String pocetakS = String.valueOf(s.getVremePocetka()); // konverzija datuma/vremena u string
                if (pocetakS == null || !pocetakS.contains(pocetak)) {
                    odgovara = false;
                }
            }

            // 🔹 po kraju
            if (kraj != null && !kraj.isEmpty()) {
                String krajS = String.valueOf(s.getVremeKraja());
                if (krajS == null || !krajS.contains(kraj)) {
                    odgovara = false;
                }
            }

            if (odgovara) {
                filtrirane.add(s);
            }
        }

        this.smene = filtrirane;
        fireTableDataChanged();
    }

}
