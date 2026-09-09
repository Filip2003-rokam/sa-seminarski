package forme.model;

import domen.Konobar;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Cofara
 */
public class ModelTabeleKonobar extends AbstractTableModel {

    private List<Konobar> lista;
    private final String[] kolone = {"ID", "Ime", "Prezime", "Username"};

    public ModelTabeleKonobar(List<Konobar> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista == null ? 0 : lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

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

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Konobar> getLista() {
        return lista;
    }

    public void setLista(List<Konobar> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

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
