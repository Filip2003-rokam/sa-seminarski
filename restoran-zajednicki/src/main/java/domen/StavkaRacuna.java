
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja jednu stavku racuna.
 * Stavka povezuje racun sa artiklom i cuva kolicinu, jedinicnu cenu
 * i ukupan iznos stavke (tipicno kolicina * cena). Odgovara tabeli
 * stavkaracuna; primarni kljuc je kompozit (idRacun, rb).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class StavkaRacuna implements ApstraktniDomenskiObjekat{

    /** Identifikator racuna kojem stavka pripada (deo kompozitnog PK). */
    private int idRacun;

    /** Redni broj stavke unutar racuna (deo kompozitnog PK). */
    private int rb;

    /** Kolicina artikla na stavci. */
    private int kolicina;

    /** Ukupan iznos stavke (tipicno kolicina * cena). */
    private double ukupanIznos;

    /** Jedinicna cena artikla u trenutku kreiranja stavke. */
    private double cena;

    /** Artikal na koji se stavka odnosi. */
    private Artikal artikal;

    /**
     * Podrazumevani konstruktor. Kreira praznu stavku bez inicijalizovanih atributa.
     */
    public StavkaRacuna() {}

    /**
     * Konstruktor koji kreira stavku racuna sa svim atributima.
     *
     * @param idRacun identifikator racuna kojem stavka pripada
     * @param rb redni broj stavke unutar racuna (ocekivano &gt; 0)
     * @param kolicina kolicina artikla (ocekivano &gt; 0)
     * @param ukupanIznos ukupan iznos stavke (ocekivano &gt;= 0)
     * @param cena jedinicna cena (ocekivano &gt; 0)
     * @param artikal artikal na koji se stavka odnosi
     */
    public StavkaRacuna(int idRacun, int rb, int kolicina, double ukupanIznos, double cena, Artikal artikal) {
        this.idRacun = idRacun;
        this.rb = rb;
        this.kolicina = kolicina;
        this.ukupanIznos = ukupanIznos;
        this.cena = cena;
        this.artikal = artikal;
    }

    /**
     * Vraca identifikator racuna kojem stavka pripada kao int.
     *
     * @return id racuna
     */
    public int getIdRacun() { return idRacun; }

    /**
     * Postavlja identifikator racuna kojem stavka pripada.
     *
     * @param idRacun id postojeceg racuna u bazi
     */
    public void setIdRacun(int idRacun) { this.idRacun = idRacun; }

    /**
     * Vraca redni broj stavke unutar racuna kao int.
     *
     * @return redni broj stavke
     */
    public int getRb() { return rb; }

    /**
     * Postavlja redni broj stavke unutar racuna.
     *
     * @param rb redni broj (ocekivano &gt; 0, jedinstven unutar istog racuna)
     */
    public void setRb(int rb) { this.rb = rb; }

    /**
     * Vraca kolicinu artikla na stavci kao int.
     *
     * @return kolicina artikla
     */
    public int getKolicina() { return kolicina; }

    /**
     * Postavlja kolicinu artikla na stavci.
     *
     * @param kolicina nova kolicina (za poslovne operacije ocekivano &gt; 0)
     */
    public void setKolicina(int kolicina) { this.kolicina = kolicina; }

    /**
     * Vraca ukupan iznos stavke kao double.
     *
     * @return ukupan iznos stavke
     */
    public double getUkupanIznos() { return ukupanIznos; }

    /**
     * Postavlja ukupan iznos stavke.
     *
     * @param ukupanIznos novi iznos (ocekivano &gt;= 0; tipicno kolicina * cena)
     */
    public void setUkupanIznos(double ukupanIznos) { this.ukupanIznos = ukupanIznos; }

    /**
     * Vraca jedinicnu cenu stavke kao double.
     *
     * @return jedinicna cena artikla na stavci
     */
    public double getCena() { return cena; }

    /**
     * Postavlja jedinicnu cenu stavke.
     *
     * @param cena nova cena (za poslovne operacije ocekivano &gt; 0)
     */
    public void setCena(double cena) { this.cena = cena; }

    /**
     * Vraca artikal stavke kao objekat tipa {@link Artikal}.
     *
     * @return artikal ili null ako nije postavljen
     */
    public Artikal getArtikal() { return artikal; }

    /**
     * Postavlja artikal stavke.
     *
     * @param artikal artikal sa validnim idArtikal (potreban za INSERT/UPDATE)
     */
    public void setArtikal(Artikal artikal) { this.artikal = artikal; }

    /**
     * Poredi stavke po kompozitnom kljucu {@code idRacun} i {@code rb}.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o StavkaRacuna sa istim idRacun i rb, inace false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StavkaRacuna)) return false;
        StavkaRacuna that = (StavkaRacuna) o;
        return idRacun == that.idRacun && rb == that.rb;
    }

    /**
     * Racuna hash kod na osnovu idRacun i rb.
     *
     * @return hash kod kompozitnog kljuca stavke
     */
    @Override
    public int hashCode() { return Objects.hash(idRacun, rb); }

    /**
     * Vraca tekstualni prikaz stavke sa idRacun, rb, kolicinom, cenom i iznosom.
     *
     * @return string reprezentacija stavke
     */
    @Override
    public String toString() {
        return "StavkaRacuna{" +
                "idRacun=" + idRacun +
                ", rb=" + rb +
                ", kolicina=" + kolicina +
                ", cena=" + cena +
                ", ukupanIznos=" + ukupanIznos +
                '}';
    }

    /**
     * Vraca naziv tabele "stavkaracuna" za SQL upite.
     *
     * @return "stavkaracuna"
     */
    @Override
public String vratiNazivTabele() {
    return "stavkaracuna";
}

/**
 * Mapira ResultSet u listu StavkaRacuna objekata. Za svaki red ucitava
 * i Artikal iz JOIN kolona (idArtikal, naziv, artikal.cena, tip).
 *
 * @param rs ResultSet sa JOIN podacima stavke i artikla
 * @return lista stavki mapiranih iz ResultSet-a
 * @throws Exception ako dodje do greske pri citanju kolona
 */
@Override
public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
    List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

    while (rs.next()) {
        // Kreiramo Artikal objekat iz JOIN-ovanih kolona
        Artikal a = new Artikal(
            rs.getInt("idArtikal"),
            rs.getString("naziv"),
            rs.getDouble("artikal.cena"),
            rs.getString("tip")
        );

        StavkaRacuna sr = new StavkaRacuna(
            rs.getInt("idRacun"),
            rs.getInt("rb"),
            rs.getInt("kolicina"),
            rs.getDouble("ukupanIznos"),
            rs.getDouble("cena"),
            a
        );

        lista.add(sr);
    }
    return lista;
}


