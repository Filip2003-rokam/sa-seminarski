package operacija.racuni;

import domen.ApstraktniDomenskiObjekat;
import domen.Racun;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje svih racuna iz baze podataka.
 * Radi nad domenom {@link Racun}. Preduslov je da parametar bude null
 * (za ucitavanje racuna ne treba parametar); u suprotnom se baca izuzetak.
 * Ucitani racuni ukljucuju povezane podatke o gostu i konobaru preko JOIN-a.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajRacuneSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih racuna nakon uspesnog izvrsavanja operacije.
     */
    private List<Racun> racuni;



    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public UcitajRacuneSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Omogucava testiranje bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public UcitajRacuneSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za ucitavanje racuna.
     * Za ovu operaciju parametar ne sme biti prosledjen.
     *
     * @param param mora biti null
     * @throws Exception ako param nije null
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // Za učitavanje računa ne treba parametar
        if (param != null) {
            throw new Exception("Za učitavanje računa ne treba parametar!");
        }
    }

    /**
     * Ucitava sve racune iz baze zajedno sa gostom i konobarom
     * (JOIN na tabele gost i konobar) i smesta ih u polje {@code racuni}.
     *
     * @param param nije koriscen; ocekivano null
     * @param kljuc dodatni uslov; nije koriscen u ovoj operaciji
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        String upit =
    " JOIN gost g ON racun.idGost = g.idGost " +
    "JOIN konobar k ON racun.idKonobar = k.idKonobar";

                    racuni = broker.getAll(new Racun(), upit);
    }

    /**
     * Vraca listu racuna ucitanih poslednjim izvrsavanjem operacije.
     *
     * @return lista objekata tipa {@link Racun}, ili null ako operacija
     *         jos nije izvrsena
     */
    public List<Racun> getRacuni() {
        return racuni;
    }
}
