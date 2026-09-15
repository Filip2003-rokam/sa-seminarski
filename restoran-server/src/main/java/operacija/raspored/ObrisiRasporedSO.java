package operacija.raspored;

import domen.KonobarSmena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje rasporeda (veze konobar-smena) iz baze.
 * Radi nad domenskom klasom {@link KonobarSmena}.
 * Preduslov zahteva da parametar bude instanca KonobarSmena (ne sme biti null).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiRasporedSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public ObrisiRasporedSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public ObrisiRasporedSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje rasporeda.
     *
     * @param param objekat koji mora biti tipa {@link KonobarSmena}
     * @throws Exception ako je param null ili nije KonobarSmena
     *         ("Sistem nije mogao da obrise raspored (neispravan objekat)!")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof KonobarSmena)) {
            throw new Exception("Sistem nije mogao da obriše raspored (neispravan objekat)!");
        }
    }

    /**
     * Brise raspored (KonobarSmena) iz baze podataka preko brokera.
     *
     * @param param raspored koji se brise
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako brisanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        KonobarSmena ks = (KonobarSmena) param;
        broker.delete(ks);
    }
}
