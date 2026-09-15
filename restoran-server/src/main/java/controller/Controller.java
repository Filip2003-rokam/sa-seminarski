package controller;

import operacija.kategorijagosta.ObrisiKategorijuGostaSO;
import domen.Gost;
import domen.Artikal;
import domen.KategorijaGosta;
import domen.Konobar;
import domen.KonobarSmena;
import domen.Racun;
import domen.Smena;
import domen.StavkaRacuna;
import java.util.List;
import operacija.gosti.DodajGostaSO;
import operacija.gosti.IzmeniGostaSO;
import operacija.gosti.ObrisiGostaSO;
import operacija.gosti.UcitajGosteSO;
import operacija.artikal.DodajArtikalSO;
import operacija.artikal.IzmeniArtikalSO;
import operacija.artikal.ObrisiArtikalSO;
import operacija.artikal.UcitajArtikleSO;
import operacija.kategorijagosta.DodajKategorijuGostaSO;
import operacija.kategorijagosta.IzmeniKategorijuGostaSO;
import operacija.kategorijagosta.UcitajKategorijeGostijuSO;
import operacija.konobar.DodajKonobaraSO;
import operacija.konobar.IzmeniKonobaraSO;
import operacija.konobar.ObrisiKonobaraSO;
import operacija.konobar.UcitajKonobareSO;
import operacija.login.LoginOperacija;
import operacija.racuni.DodajRacunSO;
import operacija.racuni.IzmeniRacunSO;
import operacija.racuni.IzveziRacuneUJsonSO;
import operacija.racuni.ObrisiRacunSO;
import operacija.racuni.UcitajRacuneSO;
import operacija.stavke.ObrisiStavkuRacunaSO;
import operacija.raspored.DodajRasporedSO;
import operacija.raspored.IzmeniRasporedSO;
import operacija.raspored.ObrisiRasporedSO;
import operacija.raspored.UcitajRasporedSO;
import operacija.smena.DodajSmenuSO;
import operacija.smena.IzmeniSmenuSO;
import operacija.smena.ObrisiSmenuSO;
import operacija.smena.UcitajSmeneSO;
import operacija.stavke.UcitajStavkeRacunaSO;

