package operacija.smena;

import domen.Smena;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje svih smena iz baze podataka.
 * Radi nad domenskom klasom {@link Smena}.
 * Preduslovi su prazni - nema posebnih provera nad parametrom
 * (parametar se ne validira, za razliku od UcitajKonobareSO gde mora biti null).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajSmeneSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih smena iz baze podataka.
     */
    private List<Smena> smene;

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public UcitajSmeneSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public UcitajSmeneSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Vraca listu ucitanih smena.
     *
     * @return lista smena, ili null ako operacija jos nije izvrsena
     */
    public List<Smena> getSmene() {
        return smene;
    }

    /**
     * Proverava preduslove za ucitavanje smena.
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
     * Ucitava sve smene iz baze podataka preko brokera (getAll sa null uslovom).
     *
     * @param param ne koristi se
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        // Ucitavanje svih smena iz baze
        smene = broker.getAll(new Smena(), null);
    }
}
