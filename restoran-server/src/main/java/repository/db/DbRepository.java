package repository.db;

import repository.Repository;

/**
 * Interfejs spremista za rad sa bazom podataka.
 * Prosiruje genericki {@link Repository} default metodama za upravljanje
 * JDBC konekcijom (connect, disconnect, commit, rollback) preko
 * {@link DbConnectionFactory}.
 *
 * @param <T> tip entitetskog objekta nad kojim se vrse operacije
 * @author Filip Oketic
 * @version 1.0
 */
public interface DbRepository<T> extends Repository<T> {

    /**
     * Otvara (ili vraca) aktivnu JDBC konekciju preko fabrike konekcija.
     *
     * @throws Exception ako konekcija ne moze da se uspostavi
     */
    default public void connect() throws Exception {
        DbConnectionFactory.getInstance().getConnection();
    }

    /**
     * Zatvara aktivnu JDBC konekciju.
     *
     * @throws Exception ako zatvaranje konekcije ne uspe
     */
    default public void disconnect() throws Exception {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    /**
     * Potvrdjuje (commit) trenutnu transakciju.
     *
     * @throws Exception ako commit ne uspe
     */
    default public void commit() throws Exception {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    /**
     * Ponistava (rollback) trenutnu transakciju.
     *
     * @throws Exception ako rollback ne uspe
     */
    default public void rollback() throws Exception {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }

}
