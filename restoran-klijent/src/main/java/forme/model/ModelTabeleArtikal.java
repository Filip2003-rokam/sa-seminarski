package forme.model;

import domen.Artikal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ModelTabeleArtikal extends AbstractTableModel {
    
    private List<Artikal> lista;
    private final String[] kolone = {"ID", "Naziv", "Tip", "Cena"};

    public ModelTabeleArtikal(List<Artikal> lista) {
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
        Artikal a = lista.get(rowIndex);
        switch(columnIndex) {
            case 0: return a.getIdArtikal();
            case 1: return a.getNaziv();
            case 2: return a.getTip();
            case 3: return a.getCena();
            default: return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Artikal> getLista() {
        return lista;
    }

    public void setLista(List<Artikal> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    // metoda za pretragu
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
