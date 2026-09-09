package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KategorijaGosta implements ApstraktniDomenskiObjekat{
    private int idKategorijaGosta;
    private String opis;
    private double popust;
    private boolean imaPopust;

    public KategorijaGosta() {}

    public KategorijaGosta(int idKategorijaGosta, String opis, double popust, boolean imaPopust) {
        this.idKategorijaGosta = idKategorijaGosta;
        this.opis = opis;
        this.popust = popust;
        this.imaPopust = imaPopust;
    }

    public int getIdKategorijaGosta() { return idKategorijaGosta; }
    public void setIdKategorijaGosta(int idKategorijaGosta) { this.idKategorijaGosta = idKategorijaGosta; }

    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }

    public double getPopust() { return popust; }
    public void setPopust(double popust) { this.popust = popust; }

    public boolean isImaPopust() { return imaPopust; }
    public void setImaPopust(boolean imaPopust) { this.imaPopust = imaPopust; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KategorijaGosta)) return false;
        KategorijaGosta that = (KategorijaGosta) o;
        return idKategorijaGosta == that.idKategorijaGosta;
    }

    @Override
    public int hashCode() { return Objects.hash(idKategorijaGosta); }

    @Override
    public String toString() {
        return opis; // + ", popust=" + popust + "%";
    }

    @Override
public String vratiNazivTabele() {
    return "kategorijagosta";
}

@Override
public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
    List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
    while (rs.next()) {
        KategorijaGosta kg = new KategorijaGosta(
            rs.getInt("idKategorijaGosta"),
            rs.getString("opis"),
            rs.getDouble("popust"),
            rs.getBoolean("imaPopust")
        );
        lista.add(kg);
    }
    return lista;
}

@Override
public String vratiKoloneZaUbacivanje() {
    return "opis, popust, imaPopust";
}

@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + opis + "', " + popust + ", " + imaPopust;
}

@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idKategorijaGosta=" + idKategorijaGosta;
}


@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new KategorijaGosta(
        rs.getInt("idKategorijaGosta"),
        rs.getString("opis"),
        rs.getDouble("popust"),
        rs.getBoolean("imaPopust")
    );
}

@Override
public String vratiVrednostiZaIzmenu() {
    return "opis='" + opis + "', popust=" + popust + ", imaPopust=" + imaPopust;
}

}
