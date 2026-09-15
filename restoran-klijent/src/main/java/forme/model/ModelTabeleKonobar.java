package forme.model;

import domen.Konobar;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz konobara u Swing {@code JTable} komponenti.
 * Prikazuje kolone: ID, Ime, Prezime, Username.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleKonobar extends AbstractTableModel {

    /** Lista konobara prikazanih u tabeli. */
    private List<Konobar> lista;
    /** Nazivi kolona tabele. */
    private final String[] kolone = {"ID", "Ime", "Prezime", "Username"};

    /**
     * Kreira model tabele na osnovu prosledjene liste konobara.
     *
     * @param lista lista konobara za prikaz
     */
    public ModelTabeleKonobar(List<Konobar> lista) {
        this.lista = lista;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj konobara u listi, ili 0 ako je lista null
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
     * Kolone: 0 - ID, 1 - Ime, 2 - Prezime, 3 - Username (korisnicko ime).
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Konobar k = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return k.getIdKonobar();
            case 1:
                return k.getIme();
            case 2:
                return k.getPrezime();
            case 3:
                return k.getKorisnickoIme();
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
     * Vraca listu konobara koja se prikazuje u tabeli.
     *
     * @return lista konobara
     */
    public List<Konobar> getLista() {
        return lista;
    }

    /**
     * Postavlja novu listu konobara i obavestava tabelu o promeni podataka.
     *
     * @param lista nova lista konobara
     */
    public void setLista(List<Konobar> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    /**
     * Filtrira listu konobara po imenu, prezimenu i korisnickom imenu.
     * Ako nijedan kriterijum nije unet, lista se ne menja.
     *
     * @param ime deo imena konobara (moze biti null ili prazan)
     * @param prezime deo prezimena konobara (moze biti null ili prazan)
     * @param username deo korisnickog imena (moze biti null ili prazan)
     */
    public void pretrazi(String ime, String prezime, String username) {
        if ((ime == null || ime.isEmpty()) && 
            (prezime == null || prezime.isEmpty()) && 
            (username == null || username.isEmpty())) {
            return; // ništa nije uneto
        }

        List<Konobar> filtrirani = new ArrayList<>();

        for (Konobar k : lista) {
            boolean odgovara = true;

            // ako je uneto ime, proveri
            if (ime != null && !ime.isEmpty()) {
                if (k.getIme() == null || !k.getIme().toLowerCase().contains(ime.toLowerCase())) {
                    odgovara = false;
                }
            }

            // ako je uneto prezime, proveri
            if (prezime != null && !prezime.isEmpty()) {
                if (k.getPrezime() == null || !k.getPrezime().toLowerCase().contains(prezime.toLowerCase())) {
                    odgovara = false;
                }
            }

            // ako je unet username, proveri
            if (username != null && !username.isEmpty()) {
                if (k.getKorisnickoIme()== null || !k.getKorisnickoIme().toLowerCase().contains(username.toLowerCase())) {
                    odgovara = false;
                }
            }

            if (odgovara) {
                filtrirani.add(k);
            }
        }

        this.lista = filtrirani;
        fireTableDataChanged();
    }

}
