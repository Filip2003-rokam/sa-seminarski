package forme.model;

import domen.Gost;
import domen.KategorijaGosta;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleGosti extends AbstractTableModel {

    private List<Gost> gosti;
    private final String[] kolone = {"Ime", "Prezime", "Kategorija"};

    public ModelTabeleGosti(List<Gost> gosti) {
        this.gosti = gosti;
    }

    @Override
    public int getRowCount() {
        return gosti == null ? 0 : gosti.size();
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

    // ako želiš da menjaš listu iz kontrolera:
    public void setGosti(List<Gost> gosti) {
        this.gosti = gosti;
        fireTableDataChanged();
    }

    // za dobijanje selektovanog gosta u JTable
    public Gost getGostAt(int rowIndex) {
        return gosti.get(rowIndex);
    }

    public List<Gost> getGosti() {
        return gosti;
    }

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
