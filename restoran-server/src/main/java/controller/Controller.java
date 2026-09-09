/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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


public class Controller {
    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Konobar login(Konobar z) throws Exception {
        LoginOperacija operacija = new LoginOperacija();
        operacija.izvrsi(z, null);
        
        System.out.println("KLASA CONTROLLER: " + operacija.getKonobar());
        
        return operacija.getKonobar();
    }

    public List<Gost> ucitajGoste() throws Exception{
        
        UcitajGosteSO operacija = new UcitajGosteSO();
        operacija.izvrsi(null, null);
        
        System.out.println("KLASA CONTROLLER: " + operacija.getGosti());
        
        return operacija.getGosti();
    }

    public void obrisiGosta(Gost g) throws Exception {
        
        ObrisiGostaSO operacija = new ObrisiGostaSO();
        operacija.izvrsi(g, null);
        
    }

    public void dodajGosta(Gost g) throws Exception{
        
        DodajGostaSO operacija = new DodajGostaSO();
        operacija.izvrsi(g, null);
        
    }

    public void izmeniGosta(Gost g) throws Exception {
        
        IzmeniGostaSO operacija = new IzmeniGostaSO();
        operacija.izvrsi(g, null);
        
    }

    public List<Racun> ucitajRacune() throws Exception {
        
        UcitajRacuneSO operacija = new UcitajRacuneSO();
        operacija.izvrsi(null, null);
        System.out.println("KLASA CONTROLLER :" + operacija.getRacuni());
        return operacija.getRacuni();

        
    }

    public List<Artikal> ucitajArtikle() throws Exception {
        
        UcitajArtikleSO operacija = new UcitajArtikleSO();
        operacija.izvrsi(null, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getArtikli());

        return operacija.getArtikli();
    }
    
    public void obrisiArtikal(Artikal artikal) throws Exception {
        ObrisiArtikalSO operacija = new ObrisiArtikalSO();
        operacija.izvrsi(artikal, null);
    }

    public void dodajArtikal(Artikal artikal) throws Exception {
        DodajArtikalSO operacija = new DodajArtikalSO();
        operacija.izvrsi(artikal, null);
    }

    public void izmeniArtikal(Artikal artikal) throws Exception {
        IzmeniArtikalSO operacija = new IzmeniArtikalSO();
        operacija.izvrsi(artikal, null);
    }

    public List<KategorijaGosta> ucitajKategorijeGostiju() throws Exception {
        
        UcitajKategorijeGostijuSO operacija = new UcitajKategorijeGostijuSO();
        operacija.izvrsi(null, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getKategorije());

        return operacija.getKategorije();
        
    }

    public void dodajKategorijuGosta(KategorijaGosta kg1) throws Exception {
        DodajKategorijuGostaSO operacija = new DodajKategorijuGostaSO();
        operacija.izvrsi(kg1, null);
    }

    public void izmeniKategorijuGosta(KategorijaGosta kg2) throws Exception {
        IzmeniKategorijuGostaSO operacija = new IzmeniKategorijuGostaSO();
        operacija.izvrsi(kg2, null);
    }

    public void obrisiKategorijuGosta(KategorijaGosta kg3) throws Exception {
        ObrisiKategorijuGostaSO operacija = new ObrisiKategorijuGostaSO();
        operacija.izvrsi(kg3, null);
    }

    public void dodajKonobara(Konobar k1) throws Exception {
        DodajKonobaraSO operacija = new DodajKonobaraSO();
        operacija.izvrsi(k1, null);
    }

    public void izmeniKonobara(Konobar k2) throws Exception {
        IzmeniKonobaraSO operacija = new IzmeniKonobaraSO();
        operacija.izvrsi(k2, null);
    }

    public void obrisiKonobara(Konobar k3) throws Exception {
        ObrisiKonobaraSO operacija = new ObrisiKonobaraSO();
        operacija.izvrsi(k3, null);
    }

    public List<Konobar> ucitajKonobare() throws Exception {
        UcitajKonobareSO operacija = new UcitajKonobareSO();
        operacija.izvrsi(null, null);
        return operacija.getKonobari();
    }

    public List<StavkaRacuna> ucitajStavke(Racun p) throws Exception {
        
        UcitajStavkeRacunaSO operacija = new UcitajStavkeRacunaSO();
        operacija.izvrsi(p, null);
        System.out.println("KLASA CONTROLLER: "+operacija.getStavke());
        return operacija.getStavke();
        
    }

    public List<Smena> ucitajSmene() throws Exception {

        UcitajSmeneSO operacija = new UcitajSmeneSO();
        operacija.izvrsi(null, null);

        System.out.println("KLASA CONTROLLER: " + operacija.getSmene());
        return operacija.getSmene();
    }

    public void dodajSmenu(Smena s1) throws Exception {
        System.out.println("[CONTROLLER] Pozvan dodajSmenu: " + s1);
        DodajSmenuSO operacija = new DodajSmenuSO();
        operacija.izvrsi(s1, null);
    }

    public void izmeniSmenu(Smena s2) throws Exception {
        IzmeniSmenuSO operacija = new IzmeniSmenuSO();
        operacija.izvrsi(s2, null);
    }



    public void obrisiSmenu(Smena s) throws Exception {
        ObrisiSmenuSO operacija = new ObrisiSmenuSO();
        operacija.izvrsi(s, null);
    }

    public List<KonobarSmena> ucitajRaspored() throws Exception {
        UcitajRasporedSO operacija = new UcitajRasporedSO();
        operacija.izvrsi(null, null);

        System.out.println("[CONTROLLER] Učitano iz baze rasporeda: " + operacija.getLista().size());
        return operacija.getLista();
    }




    public void dodajRaspored(KonobarSmena ks) throws Exception {
        DodajRasporedSO operacija = new DodajRasporedSO();
        operacija.izvrsi(ks, null);
    }

    public void izmeniRaspored(KonobarSmena ks) throws Exception {
        IzmeniRasporedSO operacija = new IzmeniRasporedSO();
        operacija.izvrsi(ks, null);
    }

    public void obrisiRaspored(KonobarSmena ks) throws Exception {
        ObrisiRasporedSO operacija = new ObrisiRasporedSO();
        operacija.izvrsi(ks, null);
    }

    public void obrisiRacun(Racun r) throws Exception {
        
        ObrisiRacunSO operacija = new ObrisiRacunSO();
        operacija.izvrsi(r,null);
        
    }

    public void dodajRacun(Racun r) throws Exception {
        
        DodajRacunSO operacija = new DodajRacunSO();
        operacija.izvrsi(r,null);
        
    }

    public void obrisiStavkuRacuna(StavkaRacuna sr) throws Exception {
        ObrisiStavkuRacunaSO operacija = new ObrisiStavkuRacunaSO();
        operacija.izvrsi(sr,null);
    }

    public void izmeniRacun(Racun r) throws Exception {
        
        IzmeniRacunSO operacija = new IzmeniRacunSO();
        operacija.izvrsi(r,null);
        
    }
    
    



}
