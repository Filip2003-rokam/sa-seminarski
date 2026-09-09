/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cordinator;

import domen.Konobar;
import forme.DetaljiGostaForma;
import forme.DodajGostaForma;
import forme.DodajArtikalForma;
import forme.DodajKategorijuGostaForma;
import forme.DodajKonobaraForma;
import forme.DodajRacunForma;
import forme.DodajRasporedForma;
import forme.DodajSmenuForma;
import forme.FormaMod;
import forme.GlavnaForma;
import forme.LoginForma;
import forme.PrikazGostijuForma;
import forme.PrikazArtikalaForma;
import forme.PrikazKategorijeGostijuForma;
import forme.PrikazKonobaraForma;
import forme.PrikazRacunaForma;
import forme.PrikazRasporedaForma;
import forme.PrikazSmenaForma;
import java.util.HashMap;
import java.util.Map;
import kontroleri.DetaljiGostaController;
import kontroleri.DodajGostaController;
import kontroleri.DodajArtikalController;
import kontroleri.DodajKategorijuGostaController;
import kontroleri.DodajKonobaraController;
import kontroleri.DodajRacunController;
import kontroleri.DodajRasporedController;
import kontroleri.DodajSmenuController;
import kontroleri.GlavnaFormaController;
import kontroleri.LoginController;
import kontroleri.PrikazGostijuController;
import kontroleri.PrikazArtikalaController;
import kontroleri.PrikazKategorijeGostijuController;
import kontroleri.PrikazKonobaraController;
import kontroleri.PrikazRacunaController;
import kontroleri.PrikazRasporedaController;
import kontroleri.PrikazSmenaController;

/**
 *
 * @author Cofara
 */
public class Cordinator {
    private static Cordinator instanca;
    private LoginController loginController;
    private GlavnaFormaController glavnaFormaController;
    private PrikazGostijuController prikazGostijuController;
    private DodajGostaController dodajGostaController;
    
    private Konobar ulogovani;
    
    private Map<String,Object> parametri;
    
    private PrikazRacunaController prikazRacunaController;
    private DodajRacunController dodajRacunController;
    
    private PrikazArtikalaController prikazArtikalaController;
    private DodajArtikalController dodajArtikalController;
    
    private PrikazKategorijeGostijuController prikazKategorijeGostijuController;
    private DodajKategorijuGostaController dodajKategorijuGostaController;
    
    private DodajKonobaraController dodajKonobaraController;
    private PrikazKonobaraController prikazKonobaraController;
    
    private PrikazSmenaController prikazSmenaController;
    private DodajSmenuController dodajSmenuController;
    
    private PrikazRasporedaController prikazRasporedaController;
    private DodajRasporedController dodajRasporedController;
    
    private DetaljiGostaController detaljiGostaController;

    private Cordinator() {
        
        parametri = new HashMap<>();
        
    }

    public static Cordinator getInstance() {
        if (instanca == null) {
            instanca = new Cordinator();
        }
        return instanca;
    }

