
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gost implements ApstraktniDomenskiObjekat{
    private int idGost;
    private String ime;
    private String prezime;
    private KategorijaGosta kategorijaGosta;

    public Gost() {}

    public Gost(int idGost, String ime, String prezime, KategorijaGosta kategorijaGosta) {
        this.idGost = idGost;
        this.ime = ime;
        this.prezime = prezime;
        this.kategorijaGosta = kategorijaGosta;
    }

    public int getIdGost() { return idGost; }
    public void setIdGost(int idGost) { this.idGost = idGost; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public KategorijaGosta getKategorijaGosta() { return kategorijaGosta; }
    public void setKategorijaGosta(KategorijaGosta kategorijaGosta) { 
        this.kategorijaGosta = kategorijaGosta; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gost)) return false;
        Gost gost = (Gost) o;
        return idGost == gost.idGost;
    }

    @Override
    public int hashCode() { return Objects.hash(idGost); }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
public String vratiNazivTabele() {
    return "gost";
}

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

@Override
public String vratiKoloneZaUbacivanje() {
    return "ime, prezime, idKategorijaGosta";
}

@Override
public String vratiVrednostiZaUbacivanje() {
    return "'" + ime + "', '" + prezime + "', " + kategorijaGosta.getIdKategorijaGosta();
}

@Override
public String vratiPrimarniKljuc() {
    return vratiNazivTabele() + ".idGost=" + idGost;
}


@Override
public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
    return new Gost(rs.getInt("idGost"), rs.getString("ime"), rs.getString("prezime"),null);
}

@Override
public String vratiVrednostiZaIzmenu() {
    return "ime='" + ime + "', prezime='" + prezime + "', idKategorijaGosta=" + 
            kategorijaGosta.getIdKategorijaGosta();
}

}

