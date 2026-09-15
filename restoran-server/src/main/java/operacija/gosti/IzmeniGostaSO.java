package operacija.gosti;

import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojeceg gosta u sistemu.
 * Radi nad domen klasom {@link Gost}. Preduslovi zahtevaju da objekat bude
 * tipa Gost, da ime ima najmanje 3 karaktera i da prezime ima najmanje 3 karaktera.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class IzmeniGostaSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public IzmeniGostaSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public IzmeniGostaSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za izmenu gosta.
     *
     * @param param objekat koji mora biti tipa {@link Gost}
     * @throws Exception ako objekat nije Gost, ako ime ima manje od 3 karaktera,
     *         ako prezime ima manje od 3 karaktera
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Gost)){
            throw new Exception("Sistem nije mogao da izmeni gosta");
        }
        
        Gost g = (Gost) param;
        
        if (g.getIme() == null || g.getIme().isEmpty() || g.getIme().length() < 3) {
            throw new Exception("GRESKA IME");
        }
        
        if (g.getPrezime() == null || g.getPrezime().isEmpty() || g.getPrezime().length() < 3) {
            throw new Exception("GRESKA PREZIME");
        }
    }

    /**
     * Azurira postojeceg gosta u bazi podataka.
     *
     * @param param objekat tipa {@link Gost} sa izmenjenim podacima
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako izmena u bazi ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        broker.edit((Gost)param);
        
    }

    
}
