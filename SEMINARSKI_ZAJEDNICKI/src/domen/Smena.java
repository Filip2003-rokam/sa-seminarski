package domen;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Smena implements ApstraktniDomenskiObjekat{
    private int idSmena;
    private String naziv;
    private LocalTime vremePocetka;
    private LocalTime vremeKraja;

    public Smena() {}

    public Smena(int idSmena, String naziv, LocalTime vremePocetka, LocalTime vremeKraja) {
        this.idSmena = idSmena;
        this.naziv = naziv;
        this.vremePocetka = vremePocetka;
        this.vremeKraja = vremeKraja;
    }

    public int getIdSmena() { return idSmena; }
    public void setIdSmena(int idSmena) { this.idSmena = idSmena; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public LocalTime getVremePocetka() { return vremePocetka; }
    public void setVremePocetka(LocalTime vremePocetka) { this.vremePocetka = vremePocetka; }

    public LocalTime getVremeKraja() { return vremeKraja; }
    public void setVremeKraja(LocalTime vremeKraja) { this.vremeKraja = vremeKraja; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Smena)) return false;
        Smena smena = (Smena) o;
        return idSmena == smena.idSmena;
    }

    @Override
    public int hashCode() { return Objects.hash(idSmena); }

    @Override
    public String toString() {
        return naziv + ", " + vremePocetka + "-" + vremeKraja;
    }

    @Override
public String vratiNazivTabele() {
    return "smena";
}

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

@Override
public String vratiKoloneZaUbacivanje() {
    return "naziv, vremePocetka, vremeKraja";
}

@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + naziv + "', '" + vremePocetka + "', " + (vremeKraja != null ? "'" + vremeKraja + "'" : "NULL");
}

@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idSmena=" + idSmena;
}


@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new Smena(
        rs.getInt("idSmena"),
        rs.getString("naziv"),
        rs.getTime("vremePocetka").toLocalTime(),
        rs.getTime("vremeKraja") != null ? rs.getTime("vremeKraja").toLocalTime() : null
    );
}

@Override
public String vratiVrednostiZaIzmenu() {
    return "naziv='" + naziv + "', vremePocetka='" + vremePocetka + "', vremeKraja=" +
           (vremeKraja != null ? "'" + vremeKraja + "'" : "NULL");
}

}

