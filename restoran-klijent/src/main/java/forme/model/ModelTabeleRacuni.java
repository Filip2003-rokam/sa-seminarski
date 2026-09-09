/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Gost;
import domen.Konobar;
import domen.Racun;
import domen.Smena;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import komunikacija.Komunikacija;

/**
 *
 * @author Cofara
 */
public class ModelTabeleRacuni extends AbstractTableModel {

    private List<Racun> racuni;
    //private final String[] kolone = {"ID", "Datum", "Vreme", "Ukupan iznos", "Izdat", "Gost", "Konobar"};

    private final String[] kolone = {"Datum", "Vreme", "Ukupan iznos", "Izdat", "Gost", "Konobar"};

    
    public ModelTabeleRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

    @Override
    public int getRowCount() {
        return racuni == null ? 0 : racuni.size();
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
        Racun r = racuni.get(rowIndex);
        switch (columnIndex) {
            //case 0: return r.getIdRacun();
            case 0: return r.getDatumIzdavanja();
            case 1: return r.getVremeIzdavanja();
            case 2: return r.getUkupanIznos();
            case 3: return r.isJeIzdat() ? "DA" : "NE";
            case 4: return r.getGost() != null ? r.getGost().getIme() + " " + r.getGost().getPrezime() : "N/A";
            case 5: return r.getKonobar() != null ? r.getKonobar().getIme() + " " + r.getKonobar().getPrezime() : "N/A";
            default: return "n/a";
        }
    }

    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
        fireTableDataChanged();
    }

    public Racun getRacunAt(int rowIndex) {
        return racuni.get(rowIndex);
    }

    public List<Racun> getRacuni() {
        return racuni;
    }
    
    public void pretrazi(Konobar konobar, Gost gost, LocalDate datum, Smena smena) {
        List<Racun> filtrirani = new ArrayList<>();

        for (Racun r : racuni) {
            boolean odgovara = true;

            // 🔸 Filter po konobaru
            if (konobar != null && (r.getKonobar() == null || r.getKonobar().getIdKonobar() != konobar.getIdKonobar())) {
                odgovara = false;
            }

            // 🔸 Filter po gostu
            if (gost != null && (r.getGost() == null || r.getGost().getIdGost() != gost.getIdGost())) {
                odgovara = false;
            }

            // 🔸 Filter po datumu
            if (datum != null && (r.getDatumIzdavanja() == null || !r.getDatumIzdavanja().equals(datum))) {
                odgovara = false;
            }

            // 🔸 Filter po smeni (proverava vreme)
            if (smena != null) {
                if (r.getVremeIzdavanja() == null) {
                    odgovara = false;
                } else {
                    // ako vreme nije u okviru smene, isključi
                    if (r.getVremeIzdavanja().isBefore(smena.getVremePocetka()) || r.getVremeIzdavanja().isAfter(smena.getVremeKraja())) {
                        odgovara = false;
                    }
                }
            }

            if (odgovara) {
                filtrirani.add(r);
            }
        }

        this.racuni = filtrirani;
        fireTableDataChanged();
    }

    
        // 🔹 Vrati broj plaćenih računa (jeIzdat == true)
    public int getBrojPlaceniRacuna() {
        int count = 0;
        for (Racun r : racuni) {
            if (r.isJeIzdat()) {
                count++;
            }
        }
        return count;
    }

    // 🔹 Vrati broj neplaćenih računa (jeIzdat == false)
    public int getBrojNeplaceniRacuna() {
        int count = 0;
        for (Racun r : racuni) {
            if (!r.isJeIzdat()) {
                count++;
            }
        }
        return count;
    }

    // 🔹 Vrati ukupan broj računa
    public int getUkupanBrojRacuna() {
        return racuni != null ? racuni.size() : 0;
    }

    // 🔹 Vrati ukupan preostali dug (suma svih neplaćenih računa)
    public double getUkupanPreostaliDug() {
        double suma = 0;
        for (Racun r : racuni) {
            if (!r.isJeIzdat()) {
                suma += r.getUkupanIznos();
            }
        }
        return suma;
    }


    
}
