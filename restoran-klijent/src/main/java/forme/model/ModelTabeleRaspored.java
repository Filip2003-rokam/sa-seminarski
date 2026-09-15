package forme.model;

import domen.KonobarSmena;
import domen.Konobar;
import domen.Smena;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabele za prikaz rasporeda (veza konobar-smena) u Swing {@code JTable} komponenti.
 * Prikazuje kolone: Konobar, Smena, Datum smene.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ModelTabeleRaspored extends AbstractTableModel {

    /** Lista rasporeda (konobar-smena) prikazanih u tabeli. */
    private List<KonobarSmena> lista;
    /** Nazivi kolona tabele. */
    private final String[] kolone = {"Konobar", "Smena", "Datum smene"};

    /** Kopija originalne liste radi resetovanja filtera. */
    private List<KonobarSmena> originalnaLista; // za reset

    /**
     * Kreira model tabele na osnovu prosledjene liste rasporeda.
     * Cuva i kopiju originalne liste radi kasnijeg resetovanja.
     *
     * @param lista lista veza konobar-smena za prikaz
     */
    public ModelTabeleRaspored(List<KonobarSmena> lista) {
        this.lista = lista;
        this.originalnaLista = new ArrayList<>(lista);
    }

    /**
     * Vraca listu rasporeda koja se prikazuje u tabeli.
     *
     * @return lista objekata {@link KonobarSmena}
     */
    public List<KonobarSmena> getLista() {
        return lista;
    }

    /**
     * Vraca broj redova u tabeli.
     *
     * @return broj zapisa u listi, ili 0 ako je lista null
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
     * Vraca vrednost celije na zadatoj poziciji.
     * Kolone: 0 - Konobar (ime i prezime), 1 - Smena (naziv), 2 - Datum smene.
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrednost celije
     */
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
     * Filtriranje se vrsi nad originalnom listom.
     *
     * @param konobar izabrani konobar iz combobox-a (moze biti null)
     * @param smena izabrana smena iz combobox-a (moze biti null)
     * @param datum datum iz textfield-a (moze biti null)
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

    /**
     * Resetuje tabelu na pocetno stanje (originalnu listu).
     */
    public void resetuj() {
        lista = new ArrayList<>(originalnaLista);
        fireTableDataChanged();
    }
}
