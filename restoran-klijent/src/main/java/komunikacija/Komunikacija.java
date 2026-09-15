package komunikacija;

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
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Singleton fasada nad TCP soket komunikacijom sa serverskom aplikacijom.
 * <p>
 * Klasa enkapsulira kreiranje soketa, slanje {@link Zahtev} objekata i
 * prijem {@link Odgovor} objekata. Svaka javna metoda mapira se na jednu
 * vrednost iz {@link Operacija} enumeracije i salje odgovarajuci parametar
 * na server.
 * </p>
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Komunikacija {
    
    /** TCP soket ka serverskoj aplikaciji. */
    private Socket soket;
    /** Objekat zaduzen za slanje zahteva preko soketa. */
    private Posiljalac posiljalac;
    /** Objekat zaduzen za prijem odgovora preko soketa. */
    private Primalac primalac;
    /** Jedina instanca singleton klase. */
    private static Komunikacija instanca;

    /**
     * Privatni konstruktor koji sprecava eksterno kreiranje instanci.
     */
    private Komunikacija() {
    }

    /**
     * Vraca jedinu instancu klase Komunikacija.
     *
     * @return singleton instanca
     */
    public static Komunikacija getInstance() {
        if (instanca == null) {
            instanca = new Komunikacija();
        }
        return instanca;
    }

    /**
     * Uspostavlja TCP konekciju ka serveru koristeci host i port iz
     * {@link konfiguracija.Konfiguracija}. Inicijalizuje {@link Posiljalac}
     * i {@link Primalac}.
     * <p>
     * Ne salje {@link Operacija}; lokalna pripremna metoda.
     * </p>
     */
    public void konekcija() {
        try {
            
            String host = konfiguracija.Konfiguracija.getInstanca().getProperty("host");
            int port = Integer.parseInt(konfiguracija.Konfiguracija.getInstanca().getProperty("port"));
            soket = new Socket(host, port);
            posiljalac = new Posiljalac(soket);
            primalac = new Primalac(soket);
        } catch (IOException ex) {
            System.out.println("SERVER NIJE POVEZAN");
        }
    }

    /**
     * Prijavljuje konobara na sistem.
     * <ul>
     *   <li>Operacija: {@link Operacija#LOGIN}</li>
     *   <li>Parametar: {@link Konobar} sa korisnickim imenom i sifrom</li>
     *   <li>Ocekivani odgovor: {@link Konobar} objekat ulogovanog korisnika,
     *       ili {@code null} ako prijava nije uspela</li>
     * </ul>
     *
     * @param ki korisnicko ime
     * @param pass sifra
     * @return ulogovani {@link Konobar}, ili {@code null} ako prijava nije uspela
     */
    public Konobar login(String ki, String pass) {
        Konobar k = new Konobar();
        k.setSifra(pass);
        k.setKorisnickoIme(ki);

        Zahtev zahtev = new Zahtev(Operacija.LOGIN, k);

        // OVO je prava linija
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            System.out.println("Server nije poslao odgovor.");
            return null;
        }

        Konobar konobar = (Konobar) odg.getOdgovor();
        if (konobar == null) {
            System.out.println("Login neuspešan.");
        }

        return konobar;
    }

    /**
     * Ucitava sve goste sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_GOSTE}</li>
     *   <li>Parametar: {@code null}</li>
     *   <li>Ocekivani odgovor: {@code List<Gost>} sa svim gostima,
     *       ili {@code null} ako server nije poslao odgovor</li>
     * </ul>
     *
     * @return lista gostiju, ili {@code null} ako nema odgovora
     */
    public List<Gost> ucitajGoste() {
        // napravi zahtev
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_GOSTE, null);

        // pošalji zahtev
        posiljalac.posalji(zahtev);

        // primi odgovor
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            System.out.println("Server nije poslao odgovor.");
            return null;
        }

        // izvuci listu gostiju
        List<Gost> gosti = (List<Gost>) odg.getOdgovor();
        if (gosti == null || gosti.isEmpty()) {
            System.out.println("Nema gostiju u bazi.");
        }

        return gosti;
    }

    /**
     * Brise gosta sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_GOSTA}</li>
     *   <li>Parametar: {@link Gost} koji se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param g gost koji se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiGosta(Gost g) throws Exception {
        
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_GOSTA,g);
        posiljalac.posalji(zahtev);
        
        Odgovor odg = (Odgovor) primalac.primi();
        
        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(null, "Uspesno obrisan gost", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Brisanje nije uspelo", "Greska", JOptionPane.ERROR_MESSAGE);
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }

        
    }

    /**
     * Dodaje novog gosta na server.
     * <ul>
     *   <li>Operacija: {@link Operacija#DODAJ_GOSTA}</li>
     *   <li>Parametar: {@link Gost} koji se dodaje</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param g gost koji se dodaje
     */
    public void dodajGosta(Gost g) {
        
         Zahtev zahtev = new Zahtev(Operacija.DODAJ_GOSTA,g);
         posiljalac.posalji(zahtev);
         Odgovor odg = (Odgovor) primalac.primi();
         if(odg.getOdgovor() == null){
             System.out.println("USPEH");
         }else{
             System.out.println("GRESKA");
         }
        
    }

    /**
     * Menja postojeceg gosta na serveru.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZMENI_GOSTA}</li>
     *   <li>Parametar: {@link Gost} sa izmenjenim podacima</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param g gost sa izmenjenim podacima
     */
    public void izmaniGosta(Gost g) {
        
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_GOSTA, g);
        posiljalac.posalji(zahtev);

        //
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("USPEH");
            JOptionPane.showMessageDialog(null, 
                "Sistem je izmenio gosta.", 
                "Obaveštenje", 
                JOptionPane.INFORMATION_MESSAGE);
            cordinator.Cordinator.getInstance().osveziFormuGosta();
        } else {
            System.out.println("GRESKA");
        }

        
    }

    /**
     * Ucitava sve racune sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_RACUNE}</li>
     *   <li>Parametar: {@code null}</li>
     *   <li>Ocekivani odgovor: {@code List<Racun>} sa svim racunima,
     *       ili {@code null} ako server nije poslao odgovor</li>
     * </ul>
     *
     * @return lista racuna, ili {@code null} ako nema odgovora
     */
    public List<Racun> ucitajRacune() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_RACUNE, null);

        // pošalji zahtev
        posiljalac.posalji(zahtev);

        // primi odgovor
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            System.out.println("Server nije poslao odgovor.");
            return null;
        }

        // izvuci listu računa
        List<Racun> racuni = (List<Racun>) odg.getOdgovor();
        if (racuni == null || racuni.isEmpty()) {
            System.out.println("Nema računa u bazi.");
        }

        return racuni;
    }



    /**
     * Ucitava sve artikle sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_ARTIKLE}</li>
     *   <li>Parametar: {@code null}</li>
     *   <li>Ocekivani odgovor: {@code List<Artikal>} sa svim artiklima,
     *       ili {@code null} ako server nije poslao odgovor</li>
     * </ul>
     *
     * @return lista artikala, ili {@code null} ako nema odgovora
     */
    public List<Artikal> ucitajArtikle() {
        // napravi zahtev
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_ARTIKLE, null);

        // pošalji zahtev
        posiljalac.posalji(zahtev);

        // primi odgovor
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            System.out.println("Sistem ne može da učita artikala.");
            return null;
        }

        // izvuci listu artikala
        List<Artikal> artikli = (List<Artikal>) odg.getOdgovor();
        if (artikli == null || artikli.isEmpty()) {
            System.out.println("Nema artikala u bazi.");
        } else {
            System.out.println("Sistem je učitao artikala.");
        }

        return artikli;
    }

    /**
     * Brise artikal sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_ARTIKAL}</li>
     *   <li>Parametar: {@link Artikal} koji se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param artikal artikal koji se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiArtikal(Artikal artikal) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_ARTIKAL, artikal);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(null, 
                "Sistem je obrisao artikal.", 
                "Obaveštenje", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Sistem ne može da obriše artikal.", 
                "Greška", 
                JOptionPane.ERROR_MESSAGE);
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }

    /**
     * Dodaje novi artikal na server.
     * <ul>
     *   <li>Operacija: {@link Operacija#DODAJ_ARTIKAL}</li>
     *   <li>Parametar: {@link Artikal} koji se dodaje</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param artikal artikal koji se dodaje
     */
    public void dodajArtikal(Artikal artikal) {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_ARTIKAL, artikal);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Sistem je zapamtio artikal.");
        } else {
            System.out.println("Sistem ne može da zapamti artikal.");
        }
    }

    /**
     * Menja postojeci artikal na serveru.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZMENI_ARTIKAL}</li>
     *   <li>Parametar: {@link Artikal} sa izmenjenim podacima</li>
     *   <li>Ocekivani odgovor: ne-{@code null} telo odgovora oznacava uspeh
     *       (osvezava formu artikala); {@code null} oznacava neuspeh</li>
     * </ul>
     *
     * @param artikal artikal sa izmenjenim podacima
     */
    public void izmeniArtikal(Artikal artikal) {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_ARTIKAL, artikal);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() != null) {
            System.out.println("Sistem je izmenio artikal.");
            cordinator.Cordinator.getInstance().osveziFormuArtikal();
        } else {
            System.out.println("Sistem ne može da izmeni artikal.");
        }
    }

    /**
     * Ucitava sve kategorije gostiju sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_KATEGORIJE_GOSTIJU}</li>
     *   <li>Parametar: {@code null}</li>
     *   <li>Ocekivani odgovor: {@code List<KategorijaGosta>} sa svim kategorijama,
     *       ili {@code null} ako server nije poslao odgovor</li>
     * </ul>
     *
     * @return lista kategorija gostiju, ili {@code null} ako nema odgovora
     */
    public List<KategorijaGosta> ucitajKategorijeGostiju() {
        
        // napravi zahtev
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_KATEGORIJE_GOSTIJU, null);

        // pošalji zahtev
        posiljalac.posalji(zahtev);

        // primi odgovor
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            System.out.println("Server nije poslao odgovor.");
            return null;
        }

        // izvuci listu gostiju
        List<KategorijaGosta> kg = (List<KategorijaGosta>) odg.getOdgovor();
        if (kg == null || kg.isEmpty()) {
            System.out.println("Nema gostiju u bazi.");
        }

        return kg;
        
    }


    /**
     * Dodaje novu kategoriju gosta na server.
     * <ul>
     *   <li>Operacija: {@link Operacija#DODAJ_KATEGORIJU_GOSTA}</li>
     *   <li>Parametar: {@link KategorijaGosta} koja se dodaje</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param kg kategorija gosta koja se dodaje
     */
    public void dodajKategorijuGosta(KategorijaGosta kg) {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_KATEGORIJU_GOSTA, kg);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Sistem je zapamtio kategoriju gosta.");
        } else {
            System.out.println("Sistem ne može da zapamti kategoriju gosta.");
        }
    }

    /**
     * Menja postojecu kategoriju gosta na serveru.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZMENI_KATEGORIJU_GOSTA}</li>
     *   <li>Parametar: {@link KategorijaGosta} sa izmenjenim podacima</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param kg kategorija gosta sa izmenjenim podacima
     */
    public void izmeniKategorijuGosta(KategorijaGosta kg) {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_KATEGORIJU_GOSTA, kg);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Sistem je izmenio kategoriju gosta.");
            cordinator.Cordinator.getInstance().osveziFormu();
        } else {
            System.out.println("Sistem ne može da izmeni kategoriju gosta.");
        }
    }

    /**
     * Brise kategoriju gosta sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_KATEGORIJU_GOSTA}</li>
     *   <li>Parametar: {@link KategorijaGosta} koja se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param kg kategorija gosta koja se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiKategorijuGosta(KategorijaGosta kg) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_KATEGORIJU_GOSTA, kg);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(null, 
                "Sistem je obrisao kategoriju gosta.", 
                "Obaveštenje", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Sistem ne može da obriše kategoriju gosta.", 
                "Greška", 
                JOptionPane.ERROR_MESSAGE);
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }



    /**
     * Dodaje novog konobara na server.
     * <ul>
     *   <li>Operacija: {@link Operacija#DODAJ_KONOBARA}</li>
     *   <li>Parametar: {@link Konobar} koji se dodaje</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param k konobar koji se dodaje
     */
    public void dodajKonobara(Konobar k) {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_KONOBARA, k);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Sistem je zapamtio konobara.");
        } else {
            System.out.println("Sistem ne može da zapamti konobara.");
        }
    }

    /**
     * Menja postojeceg konobara na serveru.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZMENI_KONOBARA}</li>
     *   <li>Parametar: {@link Konobar} sa izmenjenim podacima</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param k konobar sa izmenjenim podacima
     */
    public void izmeniKonobara(Konobar k) {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_KONOBARA, k);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Sistem je izmenio konobara.");
            cordinator.Cordinator.getInstance().osveziFormu();
        } else {
            System.out.println("Sistem ne može da izmeni konobara.");
        }
    }
    
    /**
     * Brise konobara sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_KONOBARA}</li>
     *   <li>Parametar: {@link Konobar} koji se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param k konobar koji se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiKonobara(Konobar k) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_KONOBARA, k);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(null,
                "Sistem je obrisao konobara.",
                "Obaveštenje",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Sistem ne može da obriše konobara.",
                "Greška",
                JOptionPane.ERROR_MESSAGE);
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }

    /**
     * Ucitava sve konobare sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_KONOBARA}</li>
     *   <li>Parametar: {@code null}</li>
     *   <li>Ocekivani odgovor: {@code List<Konobar>} sa svim konobarima,
     *       ili {@code null} ako server nije poslao odgovor</li>
     * </ul>
     *
     * @return lista konobara, ili {@code null} ako nema odgovora
     */
    public List<Konobar> ucitajKonobare() {
        // napravi zahtev
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_KONOBARA, null);
        posiljalac.posalji(zahtev);

        // primi odgovor
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            System.out.println("Server nije poslao odgovor.");
            return null;
        }

        // izvuci listu konobara
        List<Konobar> konobari = (List<Konobar>) odg.getOdgovor();
        if (konobari == null || konobari.isEmpty()) {
            System.out.println("Nema konobara u bazi.");
        } else {
            System.out.println("Sistem je učitao konobare.");
        }

        return konobari;
    }

    /**
     * Ucitava stavke za dati racun sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_STAVKE}</li>
     *   <li>Parametar: {@link Racun} za koji se ucitavaju stavke</li>
     *   <li>Ocekivani odgovor: {@code List<StavkaRacuna>} sa stavkama racuna</li>
     * </ul>
     *
     * @param p racun cije se stavke ucitavaju
     * @return lista stavki racuna
     */
    public List<StavkaRacuna> ucitajStavke(Racun p) {
        
        List<StavkaRacuna> stavke = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_STAVKE, p);
        posiljalac.posalji(zahtev);
        ///////////////
        
        Odgovor odg = (Odgovor) primalac.primi();
        stavke = (List<StavkaRacuna>) odg.getOdgovor();
        return stavke;
        
    }

    /**
     * Ucitava sve smene sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_SMENE}</li>
     *   <li>Parametar: {@code null}</li>
     *   <li>Ocekivani odgovor: {@code List<Smena>} sa svim smenama,
     *       ili {@code null} ako server nije poslao odgovor</li>
     * </ul>
     *
     * @return lista smena, ili {@code null} ako nema odgovora
     */
    public List<Smena> ucitajSmene() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_SMENE, null);

        // pošalji zahtev
        posiljalac.posalji(zahtev);

        // primi odgovor
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            System.out.println("Server nije poslao odgovor.");
            return null;
        }

        // izvuci listu smena
        List<Smena> smene = (List<Smena>) odg.getOdgovor();
        if (smene == null || smene.isEmpty()) {
            System.out.println("Nema smena u bazi.");
        } else {
            System.out.println("Sistem je uspešno učitao smene.");
        }

        return smene;
    }


    /**
     * Dodaje novu smenu na server.
     * <ul>
     *   <li>Operacija: {@link Operacija#DODAJ_SMENU}</li>
     *   <li>Parametar: {@link Smena} koja se dodaje</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param s smena koja se dodaje
     */
    public void dodajSmenu(Smena s) {
        System.out.println("[KOMUNIKACIJA] Slanje zahteva za DODAJ_SMENU: " + s);
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_SMENU, s);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        System.out.println("[KOMUNIKACIJA] Odgovor: " + odg);

        if (odg.getOdgovor() == null) {
            System.out.println("Sistem je zapamtio smenu.");
        } else {
            System.out.println("Sistem ne može da zapamti smenu.");
        }
    }


    /**
     * Menja postojecu smenu na serveru.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZMENI_SMENU}</li>
     *   <li>Parametar: {@link Smena} sa izmenjenim podacima</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param s smena sa izmenjenim podacima
     */
    public void izmeniSmenu(Smena s) {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_SMENU, s);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            System.out.println("Sistem je izmenio smenu.");
            cordinator.Cordinator.getInstance().osveziFormu();
        } else {
            System.out.println("Sistem ne može da izmeni smenu.");
        }
    }

    /**
     * Brise smenu sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_SMENU}</li>
     *   <li>Parametar: {@link Smena} koja se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param s smena koja se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiSmenu(Smena s) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_SMENU, s);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sistem je obrisao smenu.",
                    "Obaveštenje",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Sistem ne može da obriše smenu.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("GREŠKA PRI BRISANJU SMENE");
        }
    }

    /**
     * Ucitava raspored rada (konobar-smena) sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#UCITAJ_RASPORED}</li>
     *   <li>Parametar: {@code null}</li>
     *   <li>Ocekivani odgovor: {@code List<KonobarSmena>} sa rasporedom,
     *       ili {@code null} ako server nije poslao odgovor</li>
     * </ul>
     *
     * @return lista stavki rasporeda, ili {@code null} ako nema odgovora
     */
    public List<KonobarSmena> ucitajRaspored() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_RASPORED, null);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg == null) {
            System.out.println("Server nije poslao odgovor.");
            return null;
        }

        List<KonobarSmena> raspored = (List<KonobarSmena>) odg.getOdgovor();
        if (raspored == null || raspored.isEmpty()) {
            System.out.println("Nema podataka o rasporedu u bazi.");
        }

        return raspored;
    }
    
    /**
     * Dodaje novu stavku rasporeda na server.
     * <ul>
     *   <li>Operacija: {@link Operacija#DODAJ_RASPORED}</li>
     *   <li>Parametar: {@link KonobarSmena} koja se dodaje</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param ks stavka rasporeda koja se dodaje
     */
    public void dodajRaspored(KonobarSmena ks) {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_RASPORED, ks);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(null, "Sistem je zapamtio raspored.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Sistem ne može da zapamti raspored.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menja postojecu stavku rasporeda na serveru.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZMENI_RASPORED}</li>
     *   <li>Parametar: {@link KonobarSmena} sa izmenjenim podacima</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param ks stavka rasporeda sa izmenjenim podacima
     */
    public void izmeniRaspored(KonobarSmena ks) {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_RASPORED, ks);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(null, "Sistem je izmenio raspored.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Sistem ne može da izmeni raspored.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Brise stavku rasporeda sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_RASPORED}</li>
     *   <li>Parametar: {@link KonobarSmena} koja se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param ks stavka rasporeda koja se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiRaspored(KonobarSmena ks) throws Exception{
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_RASPORED, ks);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sistem je obrisao raspored.",
                    "Obaveštenje",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Sistem ne može da obriše raspored.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("GREŠKA PRI BRISANJU RASPOREDA");
        }
    }

    /**
     * Brise racun sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_RACUN}</li>
     *   <li>Parametar: {@link Racun} koji se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param r racun koji se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiRacun(Racun r) throws Exception {
        
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_RACUN, r);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor() == null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }

        
    }

    /**
     * Dodaje novi racun na server.
     * <ul>
     *   <li>Operacija: {@link Operacija#DODAJ_RACUN}</li>
     *   <li>Parametar: {@link Racun} koji se dodaje</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param r racun koji se dodaje
     */
    public void dodajRacun(Racun r) {
        
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_RACUN, r);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            System.out.println("USPEH");
        } else {
            System.out.println("GRESKA");
        }

        
    }

    /**
     * Brise stavku racuna sa servera.
     * <ul>
     *   <li>Operacija: {@link Operacija#OBRISI_STAVKU}</li>
     *   <li>Parametar: {@link StavkaRacuna} koja se brise</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       {@link Exception} oznacava gresku</li>
     * </ul>
     *
     * @param sr stavka racuna koja se brise
     * @throws Exception ako brisanje nije uspelo
     */
    public void obrisiStavkuRacuna(StavkaRacuna sr) throws Exception {
        
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_STAVKU, sr);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor() == null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
}

        
    }

    /**
     * Menja postojeci racun na serveru.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZMENI_RACUN}</li>
     *   <li>Parametar: {@link Racun} sa izmenjenim podacima</li>
     *   <li>Ocekivani odgovor: {@code null} u telu odgovora oznacava uspeh;
     *       objekat greske oznacava neuspeh</li>
     * </ul>
     *
     * @param r racun sa izmenjenim podacima
     */
    public void izmeniRacun(Racun r) {
        
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_RACUN, r);
        posiljalac.posalji(zahtev);

        // 
        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor() == null){
            System.out.println("USPEH");
            //cordinator.Cordinator.getInstance().osveziFormu();
        }else{
            System.out.println("GRESKA");
        }

        
    }

    /**
     * Salje zahtev serveru za izvoz liste racuna u JSON fajl.
     * <ul>
     *   <li>Operacija: {@link Operacija#IZVEZI_RACUNE}</li>
     *   <li>Parametar: lista racuna za izvoz</li>
     *   <li>Odgovor: apsolutna putanja kreiranog JSON fajla ({@link String})</li>
     * </ul>
     *
     * @param racuni lista racuna koja se izvozi
     * @return putanja kreiranog fajla na serveru
     * @throws Exception ako server vrati gresku ili izvoz ne uspe
     */
    public String izveziRacune(List<Racun> racuni) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZVEZI_RACUNE, racuni);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        if (odg == null) {
            throw new Exception("Server nije poslao odgovor.");
        }
        Object rezultat = odg.getOdgovor();
        if (rezultat instanceof Exception) {
            throw (Exception) rezultat;
        }
        return (String) rezultat;
    }

}