/**
 * Vraca SQL fragment kolona za INSERT:
 * "idRacun, rb, kolicina, cena, ukupanIznos, idArtikal".
 *
 * @return lista kolona za ubacivanje
 */
@Override
public String vratiKoloneZaUbacivanje() {
    return "idRacun, rb, kolicina, cena, ukupanIznos, idArtikal";
}

/**
 * Vraca SQL VALUES fragment sa idRacun, rb, kolicinom, cenom,
 * ukupnim iznosom i FK idArtikal.
 *
 * @return vrednosti formatirane za INSERT
 */
@Override
public String vratiVrednostiZaUbacivanje() {
    return idRacun + ", " + rb + ", " + kolicina + ", " + cena + ", " + ukupanIznos + ", " + artikal.getIdArtikal();
}

/**
 * Vraca SQL uslov kompozitnog primarnog kljuca:
 * rb=&lt;rb&gt; AND idRacun=&lt;idRacun&gt;.
 *
 * @return WHERE fragment za identifikaciju stavke
 */
@Override
public String vratiPrimarniKljuc() {
    return " rb="+rb+" AND idRacun="+idRacun;
}


/**
 * Mapira trenutni red ResultSet-a u StavkaRacuna. Artikal se postavlja
 * na null; ucitavaju se samo atributi same tabele stavkaracuna.
 *
 * @param rs ResultSet pozicioniran na red stavke
 * @return nova StavkaRacuna popunjena iz ResultSet-a (artikal = null)
 * @throws Exception ako dodje do greske pri citanju kolona
 */
@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new StavkaRacuna(
        rs.getInt("idRacun"),
        rs.getInt("rb"),
        rs.getInt("kolicina"),
        rs.getDouble("ukupanIznos"),
        rs.getDouble("cena"),
        null
    );
}

/**
 * Vraca SQL SET fragment za UPDATE: kolicina, cena, ukupanIznos i idArtikal.
 *
 * @return vrednosti za izmenu u formatu kolona=vrednost
 */
@Override
public String vratiVrednostiZaIzmenu() {
    return "kolicina=" + kolicina + ", cena=" + cena + ", ukupanIznos=" + ukupanIznos + ", idArtikal=" + artikal.getIdArtikal();
}

}
