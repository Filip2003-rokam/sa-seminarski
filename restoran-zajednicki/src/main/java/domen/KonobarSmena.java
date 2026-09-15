package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja vezu izmedju konobara i smene (M:N relacija),
 * sa dodatnim atributom datumSmene. Koristi se za raspored konobara po smenama
 * na konkretan dan. Odgovara tabeli konobarsmena u bazi podataka.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class KonobarSmena implements ApstraktniDomenskiObjekat {

    /** Konobar koji je rasporedjen na smenu. */
    private Konobar konobar;

    /** Smena na koju je konobar rasporedjen. */
    private Smena smena;

    /** Datum na koji vazi raspored konobara na smenu. */
    private LocalDate datumSmene;

    /**
     * Podrazumevani konstruktor. Kreira praznu vezu konobar-smena.
     */
    public KonobarSmena() {}

    /**
     * Konstruktor koji kreira vezu konobara, smene i datuma.
     *
     * @param konobar konobar koji se rasporedjuje (mora imati validan idKonobar)
     * @param smena smena na koju se rasporedjuje (mora imati validan idSmena)
     * @param datumSmene datum vazenja rasporeda
     */
    public KonobarSmena(Konobar konobar, Smena smena, LocalDate datumSmene) {
        this.konobar = konobar;
        this.smena = smena;
        this.datumSmene = datumSmene;
    }

    /**
     * Vraca konobara vezanog za ovaj raspored kao objekat tipa {@link Konobar}.
     *
     * @return konobar ili null ako nije postavljen
     */
    public Konobar getKonobar() {
        return konobar;
    }

    /**
     * Postavlja konobara za ovaj raspored.
     *
     * @param konobar konobar sa validnim idKonobar (potreban za INSERT/PK)
     */
    public void setKonobar(Konobar konobar) {
        this.konobar = konobar;
    }

    /**
     * Vraca smenu vezanu za ovaj raspored kao objekat tipa {@link Smena}.
     *
     * @return smena ili null ako nije postavljena
     */
    public Smena getSmena() {
        return smena;
    }

    /**
     * Postavlja smenu za ovaj raspored.
     *
     * @param smena smena sa validnim idSmena (potrebna za INSERT/PK)
     */
    public void setSmena(Smena smena) {
        this.smena = smena;
    }

    /**
     * Vraca datum smene kao {@link LocalDate}.
     *
     * @return datum na koji vazi raspored
     */
    public LocalDate getDatumSmene() {
        return datumSmene;
    }

    /**
     * Postavlja datum smene.
     *
     * @param datumSmene datum vazenja rasporeda (ne bi trebalo da bude null)
     */
    public void setDatumSmene(LocalDate datumSmene) {
        this.datumSmene = datumSmene;
    }

    /**
     * Vraca naziv tabele "konobarsmena" za SQL upite.
     *
     * @return "konobarsmena"
     */
    @Override
    public String vratiNazivTabele() {
        return "konobarsmena";
    }

    /**
     * Vraca SQL fragment kolona za INSERT: "idKonobar, idSmena, datumSmene".
     *
     * @return lista kolona za ubacivanje
     */
    @Override
    public String vratiKoloneZaUbacivanje() {
        return "idKonobar, idSmena, datumSmene";
    }

    /**
     * Vraca SQL VALUES fragment sa FK idKonobar, idSmena i datumom smene.
     *
     * @return vrednosti formatirane za INSERT
     */
    @Override
    public String vratiVrednostiZaUbacivanje() {
        return konobar.getIdKonobar() + ", " +
               smena.getIdSmena() + ", '" +
               datumSmene + "'";
    }

    /**
     * Vraca SQL uslov kompozitnog primarnog kljuca:
     * idKonobar=... AND idSmena=... AND datumSmene='...'.
     *
     * @return WHERE fragment za identifikaciju zapisa veze
     */
    @Override
    public String vratiPrimarniKljuc() {
        return "idKonobar=" + konobar.getIdKonobar() +
               " AND idSmena=" + smena.getIdSmena() +
               " AND datumSmene='" + datumSmene + "'";
    }

    /**
     * Vraca SQL SET fragment za UPDATE (azurira datumSmene).
     *
     * @return vrednosti za izmenu u formatu kolona=vrednost
     */
    @Override
    public String vratiVrednostiZaIzmenu() {
        // obicno se datum ne menja, ali primer:
        return "datumSmene='" + datumSmene + "'";
    }

    /**
     * Mapira trenutni red ResultSet-a u KonobarSmena. Ocekuje kolone
     * idKonobar, ime, prezime, idSmena, naziv, pocetak, kraj i datumSmene
     * (JOIN konobara i smene). Vreme pocetka/kraja cita iz kolona "pocetak"/"kraj".
     *
     * @param rs ResultSet pozicioniran na red veze
     * @return novi KonobarSmena popunjen iz ResultSet-a
     * @throws Exception ako dodje do greske pri citanju kolona ili konverziji vremena
     */
    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        Konobar k = new Konobar();
        k.setIdKonobar(rs.getInt("idKonobar"));
        // ako zelis mozes da ucitas i ime/prezime ako je JOIN u upitu
        k.setIme(rs.getString("ime"));
        k.setPrezime(rs.getString("prezime"));

        Smena s = new Smena();
        s.setIdSmena(rs.getInt("idSmena"));
        s.setNaziv(rs.getString("naziv"));
        s.setVremePocetka(rs.getTime("pocetak").toLocalTime());
        s.setVremeKraja(rs.getTime("kraj").toLocalTime());

        LocalDate datum = rs.getDate("datumSmene").toLocalDate();

        return new KonobarSmena(k, s, datum);
    }

    /**
     * Mapira ResultSet u listu KonobarSmena objekata. Za svaki red ucitava
     * kompletnog Konobara (ukljucujuci kredencijale) i Smene (vremePocetka,
     * vremeKraja) zajedno sa datumom smene.
     *
     * @param rs ResultSet sa JOIN podacima konobara, smene i datuma
     * @return lista veza KonobarSmena mapiranih iz ResultSet-a
     * @throws Exception ako dodje do greske pri citanju kolona ili konverziji vremena/datuma
     */
    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Konobar k = new Konobar(
                rs.getInt("idKonobar"),
                rs.getString("ime"),
                rs.getString("prezime"),
                rs.getString("korisnickoIme"),
                rs.getString("sifra")
            );

            Smena s = new Smena(
                rs.getInt("idSmena"),
                rs.getString("naziv"),
                rs.getTime("vremePocetka").toLocalTime(),
                rs.getTime("vremeKraja").toLocalTime()
            );

            KonobarSmena ks = new KonobarSmena(k, s, rs.getDate("datumSmene").toLocalDate());
            lista.add(ks);
        }
        return lista;
    }


    /**
     * Poredi veze po konobaru, smeni i datumu smene.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako su sva tri atributa jednaka, inace false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KonobarSmena)) return false;
        KonobarSmena that = (KonobarSmena) o;
        return Objects.equals(konobar, that.konobar)
                && Objects.equals(smena, that.smena)
                && Objects.equals(datumSmene, that.datumSmene);
    }

    /**
     * Racuna hash kod na osnovu konobara, smene i datuma smene.
     *
     * @return hash kod kompozitnog kljuca veze
     */
    @Override
    public int hashCode() {
        return Objects.hash(konobar, smena, datumSmene);
    }

    /**
     * Vraca tekstualni prikaz veze konobar-smena sa datumom.
     *
     * @return string reprezentacija objekta
     */
    @Override
    public String toString() {
        return "KonobarSmena{" +
                "konobar=" + konobar +
                ", smena=" + smena +
                ", datumSmene=" + datumSmene +
                '}';
    }
}
