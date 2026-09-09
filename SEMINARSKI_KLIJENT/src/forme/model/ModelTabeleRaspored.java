package forme.model;

import domen.KonobarSmena;
import domen.Konobar;
import domen.Smena;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz rasporeda (KonobarSmena)
 * koji povezuje konobare, smene i datume rada.
 * @author Cofara
 */
public class ModelTabeleRaspored extends AbstractTableModel {

    private List<KonobarSmena> lista;
    private final String[] kolone = {"Konobar", "Smena", "Datum smene"};

    private List<KonobarSmena> originalnaLista; // za reset

    public ModelTabeleRaspored(List<KonobarSmena> lista) {
        this.lista = lista;
        this.originalnaLista = new ArrayList<>(lista);
    }

    public List<KonobarSmena> getLista() {
        return lista;
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
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KonobarSmena ks = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return ks.getKonobar() != null
                        ? ks.getKonobar().getIme() + " " + ks.getKonobar().getPrezime()
                        : "Nepoznato";
            case 1:
                return ks.getSmena() != null
                        ? ks.getSmena().getNaziv()
                        : "Nepoznato";
            case 2:
                return ks.getDatumSmene() != null
                        ? ks.getDatumSmene().toString()
                        : "/";
            default:
                return "";
        }
    }

    /**
     * Filtrira raspored po izabranom konobaru, smeni i datumu.
     *
     * @param konobar izabrani konobar iz combobox-a (može biti null)
     * @param smena izabrana smena iz combobox-a (može biti null)
     * @param datum datum iz textfield-a (može biti null)
     */
    public void pretrazi(Konobar konobar, Smena smena, LocalDate datum) {
        lista = new ArrayList<>();

        for (KonobarSmena ks : originalnaLista) {
            boolean odgovara = true;

            if (konobar != null && ks.getKonobar() != null) {
                if (ks.getKonobar().getIdKonobar() != konobar.getIdKonobar()) {
                    odgovara = false;
                }
            }

            if (smena != null && ks.getSmena() != null) {
                if (ks.getSmena().getIdSmena() != smena.getIdSmena()) {
                    odgovara = false;
                }
            }

            if (datum != null && ks.getDatumSmene() != null) {
                if (!ks.getDatumSmene().equals(datum)) {
                    odgovara = false;
                }
            }

            if (odgovara) {
                lista.add(ks);
            }
        }

        fireTableDataChanged();
    }

    /** Resetuje tabelu na početno stanje. */
    public void resetuj() {
        lista = new ArrayList<>(originalnaLista);
        fireTableDataChanged();
    }
}
