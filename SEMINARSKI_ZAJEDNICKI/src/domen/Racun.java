package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Racun implements ApstraktniDomenskiObjekat{
    private int idRacun;
    private LocalDate datumIzdavanja;
    private LocalTime vremeIzdavanja;
    private double ukupanIznos;
    private boolean jeIzdat;

    private Konobar konobar;
    private Gost gost;
    private List<StavkaRacuna> stavke;

    public Racun() {}

    public Racun(int idRacun, LocalDate datumIzdavanja, LocalTime vremeIzdavanja, double ukupanIznos,
                 boolean jeIzdat, Konobar konobar, Gost gost, List<StavkaRacuna> stavke) {
        this.idRacun = idRacun;
        this.datumIzdavanja = datumIzdavanja;
        this.vremeIzdavanja = vremeIzdavanja;
        this.ukupanIznos = ukupanIznos;
        this.jeIzdat = jeIzdat;
        this.konobar = konobar;
        this.gost = gost;
        this.stavke = stavke;
    }

    public int getIdRacun() { return idRacun; }
    public void setIdRacun(int idRacun) { this.idRacun = idRacun; }

    public LocalDate getDatumIzdavanja() { return datumIzdavanja; }
    public void setDatumIzdavanja(LocalDate datumIzdavanja) { this.datumIzdavanja = datumIzdavanja; }

    public LocalTime getVremeIzdavanja() { return vremeIzdavanja; }
    public void setVremeIzdavanja(LocalTime vremeIzdavanja) { this.vremeIzdavanja = vremeIzdavanja; }

    public double getUkupanIznos() { return ukupanIznos; }
    public void setUkupanIznos(double ukupanIznos) { this.ukupanIznos = ukupanIznos; }

    public boolean isJeIzdat() { return jeIzdat; }
    public void setJeIzdat(boolean jeIzdat) { this.jeIzdat = jeIzdat; }

    public Konobar getKonobar() { return konobar; }
    public void setKonobar(Konobar konobar) { this.konobar = konobar; }

    public Gost getGost() { return gost; }
    public void setGost(Gost gost) { this.gost = gost; }

    public List<StavkaRacuna> getStavke() { return stavke; }
    public void setStavke(List<StavkaRacuna> stavke) { this.stavke = stavke; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Racun)) return false;
        Racun racun = (Racun) o;
        return idRacun == racun.idRacun;
    }

    @Override
    public int hashCode() { return Objects.hash(idRacun); }

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

    @Override
    public String vratiNazivTabele() {
        return "racun ";
    }

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
            null // ako imaš i kategoriju gosta, ovde se dodaje
        );
        
        

        Racun r = new Racun(
            rs.getInt("idRacun"),
            rs.getDate("datumIzdavanja") != null ? rs.getDate("datumIzdavanja").toLocalDate() : null,
            rs.getTime("vremeIzdavanja") != null ? rs.getTime("vremeIzdavanja").toLocalTime() : null,
            rs.getDouble("ukupanIznos"),
            rs.getBoolean("jeIzdat"),
            k, // ovde ide konobar
            g, // ovde ide gost
            null // stavke kasnije posebno
        );

        lista.add(r);
    }
    return lista;
}


    @Override
    public String vratiKoloneZaUbacivanje() {
        return "datumIzdavanja, vremeIzdavanja, ukupanIznos, jeIzdat, idKonobar, idGost";
    }

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



    @Override
    public String vratiPrimarniKljuc() {
        return "racun.idRacun=" + idRacun;
    }


    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        return new Racun(
            rs.getInt("idRacun"),
            rs.getDate("datumIzdavanja") != null ? rs.getDate("datumIzdavanja").toLocalDate() : null,
            rs.getTime("vremeIzdavanja") != null ? rs.getTime("vremeIzdavanja").toLocalTime() : null,
            rs.getDouble("ukupanIznos"),
            rs.getBoolean("jeIzdat"),
            null,
            null,
            null
        );
    }

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

