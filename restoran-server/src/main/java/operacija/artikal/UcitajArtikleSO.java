package operacija.artikal;

import domen.ApstraktniDomenskiObjekat;
import domen.Artikal;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje svih artikala iz sistema.
 * Radi nad domen klasom {@link Artikal}. Preduslov je da se ne prosledjuje
 * parametar (param mora biti null).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajArtikleSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih artikala iz baze podataka.
     */
    private List<Artikal> artikli;
    
    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public UcitajArtikleSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public UcitajArtikleSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za ucitavanje artikala.
     *
     * @param param mora biti null
     * @throws Exception ako je param razlicit od null
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // ovde nema posebnih preduslova jer ne dobijaš parametar
        // ali možeš staviti check da je param null
        if (param != null) {
            throw new Exception("Za učitavanje artikala ne treba parametar!");
        }
    }

    /**
     * Ucitava sve artikle iz baze podataka.
     *
     * @param param objekat parametra, nije koriscen
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        artikli = broker.getAll(new Artikal(), "");
    }

    /**
     * Vraca listu ucitanih artikala nakon izvrsenja operacije.
     *
     * @return lista objekata tipa {@link Artikal}
     */
    public List<Artikal> getArtikli() {
        return artikli;
    }
}
