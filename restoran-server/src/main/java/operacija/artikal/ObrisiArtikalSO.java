package operacija.artikal;

import domen.Artikal;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje artikla iz sistema.
 * Radi nad domen klasom {@link Artikal}. Preduslov je da prosledjeni objekat
 * nije null i da je tipa Artikal.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiArtikalSO extends ApstraktnaGenerickaOperacija {

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public ObrisiArtikalSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public ObrisiArtikalSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje artikla.
     *
     * @param param objekat koji mora biti tipa {@link Artikal}
     * @throws Exception ako je param null ili nije tipa Artikal
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa Artikal
        if (param == null || !(param instanceof Artikal)) {
            throw new Exception("Sistem nije mogao da obriše artikal");
        }
    }

    /**
     * Brise artikal iz baze podataka.
     *
     * @param param objekat tipa {@link Artikal} koji se brise
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako brisanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.delete((Artikal) param);
    }
}
