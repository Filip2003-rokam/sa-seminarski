
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja gosta restorana.
 * Gost je povezan sa kategorijom gosta koja odredjuje eventualni popust
 * na racunu. Koristi se pri izdavanju racuna i pri CRUD operacijama
 * nad tabelom gost.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Gost implements ApstraktniDomenskiObjekat{

    /** Jedinstveni identifikator gosta u bazi podataka. */
    private int idGost;

    /** Ime gosta. */
    private String ime;

    /** Prezime gosta. */
    private String prezime;

    /** Kategorija gosta koja odredjuje popust (veza ka KategorijaGosta). */
    private KategorijaGosta kategorijaGosta;

    /**
     * Podrazumevani konstruktor. Kreira praznog gosta bez inicijalizovanih atributa.
     */
    public Gost() {}

    /**
     * Konstruktor koji kreira gosta sa svim atributima.
     *
     * @param idGost jedinstveni identifikator gosta
     * @param ime ime gosta
     * @param prezime prezime gosta
     * @param kategorijaGosta kategorija kojoj gost pripada (moze uticati na popust)
     */
    public Gost(int idGost, String ime, String prezime, KategorijaGosta kategorijaGosta) {
        this.idGost = idGost;
        this.ime = ime;
        this.prezime = prezime;
        this.kategorijaGosta = kategorijaGosta;
    }

    /**
     * Vraca jedinstveni identifikator gosta kao int.
     *
     * @return id gosta
     */
    public int getIdGost() { return idGost; }

    /**
     * Postavlja jedinstveni identifikator gosta.
     *
     * @param idGost novi id gosta (pozitivan broj koji odgovara PK u bazi)
     */
    public void setIdGost(int idGost) { this.idGost = idGost; }

    /**
     * Vraca ime gosta kao String.
     *
     * @return ime gosta
     */
    public String getIme() { return ime; }

    /**
     * Postavlja ime gosta.
     *
     * @param ime novo ime (ne bi trebalo da bude null ili prazan string)
     */
    public void setIme(String ime) { this.ime = ime; }

    /**
     * Vraca prezime gosta kao String.
     *
     * @return prezime gosta
     */
    public String getPrezime() { return prezime; }

    /**
     * Postavlja prezime gosta.
     *
     * @param prezime novo prezime (ne bi trebalo da bude null ili prazan string)
     */
    public void setPrezime(String prezime) { this.prezime = prezime; }

    /**
     * Vraca kategoriju gosta kao objekat tipa {@link KategorijaGosta}.
     *
     * @return kategorija gosta ili null ako nije postavljena
     */
    public KategorijaGosta getKategorijaGosta() { return kategorijaGosta; }

    /**
     * Postavlja kategoriju gosta.
     *
     * @param kategorijaGosta kategorija sa validnim idKategorijaGosta
     *        (potrebna za INSERT/UPDATE jer se koristi FK)
     */
    public void setKategorijaGosta(KategorijaGosta kategorijaGosta) { 
        this.kategorijaGosta = kategorijaGosta; 
    }

    /**
     * Poredi goste po identifikatoru {@code idGost}.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o Gost sa istim idGost, inace false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gost)) return false;
        Gost gost = (Gost) o;
        return idGost == gost.idGost;
    }

    /**
     * Racuna hash kod na osnovu identifikatora gosta.
     *
     * @return hash kod zasnovan na idGost
     */
    @Override
    public int hashCode() { return Objects.hash(idGost); }

    /**
     * Vraca tekstualni prikaz gosta u formatu "ime prezime".
     *
     * @return string reprezentacija gosta
     */
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    /**
     * Vraca naziv tabele "gost" za SQL upite.
     *
     * @return "gost"
     */
    @Override
public String vratiNazivTabele() {
    return "gost";
}

  /**
   * Mapira ResultSet u listu Gost objekata. Za svaki red ucitava i
   * KategorijaGosta (kolone idKategorijaGosta, opis, popust) uz fiksiran
   * imaPopust=true.
   *
   * @param rs ResultSet sa JOIN podacima gosta i kategorije
   * @return lista gostiju mapiranih iz ResultSet-a
   * @throws Exception ako dodje do greske pri citanju kolona
   */
  @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            KategorijaGosta kg = new KategorijaGosta( rs.getInt("idKategorijaGosta"),
                rs.getString("opis"),
                rs.getDouble("popust"),
                true
            );

            Gost g = new Gost( rs.getInt("idGost"),
                rs.getString("ime"),
                rs.getString("prezime"),
                kg
            );

            lista.add(g);
        }
        return lista;
    }

/**
 * Vraca SQL fragment kolona za INSERT: "ime, prezime, idKategorijaGosta".
 *
 * @return lista kolona za ubacivanje
 */
@Override
public String vratiKoloneZaUbacivanje() {
    return "ime, prezime, idKategorijaGosta";
}

/**
 * Vraca SQL VALUES fragment sa imenom, prezimenom i FK idKategorijaGosta.
 *
 * @return vrednosti formatirane za INSERT
 */
@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + ime + "', '" + prezime + "', " + kategorijaGosta.getIdKategorijaGosta();
}

/**
 * Vraca SQL uslov primarnog kljuca u formatu gost.idGost=&lt;id&gt;.
 *
 * @return WHERE fragment za identifikaciju gosta
 */
@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idGost=" + idGost;
}


/**
 * Mapira trenutni red ResultSet-a u Gost objekat. Kategorija se postavlja
 * na null (ucitava se samo idGost, ime i prezime).
 *
 * @param rs ResultSet pozicioniran na red gosta
 * @return novi Gost popunjen iz ResultSet-a (kategorijaGosta = null)
 * @throws Exception ako dodje do greske pri citanju kolona
 */
@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new Gost(rs.getInt("idGost"), rs.getString("ime"), rs.getString("prezime"),null);
}

/**
 * Vraca SQL SET fragment za UPDATE: ime, prezime i idKategorijaGosta.
 *
 * @return vrednosti za izmenu u formatu kolona=vrednost
 */
@Override
public String vratiVrednostiZaIzmenu() {
    return "ime='" + ime + "', prezime='" + prezime + "', idKategorijaGosta=" + 
            kategorijaGosta.getIdKategorijaGosta();
}

}
