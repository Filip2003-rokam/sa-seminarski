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
 * Singleton koordinator koji upravlja otvaranjem formi klijentske aplikacije.
 * <p>
 * Cuva reference na kontrolere, ulogovanog konobara i mapu parametara
 * koji se prosledjuju izmedju formi.
 * </p>
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class Cordinator {
    /** Jedina instanca singleton klase. */
    private static Cordinator instanca;
    /** Kontroler login forme. */
    private LoginController loginController;
    /** Kontroler glavne forme. */
    private GlavnaFormaController glavnaFormaController;
    /** Kontroler forme za prikaz gostiju. */
    private PrikazGostijuController prikazGostijuController;
    /** Kontroler forme za dodavanje/izmenu gosta. */
    private DodajGostaController dodajGostaController;
    
    /** Trenutno ulogovani konobar. */
    private Konobar ulogovani;
    
    /** Mapa parametara za razmenu podataka izmedju formi. */
    private Map<String,Object> parametri;
    
    /** Kontroler forme za prikaz racuna. */
    private PrikazRacunaController prikazRacunaController;
    /** Kontroler forme za dodavanje/izmenu racuna. */
    private DodajRacunController dodajRacunController;
    
    /** Kontroler forme za prikaz artikala. */
    private PrikazArtikalaController prikazArtikalaController;
    /** Kontroler forme za dodavanje/izmenu artikla. */
    private DodajArtikalController dodajArtikalController;
    
    /** Kontroler forme za prikaz kategorija gostiju. */
    private PrikazKategorijeGostijuController prikazKategorijeGostijuController;
    /** Kontroler forme za dodavanje/izmenu kategorije gosta. */
    private DodajKategorijuGostaController dodajKategorijuGostaController;
    
    /** Kontroler forme za dodavanje/izmenu konobara. */
    private DodajKonobaraController dodajKonobaraController;
    /** Kontroler forme za prikaz konobara. */
    private PrikazKonobaraController prikazKonobaraController;
    
    /** Kontroler forme za prikaz smena. */
    private PrikazSmenaController prikazSmenaController;
    /** Kontroler forme za dodavanje/izmenu smene. */
    private DodajSmenuController dodajSmenuController;
    
    /** Kontroler forme za prikaz rasporeda. */
    private PrikazRasporedaController prikazRasporedaController;
    /** Kontroler forme za dodavanje/izmenu rasporeda. */
    private DodajRasporedController dodajRasporedController;
    
    /** Kontroler forme za detalje gosta. */
    private DetaljiGostaController detaljiGostaController;

    /**
     * Privatni konstruktor koji inicijalizuje mapu parametara.
     */
    private Cordinator() {
        
        parametri = new HashMap<>();
        
    }

    /**
     * Vraca jedinu instancu klase Cordinator.
     *
     * @return singleton instanca
     */
    public static Cordinator getInstance() {
        if (instanca == null) {
            instanca = new Cordinator();
        }
        return instanca;
    }

    /**
     * Otvara {@link LoginForma} za prijavu konobara.
     */
    public void otvoriLoginFormu() {
        
        loginController = new LoginController(new LoginForma());
        loginController.otvoriFormu();
        
    }

    /**
     * Otvara {@link GlavnaForma} glavni meni aplikacije.
     */
    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }
    
     /**
      * Otvara {@link PrikazGostijuForma} za prikaz liste gostiju.
      */
     public void otvoriPrikazGostijuFormu() {
        prikazGostijuController = new PrikazGostijuController(new PrikazGostijuForma());
        prikazGostijuController.otvoriFormu();
    }
    

    /**
     * Vraca trenutno ulogovanog konobara.
     *
     * @return ulogovani {@link Konobar}
     */
    public Konobar getUlogovani() {
        return ulogovani;
    }

    /**
     * Postavlja trenutno ulogovanog konobara.
     *
     * @param ulogovani konobar koji je uspesno prijavljen
     */
    public void setUlogovani(Konobar ulogovani) {
        this.ulogovani = ulogovani;
    }

    

    /**
     * Osvezava tabelu gostiju na formi za prikaz gostiju.
     */
    public void osveziTabeluGostiju() {
        
        prikazGostijuController.pripremiFormu();
        
    }

   
    /**
     * Dodaje parametar u mapu za razmenu podataka izmedju formi.
     *
     * @param s kljuc parametra
     * @param o vrednost parametra
     */
    public void dodajParam(String s, Object o) {
        parametri.put(s, o);
    }

    /**
     * Vraca parametar iz mape po kljucu.
     *
     * @param s kljuc parametra
     * @return vrednost parametra, ili {@code null} ako ne postoji
     */
    public Object vratiParam(String s) {
        return parametri.get(s);
    }

    /**
     * Otvara {@link DodajGostaForma} u rezimu {@link FormaMod#IZMENI}.
     */
    public void otvoriIzmeniGostaFormu() {
        
        dodajGostaController = new DodajGostaController(new DodajGostaForma());
        dodajGostaController.otvoriFormu(FormaMod.IZMENI);
        
    }
    
    /**
     * Otvara {@link DodajGostaForma} u rezimu {@link FormaMod#DODAJ}.
     */
    public void otvoriDodajGostaFormu() {
        dodajGostaController = new DodajGostaController(new DodajGostaForma());
        dodajGostaController.otvoriFormu(FormaMod.DODAJ);
    }

    /**
     * Osvezava prikaz forme gostiju.
     */
    public void osveziFormu() {

        prikazGostijuController.osveziPrikazGostijuFormu();

        //prikazArtikalaController.osveziPrikazArtikalaFormu();

        //prikazKategorijeGostijuController.osveziPrikazKategorijaGostijuFormu();

        //prikazKonobaraController.osveziPrikazKonobaraFormu();

}

    
    /**
     * Otvara {@link PrikazRacunaForma} za prikaz liste racuna.
     */
    public void otvoriPrikazRacunaFormu() {
        prikazRacunaController = new PrikazRacunaController(new PrikazRacunaForma());
        prikazRacunaController.otvoriFormu();
    }

    /**
     * Otvara {@link PrikazArtikalaForma} za prikaz liste artikala.
     */
    public void otvoriPrikazArtikalaFormu() {
        prikazArtikalaController = new PrikazArtikalaController(new PrikazArtikalaForma());
        prikazArtikalaController.otvoriFormu();
    }

    /**
     * Otvara {@link DodajArtikalForma} u rezimu {@link FormaMod#DODAJ}.
     */
    public void otvoriDodajArtikalFormu() {
        dodajArtikalController = new DodajArtikalController(new DodajArtikalForma());
        dodajArtikalController.otvoriFormu(FormaMod.DODAJ);
    }

    /**
     * Otvara {@link DodajArtikalForma} u rezimu {@link FormaMod#IZMENI}.
     */
    public void otvoriIzmeniArtikalFormu() {
        dodajArtikalController = new DodajArtikalController(new DodajArtikalForma());
        dodajArtikalController.otvoriFormu(FormaMod.IZMENI);
    }

    /**
     * Otvara {@link PrikazKategorijeGostijuForma} za prikaz kategorija gostiju.
     */
    public void otvoriPrikazKategorijaGostijuFormu() {
        prikazKategorijeGostijuController = new PrikazKategorijeGostijuController(new PrikazKategorijeGostijuForma());
        prikazKategorijeGostijuController.otvoriFormu();
    }

    /**
     * Otvara {@link DodajKategorijuGostaForma} u rezimu {@link FormaMod#DODAJ}.
     */
    public void otvoriDodajKategorijuGostaFormu() {
        dodajKategorijuGostaController = new DodajKategorijuGostaController(new DodajKategorijuGostaForma());
        dodajKategorijuGostaController.otvoriFormu(FormaMod.DODAJ);
    }

    /**
     * Otvara {@link DodajKategorijuGostaForma} u rezimu {@link FormaMod#IZMENI}.
     */
    public void otvoriIzmeniKategorijuGostaFormu() {
        dodajKategorijuGostaController = new DodajKategorijuGostaController(new DodajKategorijuGostaForma());
        dodajKategorijuGostaController.otvoriFormu(FormaMod.IZMENI);
    }

    /**
     * Otvara {@link PrikazKonobaraForma} za prikaz liste konobara.
     */
    public void otvoriPrikazKonobaraFormu() {
        prikazKonobaraController = new PrikazKonobaraController(new PrikazKonobaraForma());
        prikazKonobaraController.otvoriFormu();
    }

    /**
     * Otvara {@link DodajKonobaraForma} u rezimu {@link FormaMod#DODAJ}.
     */
    public void otvoriDodajKonobaraFormu() {
        dodajKonobaraController = new DodajKonobaraController(new DodajKonobaraForma());
        dodajKonobaraController.otvoriFormu(FormaMod.DODAJ);
    }

    /**
     * Otvara {@link DodajKonobaraForma} u rezimu {@link FormaMod#IZMENI}.
     */
    public void otvoriIzmeniKonobaraFormu() {
        dodajKonobaraController = new DodajKonobaraController(new DodajKonobaraForma());
        dodajKonobaraController.otvoriFormu(FormaMod.IZMENI);
    }

    /**
     * Otvara {@link PrikazSmenaForma} za prikaz liste smena.
     */
    public void otvoriPrikazSmenaFormu() {
        prikazSmenaController = new PrikazSmenaController(new PrikazSmenaForma());
        prikazSmenaController.otvoriFormu();
    }

    /**
     * Otvara {@link DodajSmenuForma} u rezimu {@link FormaMod#DODAJ}.
     */
    public void otvoriDodajSmenuFormu() {
        dodajSmenuController = new DodajSmenuController(new DodajSmenuForma());
        dodajSmenuController.otvoriFormu(FormaMod.DODAJ);
    }

    /**
     * Otvara {@link DodajSmenuForma} u rezimu {@link FormaMod#IZMENI}.
     */
    public void otvoriIzmeniSmenuFormu() {
        dodajSmenuController = new DodajSmenuController(new DodajSmenuForma());
        dodajSmenuController.otvoriFormu(FormaMod.IZMENI);
    }

    /**
     * Otvara {@link PrikazRasporedaForma} za prikaz rasporeda rada.
     */
    public void otvoriPrikazRasporedaFormu() {
        
        prikazRasporedaController = new PrikazRasporedaController(new PrikazRasporedaForma());
        prikazRasporedaController.otvoriFormu();
        
        
        
    }
    
    /**
     * Otvara {@link DodajRasporedForma} u rezimu {@link FormaMod#DODAJ}.
     */
    public void otvoriDodajRasporedFormu() {
        dodajRasporedController = new DodajRasporedController(new DodajRasporedForma());
        dodajRasporedController.otvoriFormu(FormaMod.DODAJ);
    }

    /**
     * Otvara {@link DodajRasporedForma} u rezimu {@link FormaMod#IZMENI}.
     */
    public void otvoriIzmeniRasporedFormu() {
        dodajRasporedController = new DodajRasporedController(new DodajRasporedForma());
        dodajRasporedController.otvoriFormu(FormaMod.IZMENI);
}

    /**
     * Osvezava prikaz forme gostiju nakon izmene gosta.
     */
    public void osveziFormuGosta() {
        prikazGostijuController.osveziPrikazGostijuFormu();
    }

    /**
     * Osvezava prikaz forme artikala nakon izmene artikla.
     */
    public void osveziFormuArtikal() {
        prikazArtikalaController.osveziPrikazArtikalaFormu();
    }

    /**
     * Otvara {@link DodajRacunForma} u rezimu {@link FormaMod#DODAJ}.
     */
    public void otvoriDodajRacunFormu() {
        dodajRacunController = new DodajRacunController(new DodajRacunForma());
        dodajRacunController.otvoriFormu(FormaMod.DODAJ);
    }

    /**
     * Otvara {@link DodajRacunForma} u rezimu {@link FormaMod#IZMENI}.
     */
    public void otvoriIzmeniRacunFormu() {
        dodajRacunController = new DodajRacunController(new DodajRacunForma());
        dodajRacunController.otvoriFormu(FormaMod.IZMENI);
    }

    /**
     * Otvara {@link DetaljiGostaForma} za prikaz detalja izabranog gosta.
     */
    public void otvoriDetaljiGostaFormu() {
        detaljiGostaController = new DetaljiGostaController(new DetaljiGostaForma());
        detaljiGostaController.otvoriFormu();
    }






    
}
