package forme.model;

import domen.KategorijaGosta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz kategorija gostiju u Swing {@code JTable} komponenti.
 * Prikazuje kolone: Naziv, Popust.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleKategorijaGosta extends AbstractTableModel {

    /** Lista kategorija gostiju prikazanih u tabeli. */
    private List<KategorijaGosta> lista;
    /** Nazivi kolona tabele. */
    private final String[] kolone = {"Naziv", "Popust"};

    /**
     * Kreira model tabele na osnovu prosledjene liste kategorija gostiju.
     *
     * @param lista lista kategorija gostiju za prikaz
     */
    public ModelTabeleKategorijaGosta(List<KategorijaGosta> lista) {
        this.lista = lista;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj kategorija u listi, ili 0 ako je lista null
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
     * Kolone: 0 - Naziv (opis), 1 - Popust.
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KategorijaGosta kg = lista.get(rowIndex);
        switch (columnIndex) {
            // case 0: return kg.getIdKategorijaGosta();
            case 0:
                return kg.getOpis();
            case 1:
                return kg.getPopust();
            default:
                return "N/A";
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
     * Vraca listu kategorija gostiju koja se prikazuje u tabeli.
     *
     * @return lista kategorija gostiju
     */
    public List<KategorijaGosta> getLista() {
        return lista;
    }

    /**
     * Postavlja novu listu kategorija i obavestava tabelu o promeni podataka.
     *
     * @param lista nova lista kategorija gostiju
     */
    public void setLista(List<KategorijaGosta> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }


    /**
     * Filtrira listu kategorija po nazivu (opisu) i popustu.
     * Ako nijedan kriterijum nije unet, lista se ne menja.
     *
     * @param naziv deo naziva/opisa kategorije (moze biti null ili prazan)
     * @param popust tacna vrednost popusta (moze biti null)
     */
    public void pretrazi(String naziv, Double popust) {
        // Ako oba kriterijuma nisu uneta, nema pretrage
        if ((naziv == null || naziv.isEmpty()) && popust == null) {
            return;
        }

        List<KategorijaGosta> filtrirane = new ArrayList<>();

        for (KategorijaGosta kg : lista) {
            boolean odgovara = true;

            // Ako je unet naziv, proveri match
            if (naziv != null && !naziv.isEmpty()) {
                if (kg.getOpis()== null || !kg.getOpis().toLowerCase().contains(naziv.toLowerCase())) {
                    odgovara = false;
                }
            }

            // Ako je unet popust, proveri match
            if (popust != null) {
                if (Double.compare(kg.getPopust(), popust) != 0) {
                    odgovara = false;
                }
            }

            if (odgovara) {
                filtrirane.add(kg);
            }
        }

        this.lista = filtrirane;
        fireTableDataChanged();
    }

}
