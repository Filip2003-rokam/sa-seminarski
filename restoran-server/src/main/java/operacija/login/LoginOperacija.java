package operacija.login;

import domen.Gost;
import domen.Konobar;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za prijavu (login) konobara u sistem.
 * Radi nad domen klasom {@link Konobar}. Preduslov je da prosledjeni
 * objekat nije null i da je tipa Konobar; zatim se u bazi trazi konobar
 * ciji kredencijali odgovaraju unetim podacima.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class LoginOperacija extends ApstraktnaGenerickaOperacija {

    /**
     * Konobar koji je uspesno ulogovan, ili null ako kredencijali nisu ispravni.
     */
    Konobar konobar;

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public LoginOperacija() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public LoginOperacija(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za login.
     *
     * @param param objekat koji mora biti tipa {@link Konobar}
     * @throws Exception ako je param null ili nije tipa Konobar
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Konobar)){
            throw new Exception("Ne moze da se uloguje");
        }
    }

    /**
     * Vraca ulogovanog konobara nakon izvrsenja operacije.
     *
     * @return ulogovani {@link Konobar}, ili null ako prijava nije uspela
     */
    public Konobar getKonobar() {
        return konobar;
    }

    /**
     * Ucitava sve konobare iz baze i trazi onog ciji kredencijali
     * (korisnicko ime i sifra) odgovaraju prosledjenom objektu.
     * Ako je nadjen, postavlja polje {@code konobar}; inace ga postavlja na null.
     *
     * @param param objekat tipa {@link Konobar} sa kredencijalima za proveru
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako pristup bazi ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        Konobar trazeni = (Konobar) param;
        List<Konobar> sviKonobari = broker.getAll(trazeni, null);
        System.out.println("KLASA LoginOperacija SO " + sviKonobari);

        for (Konobar k : sviKonobari) {
            if (k.proveriKredencijale(trazeni.getKorisnickoIme(), trazeni.getSifra())) {
                konobar = k;
                return;
            }
        }

        konobar = null;

        
    }
    
}
