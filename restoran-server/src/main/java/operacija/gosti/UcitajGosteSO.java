package operacija.gosti;

import java.util.List;
import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje svih gostiju iz sistema.
 * Radi nad domen klasom {@link Gost}. Nema posebnih preduslova;
 * rezultat ukljucuje i podatke o kategoriji gosta (JOIN).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajGosteSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih gostiju iz baze podataka.
     */
    private List<Gost> gosti;

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public UcitajGosteSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public UcitajGosteSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Vraca listu ucitanih gostiju nakon izvrsenja operacije.
     *
     * @return lista objekata tipa {@link Gost}
     */
    public List<Gost> getGosti() {
        return gosti;
    }

    /**
     * Proverava preduslove za ucitavanje gostiju.
     * Nema posebnih preduslova.
     *
     * @param param objekat parametra, nije koriscen
     * @throws Exception ne baca se izuzetak zbog preduslova
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema posebnih preduslova
    }

    /**
     * Ucitava sve goste iz baze podataka, ukljucujuci JOIN sa tabelom
     * kategorijagosta radi popunjavanja podataka o kategoriji.
     *
     * @param param objekat parametra, nije koriscen
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        gosti = broker.getAll(new Gost(),
                " JOIN kategorijagosta ON gost.idKategorijaGosta = kategorijagosta.idKategorijaGosta");
    }
}
