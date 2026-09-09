package forme.model;

import domen.Smena;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleSmena extends AbstractTableModel {

    private List<Smena> smene;
    private final String[] kolone = {"Naziv", "Početak", "Kraj"};
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public ModelTabeleSmena(List<Smena> smene) {
        this.smene = smene;
    }

    @Override
    public int getRowCount() {
        return smene == null ? 0 : smene.size();
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
        Smena s = smene.get(rowIndex);
        switch (columnIndex) {
            //case 0: return s.getIdSmena();
            case 0: return s.getNaziv();
            case 1: return s.getVremePocetka()!= null ? s.getVremePocetka().format(formatter) : "";
            case 2: return s.getVremeKraja()!= null ? s.getVremeKraja().format(formatter) : "";
            default: return null;
        }
    }

    public Smena getSmena(int rowIndex) {
        return smene.get(rowIndex);
    }

    public void setSmene(List<Smena> smene) {
        this.smene = smene;
        fireTableDataChanged();
    }

    public List<Smena> getSmene() {
        return smene;
    }
    
    

    public void dodajSmenu(Smena s) {
        smene.add(s);
        fireTableRowsInserted(smene.size() - 1, smene.size() - 1);
    }

    public void obrisiSmenu(int rowIndex) {
        smene.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

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
