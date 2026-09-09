
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StavkaRacuna implements ApstraktniDomenskiObjekat{
    private int idRacun;
    private int rb;
    private int kolicina;
    private double ukupanIznos;
    private double cena;

    private Artikal artikal;

    public StavkaRacuna() {}

    public StavkaRacuna(int idRacun, int rb, int kolicina, double ukupanIznos, double cena, Artikal artikal) {
        this.idRacun = idRacun;
        this.rb = rb;
        this.kolicina = kolicina;
        this.ukupanIznos = ukupanIznos;
        this.cena = cena;
        this.artikal = artikal;
    }

    public int getIdRacun() { return idRacun; }
    public void setIdRacun(int idRacun) { this.idRacun = idRacun; }

    public int getRb() { return rb; }
    public void setRb(int rb) { this.rb = rb; }

    public int getKolicina() { return kolicina; }
    public void setKolicina(int kolicina) { this.kolicina = kolicina; }

    public double getUkupanIznos() { return ukupanIznos; }
    public void setUkupanIznos(double ukupanIznos) { this.ukupanIznos = ukupanIznos; }

    public double getCena() { return cena; }
    public void setCena(double cena) { this.cena = cena; }

    public Artikal getArtikal() { return artikal; }
    public void setArtikal(Artikal artikal) { this.artikal = artikal; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StavkaRacuna)) return false;
        StavkaRacuna that = (StavkaRacuna) o;
        return idRacun == that.idRacun && rb == that.rb;
    }

    @Override
    public int hashCode() { return Objects.hash(idRacun, rb); }

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

    @Override
public String vratiNazivTabele() {
    return "stavkaracuna";
}

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


@Override
public String vratiKoloneZaUbacivanje() {
    return "idRacun, rb, kolicina, cena, ukupanIznos, idArtikal";
}

@Override
public String vratiVrednostiZaUbacivanje() {
    return idRacun + ", " + rb + ", " + kolicina + ", " + cena + ", " + ukupanIznos + ", " + artikal.getIdArtikal();
}

@Override
public String vratiPrimarniKljuc() {
    return " rb="+rb+" AND idRacun="+idRacun;
}


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

@Override
public String vratiVrednostiZaIzmenu() {
    return "kolicina=" + kolicina + ", cena=" + cena + ", ukupanIznos=" + ukupanIznos + ", idArtikal=" + artikal.getIdArtikal();
}

}

