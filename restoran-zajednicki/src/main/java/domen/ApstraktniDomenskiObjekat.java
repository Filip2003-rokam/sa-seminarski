package domen;

import java.io.Serializable;
import java.util.List;
import java.sql.ResultSet;

/**
 * Opsti ugovor (interfejs) za sve domenske objekte u sistemu restorana.
 * Definise metode koje genericki sloj za rad sa bazom koristi za mapiranje
 * objekata na SQL tabele, ubacivanje, izmenu, citanje i identifikaciju zapisa.
 * Svaka konkretna domenska klasa implementira ovaj interfejs i vraca SQL
 * fragmente i mape ResultSet-a specificne za svoju tabelu.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public interface ApstraktniDomenskiObjekat extends Serializable {
    
    /**
     * Vraca naziv tabele u bazi podataka koja odgovara ovom domenskom objektu.
     * Koristi se kao deo SQL upita (FROM / UPDATE / DELETE) u generickom
     * Brokeru baze.
     *
     * @return naziv tabele kao String (npr. "artikal", "gost", "racun")
     */
    public String vratiNazivTabele();

    /**
     * Mapira kompletan {@link ResultSet} u listu domenskih objekata.
     * Metoda prolazi kroz sve redove ResultSet-a i za svaki red kreira
     * konkretan objekat tipa implementacije. Ocekivane kolone zavise od
     * konkretne klase (cesto ukljucuju JOIN kolone).
     *
     * @param rs ResultSet dobijen izvrsavanjem SELECT upita
     * @return lista {@link ApstraktniDomenskiObjekat} mapiranih iz ResultSet-a;
     *         prazna lista ako nema redova
     * @throws Exception ako dodje do greske pri citanju kolona iz ResultSet-a
     *         (npr. nepostojeca kolona, SQLException)
     */
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception;

    /**
     * Vraca listu kolona (bez primarnog kljuca ako je auto-increment) koje se
     * koriste u INSERT upitu, u formatu pogodnom za SQL fragment
     * {@code INSERT INTO tabela (kolone) VALUES (...)}.
     *
     * @return String sa imenima kolona razdvojenim zarezima
     *         (npr. "naziv, cena, tip")
     */
    public String vratiKoloneZaUbacivanje();

    /**
     * Vraca vrednosti atributa objekta formatirane za VALUES deo INSERT upita.
     * String vrednosti se obicno navode u jednostrukim navodnicima, a brojevi
     * bez navodnika. Tacan format zavisi od konkretne implementacije.
     *
     * @return String sa SQL vrednostima razdvojenim zarezima
     *         (npr. "'Pizza', 500.0, 'Jelo'")
     */
    public String vratiVrednostiZaUbacivanje();

    /**
     * Vraca SQL uslov primarnog kljuca za WHERE klauzulu (UPDATE / DELETE / SELECT
     * po kljucu). Format je tipicno {@code tabela.kolona=vrednost} ili
     * kompozitni uslov sa AND za visekljucne tabele.
     *
     * @return SQL fragment uslova primarnog kljuca
     */
    public String vratiPrimarniKljuc();

    /**
     * Mapira trenutni red {@link ResultSet}-a u jedan domenski objekat.
     * Za razliku od {@link #vratiListu(ResultSet)}, ova metoda ne prolazi petljom
     * kroz sve redove vec cita samo trenutni red (korisno kada ResultSet ima
     * tacno jedan red ili kada se kursor vec pozicionirao).
     *
     * @param rs ResultSet pozicioniran na zeljeni red
     * @return novi {@link ApstraktniDomenskiObjekat} popunjen podacima iz reda
     * @throws Exception ako dodje do greske pri citanju kolona iz ResultSet-a
     */
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception;

    /**
     * Vraca SET deo SQL UPDATE upita sa novim vrednostima atributa objekta.
     * Format je tipicno {@code kolona1=vrednost1, kolona2=vrednost2, ...}
     * bez WHERE klauzule (WHERE se gradi preko {@link #vratiPrimarniKljuc()}).
     *
     * @return String sa SQL dodelama vrednosti razdvojenim zarezima
     */
    public String vratiVrednostiZaIzmenu();

    
}
