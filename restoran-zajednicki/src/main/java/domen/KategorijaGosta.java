package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja kategoriju gosta u restoranu.
 * Kategorija odredjuje da li gost ima popust i koliki je procenat popusta.
 * Koristi se pri registraciji gostiju i pri obracunu iznosa racuna.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class KategorijaGosta implements ApstraktniDomenskiObjekat{

    /** Jedinstveni identifikator kategorije gosta u bazi podataka. */
    private int idKategorijaGosta;

    /** Opis kategorije (npr. "Student", "Penzioner", "Regularan"). */
    private String opis;

    /** Procenat popusta koji vazi za ovu kategoriju. */
    private double popust;

    /** Indikator da li kategorija ostvaruje popust. */
    private boolean imaPopust;

    /**
     * Podrazumevani konstruktor. Kreira praznu kategoriju bez inicijalizovanih atributa.
     */
    public KategorijaGosta() {}

    /**
     * Konstruktor koji kreira kategoriju gosta sa svim atributima.
     * Validacija se vrsi preko setera.
     *
     * @param idKategorijaGosta jedinstveni identifikator kategorije
     * @param opis tekstualni opis kategorije
     * @param popust procenat popusta (ocekivano &gt;= 0)
     * @param imaPopust true ako kategorija ima pravo na popust, inace false
     * @throws IllegalArgumentException ako bilo koja prosledjena vrednost ne zadovoljava pravila settera
     */
    public KategorijaGosta(int idKategorijaGosta, String opis, double popust, boolean imaPopust) {
        setIdKategorijaGosta(idKategorijaGosta);
        setOpis(opis);
        setPopust(popust);
        setImaPopust(imaPopust);
    }

    /**
     * Vraca jedinstveni identifikator kategorije gosta kao int.
     *
     * @return id kategorije gosta
     */
    public int getIdKategorijaGosta() { return idKategorijaGosta; }

    /**
     * Postavlja jedinstveni identifikator kategorije gosta.
     *
     * @param idKategorijaGosta novi id (ne sme biti negativan)
     * @throws IllegalArgumentException ako je idKategorijaGosta negativan
     */
    public void setIdKategorijaGosta(int idKategorijaGosta) {
        if (idKategorijaGosta < 0) {
            throw new IllegalArgumentException("Id kategorije gosta ne sme biti negativan.");
        }
        this.idKategorijaGosta = idKategorijaGosta;
    }

    /**
     * Vraca opis kategorije gosta kao String.
     *
     * @return opis kategorije
     */
    public String getOpis() { return opis; }

    /**
     * Postavlja opis kategorije gosta.
     *
     * @param opis novi opis (minimum 2 karaktera)
     * @throws IllegalArgumentException ako je opis null ili ako ima manje od 2 karaktera
     */
    public void setOpis(String opis) {
        if (opis == null || opis.length() < 2) {
            throw new IllegalArgumentException("Opis kategorije gosta mora imati najmanje 2 karaktera.");
        }
        this.opis = opis;
    }

    /**
     * Vraca procenat popusta kategorije kao double.
     *
     * @return popust u procentima
     */
    public double getPopust() { return popust; }

    /**
     * Postavlja procenat popusta kategorije.
     *
     * @param popust novi popust (mora biti &gt;= 0)
     * @throws IllegalArgumentException ako je popust manji od nule
     */
    public void setPopust(double popust) {
        if (popust < 0) {
            throw new IllegalArgumentException("Popust kategorije gosta mora biti veci ili jednak nuli.");
        }
        this.popust = popust;
    }

    /**
     * Vraca da li kategorija ima pravo na popust.
     *
     * @return true ako ima popust, inace false
     */
    public boolean isImaPopust() { return imaPopust; }

    /**
     * Postavlja indikator da li kategorija ostvaruje popust.
     *
     * @param imaPopust true ako kategorija ima popust, inace false
     */
    public void setImaPopust(boolean imaPopust) { this.imaPopust = imaPopust; }

    /**
     * Poredi kategorije po identifikatoru {@code idKategorijaGosta}.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o KategorijaGosta sa istim id, inace false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KategorijaGosta)) return false;
        KategorijaGosta that = (KategorijaGosta) o;
        return idKategorijaGosta == that.idKategorijaGosta;
    }

    /**
     * Racuna hash kod na osnovu identifikatora kategorije.
     *
     * @return hash kod zasnovan na idKategorijaGosta
     */
    @Override
    public int hashCode() { return Objects.hash(idKategorijaGosta); }

    /**
     * Vraca tekstualni prikaz kategorije (opis).
     *
     * @return opis kategorije kao string
     */
    @Override
    public String toString() {
        return opis; // + ", popust=" + popust + "%";
    }

    /**
     * Vraca naziv tabele "kategorijagosta" za SQL upite.
     *
     * @return "kategorijagosta"
     */
    @Override
public String vratiNazivTabele() {
    return "kategorijagosta";
}

/**
 * Mapira ResultSet u listu KategorijaGosta objekata citajuci kolone
 * idKategorijaGosta, opis, popust i imaPopust.
 *
 * @param rs ResultSet sa redovima tabele kategorijagosta
 * @return lista kategorija mapiranih iz ResultSet-a
 * @throws Exception ako dodje do greske pri citanju kolona
 */
@Override
public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
    List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
    while (rs.next()) {
        KategorijaGosta kg = new KategorijaGosta(
            rs.getInt("idKategorijaGosta"),
            rs.getString("opis"),
            rs.getDouble("popust"),
            rs.getBoolean("imaPopust")
        );
        lista.add(kg);
    }
    return lista;
}

/**
 * Vraca SQL fragment kolona za INSERT: "opis, popust, imaPopust".
 *
 * @return lista kolona za ubacivanje
 */
@Override
public String vratiKoloneZaUbacivanje() {
    return "opis, popust, imaPopust";
}

/**
 * Vraca SQL VALUES fragment sa trenutnim vrednostima opis, popust i imaPopust.
 *
 * @return vrednosti formatirane za INSERT
 */
@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + opis + "', " + popust + ", " + imaPopust;
}

/**
 * Vraca SQL uslov primarnog kljuca u formatu
 * kategorijagosta.idKategorijaGosta=&lt;id&gt;.
 *
 * @return WHERE fragment za identifikaciju kategorije
 */
@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idKategorijaGosta=" + idKategorijaGosta;
}


/**
 * Mapira trenutni red ResultSet-a u jedan KategorijaGosta objekat.
 *
 * @param rs ResultSet pozicioniran na red kategorije
 * @return nova KategorijaGosta popunjena iz ResultSet-a
 * @throws Exception ako dodje do greske pri citanju kolona
 */
@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new KategorijaGosta(
        rs.getInt("idKategorijaGosta"),
        rs.getString("opis"),
        rs.getDouble("popust"),
        rs.getBoolean("imaPopust")
    );
}

/**
 * Vraca SQL SET fragment za UPDATE: opis, popust i imaPopust.
 *
 * @return vrednosti za izmenu u formatu kolona=vrednost
 */
@Override
public String vratiVrednostiZaIzmenu() {
    return "opis='" + opis + "', popust=" + popust + ", imaPopust=" + imaPopust;
}

}
