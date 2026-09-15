package repository.db;

import java.sql.Connection;
import java.sql.DriverManager;
import konfiguracija.Konfiguracija;

/**
 * Singleton fabrika JDBC konekcija ka bazi podataka.
 * Parametre konekcije (url, username, password) cita iz {@link Konfiguracija}.
 * Konekcija se kreira sa {@code autoCommit = false} radi rukovanja transakcijama.
 * Ako uspostavljanje veze ne uspe, izuzetak se propagira dalje umesto da ostane
 * sakriven sa {@code null} konekcijom.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DbConnectionFactory {

    /**
     * Jedina instanca fabrike (singleton).
     */
    private static DbConnectionFactory instance;

    /**
     * Aktivna JDBC konekcija ka bazi.
     */
    private Connection connection;

    /**
     * Privatni konstruktor koji uspostavlja konekciju na osnovu
     * konfiguracionih parametara iz {@link Konfiguracija}.
     *
     * @throws Exception ako konekcija ne moze da se uspostavi
     */
    private DbConnectionFactory() throws Exception {
        if (connection == null || connection.isClosed()) {
            String url = Konfiguracija.getInstanca().getProperty("url");
            String username = Konfiguracija.getInstanca().getProperty("username");
            String password = Konfiguracija.getInstanca().getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
    }

    /**
     * Vraca jedinu instancu fabrike konekcija.
     * Pri prvom pozivu uspostavlja JDBC vezu; ako veza ne uspe, izuzetak se
     * propagira i instanca se ne kesira.
     *
     * @return instanca klase DbConnectionFactory
     * @throws Exception ako konekcija ne moze da se uspostavi
     */
    public static DbConnectionFactory getInstance() throws Exception {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    /**
     * Vraca aktivnu JDBC konekciju.
     *
     * @return JDBC {@link Connection} objekat, ili {@code null} ako veza nije uspostavljena
     */
    public Connection getConnection() {
        return connection;
    }

}
