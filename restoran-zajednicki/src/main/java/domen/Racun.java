package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domenska klasa koja predstavlja racun u restoranu.
 * Racun povezuje konobara, gosta i listu stavki, cuva datum i vreme
 * izdavanja, ukupan iznos i status da li je racun izdat. Koristi se
 * kao centralni objekat pri kreiranju i obradi porudzbina.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Racun implements ApstraktniDomenskiObjekat{

    /** Jedinstveni identifikator racuna u bazi podataka. */
    private int idRacun;

    /** Datum izdavanja racuna. */
    private LocalDate datumIzdavanja;

    /** Vreme izdavanja racuna. */
    private LocalTime vremeIzdavanja;

    /** Ukupan iznos racuna (zbir stavki, eventualno sa popustom). */
    private double ukupanIznos;

    /** Indikator da li je racun izdat (true) ili jos u pripremi (false). */
    private boolean jeIzdat;

    /** Konobar koji je izdao racun. */
    private Konobar konobar;

    /** Gost za kog je racun izdat. */
    private Gost gost;

    /** Lista stavki koje cine sadrzaj racuna. */
    private List<StavkaRacuna> stavke;

    /**
     * Podrazumevani konstruktor. Kreira prazan racun bez inicijalizovanih atributa.
     */
    public Racun() {}

    /**
     * Konstruktor koji kreira racun sa svim atributima.
     * Validacija se vrsi preko setera.
     *
     * @param idRacun jedinstveni identifikator racuna
     * @param datumIzdavanja datum izdavanja
     * @param vremeIzdavanja vreme izdavanja
     * @param ukupanIznos ukupan iznos (mora biti &gt; 0)
     * @param jeIzdat true ako je racun izdat, false ako je u pripremi
     * @param konobar konobar koji izdaje racun
     * @param gost gost za kog se izdaje racun
     * @param stavke lista stavki racuna (ne sme biti null ni prazna)
     * @throws IllegalArgumentException ako bilo koja prosledjena vrednost ne zadovoljava pravila settera
     */
    public Racun(int idRacun, LocalDate datumIzdavanja, LocalTime vremeIzdavanja, double ukupanIznos,
                 boolean jeIzdat, Konobar konobar, Gost gost, List<StavkaRacuna> stavke) {
        setIdRacun(idRacun);
        setDatumIzdavanja(datumIzdavanja);
        setVremeIzdavanja(vremeIzdavanja);
        setUkupanIznos(ukupanIznos);
        setJeIzdat(jeIzdat);
        setKonobar(konobar);
        setGost(gost);
        setStavke(stavke);
    }

    /**
     * Vraca jedinstveni identifikator racuna kao int.
     *
     * @return id racuna
     */
    public int getIdRacun() { return idRacun; }

    /**
     * Postavlja jedinstveni identifikator racuna.
     *
     * @param idRacun novi id (ne sme biti negativan)
     * @throws IllegalArgumentException ako je idRacun negativan
     */
    public void setIdRacun(int idRacun) {
        if (idRacun < 0) {
            throw new IllegalArgumentException("Id racuna ne sme biti negativan.");
        }
        this.idRacun = idRacun;
    }

    /**
     * Vraca datum izdavanja racuna kao {@link LocalDate}.
     *
     * @return datum izdavanja ili null ako nije postavljen
     */
    public LocalDate getDatumIzdavanja() { return datumIzdavanja; }

    /**
     * Postavlja datum izdavanja racuna.
     *
     * @param datumIzdavanja datum izdavanja (ne sme biti null)
     * @throws IllegalArgumentException ako je datumIzdavanja null
     */
    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        if (datumIzdavanja == null) {
            throw new IllegalArgumentException("Datum izdavanja racuna ne sme biti null.");
        }
        this.datumIzdavanja = datumIzdavanja;
    }

    /**
     * Vraca vreme izdavanja racuna kao {@link LocalTime}.
     *
     * @return vreme izdavanja ili null ako nije postavljeno
     */
    public LocalTime getVremeIzdavanja() { return vremeIzdavanja; }

    /**
     * Postavlja vreme izdavanja racuna.
     *
     * @param vremeIzdavanja vreme izdavanja (ne sme biti null)
     * @throws IllegalArgumentException ako je vremeIzdavanja null
     */
    public void setVremeIzdavanja(LocalTime vremeIzdavanja) {
        if (vremeIzdavanja == null) {
            throw new IllegalArgumentException("Vreme izdavanja racuna ne sme biti null.");
        }
        this.vremeIzdavanja = vremeIzdavanja;
    }

    /**
     * Vraca ukupan iznos racuna kao double.
     *
     * @return ukupan iznos racuna
     */
    public double getUkupanIznos() { return ukupanIznos; }

    /**
     * Postavlja ukupan iznos racuna.
     *
     * @param ukupanIznos novi iznos (mora biti &gt; 0)
     * @throws IllegalArgumentException ako je ukupanIznos manji ili jednak nuli
     */
    public void setUkupanIznos(double ukupanIznos) {
        if (ukupanIznos <= 0) {
            throw new IllegalArgumentException("Ukupan iznos racuna mora biti veci od nule.");
        }
        this.ukupanIznos = ukupanIznos;
    }

    /**
     * Vraca da li je racun izdat.
     *
     * @return true ako je racun izdat, inace false
     */
    public boolean isJeIzdat() { return jeIzdat; }

    /**
     * Postavlja status izdatosti racuna.
     *
     * @param jeIzdat true za izdat racun, false za racun u pripremi
     */
    public void setJeIzdat(boolean jeIzdat) { this.jeIzdat = jeIzdat; }

    /**
     * Vraca konobara koji je izdao racun kao objekat tipa {@link Konobar}.
     *
     * @return konobar ili null ako nije postavljen
     */
    public Konobar getKonobar() { return konobar; }

    /**
     * Postavlja konobara racuna.
     *
     * @param konobar konobar sa validnim idKonobar (ne sme biti null)
     * @throws IllegalArgumentException ako je konobar null
     */
    public void setKonobar(Konobar konobar) {
        if (konobar == null) {
            throw new IllegalArgumentException("Konobar racuna ne sme biti null.");
        }
        this.konobar = konobar;
    }

    /**
     * Vraca gosta racuna kao objekat tipa {@link Gost}.
     *
     * @return gost ili null ako nije postavljen
     */
    public Gost getGost() { return gost; }

    /**
     * Postavlja gosta racuna.
     *
     * @param gost gost sa validnim idGost (ne sme biti null)
     * @throws IllegalArgumentException ako je gost null
     */
    public void setGost(Gost gost) {
        if (gost == null) {
            throw new IllegalArgumentException("Gost racuna ne sme biti null.");
        }
        this.gost = gost;
    }

    /**
     * Vraca listu stavki racuna.
     *
     * @return lista {@link StavkaRacuna} ili null ako nije postavljena
     */
    public List<StavkaRacuna> getStavke() { return stavke; }

    /**
     * Postavlja listu stavki racuna.
     *
     * @param stavke lista stavki (ne sme biti null ni prazna)
     * @throws IllegalArgumentException ako je lista null ili prazna
     */
    public void setStavke(List<StavkaRacuna> stavke) {
        if (stavke == null || stavke.isEmpty()) {
            throw new IllegalArgumentException("Racun mora sadrzati barem jednu stavku.");
        }
        this.stavke = stavke;
    }

    /**
     * Poredi racune po identifikatoru {@code idRacun}.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o Racun sa istim idRacun, inace false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Racun)) return false;
        Racun racun = (Racun) o;
        return idRacun == racun.idRacun;
    }

    /**
     * Racuna hash kod na osnovu identifikatora racuna.
     *
     * @return hash kod zasnovan na idRacun
     */
    @Override
    public int hashCode() { return Objects.hash(idRacun); }

    /**
     * Vraca tekstualni prikaz racuna sa id, datumom, vremenom, iznosom i statusom.
     *
     * @return string reprezentacija racuna
     */
    @Override
    public String toString() {
        return "Racun{" +
                "id=" + idRacun +
                ", datum=" + datumIzdavanja +
                ", vreme=" + vremeIzdavanja +
                ", ukupanIznos=" + ukupanIznos +
                ", jeIzdat=" + jeIzdat +
                '}';
    }

    /**
     * Vraca naziv tabele "racun" za SQL upite.
     *
     * @return "racun"
     */
    @Override
    public String vratiNazivTabele() {
        return "racun";
    }

    /**
     * Mapira ResultSet u listu Racun objekata. Za svaki red ucitava
     * Konobara (kolone idKonobar, k.ime, k.prezime, k.korisnickoIme, k.sifra)
     * i Gosta (idGost, g.ime, g.prezime; kategorija = null). Stavke se
     * ne ucitavaju ovde (postavljaju se na null).
     *
     * @param rs ResultSet sa JOIN podacima racuna, konobara i gosta
     * @return lista racuna mapiranih iz ResultSet-a
     * @throws Exception ako dodje do greske pri citanju kolona
     */
    @Override
