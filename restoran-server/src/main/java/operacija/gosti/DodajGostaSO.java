package operacija.gosti;

import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;
import repository.Repository;
import repository.db.impl.DbRepositoryGeneric;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje novog gosta u sistem.
 * Radi nad domen klasom {@link Gost}. Preduslovi zahtevaju da objekat bude
 * tipa Gost. Validacija atributa (ime, prezime) je u setterima domen klase.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajGostaSO extends ApstraktnaGenerickaOperacija{

    
    //@Override
    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public DodajGostaSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public DodajGostaSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za dodavanje gosta.
     *
     * @param param objekat koji mora biti tipa {@link Gost}
     * @throws Exception ako je param null ili nije tipa {@link Gost}
     */
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Gost)){
            throw new Exception("Sistem nije mogao da doda gosta");
        }
    }

    /**
     * Dodaje novog gosta u bazu podataka.
     *
     * @param param objekat tipa {@link Gost} koji se dodaje
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako dodavanje u bazu ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        broker.add((Gost)param);
        
    }

    
}
