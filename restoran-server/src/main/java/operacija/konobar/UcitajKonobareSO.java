package operacija.konobar;

import domen.Konobar;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje svih konobara iz baze podataka.
 * Radi nad domenskom klasom {@link Konobar}.
 * Preduslov zahteva da parametar bude null - za ucitavanje konobara
 * ne treba parametar.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajKonobareSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih konobara iz baze podataka.
     */
    private List<Konobar> konobari;

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public UcitajKonobareSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public UcitajKonobareSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za ucitavanje konobara.
     * Za razliku od praznih preduslova kod drugih Ucitaj SO, ovde parametar
     * mora biti null.
     *
     * @param param mora biti null
     * @throws Exception ako parametar nije null
     *         ("Za ucitavanje konobara ne treba parametar!")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param != null) {
            throw new Exception("Za učitavanje konobara ne treba parametar!");
        }
    }

    /**
     * Ucitava sve konobare iz baze podataka preko brokera (getAll sa praznim uslovom).
     *
     * @param param ne koristi se (mora biti null zbog preduslova)
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        konobari = broker.getAll(new Konobar(), "");
    }

    /**
     * Vraca listu ucitanih konobara.
     *
     * @return lista konobara, ili null ako operacija jos nije izvrsena
     */
    public List<Konobar> getKonobari() {
        return konobari;
    }
}
