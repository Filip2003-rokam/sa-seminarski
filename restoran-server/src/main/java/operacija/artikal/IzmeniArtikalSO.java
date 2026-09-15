package operacija.artikal;

import domen.Artikal;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojeceg artikla u sistemu.
 * Radi nad domen klasom {@link Artikal}. Preduslovi zahtevaju da objekat bude
 * tipa Artikal, da naziv ima najmanje 2 karaktera, da tip nije prazan
 * i da cena bude veca od nule.
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
     * @throws Exception ako objekat nije Artikal, ako naziv ima manje od 2 karaktera,
     *         ako tip nije unet, ako cena nije veca od nule
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa Artikal
        if (param == null || !(param instanceof Artikal)) {
            throw new Exception("Sistem nije mogao da izmeni artikal");
        }

        Artikal artikal = (Artikal) param;

        if (artikal.getNaziv() == null || artikal.getNaziv().isEmpty() || artikal.getNaziv().length() < 2) {
            throw new Exception("GRESKA NAZIV");
        }

        if (artikal.getTip() == null || artikal.getTip().isEmpty()) {
            throw new Exception("GRESKA TIP");
        }

        if (artikal.getCena() <= 0) {
            throw new Exception("GRESKA CENA");
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
