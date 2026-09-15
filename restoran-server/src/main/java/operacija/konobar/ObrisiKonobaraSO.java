package operacija.konobar;

import domen.Konobar;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje konobara iz baze podataka.
 * Radi nad domenskom klasom {@link Konobar}.
 * Preduslov zahteva da parametar bude instanca Konobar (ne sme biti null).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiKonobaraSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public ObrisiKonobaraSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public ObrisiKonobaraSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje konobara.
     *
     * @param param objekat koji mora biti tipa {@link Konobar}
     * @throws Exception ako je param null ili nije Konobar
     *         ("Sistem nije mogao da obrise konobara!")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Konobar)) {
            throw new Exception("Sistem nije mogao da obriše konobara!");
        }
    }

    /**
     * Brise konobara iz baze podataka preko brokera.
     *
     * @param param konobar koji se brise
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako brisanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.delete((Konobar) param);
    }
}
