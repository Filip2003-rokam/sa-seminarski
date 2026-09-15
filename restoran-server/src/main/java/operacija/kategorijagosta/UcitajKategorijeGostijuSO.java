package operacija.kategorijagosta;


import domen.KategorijaGosta;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje svih kategorija gostiju iz sistema.
 * Radi nad domen klasom {@link KategorijaGosta}. Preduslov je da se ne
 * prosledjuje parametar (param mora biti null).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajKategorijeGostijuSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih kategorija gostiju iz baze podataka.
     */
    private List<KategorijaGosta> kategorije;

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public UcitajKategorijeGostijuSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public UcitajKategorijeGostijuSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za ucitavanje kategorija gostiju.
     *
     * @param param mora biti null
     * @throws Exception ako je param razlicit od null
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // ovde nema posebnih preduslova jer ne dobijaš parametar
        if (param != null) {
            throw new Exception("Za učitavanje kategorija gostiju ne treba parametar!");
        }
    }

    /**
     * Ucitava sve kategorije gostiju iz baze podataka.
     *
     * @param param objekat parametra, nije koriscen
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        kategorije = broker.getAll(new KategorijaGosta(), "");
    }

    /**
     * Vraca listu ucitanih kategorija gostiju nakon izvrsenja operacije.
     *
     * @return lista objekata tipa {@link KategorijaGosta}
     */
    public List<KategorijaGosta> getKategorije() {
        return kategorije;
    }
}
