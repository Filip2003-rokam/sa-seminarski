package operacija;

import repository.db.DbRepository;
import repository.db.impl.DbRepositoryGeneric;

/**
 * Apstraktna klasa koja predstavlja sablon (Template Method) za izvrsavanje
 * svih sistemskih operacija u sistemu.
 *
 * Definise nepromenljiv redosled koraka u metodi {@link #izvrsi(Object, String)}:
 * {@code preduslovi} → povezivanje na bazu ({@code connect}) →
 * {@code izvrsiOperaciju} → potvrda transakcije ({@code commit}).
 * Ukoliko dodje do greske u bilo kom koraku, transakcija se ponistava
 * ({@code rollback}) i izuzetak se prosledjuje dalje.
 *
 * Postoje dva konstruktora: podrazumevani koristi stvarni
 * {@link DbRepositoryGeneric}, dok konstruktor sa parametrom omogucava
 * ubacivanje mock ili drugog repozitorijuma radi testiranja.
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
     * Koristi se u produkcijskom radu aplikacije.
     */
    public ApstraktnaGenerickaOperacija() {
        this(new DbRepositoryGeneric());
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Ovaj konstruktor omogucava testiranje operacije bez stvarne baze podataka,
     * prosledjivanjem mock ili alternativne implementacije {@link DbRepository}.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public ApstraktnaGenerickaOperacija(DbRepository broker) {
        this.broker = broker;
    }

    /**
     * Izvrsava sistemsku operaciju po utvrdjenom sablonu.
     * Redosled: provera preduslova, zapocinjanje transakcije (connect),
     * izvrsavanje konkretne operacije, pa commit. Pri gresci se radi rollback.
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
     * Konkretne implementacije odredjuju poslovna pravila.
     *
     * @param param objekat nad kojim se operacija izvrsava
     * @throws Exception ako preduslovi nisu ispunjeni
     */
    protected abstract void preduslovi(Object param) throws Exception;

    /**
     * Izvrsava konkretnu sistemsku operaciju nad bazom podataka.
     *
     * @param param objekat nad kojim se operacija izvrsava
     * @param kljuc dodatni uslov ili parametar operacije, moze biti null
     * @throws Exception ako operacija ne uspe
     */
    protected abstract void izvrsiOperaciju(Object param, String kljuc) throws Exception;

    /**
     * Zapocinje transakciju povezivanjem na bazu podataka.
     *
     * @throws Exception ako povezivanje ne uspe
     */
    private void zapocniTransakciju() throws Exception {
        broker.connect();
    }

    /**
     * Potvrdjuje (commit) tekucu transakciju.
     *
     * @throws Exception ako potvrda transakcije ne uspe
     */
    private void potvrdiTransakciju() throws Exception {
        broker.commit();
    }

    /**
     * Ponistava (rollback) tekucu transakciju u slucaju greske.
     *
     * @throws Exception ako ponistavanje transakcije ne uspe
     */
    private void ponistiTransakciju() throws Exception {
        broker.rollback();
    }
}