public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
    List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
    while (rs.next()) {
        Konobar k = new Konobar(
            rs.getInt("idKonobar"),
            rs.getString("k.ime"),
            rs.getString("k.prezime"),
            rs.getString("k.korisnickoIme"),
            rs.getString("k.sifra")
        );

        Gost g = new Gost(
            rs.getInt("idGost"),
            rs.getString("g.ime"),
            rs.getString("g.prezime"),
            null // ako imas i kategoriju gosta, ovde se dodaje
        );

        Racun r = new Racun();
        r.setIdRacun(rs.getInt("idRacun"));
        if (rs.getDate("datumIzdavanja") != null) {
            r.setDatumIzdavanja(rs.getDate("datumIzdavanja").toLocalDate());
        }
        if (rs.getTime("vremeIzdavanja") != null) {
            r.setVremeIzdavanja(rs.getTime("vremeIzdavanja").toLocalTime());
        }
        double iznos = rs.getDouble("ukupanIznos");
        if (iznos > 0) {
            r.setUkupanIznos(iznos);
        } else {
            r.ukupanIznos = iznos;
        }
        r.setJeIzdat(rs.getBoolean("jeIzdat"));
        r.setKonobar(k);
        r.setGost(g);
        // stavke se ucitavaju posebno

        lista.add(r);
    }
    return lista;
}


    /**
     * Vraca SQL fragment kolona za INSERT:
     * "datumIzdavanja, vremeIzdavanja, ukupanIznos, jeIzdat, idKonobar, idGost".
     *
     * @return lista kolona za ubacivanje
     */
    @Override
    public String vratiKoloneZaUbacivanje() {
        return "datumIzdavanja, vremeIzdavanja, ukupanIznos, jeIzdat, idKonobar, idGost";
    }

    /**
     * Vraca SQL VALUES fragment sa datumom, vremenom, iznosom, statusom
     * jeIzdat (kao 0/1), te FK idKonobar i idGost. Null datum/vreme
     * mapiraju se na SQL NULL.
     *
     * @return vrednosti formatirane za INSERT
     */
    @Override
    public String vratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum = (datumIzdavanja != null) ? java.sql.Date.valueOf(datumIzdavanja) : null;
        java.sql.Time sqlVreme = (vremeIzdavanja != null) ? java.sql.Time.valueOf(vremeIzdavanja) : null;

        int izdatInt = jeIzdat ? 1 : 0;

        return (sqlDatum != null ? "'" + sqlDatum + "'" : "NULL") + ", " +
               (sqlVreme != null ? "'" + sqlVreme + "'" : "NULL") + ", " +
               ukupanIznos + ", " +
               izdatInt + ", " +
               konobar.getIdKonobar() + ", " +
               gost.getIdGost();
    }



    /**
     * Vraca SQL uslov primarnog kljuca u formatu racun.idRacun=&lt;id&gt;.
     *
     * @return WHERE fragment za identifikaciju racuna
     */
    @Override
    public String vratiPrimarniKljuc() {
        return "racun.idRacun=" + idRacun;
    }


    /**
     * Mapira trenutni red ResultSet-a u Racun. Konobar, gost i stavke
     * se postavljaju na null; ucitavaju se samo atributi same tabele racun.
     *
     * @param rs ResultSet pozicioniran na red racuna
     * @return novi Racun popunjen iz ResultSet-a (veze = null)
     * @throws Exception ako dodje do greske pri citanju kolona
     */
    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        Racun r = new Racun();
        r.setIdRacun(rs.getInt("idRacun"));
        if (rs.getDate("datumIzdavanja") != null) {
            r.setDatumIzdavanja(rs.getDate("datumIzdavanja").toLocalDate());
        }
        if (rs.getTime("vremeIzdavanja") != null) {
            r.setVremeIzdavanja(rs.getTime("vremeIzdavanja").toLocalTime());
        }
        double iznos = rs.getDouble("ukupanIznos");
        if (iznos > 0) {
            r.setUkupanIznos(iznos);
        } else {
            r.ukupanIznos = iznos;
        }
        r.setJeIzdat(rs.getBoolean("jeIzdat"));
        // konobar, gost i stavke se ne ucitavaju ovde
        return r;
    }

    /**
     * Vraca SQL SET fragment za UPDATE: datum, vreme, ukupanIznos,
     * jeIzdat, idKonobar i idGost. Null datum/vreme mapiraju se na NULL.
     *
     * @return vrednosti za izmenu u formatu kolona=vrednost
     */
    @Override
    public String vratiVrednostiZaIzmenu() {
        return "datumIzdavanja=" + (datumIzdavanja != null ? "'" + datumIzdavanja + "'" : "NULL") +
               ", vremeIzdavanja=" + (vremeIzdavanja != null ? "'" + vremeIzdavanja + "'" : "NULL") +
               ", ukupanIznos=" + ukupanIznos +
               ", jeIzdat=" + jeIzdat +
               ", idKonobar=" + konobar.getIdKonobar() +
               ", idGost=" + gost.getIdGost();
    }
    



}
