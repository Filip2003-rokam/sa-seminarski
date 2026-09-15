package niti;

import controller.Controller;
import domen.Gost;
import domen.Artikal;
import domen.KategorijaGosta;
import domen.Konobar;
import domen.KonobarSmena;
import domen.Racun;
import domen.Smena;
import domen.StavkaRacuna;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.Odgovor;
import static komunikacija.Operacija.DODAJ_GOSTA;
import static komunikacija.Operacija.IZMENI_GOSTA;
import static komunikacija.Operacija.IZMENI_ARTIKAL;
import static komunikacija.Operacija.LOGIN;
import static komunikacija.Operacija.UCITAJ_GOSTE;
import komunikacija.Posiljalac;
import komunikacija.Primalac;
import komunikacija.Zahtev;
import server.Server;

/**
 * Nit koja obradjuje zahteve jednog povezanog klijenta.
 * U petlji prima {@link Zahtev}, poziva odgovarajucu metodu
 * {@link Controller}-a i salje {@link Odgovor}. Ako je primljeni
 * zahtev {@code null} (prekida se konekcija), petlja se zavrsava.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObradaKlijentskiihZahteva extends Thread {

    /**
     * Soket ka klijentu.
     */
    Socket socket;

    /**
     * Objekat za slanje odgovora klijentu.
     */
    Posiljalac posiljalac;

    /**
     * Objekat za prijem zahteva od klijenta.
     */
    Primalac primalac;

    /**
     * Referenca na server koji je pokrenuo ovu nit.
     */
    private Server server;

    /**
     * Trenutno ulogovani konobar na ovoj sesiji (postavlja se pri LOGIN).
     */
    Konobar k;

    /**
     * Flag za prekid petlje obrade.
     */
    boolean kraj = false;

    /**
     * Kreira nit za obradu zahteva datog klijentskog soketa.
     *
     * @param s soket ka klijentu
     * @param server server koji upravlja listom klijenata i UI-jem
     */
    public ObradaKlijentskiihZahteva(Socket s, Server server) {
        this.socket = s;
        this.server = server;
        posiljalac = new Posiljalac(socket);
        primalac = new Primalac(socket);
    }



    /**
     * Glavna petlja: prima zahteve dok {@code kraj} nije true.
     * Ako je zahtev null, postavlja {@code kraj} i izlazi iz petlje.
     */
    @Override
    public void run() {

        while(!kraj){

            try {
                Zahtev zahtev = (Zahtev) primalac.primi();
                if (zahtev == null) {
                    kraj = true;
                    break;
                }
                Odgovor odgovor = new Odgovor();
                switch (zahtev.getOperacija()) {
                    case LOGIN:
                        // Konobar k = (Konobar) zahtev.getParametar();
                        k = (Konobar) zahtev.getParametar();
                        k = Controller.getInstance().login(k);
                        server.osveziFormu();
                        odgovor.setOdgovor(k);
                        break;
                    case UCITAJ_GOSTE:
                        List<Gost> gosti = Controller.getInstance().ucitajGoste();
                        odgovor.setOdgovor(gosti);
                        break;
                    case OBRISI_GOSTA:

                        try{
                            Gost g = (Gost) zahtev.getParametar();
                            Controller.getInstance().obrisiGosta(g);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){
                            odgovor.setOdgovor(e);
                        }


                        break;

                    case DODAJ_GOSTA:
                        Gost g = (Gost) zahtev.getParametar();
                        Controller.getInstance().dodajGosta(g);
                        odgovor.setOdgovor(null);
                        break;

                    case IZMENI_GOSTA:
                        Gost g1 = (Gost) zahtev.getParametar();
                        Controller.getInstance().izmeniGosta(g1);
                        odgovor.setOdgovor(null);
                        odgovor.setUspeh(true);
                        break;

                    case UCITAJ_RACUNE:
                        List<Racun> racuni = Controller.getInstance().ucitajRacune();

                        System.out.println("KLASA OBZ: ");
                        System.out.println(racuni);

                        odgovor.setOdgovor(racuni);
                        break;

                    case UCITAJ_ARTIKLE:
                        List<Artikal> artikli = Controller.getInstance().ucitajArtikle();
                        odgovor.setOdgovor(artikli);
                        break;

                    case DODAJ_ARTIKAL:
                        Artikal p = (Artikal) zahtev.getParametar();
                        Controller.getInstance().dodajArtikal(p);
                        odgovor.setOdgovor(null);
                        break;

                    case IZMENI_ARTIKAL:
                        Artikal p1 = (Artikal) zahtev.getParametar();
                        Controller.getInstance().izmeniArtikal(p1);
                        odgovor.setOdgovor(null);
                        System.out.println("Operacija: " + zahtev.getOperacija());
                        System.out.println("Odgovor koji saljem: " + odgovor.getOdgovor());

                        break;

                     case OBRISI_ARTIKAL:
                         // ne radi brisanje jer ima u racunu
                        try{
                            Artikal p2 = (Artikal) zahtev.getParametar();
                            Controller.getInstance().obrisiArtikal(p2);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){
                            odgovor.setOdgovor(e);
                        }
                        break;

                    case UCITAJ_KATEGORIJE_GOSTIJU:
                        List<KategorijaGosta> kg = Controller.getInstance().ucitajKategorijeGostiju();
                        odgovor.setOdgovor(kg);
                        break;

                    case DODAJ_KATEGORIJU_GOSTA:
                        KategorijaGosta kg1 = (KategorijaGosta) zahtev.getParametar();
                        Controller.getInstance().dodajKategorijuGosta(kg1);
                        odgovor.setOdgovor(null);
                        break;

                    case IZMENI_KATEGORIJU_GOSTA:
                        KategorijaGosta kg2 = (KategorijaGosta) zahtev.getParametar();
                        Controller.getInstance().izmeniKategorijuGosta(kg2);
                        odgovor.setOdgovor(null);
                        break;

                    case OBRISI_KATEGORIJU_GOSTA:
                        try {
                            KategorijaGosta kg3 = (KategorijaGosta) zahtev.getParametar();
                            Controller.getInstance().obrisiKategorijuGosta(kg3);
                            odgovor.setOdgovor(null);
                        } catch (Exception e) {
                            odgovor.setOdgovor(e);
                        }
                        break;

                    case UCITAJ_KONOBARA:
                        List<Konobar> konobari = Controller.getInstance().ucitajKonobare();
                        odgovor.setOdgovor(konobari);
                        break;

                    case DODAJ_KONOBARA:
                        Konobar k1 = (Konobar) zahtev.getParametar();
                        Controller.getInstance().dodajKonobara(k1);
                        odgovor.setOdgovor(null);
                        break;

                    case IZMENI_KONOBARA:
                        Konobar k2 = (Konobar) zahtev.getParametar();
                        Controller.getInstance().izmeniKonobara(k2);
                        odgovor.setOdgovor(null);
                        break;

                    case OBRISI_KONOBARA:
                        try {
                            Konobar k3 = (Konobar) zahtev.getParametar();
                            Controller.getInstance().obrisiKonobara(k3);
                            odgovor.setOdgovor(null);
                        } catch (Exception e) {
                            odgovor.setOdgovor(e);
                        }
                        break;

                    case UCITAJ_STAVKE:
                        Racun r = (Racun) zahtev.getParametar();
                        List<StavkaRacuna> stavke = Controller.getInstance().ucitajStavke(r);
                        odgovor.setOdgovor(stavke);

                        break;


                    case UCITAJ_SMENE:
                        List<Smena> smene = Controller.getInstance().ucitajSmene();

                        System.out.println("KLASA OBZ: ");
                        System.out.println(smene);

                        odgovor.setOdgovor(smene);
                        break;

                    case DODAJ_SMENU:
                        System.out.println("[SERVER] Primljen zahtev za dodavanje smene!");
                        try {
                            Smena s1 = (Smena) zahtev.getParametar();
                            System.out.println("[SERVER] Podaci o smeni: " + s1);
                            Controller.getInstance().dodajSmenu(s1);
                            odgovor.setOdgovor(null);
                            System.out.println("[SERVER] Smena uspešno prosleđena controlleru.");
                        } catch (Exception e) {
                            System.out.println("[SERVER] Greška prilikom dodavanja smene: " + e.getMessage());
                            odgovor.setOdgovor(e);
                        }
                        break;


                    case IZMENI_SMENU:
                        try {
                            Smena s2 = (Smena) zahtev.getParametar();
                            Controller.getInstance().izmeniSmenu(s2);
                            odgovor.setOdgovor(null);
                        } catch (Exception e) {
                            odgovor.setOdgovor(e);
                        }
                        break;

                    case OBRISI_SMENU:
                        try {
                            Smena s = (Smena) zahtev.getParametar();
                            Controller.getInstance().obrisiSmenu(s);
                            odgovor.setOdgovor(null);
                            System.out.println("[SERVER] Smena uspešno obrisana: " + s);
                        } catch (Exception e) {
                            System.out.println("[SERVER] Greška prilikom brisanja smene: " + e.getMessage());
                            odgovor.setOdgovor(e);
                        }
                        break;

                    case UCITAJ_RASPORED:
                        try {
                            System.out.println("[SERVER] Primljen zahtev za učitavanje rasporeda...");
                            List<KonobarSmena> raspored = Controller.getInstance().ucitajRaspored();
                            System.out.println("[SERVER] Učitano " + raspored.size() + " redova rasporeda.");
                            odgovor.setOdgovor(raspored);
                        } catch (Exception e) {
                            System.out.println("[SERVER] Greška u case UCITAJ_RASPORED: " + e.getMessage());
                            e.printStackTrace(); // << VAŽNO — da vidiš tačno gde je puklo
                            odgovor.setOdgovor(e);
                        }
                        break;

                        case DODAJ_RASPORED:
                            try {
                                KonobarSmena ks1 = (KonobarSmena) zahtev.getParametar();
                                Controller.getInstance().dodajRaspored(ks1);
                                odgovor.setOdgovor(null);
                                System.out.println("[SERVER] Sistem je zapamtio raspored.");
                            } catch (Exception e) {
                                System.out.println("[SERVER] Sistem ne može da zapamti raspored: " + e.getMessage());
                                odgovor.setOdgovor(e);
                            }
                            break;

                        case IZMENI_RASPORED:
                            try {
                                KonobarSmena ks2 = (KonobarSmena) zahtev.getParametar();
                                Controller.getInstance().izmeniRaspored(ks2);
                                odgovor.setOdgovor(null);
                                System.out.println("[SERVER] Sistem je izmenio raspored.");
                            } catch (Exception e) {
                                System.out.println("[SERVER] Sistem ne može da izmeni raspored: " + e.getMessage());
                                odgovor.setOdgovor(e);

                            }
                            break;

                        case OBRISI_RASPORED:
                            try {
                                KonobarSmena ks = (KonobarSmena) zahtev.getParametar();
                                Controller.getInstance().obrisiRaspored(ks);
                                odgovor.setOdgovor(null);
                                System.out.println("[SERVER] Raspored uspešno obrisan: "
                                        + ks.getKonobar().getIme() + " " + ks.getKonobar().getPrezime()
                                        + " - " + ks.getSmena().getNaziv()
                                        + " (" + ks.getDatumSmene() + ")");
                            } catch (Exception e) {
                                System.out.println("[SERVER] Greška prilikom brisanja rasporeda: " + e.getMessage());
                                odgovor.setOdgovor(e);
                            }
                            break;

                        case OBRISI_RACUN:
                            try{
                                Racun r1 = (Racun) zahtev.getParametar();
                                Controller.getInstance().obrisiRacun(r1);
                                odgovor.setOdgovor(null);
                            }catch (Exception e){
                                odgovor.setOdgovor(e);
                            }
                            break;

                        case DODAJ_RACUN:
                            Racun r2 = (Racun) zahtev.getParametar();
                            Controller.getInstance().dodajRacun(r2);
                            odgovor.setOdgovor(null);
                            break;

                        case OBRISI_STAVKU:
                            try{
                                StavkaRacuna sr = (StavkaRacuna) zahtev.getParametar();
                                Controller.getInstance().obrisiStavkuRacuna(sr);
                                odgovor.setOdgovor(null);
                            }catch (Exception e){
                                odgovor.setOdgovor(e);
                            }
                            break;

                        case IZMENI_RACUN:
                            Racun r3 = (Racun) zahtev.getParametar();
                            Controller.getInstance().izmeniRacun(r3);
                            odgovor.setOdgovor(null);
                            break;

                        case IZVEZI_RACUNE:
                            try {
                                List<Racun> racuniZaIzvoz = (List<Racun>) zahtev.getParametar();
                                String putanja = Controller.getInstance().izveziRacune(racuniZaIzvoz);
                                odgovor.setOdgovor(putanja);
                            } catch (Exception e) {
                                odgovor.setOdgovor(e);
                            }
                            break;

                    default:
                        System.out.println("GRESKA");
                }

                posiljalac.posalji(odgovor);

            } catch (Exception ex) {
                Logger.getLogger(ObradaKlijentskiihZahteva.class.getName()).log(Level.SEVERE, null, ex);
              }

            }


    }

    /**
     * Prekida obradu zahteva: postavlja flag kraj, zatvara soket i prekida nit.
     */
    public void prekini(){
        kraj = true;
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskiihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        interrupt();
    }

    /**
     * Vraca ulogovanog konobara na ovoj sesiji.
     *
     * @return ulogovani konobar ili null ako nije prijavljen
     */
    public Konobar getK() {
        return k;
    }

    /**
     * Postavlja ulogovanog konobara na ovoj sesiji.
     *
     * @param k konobar koji se vezuje za sesiju
     */
    public void setK(Konobar k) {
        this.k = k;
    }







}
