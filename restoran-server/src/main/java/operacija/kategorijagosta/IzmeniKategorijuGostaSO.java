package operacija.kategorijagosta;


import domen.KategorijaGosta;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojece kategorije gosta u sistemu.
 * Radi nad domen klasom {@link KategorijaGosta}. Preduslovi zahtevaju da objekat
 * bude tipa KategorijaGosta, da opis ima najmanje 2 karaktera
 * i da popust bude veci ili jednak nuli.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class IzmeniKategorijuGostaSO extends ApstraktnaGenerickaOperacija {

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public IzmeniKategorijuGostaSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public IzmeniKategorijuGostaSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za izmenu kategorije gosta.
     *
     * @param param objekat koji mora biti tipa {@link KategorijaGosta}
     * @throws Exception ako objekat nije KategorijaGosta, ako opis ima manje od 2 karaktera,
     *         ako popust nije veci ili jednak nuli
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof KategorijaGosta)) {
            throw new Exception("Sistem nije mogao da izmeni kategoriju gosta!");
        }

        KategorijaGosta kg = (KategorijaGosta) param;

        if (kg.getOpis()== null || kg.getOpis().isEmpty() || kg.getOpis().length() < 2) {
            throw new Exception("GRESKA OPIS");
        }

        if (kg.getPopust() < 0) {
            throw new Exception("GRESKA POPUST - mora biti >= 0");
        }
    }

    /**
     * Azurira postojecu kategoriju gosta u bazi podataka.
     *
     * @param param objekat tipa {@link KategorijaGosta} sa izmenjenim podacima
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako izmena u bazi ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.edit((KategorijaGosta) param);
    }
}
