package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Odgovoran je za prijem serijalizovanih objekata preko mrezne uticnice.
 * Koristi {@link ObjectInputStream} za citanje {@link Zahtev} ili {@link Odgovor}
 * objekata izmedju klijenta i servera. U slucaju greske ili prekida veze
 * metoda {@link #primi()} vraca {@code null}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Primalac {

    /** Uticnica preko koje se primaju objekti. */
    private Socket socket;

    /**
     * Kreira primaoca vezanog za zadatu uticnicu.
     *
     * @param socket mrezna uticnica za prijem podataka
     */
    public Primalac(Socket socket) {
        this.socket = socket;
    }

    /**
     * Prima serijalizovani objekat sa uticnice.
     * U slucaju greske pri citanju ili prekida veze ispisuje stek trag
     * i vraca {@code null}.
     *
     * @return primljeni objekat, ili {@code null} pri gresci / prekidu veze
     */
    public Object primi() {

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

    }

}
