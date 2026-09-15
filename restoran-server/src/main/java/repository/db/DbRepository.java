package repository.db;

import java.sql.Connection;
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
     * Vraca aktivnu JDBC konekciju ili baca izuzetak ako veza nije uspostavljena.
     *
     * @return aktivna JDBC konekcija
     * @throws Exception ako konekcija nije uspostavljena (null)
     */
    private Connection zahtevajKonekciju() throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        if (connection == null) {
            throw new Exception("Veza sa bazom podataka nije uspostavljena.");
        }
        return connection;
    }

    /**
     * Otvara (ili vraca) aktivnu JDBC konekciju preko fabrike konekcija.
     *
     * @throws Exception ako konekcija ne moze da se uspostavi
     */
    default public void connect() throws Exception {
        zahtevajKonekciju();
    }

    /**
     * Zatvara aktivnu JDBC konekciju.
     *
     * @throws Exception ako veza nije uspostavljena ili zatvaranje ne uspe
     */
    default public void disconnect() throws Exception {
        zahtevajKonekciju().close();
    }

    /**
     * Potvrdjuje (commit) trenutnu transakciju.
     *
     * @throws Exception ako veza nije uspostavljena ili commit ne uspe
     */
    default public void commit() throws Exception {
        zahtevajKonekciju().commit();
    }

    /**
     * Ponistava (rollback) trenutnu transakciju.
     *
     * @throws Exception ako veza nije uspostavljena ili rollback ne uspe
     */
    default public void rollback() throws Exception {
        zahtevajKonekciju().rollback();
    }

}
