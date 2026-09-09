/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Cofara
 */
public class Komunikacija {
    
    private Socket soket;
    private Posiljalac posiljalac;
    private Primalac primalac;
    private static Komunikacija instanca;

    private Komunikacija() {
    }

    public static Komunikacija getInstance() {
        if (instanca == null) {
            instanca = new Komunikacija();
        }
        return instanca;
    }

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

    public List<StavkaRacuna> ucitajStavke(Racun p) {
        
        List<StavkaRacuna> stavke = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_STAVKE, p);
        posiljalac.posalji(zahtev);
        ///////////////
        
        Odgovor odg = (Odgovor) primalac.primi();
        stavke = (List<StavkaRacuna>) odg.getOdgovor();
        return stavke;
        
    }

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







    
}
