package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Pomoćna klasa koja predstavlja vezu između konobara i smene (M:N),
 * sa dodatnim atributom datumSmene.
 */
public class KonobarSmena implements ApstraktniDomenskiObjekat {

    private Konobar konobar;
    private Smena smena;
    private LocalDate datumSmene;

    public KonobarSmena() {}

    public KonobarSmena(Konobar konobar, Smena smena, LocalDate datumSmene) {
        this.konobar = konobar;
        this.smena = smena;
        this.datumSmene = datumSmene;
    }

    public Konobar getKonobar() {
        return konobar;
    }

    public void setKonobar(Konobar konobar) {
        this.konobar = konobar;
    }

    public Smena getSmena() {
        return smena;
    }

    public void setSmena(Smena smena) {
        this.smena = smena;
    }

    public LocalDate getDatumSmene() {
        return datumSmene;
    }

    public void setDatumSmene(LocalDate datumSmene) {
        this.datumSmene = datumSmene;
    }

    // ---------------------- ADO implementacija ----------------------

    @Override
    public String vratiNazivTabele() {
        return "konobarsmena";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "idKonobar, idSmena, datumSmene";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return konobar.getIdKonobar() + ", " +
               smena.getIdSmena() + ", '" +
               datumSmene + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "idKonobar=" + konobar.getIdKonobar() +
               " AND idSmena=" + smena.getIdSmena() +
               " AND datumSmene='" + datumSmene + "'";
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        // obično se datum ne menja, ali primer:
        return "datumSmene='" + datumSmene + "'";
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        Konobar k = new Konobar();
        k.setIdKonobar(rs.getInt("idKonobar"));
        // ako želiš možeš da učitaš i ime/prezime ako je JOIN u upitu
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


    // ---------------------- equals / hashCode / toString ----------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KonobarSmena)) return false;
        KonobarSmena that = (KonobarSmena) o;
        return Objects.equals(konobar, that.konobar)
                && Objects.equals(smena, that.smena)
                && Objects.equals(datumSmene, that.datumSmene);
    }

    @Override
    public int hashCode() {
        return Objects.hash(konobar, smena, datumSmene);
    }

    @Override
    public String toString() {
        return "KonobarSmena{" +
                "konobar=" + konobar +
                ", smena=" + smena +
                ", datumSmene=" + datumSmene +
                '}';
    }
}
