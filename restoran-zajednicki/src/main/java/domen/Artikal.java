package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja artikal (jelo ili pice) u meniju restorana.
 * Koristi se pri kreiranju stavki racuna i pri CRUD operacijama nad tabelom
 * artikal. Tip artikla razlikuje jelovnik od napitaka, a cena se koristi
 * za obracun iznosa stavke.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Artikal implements ApstraktniDomenskiObjekat{

    /** Jedinstveni identifikator artikla u bazi podataka. */
    private int idArtikal;

    /** Naziv artikla (npr. naziv jela ili pica). */
    private String naziv;

    /** Jedinicna cena artikla. */
    private double cena;

    /** Tip artikla: ocekivane vrednosti su "Jelo" ili "Pice". */
    private String tip; // Jelo ili Pice

    /**
     * Podrazumevani konstruktor. Kreira prazan artikal bez inicijalizovanih atributa.
     */
    public Artikal() {}

    /**
     * Konstruktor koji kreira artikal sa svim atributima.
     * Validacija se vrsi preko setera.
     *
     * @param idArtikal jedinstveni identifikator artikla
     * @param naziv naziv artikla
     * @param cena jedinicna cena artikla (za poslovne operacije ocekivano &gt; 0)
     * @param tip tip artikla: "Jelo" ili "Pice"
     * @throws IllegalArgumentException ako bilo koja prosledjena vrednost ne zadovoljava pravila settera
     */
    public Artikal(int idArtikal, String naziv, double cena, String tip) {
        setIdArtikal(idArtikal);
        setNaziv(naziv);
        setCena(cena);
        setTip(tip);
    }

    /**
     * Vraca jedinstveni identifikator artikla kao int.
     *
     * @return id artikla
     */
    public int getIdArtikal() { return idArtikal; }

    /**
     * Postavlja jedinstveni identifikator artikla.
     *
     * @param idArtikal novi id artikla (ne sme biti negativan)
     * @throws IllegalArgumentException ako je idArtikal negativan
     */
    public void setIdArtikal(int idArtikal) {
        if (idArtikal < 0) {
            throw new IllegalArgumentException("Id artikla ne sme biti negativan.");
        }
        this.idArtikal = idArtikal;
    }

    /**
     * Vraca naziv artikla kao String.
     *
     * @return naziv artikla
     */
    public String getNaziv() { return naziv; }

    /**
     * Postavlja naziv artikla.
     *
     * @param naziv novi naziv (minimum 2 karaktera)
     * @throws IllegalArgumentException ako je naziv null ili ako ima manje od 2 karaktera
     */
    public void setNaziv(String naziv) {
        if (naziv == null || naziv.length() < 2) {
            throw new IllegalArgumentException("Naziv artikla mora imati najmanje 2 karaktera.");
        }
        this.naziv = naziv;
    }

    /**
     * Vraca jedinicnu cenu artikla kao double.
     *
     * @return cena artikla
     */
    public double getCena() { return cena; }

    /**
     * Postavlja jedinicnu cenu artikla.
     *
     * @param cena nova cena (mora biti veca od nule)
     * @throws IllegalArgumentException ako je cena manja ili jednaka nuli
     */
    public void setCena(double cena) {
        if (cena <= 0) {
            throw new IllegalArgumentException("Cena artikla mora biti veca od nule.");
        }
        this.cena = cena;
    }

    /**
     * Vraca tip artikla kao String ("Jelo" ili "Pice").
     *
     * @return tip artikla
     */
    public String getTip() { return tip; }

    /**
     * Postavlja tip artikla.
     *
     * @param tip ocekivane vrednosti: "Jelo" ili "Pice" (ne sme biti null ni prazan)
     * @throws IllegalArgumentException ako je tip null ili prazan string
     */
    public void setTip(String tip) {
        if (tip == null || tip.isEmpty()) {
            throw new IllegalArgumentException("Tip artikla ne sme biti prazan.");
        }
        this.tip = tip;
    }

    /**
     * Poredi artikle po identifikatoru {@code idArtikal}.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o Artikal sa istim idArtikal, inace false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artikal)) return false;
        Artikal that = (Artikal) o;
        return idArtikal == that.idArtikal;
    }

    /**
     * Racuna hash kod na osnovu identifikatora artikla.
     *
     * @return hash kod zasnovan na idArtikal
     */
    @Override
    public int hashCode() { return Objects.hash(idArtikal); }

    /**
     * Vraca tekstualni prikaz artikla u formatu naziv i cena.
     *
     * @return string reprezentacija artikla
     */
    @Override
    public String toString() {
        return naziv + ' ' + ", " + cena;
    }

/**
 * Vraca naziv tabele "artikal" za SQL upite.
 *
 * @return "artikal"
 */
@Override
public String vratiNazivTabele() {
    return "artikal";
}

/**
 * Mapira ResultSet u listu Artikal objekata citajuci kolone
 * idArtikal, naziv, cena i tip.
 *
 * @param rs ResultSet sa redovima tabele artikal
 * @return lista artikala mapiranih iz ResultSet-a
 * @throws Exception ako dodje do greske pri citanju kolona
 */
@Override
public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
    List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
    while (rs.next()) {
        Artikal a = new Artikal(
            rs.getInt("idArtikal"),
            rs.getString("naziv"),
            rs.getDouble("cena"),
            rs.getString("tip")
        );
        lista.add(a);
    }
    return lista;
}

/**
 * Vraca SQL fragment kolona za INSERT: "naziv, cena, tip".
 *
 * @return lista kolona za ubacivanje
 */
@Override
public String vratiKoloneZaUbacivanje() {
    return "naziv, cena, tip";
}

/**
 * Vraca SQL VALUES fragment sa trenutnim vrednostima naziv, cena i tip.
 *
 * @return vrednosti formatirane za INSERT
 */
@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + naziv + "', " + cena + ", '" + tip + "'";
}

/**
 * Vraca SQL uslov primarnog kljuca u formatu artikal.idArtikal=&lt;id&gt;.
 *
 * @return WHERE fragment za identifikaciju artikla
 */
@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idArtikal=" + idArtikal;
}


/**
 * Mapira trenutni red ResultSet-a u jedan Artikal objekat.
 *
 * @param rs ResultSet pozicioniran na red artikla
 * @return novi Artikal popunjen iz ResultSet-a
 * @throws Exception ako dodje do greske pri citanju kolona
 */
@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new Artikal(
        rs.getInt("idArtikal"),
        rs.getString("naziv"),
        rs.getDouble("cena"),
        rs.getString("tip")
    );
}

/**
 * Vraca SQL SET fragment za UPDATE: naziv, cena i tip.
 *
 * @return vrednosti za izmenu u formatu kolona=vrednost
 */
@Override
public String vratiVrednostiZaIzmenu() {
    return "naziv='" + naziv + "', cena=" + cena + ", tip='" + tip + "'";
}

}