    public void otvoriLoginFormu() {
        
        loginController = new LoginController(new LoginForma());
        loginController.otvoriFormu();
        
    }

    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }
    
     public void otvoriPrikazGostijuFormu() {
        prikazGostijuController = new PrikazGostijuController(new PrikazGostijuForma());
        prikazGostijuController.otvoriFormu();
    }
    

    public Konobar getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Konobar ulogovani) {
        this.ulogovani = ulogovani;
    }

    

    public void osveziTabeluGostiju() {
        
        prikazGostijuController.pripremiFormu();
        
    }

   
    public void dodajParam(String s, Object o) {
        parametri.put(s, o);
    }

    public Object vratiParam(String s) {
        return parametri.get(s);
    }

    public void otvoriIzmeniGostaFormu() {
        
        dodajGostaController = new DodajGostaController(new DodajGostaForma());
        dodajGostaController.otvoriFormu(FormaMod.IZMENI);
        
    }
    
    public void otvoriDodajGostaFormu() {
        dodajGostaController = new DodajGostaController(new DodajGostaForma());
        dodajGostaController.otvoriFormu(FormaMod.DODAJ);
    }

    public void osveziFormu() {

        prikazGostijuController.osveziPrikazGostijuFormu();

        //prikazArtikalaController.osveziPrikazArtikalaFormu();

        //prikazKategorijeGostijuController.osveziPrikazKategorijaGostijuFormu();

        //prikazKonobaraController.osveziPrikazKonobaraFormu();

}

    
    public void otvoriPrikazRacunaFormu() {
        prikazRacunaController = new PrikazRacunaController(new PrikazRacunaForma());
        prikazRacunaController.otvoriFormu();
    }

    public void otvoriPrikazArtikalaFormu() {
        prikazArtikalaController = new PrikazArtikalaController(new PrikazArtikalaForma());
        prikazArtikalaController.otvoriFormu();
    }

    public void otvoriDodajArtikalFormu() {
        dodajArtikalController = new DodajArtikalController(new DodajArtikalForma());
        dodajArtikalController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriIzmeniArtikalFormu() {
        dodajArtikalController = new DodajArtikalController(new DodajArtikalForma());
        dodajArtikalController.otvoriFormu(FormaMod.IZMENI);
    }

    public void otvoriPrikazKategorijaGostijuFormu() {
        prikazKategorijeGostijuController = new PrikazKategorijeGostijuController(new PrikazKategorijeGostijuForma());
        prikazKategorijeGostijuController.otvoriFormu();
    }

    public void otvoriDodajKategorijuGostaFormu() {
        dodajKategorijuGostaController = new DodajKategorijuGostaController(new DodajKategorijuGostaForma());
        dodajKategorijuGostaController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriIzmeniKategorijuGostaFormu() {
        dodajKategorijuGostaController = new DodajKategorijuGostaController(new DodajKategorijuGostaForma());
        dodajKategorijuGostaController.otvoriFormu(FormaMod.IZMENI);
    }

    public void otvoriPrikazKonobaraFormu() {
        prikazKonobaraController = new PrikazKonobaraController(new PrikazKonobaraForma());
        prikazKonobaraController.otvoriFormu();
    }

    public void otvoriDodajKonobaraFormu() {
        dodajKonobaraController = new DodajKonobaraController(new DodajKonobaraForma());
        dodajKonobaraController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriIzmeniKonobaraFormu() {
        dodajKonobaraController = new DodajKonobaraController(new DodajKonobaraForma());
        dodajKonobaraController.otvoriFormu(FormaMod.IZMENI);
    }

    public void otvoriPrikazSmenaFormu() {
        prikazSmenaController = new PrikazSmenaController(new PrikazSmenaForma());
        prikazSmenaController.otvoriFormu();
    }

    public void otvoriDodajSmenuFormu() {
        dodajSmenuController = new DodajSmenuController(new DodajSmenuForma());
        dodajSmenuController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriIzmeniSmenuFormu() {
        dodajSmenuController = new DodajSmenuController(new DodajSmenuForma());
        dodajSmenuController.otvoriFormu(FormaMod.IZMENI);
    }

    public void otvoriPrikazRasporedaFormu() {
        
        prikazRasporedaController = new PrikazRasporedaController(new PrikazRasporedaForma());
        prikazRasporedaController.otvoriFormu();
        
        
        
    }
    
    public void otvoriDodajRasporedFormu() {
        dodajRasporedController = new DodajRasporedController(new DodajRasporedForma());
        dodajRasporedController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriIzmeniRasporedFormu() {
        dodajRasporedController = new DodajRasporedController(new DodajRasporedForma());
        dodajRasporedController.otvoriFormu(FormaMod.IZMENI);
}

    public void osveziFormuGosta() {
        prikazGostijuController.osveziPrikazGostijuFormu();
    }

    public void osveziFormuArtikal() {
        prikazArtikalaController.osveziPrikazArtikalaFormu();
    }

    public void otvoriDodajRacunFormu() {
        dodajRacunController = new DodajRacunController(new DodajRacunForma());
        dodajRacunController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriIzmeniRacunFormu() {
        dodajRacunController = new DodajRacunController(new DodajRacunForma());
        dodajRacunController.otvoriFormu(FormaMod.IZMENI);
    }

    public void otvoriDetaljiGostaFormu() {
        detaljiGostaController = new DetaljiGostaController(new DetaljiGostaForma());
        detaljiGostaController.otvoriFormu();
    }







    
}
