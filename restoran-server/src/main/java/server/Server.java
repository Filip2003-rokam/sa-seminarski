package server;

import domen.Konobar;
import forme.ServerskaForma;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.ObradaKlijentskiihZahteva;

/**
 * Serverska nit koja na {@link ServerSocket}-u prihvata klijentske konekcije
 * i za svaku pokrece posebnu nit {@link ObradaKlijentskiihZahteva}.
 * Port se cita iz {@link konfiguracija.Konfiguracija}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Server extends Thread{

    /**
     * Flag za prekid accept petlje.
     */
    boolean kraj = false;

    /**
     * Serverski soket na kojem se cekaju klijenti.
     */
    ServerSocket serverSoket;

    /**
     * Lista aktivnih niti za obradu klijentskih zahteva.
     */
    List<ObradaKlijentskiihZahteva> klijenti;

    /**
     * Lista ulogovanih konobara (rezervisano za pracenje sesija).
     */
    private final List<Konobar> ulogovaniKonobari = new ArrayList<>();

    /**
     * Referenca na serversku formu radi osvezavanja UI-ja.
     */
    private final ServerskaForma sf;

    /**
     * Kreira serversku nit vezanu za datu formu.
     *
     * @param sf serverska forma za prikaz statusa i ulogovanih konobara
     */
    public Server(ServerskaForma sf) {
        klijenti = new ArrayList<ObradaKlijentskiihZahteva>();
        this.sf = sf;
    }

    /**
     * Otvara {@link ServerSocket} na konfigurisanom portu i u petlji
     * prihvata klijente; za svakog pokrece {@link ObradaKlijentskiihZahteva}.
     */
    @Override
    public void run() {
        try {
            int port = Integer.parseInt(konfiguracija.Konfiguracija.getInstanca().getProperty("port"));
            serverSoket = new ServerSocket(port);

            while(!kraj){
                Socket s = serverSoket.accept();
                System.out.println("Klijent je povezan");

                ObradaKlijentskiihZahteva okz = new ObradaKlijentskiihZahteva(s,this);

                klijenti.add(okz);

             okz.start();


            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    /**
     * Zaustavlja server: prekida sve klijentske niti i zatvara serverski soket.
     */
    public void zaustaviServer(){

        kraj = true;
        try {

            for(ObradaKlijentskiihZahteva k:klijenti){
                k.prekini();
            }

            serverSoket.close();
        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }

    /**
     * Osvezava labelu na serverskoj formi listom trenutno ulogovanih konobara.
     */
    public void osveziFormu() {
        StringBuilder sb = new StringBuilder();

        boolean imaUlogovanih = false;
            for (ObradaKlijentskiihZahteva okz : klijenti) {
                Konobar k = okz.getK();
                if (k != null) {
                    imaUlogovanih = true;
                    sb.append("Konobar: ")
                      .append(k.getIme())
                      .append(" ")
                      .append(k.getPrezime())
                      .append(" (")
                      .append(k.getKorisnickoIme())
                      .append(")")
                      .append("<br>");
                }
            }

            if (!imaUlogovanih) {
                sb.append("Nema trenutno ulogovanih konobara.");
            }

            if (sf != null && sf.getjLabelKonobari() != null) {
                sf.getjLabelKonobari().setText("<html>" + sb.toString() + "</html>");
            }
    }



}
