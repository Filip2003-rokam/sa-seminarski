package operacija.kategorijagosta;



import domen.KategorijaGosta;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje kategorije gosta iz sistema.
 * Radi nad domen klasom {@link KategorijaGosta}. Preduslov je da prosledjeni
 * objekat nije null i da je tipa KategorijaGosta.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiKategorijuGostaSO extends ApstraktnaGenerickaOperacija {

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public ObrisiKategorijuGostaSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public ObrisiKategorijuGostaSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje kategorije gosta.
     *
     * @param param objekat koji mora biti tipa {@link KategorijaGosta}
     * @throws Exception ako je param null ili nije tipa KategorijaGosta
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa KategorijaGosta
        if (param == null || !(param instanceof KategorijaGosta)) {
            throw new Exception("Sistem nije mogao da obriše kategoriju gosta!");
        }
    }

    /**
     * Brise kategoriju gosta iz baze podataka.
     *
     * @param param objekat tipa {@link KategorijaGosta} koji se brise
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako brisanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.delete((KategorijaGosta) param);
    }
}
