package forme.model;

import domen.KategorijaGosta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Cofara
 */
public class ModelTabeleKategorijaGosta extends AbstractTableModel {

    private List<KategorijaGosta> lista;
    private final String[] kolone = {"Naziv", "Popust"};

    public ModelTabeleKategorijaGosta(List<KategorijaGosta> lista) {
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

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<KategorijaGosta> getLista() {
        return lista;
    }

    public void setLista(List<KategorijaGosta> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }


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
