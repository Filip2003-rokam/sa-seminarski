package operacija;

import repository.db.DbRepository;
import repository.db.impl.DbRepositoryGeneric;

/**
 * Apstraktna klasa koja predstavlja sablon za izvrsavanje svih sistemskih
 * operacija u sistemu.
 *
 * Definise nepromenljiv redosled koraka: provera preduslova, zapocinjanje
 * transakcije, izvrsavanje same operacije i potvrda transakcije. Ukoliko
 * dodje do greske u bilo kom koraku, transakcija se ponistava.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public abstract class ApstraktnaGenerickaOperacija {

    /**
     * Repozitorijum preko kojeg operacija pristupa bazi podataka.
     */
    protected final DbRepository broker;

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public ApstraktnaGenerickaOperacija() {
        this(new DbRepositoryGeneric());
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Ovaj konstruktor omogucava testiranje operacije bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public ApstraktnaGenerickaOperacija(DbRepository broker) {
        this.broker = broker;
    }

    /**
     * Izvrsava sistemsku operaciju po utvrdjenom sablonu.
     *
     * @param objekat objekat nad kojim se operacija izvrsava
     * @param kljuc dodatni uslov ili parametar operacije, moze biti null
     * @throws Exception ako preduslovi nisu ispunjeni ili ako operacija ne uspe
     */
    public final void izvrsi(Object objekat, String kljuc) throws Exception {
        try {
            preduslovi(objekat);
            zapocniTransakciju();
            izvrsiOperaciju(objekat, kljuc);
            potvrdiTransakciju();
        } catch (Exception e) {
            ponistiTransakciju();
            throw e;
        }
    }

    /**
     * Proverava da li su ispunjeni preduslovi za izvrsavanje operacije.
     *
     * @param param objekat nad kojim se operacija izvrsava
     * @throws Exception ako preduslovi nisu ispunjeni
     */
    protected abstract void preduslovi(Object param) throws Exception;

    /**
     * Izvrsava konkretnu sistemsku operaciju.
     *
     * @param param objekat nad kojim se operacija izvrsava
     * @param kljuc dodatni uslov ili parametar operacije, moze biti null
     * @throws Exception ako operacija ne uspe
     */
    protected abstract void izvrsiOperaciju(Object param, String kljuc) throws Exception;

    private void zapocniTransakciju() throws Exception {
        broker.connect();
    }

    private void potvrdiTransakciju() throws Exception {
        broker.commit();
    }

    private void ponistiTransakciju() throws Exception {
        broker.rollback();
    }
}