package repository.db;

import java.sql.Connection;
import java.sql.DriverManager;
import konfiguracija.Konfiguracija;

/**
 * Singleton fabrika JDBC konekcija ka bazi podataka.
 * Parametre konekcije (url, username, password) cita iz {@link Konfiguracija}.
 * Konekcija se kreira sa {@code autoCommit = false} radi rukovanja transakcijama.
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
     */
    private DbConnectionFactory() {

            try {
                if(connection == null || connection.isClosed()) {

                    String url = Konfiguracija.getInstanca().getProperty("url");
                    String username = Konfiguracija.getInstanca().getProperty("username");
                    String password = Konfiguracija.getInstanca().getProperty("password");
                    connection = DriverManager.getConnection(url, username, password);
                    connection.setAutoCommit(false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


    }

    /**
     * Vraca jedinu instancu fabrike konekcija.
     *
     * @return instanca klase DbConnectionFactory
     */
    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    /**
     * Vraca aktivnu JDBC konekciju.
     *
     * @return JDBC {@link Connection} objekat
     */
    public Connection getConnection() {
        return connection;
    }




}
