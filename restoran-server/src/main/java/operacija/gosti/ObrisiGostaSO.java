package operacija.gosti;

import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje gosta iz sistema.
 * Radi nad domen klasom {@link Gost}. Preduslov je da prosledjeni objekat
 * nije null i da je tipa Gost.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiGostaSO extends ApstraktnaGenerickaOperacija {

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public ObrisiGostaSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public ObrisiGostaSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje gosta.
     *
     * @param param objekat koji mora biti tipa {@link Gost}
     * @throws Exception ako je param null ili nije tipa Gost
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        
        if(param == null || !(param instanceof Gost)){
            throw new Exception("Sistem nije mogao da obriše gosta");
        }
        
    }

    /**
     * Brise gosta iz baze podataka.
     *
     * @param param objekat tipa {@link Gost} koji se brise
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako brisanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        broker.delete((Gost)param);
        
    }
    
    
    
}
