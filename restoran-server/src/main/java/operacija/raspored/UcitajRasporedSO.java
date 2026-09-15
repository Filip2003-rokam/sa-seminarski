package operacija.raspored;

import domen.KonobarSmena;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje rasporeda konobara po smenama iz baze.
 * Radi nad domenskom klasom {@link KonobarSmena}.
 * Preduslovi su prazni - nema posebnih provera nad parametrom
 * (parametar se ne validira, za razliku od UcitajKonobareSO gde mora biti null).
 * Rezultat se vraca preko gettera {@link #getLista()}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajRasporedSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih rasporeda (KonobarSmena) iz baze podataka.
     */
    private List<KonobarSmena> lista;

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public UcitajRasporedSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public UcitajRasporedSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Vraca listu ucitanih rasporeda.
     *
     * @return lista KonobarSmena objekata, ili null ako operacija jos nije izvrsena
     */
    public List<KonobarSmena> getLista() {
        return lista;
    }

    /**
     * Proverava preduslove za ucitavanje rasporeda.
     * Nema posebnih preduslova - metoda je prazna.
     *
     * @param param parametar operacije, ne proverava se
     * @throws Exception ne baca se iz ove metode
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema posebnih preduslova
    }

    /**
     * Ucitava sve rasporede iz baze podataka preko brokera (getAll),
     * uz JOIN na tabele konobar i smena.
     *
     * @param param ne koristi se
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        lista = broker.getAll(
            new KonobarSmena(),
            " JOIN konobar ON konobarsmena.idKonobar = konobar.idKonobar " +
            " JOIN smena ON konobarsmena.idSmena = smena.idSmena"
        );
    }
}
