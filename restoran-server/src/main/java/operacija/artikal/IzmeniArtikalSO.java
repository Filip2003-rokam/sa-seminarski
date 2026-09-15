package operacija.artikal;

import domen.Artikal;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojeceg artikla u sistemu.
 * Radi nad domen klasom {@link Artikal}. Preduslovi zahtevaju da objekat bude
 * tipa Artikal. Validacija atributa (naziv, tip, cena) je u setterima domen klase.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class IzmeniArtikalSO extends ApstraktnaGenerickaOperacija {

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public IzmeniArtikalSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public IzmeniArtikalSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za izmenu artikla.
     *
     * @param param objekat koji mora biti tipa {@link Artikal}
     * @throws Exception ako je param null ili nije tipa {@link Artikal}
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa Artikal
        if (param == null || !(param instanceof Artikal)) {
            throw new Exception("Sistem nije mogao da izmeni artikal");
        }
    }

    /**
     * Azurira postojeci artikal u bazi podataka.
     *
     * @param param objekat tipa {@link Artikal} sa izmenjenim podacima
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako izmena u bazi ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.edit((Artikal) param);
    }
}
