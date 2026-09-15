package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja konobara u sistemu restorana.
 * Konobar se autentifikuje korisnickim imenom i sifrom, izdaje racune
 * i moze biti rasporedjen na smene. Klasa se koristi pri prijavi na
 * sistem i pri CRUD operacijama nad tabelom konobar.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Konobar implements ApstraktniDomenskiObjekat{

    /** Jedinstveni identifikator konobara u bazi podataka. */
    private int idKonobar;

    /** Ime konobara. */
    private String ime;

    /** Prezime konobara. */
    private String prezime;

    /** Korisnicko ime za prijavu na sistem. */
    private String korisnickoIme;

    /** Sifra za prijavu na sistem. */
    private String sifra;

    /**
     * Podrazumevani konstruktor. Kreira praznog konobara bez inicijalizovanih atributa.
     */
    public Konobar() {}

    /**
     * Konstruktor koji kreira konobara sa svim atributima.
     *
     * @param idKonobar jedinstveni identifikator konobara
     * @param ime ime konobara
     * @param prezime prezime konobara
     * @param korisnickoIme korisnicko ime za autentifikaciju
     * @param sifra sifra za autentifikaciju
     */
    public Konobar(int idKonobar, String ime, String prezime, String korisnickoIme, String sifra) {
        this.idKonobar = idKonobar;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    /**
     * Vraca jedinstveni identifikator konobara kao int.
     *
     * @return id konobara
     */
    public int getIdKonobar() { return idKonobar; }

    /**
     * Postavlja jedinstveni identifikator konobara.
     *
     * @param idKonobar novi id (pozitivan broj koji odgovara PK u bazi)
     */
    public void setIdKonobar(int idKonobar) { this.idKonobar = idKonobar; }

    /**
     * Vraca ime konobara kao String.
     *
     * @return ime konobara
     */
    public String getIme() { return ime; }

    /**
     * Postavlja ime konobara.
     *
     * @param ime novo ime (ne bi trebalo da bude null ili prazan string)
     */
    public void setIme(String ime) { this.ime = ime; }

    /**
     * Vraca prezime konobara kao String.
     *
     * @return prezime konobara
     */
    public String getPrezime() { return prezime; }

    /**
     * Postavlja prezime konobara.
     *
     * @param prezime novo prezime (ne bi trebalo da bude null ili prazan string)
     */
    public void setPrezime(String prezime) { this.prezime = prezime; }

    /**
     * Vraca korisnicko ime konobara kao String.
     *
     * @return korisnicko ime za prijavu
     */
    public String getKorisnickoIme() { return korisnickoIme; }

    /**
     * Postavlja korisnicko ime konobara.
     *
     * @param korisnickoIme novo korisnicko ime (jedinstveno u sistemu, ne prazno)
     */
    public void setKorisnickoIme(String korisnickoIme) { this.korisnickoIme = korisnickoIme; }

    /**
     * Vraca sifru konobara kao String.
     *
     * @return sifra za prijavu
     */
    public String getSifra() { return sifra; }

    /**
     * Postavlja sifru konobara.
     *
     * @param sifra nova sifra (ne bi trebalo da bude null ili prazan string)
     */
    public void setSifra(String sifra) { this.sifra = sifra; }

    /**
     * Proverava da li prosledjeni kredencijali odgovaraju ovom konobaru.
     * Poredi korisnicko ime i sifru pomocu {@link Objects#equals(Object, Object)},
     * sto ispravno rukuje i null vrednostima. Vraca true samo ako se oba
     * atributa poklapaju sa unetim vrednostima (uspeh prijave).
     *
     * @param korisnickoIme uneto korisnicko ime za proveru
     * @param sifra uneta sifra za proveru
     * @return true ako se korisnicko ime i sifra poklapaju sa atributima objekta,
     *         inace false
     */
    public boolean proveriKredencijale(String korisnickoIme, String sifra) {
        return Objects.equals(this.korisnickoIme, korisnickoIme)
                && Objects.equals(this.sifra, sifra);
    }

    /**
     * Vraca tekstualni prikaz konobara u formatu "ime prezime".
     *
     * @return string reprezentacija konobara
     */
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    /**
     * Vraca naziv tabele "konobar" za SQL upite.
     *
     * @return "konobar"
     */
    @Override
    public String vratiNazivTabele() {
        return "konobar";
    }

    /**
     * Mapira ResultSet u listu Konobar objekata. Ocekuje kvalifikovane
     * kolone: konobar.idKonobar, konobar.ime, konobar.prezime,
     * konobar.korisnickoIme, konobar.sifra (tipicno iz JOIN upita).
     *
     * @param rs ResultSet sa redovima konobara
     * @return lista konobara mapiranih iz ResultSet-a
     * @throws Exception ako dodje do greske pri citanju kolona
     */
    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Konobar k = new Konobar(
                rs.getInt("konobar.idKonobar"),
                rs.getString("konobar.ime"),
                rs.getString("konobar.prezime"),
                rs.getString("konobar.korisnickoIme"),
                rs.getString("konobar.sifra")
            );
            lista.add(k);
        }
        return lista;
    }

    /**
     * Vraca SQL fragment kolona za INSERT: "ime, prezime, korisnickoIme, sifra".
     *
     * @return lista kolona za ubacivanje
     */
    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime, prezime, korisnickoIme, sifra";
    }

    /**
     * Vraca SQL VALUES fragment sa imenom, prezimenom, korisnickim imenom i sifrom.
     *
     * @return vrednosti formatirane za INSERT
     */
    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + ime + "', '" + prezime + "', '" + korisnickoIme + "', '" + sifra + "'";
    }

    /**
     * Vraca SQL uslov primarnog kljuca u formatu konobar.idKonobar=&lt;id&gt;.
     *
     * @return WHERE fragment za identifikaciju konobara
     */
    @Override
    public String vratiPrimarniKljuc() {
        return vratiNazivTabele() + ".idKonobar=" + idKonobar;
    }


    /**
     * Mapira trenutni red ResultSet-a u jedan Konobar objekat citajuci
     * nekvalifikovane kolone idKonobar, ime, prezime, korisnickoIme, sifra.
     *
     * @param rs ResultSet pozicioniran na red konobara
     * @return novi Konobar popunjen iz ResultSet-a
     * @throws Exception ako dodje do greske pri citanju kolona
     */
    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        return new Konobar(
            rs.getInt("idKonobar"),
            rs.getString("ime"),
            rs.getString("prezime"),
            rs.getString("korisnickoIme"),
            rs.getString("sifra")
        );
    }

    /**
     * Vraca SQL SET fragment za UPDATE: ime, prezime, korisnickoIme i sifra.
     *
     * @return vrednosti za izmenu u formatu kolona=vrednost
     */
    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "', prezime='" + prezime + "', korisnickoIme='" + korisnickoIme + "', sifra='" + sifra + "'";
    }

    /**
     * Racuna hash kod na osnovu identifikatora konobara.
     *
     * @return hash kod zasnovan na idKonobar
     */
    @Override
    public int hashCode() {
        return Objects.hash(idKonobar);
    }

    /**
     * Poredi konobare po identifikatoru {@code idKonobar} (ista klasa i isti id).
     *
     * @param obj objekat sa kojim se poredi
     * @return true ako je obj Konobar sa istim idKonobar, inace false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Konobar other = (Konobar) obj;
        return this.idKonobar == other.idKonobar;
    }

    
    
    
}
