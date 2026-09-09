package forme.model;

import domen.StavkaRacuna;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleStavkeRacuna extends AbstractTableModel {

    private List<StavkaRacuna> stavke;
    private final String[] kolone = {"RB", "Naziv", "Količina", "Cena", "Ukupan iznos"};

    public ModelTabeleStavkeRacuna(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    @Override
    public int getRowCount() {
        return stavke == null ? 0 : stavke.size();
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
        StavkaRacuna sr = stavke.get(rowIndex);
        switch (columnIndex) {
            case 0: return sr.getRb();
            case 1: return sr.getArtikal() != null ? sr.getArtikal().getNaziv() : "N/A";
            case 2: return sr.getKolicina();
            case 3: return sr.getCena();
            case 4: return sr.getUkupanIznos();
            default: return "n/a";
        }
    }

    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
        fireTableDataChanged();
    }

    public StavkaRacuna getStavkaAt(int rowIndex) {
        return stavke.get(rowIndex);
    }

    public void dodajStavku(StavkaRacuna sr) {
        stavke.add(sr);
        fireTableRowsInserted(stavke.size() - 1, stavke.size() - 1);
    }

    public void obrisiStavku(int rowIndex) {
        stavke.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }
    
    public double getUkupanIznosRacuna() {
        double suma = 0;
        for (StavkaRacuna sr : stavke) {
            suma += sr.getUkupanIznos();
        }
        
        
        return suma;
    }

    
    
}
