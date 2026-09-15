package domen;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja radnu smenu u restoranu.
 * Smena ima naziv i vremenski interval (pocetak i kraj) i koristi se
 * pri rasporedjivanju konobara preko veze {@link KonobarSmena}.
 * Odgovara tabeli smena u bazi podataka.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Smena implements ApstraktniDomenskiObjekat{

    /** Jedinstveni identifikator smene u bazi podataka. */
    private int idSmena;

    /** Naziv smene (npr. "Jutarnja", "Popodnevna", "Vecernja"). */
    private String naziv;

    /** Vreme pocetka smene. */
    private LocalTime vremePocetka;

    /** Vreme kraja smene (moze biti null ako nije definisano). */
    private LocalTime vremeKraja;

    /**
     * Podrazumevani konstruktor. Kreira praznu smenu bez inicijalizovanih atributa.
     */
    public Smena() {}

    /**
     * Konstruktor koji kreira smenu sa svim atributima.
     *
     * @param idSmena jedinstveni identifikator smene
     * @param naziv naziv smene
     * @param vremePocetka vreme pocetka smene
     * @param vremeKraja vreme kraja smene (moze biti null)
     */
    public Smena(int idSmena, String naziv, LocalTime vremePocetka, LocalTime vremeKraja) {
        this.idSmena = idSmena;
        this.naziv = naziv;
        this.vremePocetka = vremePocetka;
        this.vremeKraja = vremeKraja;
    }

    /**
     * Vraca jedinstveni identifikator smene kao int.
     *
     * @return id smene
     */
    public int getIdSmena() { return idSmena; }

    /**
     * Postavlja jedinstveni identifikator smene.
     *
     * @param idSmena novi id (pozitivan broj koji odgovara PK u bazi)
     */
    public void setIdSmena(int idSmena) { this.idSmena = idSmena; }

    /**
     * Vraca naziv smene kao String.
     *
     * @return naziv smene
     */
    public String getNaziv() { return naziv; }

    /**
     * Postavlja naziv smene.
     *
     * @param naziv novi naziv (ne bi trebalo da bude null ili prazan string)
     */
    public void setNaziv(String naziv) { this.naziv = naziv; }

    /**
     * Vraca vreme pocetka smene kao {@link LocalTime}.
     *
     * @return vreme pocetka
     */
    public LocalTime getVremePocetka() { return vremePocetka; }

    /**
     * Postavlja vreme pocetka smene.
     *
     * @param vremePocetka vreme pocetka (ne bi trebalo da bude null)
     */
    public void setVremePocetka(LocalTime vremePocetka) { this.vremePocetka = vremePocetka; }

    /**
     * Vraca vreme kraja smene kao {@link LocalTime}.
     *
     * @return vreme kraja ili null ako nije definisano
     */
    public LocalTime getVremeKraja() { return vremeKraja; }

    /**
     * Postavlja vreme kraja smene.
     *
     * @param vremeKraja vreme kraja (moze biti null; ako je postavljeno,
     *        ocekivano je posle vremena pocetka)
     */
    public void setVremeKraja(LocalTime vremeKraja) { this.vremeKraja = vremeKraja; }

    /**
     * Poredi smene po identifikatoru {@code idSmena}.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o Smena sa istim idSmena, inace false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Smena)) return false;
        Smena smena = (Smena) o;
        return idSmena == smena.idSmena;
    }

    /**
     * Racuna hash kod na osnovu identifikatora smene.
     *
     * @return hash kod zasnovan na idSmena
     */
    @Override
    public int hashCode() { return Objects.hash(idSmena); }

    /**
     * Vraca tekstualni prikaz smene u formatu "naziv, pocetak-kraj".
     *
     * @return string reprezentacija smene
     */
    @Override
    public String toString() {
        return naziv + ", " + vremePocetka + "-" + vremeKraja;
    }

    /**
     * Vraca naziv tabele "smena" za SQL upite.
     *
     * @return "smena"
     */
    @Override
public String vratiNazivTabele() {
    return "smena";
}

/**
 * Mapira ResultSet u listu Smena objekata citajuci kolone
 * idSmena, naziv, vremePocetka i vremeKraja (vremeKraja moze biti null).
 *
 * @param rs ResultSet sa redovima tabele smena
 * @return lista smena mapiranih iz ResultSet-a
 * @throws Exception ako dodje do greske pri citanju kolona ili konverziji vremena
 */
@Override
public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
    List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
    while (rs.next()) {
        Smena s = new Smena(
            rs.getInt("idSmena"),
            rs.getString("naziv"),
            rs.getTime("vremePocetka").toLocalTime(),
            rs.getTime("vremeKraja") != null ? rs.getTime("vremeKraja").toLocalTime() : null
        );
        lista.add(s);
    }
    return lista;
}

/**
 * Vraca SQL fragment kolona za INSERT: "naziv, vremePocetka, vremeKraja".
 *
 * @return lista kolona za ubacivanje
 */
@Override
public String vratiKoloneZaUbacivanje() {
    return "naziv, vremePocetka, vremeKraja";
}

/**
 * Vraca SQL VALUES fragment sa nazivom, vremenom pocetka i kraja.
 * Null vremeKraja mapira se na SQL NULL.
 *
 * @return vrednosti formatirane za INSERT
 */
@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + naziv + "', '" + vremePocetka + "', " + (vremeKraja != null ? "'" + vremeKraja + "'" : "NULL");
}

/**
 * Vraca SQL uslov primarnog kljuca u formatu smena.idSmena=&lt;id&gt;.
 *
 * @return WHERE fragment za identifikaciju smene
 */
@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idSmena=" + idSmena;
}


/**
 * Mapira trenutni red ResultSet-a u jedan Smena objekat.
 *
 * @param rs ResultSet pozicioniran na red smene
 * @return nova Smena popunjena iz ResultSet-a
 * @throws Exception ako dodje do greske pri citanju kolona ili konverziji vremena
 */
@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new Smena(
        rs.getInt("idSmena"),
        rs.getString("naziv"),
        rs.getTime("vremePocetka").toLocalTime(),
        rs.getTime("vremeKraja") != null ? rs.getTime("vremeKraja").toLocalTime() : null
    );
}

/**
 * Vraca SQL SET fragment za UPDATE: naziv, vremePocetka i vremeKraja.
 * Null vremeKraja mapira se na SQL NULL.
 *
 * @return vrednosti za izmenu u formatu kolona=vrednost
 */
@Override
public String vratiVrednostiZaIzmenu() {
    return "naziv='" + naziv + "', vremePocetka='" + vremePocetka + "', vremeKraja=" +
           (vremeKraja != null ? "'" + vremeKraja + "'" : "NULL");
}

}
