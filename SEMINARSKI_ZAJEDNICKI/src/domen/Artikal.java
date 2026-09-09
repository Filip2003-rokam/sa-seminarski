package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Artikal implements ApstraktniDomenskiObjekat{
    private int idArtikal;
    private String naziv;
    private double cena;
    private String tip; // Jelo ili Pice

    public Artikal() {}

    public Artikal(int idArtikal, String naziv, double cena, String tip) {
        this.idArtikal = idArtikal;
        this.naziv = naziv;
        this.cena = cena;
        this.tip = tip;
    }

    public int getIdArtikal() { return idArtikal; }
    public void setIdArtikal(int idArtikal) { this.idArtikal = idArtikal; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public double getCena() { return cena; }
    public void setCena(double cena) { this.cena = cena; }

    public String getTip() { return tip; }
    public void setTip(String tip) { this.tip = tip; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artikal)) return false;
        Artikal that = (Artikal) o;
        return idArtikal == that.idArtikal;
    }

    @Override
    public int hashCode() { return Objects.hash(idArtikal); }

    @Override
    public String toString() {
        return naziv + ' ' + ", " + cena;
    }

@Override
public String vratiNazivTabele() {
    return "artikal";
}

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

@Override
public String vratiKoloneZaUbacivanje() {
    return "naziv, cena, tip";
}

@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + naziv + "', " + cena + ", '" + tip + "'";
}

@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idArtikal=" + idArtikal;
}


@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new Artikal(
        rs.getInt("idArtikal"),
        rs.getString("naziv"),
        rs.getDouble("cena"),
        rs.getString("tip")
    );
}

@Override
public String vratiVrednostiZaIzmenu() {
    return "naziv='" + naziv + "', cena=" + cena + ", tip='" + tip + "'";
}

}
