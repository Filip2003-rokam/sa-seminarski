/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Konobar implements ApstraktniDomenskiObjekat{
    private int idKonobar;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String sifra;

    public Konobar() {}

    public Konobar(int idKonobar, String ime, String prezime, String korisnickoIme, String sifra) {
        this.idKonobar = idKonobar;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public int getIdKonobar() { return idKonobar; }
    public void setIdKonobar(int idKonobar) { this.idKonobar = idKonobar; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getKorisnickoIme() { return korisnickoIme; }
    public void setKorisnickoIme(String korisnickoIme) { this.korisnickoIme = korisnickoIme; }

    public String getSifra() { return sifra; }
    public void setSifra(String sifra) { this.sifra = sifra; }

    

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String vratiNazivTabele() {
        return "konobar";
    }

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

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime, prezime, korisnickoIme, sifra";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + ime + "', '" + prezime + "', '" + korisnickoIme + "', '" + sifra + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return vratiNazivTabele() + ".idKonobar=" + idKonobar;
    }


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

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "', prezime='" + prezime + "', korisnickoIme='" + korisnickoIme + "', sifra='" + sifra + "'";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

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
        if (!Objects.equals(this.korisnickoIme, other.korisnickoIme)) {
            return false;
        }
        return Objects.equals(this.sifra, other.sifra);
    }

    
    
    
}
