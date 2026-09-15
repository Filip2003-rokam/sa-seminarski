package komunikacija;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Odgovoran je za slanje serijalizovanih objekata preko mrezne uticnice.
 * Koristi {@link ObjectOutputStream} za prenos {@link Zahtev} ili {@link Odgovor}
 * objekata izmedju klijenta i servera. Greske pri pisanju se hvataju lokalno
 * i ne propagiraju se pozivaocu.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Posiljalac {

    /** Uticnica preko koje se salju objekti. */
    private Socket socket;

    /**
     * Kreira posiljaoca vezanog za zadatu uticnicu.
     *
     * @param socket mrezna uticnica za slanje podataka
     */
    public Posiljalac(Socket socket) {
        this.socket = socket;
    }

    /**
     * Salje serijalizovani objekat preko uticnice.
     * U slucaju {@link IOException} greska se ispisuje na konzolu
     * ({@code printStackTrace}) i ne propagira se dalje.
     *
     * @param ob objekat koji se salje (obicno {@link Zahtev} ili {@link Odgovor})
     */
    public void posalji(Object ob) {

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(ob);
            out.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