/**
 * Singleton kontroler serverske aplikacije.
 * Prima zahteve iz niti za obradu klijenata i delegira ih odgovarajucim
 * sistemskim operacijama (SO).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Controller {

    /**
     * Jedina instanca kontrolera (singleton).
     */
    private static Controller instance;

    /**
     * Privatni konstruktor – sprecava kreiranje vise instanci.
     */
    private Controller() {
    }

    /**
     * Vraca jedinu instancu kontrolera.
     *
     * @return instanca klase Controller
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Prijavljuje konobara na sistem.
     * Poziva sistemsku operaciju {@link LoginOperacija}.
     *
     * @param z konobar sa unetim kredencijalima
     * @return ulogovani konobar ili null ako prijava nije uspela
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public Konobar login(Konobar z) throws Exception {
        LoginOperacija operacija = new LoginOperacija();
        operacija.izvrsi(z, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getKonobar());

        return operacija.getKonobar();
    }

    /**
     * Ucitava sve goste.
     * Poziva sistemsku operaciju {@link UcitajGosteSO}.
     *
     * @return lista gostiju
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<Gost> ucitajGoste() throws Exception{

        UcitajGosteSO operacija = new UcitajGosteSO();
        operacija.izvrsi(null, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getGosti());

        return operacija.getGosti();
    }

    /**
     * Brise gosta.
     * Poziva sistemsku operaciju {@link ObrisiGostaSO}.
     *
     * @param g gost koji se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiGosta(Gost g) throws Exception {

        ObrisiGostaSO operacija = new ObrisiGostaSO();
        operacija.izvrsi(g, null);

    }

    /**
     * Dodaje novog gosta.
     * Poziva sistemsku operaciju {@link DodajGostaSO}.
     *
     * @param g gost koji se dodaje
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void dodajGosta(Gost g) throws Exception{

        DodajGostaSO operacija = new DodajGostaSO();
        operacija.izvrsi(g, null);

    }

    /**
     * Menja podatke o gostu.
     * Poziva sistemsku operaciju {@link IzmeniGostaSO}.
     *
     * @param g gost sa novim vrednostima
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void izmeniGosta(Gost g) throws Exception {

        IzmeniGostaSO operacija = new IzmeniGostaSO();
        operacija.izvrsi(g, null);

    }

    /**
     * Ucitava sve racune.
     * Poziva sistemsku operaciju {@link UcitajRacuneSO}.
     *
     * @return lista racuna
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<Racun> ucitajRacune() throws Exception {

        UcitajRacuneSO operacija = new UcitajRacuneSO();
        operacija.izvrsi(null, null);
        System.out.println("KLASA CONTROLLER :" + operacija.getRacuni());
        return operacija.getRacuni();


    }

    /**
     * Ucitava sve artikle.
     * Poziva sistemsku operaciju {@link UcitajArtikleSO}.
     *
     * @return lista artikala
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<Artikal> ucitajArtikle() throws Exception {

        UcitajArtikleSO operacija = new UcitajArtikleSO();
        operacija.izvrsi(null, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getArtikli());

        return operacija.getArtikli();
    }

    /**
     * Brise artikal.
     * Poziva sistemsku operaciju {@link ObrisiArtikalSO}.
     *
     * @param artikal artikal koji se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiArtikal(Artikal artikal) throws Exception {
        ObrisiArtikalSO operacija = new ObrisiArtikalSO();
        operacija.izvrsi(artikal, null);
    }

    /**
     * Dodaje novi artikal.
     * Poziva sistemsku operaciju {@link DodajArtikalSO}.
     *
     * @param artikal artikal koji se dodaje
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void dodajArtikal(Artikal artikal) throws Exception {
        DodajArtikalSO operacija = new DodajArtikalSO();
        operacija.izvrsi(artikal, null);
    }

    /**
     * Menja podatke o artiklu.
     * Poziva sistemsku operaciju {@link IzmeniArtikalSO}.
     *
     * @param artikal artikal sa novim vrednostima
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void izmeniArtikal(Artikal artikal) throws Exception {
        IzmeniArtikalSO operacija = new IzmeniArtikalSO();
        operacija.izvrsi(artikal, null);
    }

    /**
     * Ucitava sve kategorije gostiju.
     * Poziva sistemsku operaciju {@link UcitajKategorijeGostijuSO}.
     *
     * @return lista kategorija gostiju
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<KategorijaGosta> ucitajKategorijeGostiju() throws Exception {

        UcitajKategorijeGostijuSO operacija = new UcitajKategorijeGostijuSO();
        operacija.izvrsi(null, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getKategorije());

        return operacija.getKategorije();

    }

    /**
     * Dodaje novu kategoriju gosta.
     * Poziva sistemsku operaciju {@link DodajKategorijuGostaSO}.
     *
     * @param kg1 kategorija koja se dodaje
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void dodajKategorijuGosta(KategorijaGosta kg1) throws Exception {
        DodajKategorijuGostaSO operacija = new DodajKategorijuGostaSO();
        operacija.izvrsi(kg1, null);
    }

    /**
     * Menja podatke o kategoriji gosta.
     * Poziva sistemsku operaciju {@link IzmeniKategorijuGostaSO}.
     *
     * @param kg2 kategorija sa novim vrednostima
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void izmeniKategorijuGosta(KategorijaGosta kg2) throws Exception {
        IzmeniKategorijuGostaSO operacija = new IzmeniKategorijuGostaSO();
        operacija.izvrsi(kg2, null);
    }

    /**
     * Brise kategoriju gosta.
     * Poziva sistemsku operaciju {@link ObrisiKategorijuGostaSO}.
     *
     * @param kg3 kategorija koja se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiKategorijuGosta(KategorijaGosta kg3) throws Exception {
        ObrisiKategorijuGostaSO operacija = new ObrisiKategorijuGostaSO();
        operacija.izvrsi(kg3, null);
    }

    /**
     * Dodaje novog konobara.
     * Poziva sistemsku operaciju {@link DodajKonobaraSO}.
     *
     * @param k1 konobar koji se dodaje
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void dodajKonobara(Konobar k1) throws Exception {
        DodajKonobaraSO operacija = new DodajKonobaraSO();
        operacija.izvrsi(k1, null);
    }

    /**
     * Menja podatke o konobaru.
     * Poziva sistemsku operaciju {@link IzmeniKonobaraSO}.
     *
     * @param k2 konobar sa novim vrednostima
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void izmeniKonobara(Konobar k2) throws Exception {
        IzmeniKonobaraSO operacija = new IzmeniKonobaraSO();
        operacija.izvrsi(k2, null);
    }

    /**
     * Brise konobara.
     * Poziva sistemsku operaciju {@link ObrisiKonobaraSO}.
     *
     * @param k3 konobar koji se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiKonobara(Konobar k3) throws Exception {
        ObrisiKonobaraSO operacija = new ObrisiKonobaraSO();
        operacija.izvrsi(k3, null);
    }

    /**
     * Ucitava sve konobare.
     * Poziva sistemsku operaciju {@link UcitajKonobareSO}.
     *
     * @return lista konobara
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<Konobar> ucitajKonobare() throws Exception {
        UcitajKonobareSO operacija = new UcitajKonobareSO();
        operacija.izvrsi(null, null);
        return operacija.getKonobari();
    }

    /**
     * Ucitava stavke za dati racun.
     * Poziva sistemsku operaciju {@link UcitajStavkeRacunaSO}.
     *
     * @param p racun cije se stavke ucitavaju
     * @return lista stavki racuna
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<StavkaRacuna> ucitajStavke(Racun p) throws Exception {

        UcitajStavkeRacunaSO operacija = new UcitajStavkeRacunaSO();
        operacija.izvrsi(p, null);
        System.out.println("KLASA CONTROLLER: "+operacija.getStavke());
        return operacija.getStavke();

    }

    /**
     * Ucitava sve smene.
     * Poziva sistemsku operaciju {@link UcitajSmeneSO}.
     *
     * @return lista smena
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<Smena> ucitajSmene() throws Exception {

        UcitajSmeneSO operacija = new UcitajSmeneSO();
        operacija.izvrsi(null, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getSmene());
        return operacija.getSmene();
    }

    /**
     * Dodaje novu smenu.
     * Poziva sistemsku operaciju {@link DodajSmenuSO}.
     *
     * @param s1 smena koja se dodaje
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void dodajSmenu(Smena s1) throws Exception {
        System.out.println("[CONTROLLER] Pozvan dodajSmenu: " + s1);
        DodajSmenuSO operacija = new DodajSmenuSO();
        operacija.izvrsi(s1, null);
    }

    /**
     * Menja podatke o smeni.
     * Poziva sistemsku operaciju {@link IzmeniSmenuSO}.
     *
     * @param s2 smena sa novim vrednostima
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void izmeniSmenu(Smena s2) throws Exception {
        IzmeniSmenuSO operacija = new IzmeniSmenuSO();
        operacija.izvrsi(s2, null);
    }



    /**
     * Brise smenu.
     * Poziva sistemsku operaciju {@link ObrisiSmenuSO}.
     *
     * @param s smena koja se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiSmenu(Smena s) throws Exception {
        ObrisiSmenuSO operacija = new ObrisiSmenuSO();
        operacija.izvrsi(s, null);
    }

    /**
     * Ucitava raspored (veze konobar–smena).
     * Poziva sistemsku operaciju {@link UcitajRasporedSO}.
     *
     * @return lista rasporeda
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public List<KonobarSmena> ucitajRaspored() throws Exception {
        UcitajRasporedSO operacija = new UcitajRasporedSO();
        operacija.izvrsi(null, null);

        System.out.println("[CONTROLLER] Učitano iz baze rasporeda: " + operacija.getLista().size());
        return operacija.getLista();
    }




    /**
     * Dodaje novi unos u raspored.
     * Poziva sistemsku operaciju {@link DodajRasporedSO}.
     *
     * @param ks veza konobar–smena koja se dodaje
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void dodajRaspored(KonobarSmena ks) throws Exception {
        DodajRasporedSO operacija = new DodajRasporedSO();
        operacija.izvrsi(ks, null);
    }

    /**
     * Menja unos u rasporedu.
     * Poziva sistemsku operaciju {@link IzmeniRasporedSO}.
     *
     * @param ks veza konobar–smena sa novim vrednostima
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void izmeniRaspored(KonobarSmena ks) throws Exception {
        IzmeniRasporedSO operacija = new IzmeniRasporedSO();
        operacija.izvrsi(ks, null);
    }

    /**
     * Brise unos iz rasporeda.
     * Poziva sistemsku operaciju {@link ObrisiRasporedSO}.
     *
     * @param ks veza konobar–smena koja se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiRaspored(KonobarSmena ks) throws Exception {
        ObrisiRasporedSO operacija = new ObrisiRasporedSO();
        operacija.izvrsi(ks, null);
    }

    /**
     * Brise racun.
     * Poziva sistemsku operaciju {@link ObrisiRacunSO}.
     *
     * @param r racun koji se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiRacun(Racun r) throws Exception {

        ObrisiRacunSO operacija = new ObrisiRacunSO();
        operacija.izvrsi(r,null);

    }

    /**
     * Dodaje novi racun.
     * Poziva sistemsku operaciju {@link DodajRacunSO}.
     *
     * @param r racun koji se dodaje
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void dodajRacun(Racun r) throws Exception {

        DodajRacunSO operacija = new DodajRacunSO();
        operacija.izvrsi(r,null);

    }

    /**
     * Brise stavku racuna.
     * Poziva sistemsku operaciju {@link ObrisiStavkuRacunaSO}.
     *
     * @param sr stavka koja se brise
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void obrisiStavkuRacuna(StavkaRacuna sr) throws Exception {
        ObrisiStavkuRacunaSO operacija = new ObrisiStavkuRacunaSO();
        operacija.izvrsi(sr,null);
    }

    /**
     * Menja podatke o racunu.
     * Poziva sistemsku operaciju {@link IzmeniRacunSO}.
     *
     * @param r racun sa novim vrednostima
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public void izmeniRacun(Racun r) throws Exception {

        IzmeniRacunSO operacija = new IzmeniRacunSO();
        operacija.izvrsi(r,null);

    }

    /**
     * Izvozi listu racuna u JSON fajl.
     * Poziva sistemsku operaciju {@link IzveziRacuneUJsonSO}.
     *
     * @param racuni lista racuna za izvoz
     * @return apsolutna putanja kreiranog JSON fajla
     * @throws Exception ako dodje do greske pri izvrsavanju operacije
     */
    public String izveziRacune(List<Racun> racuni) throws Exception {
        IzveziRacuneUJsonSO operacija = new IzveziRacuneUJsonSO();
        operacija.izvrsi(racuni, null);
        return operacija.getPutanjaFajla();
    }






}
