package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojece smene u bazi podataka.
 * Radi nad domenskom klasom {@link Smena}.
 * Preduslovi zahtevaju da parametar bude instanca Smena, da naziv ima
 * najmanje 3 karaktera, da vreme pocetka i vreme kraja budu uneti (odvojeno),
 * te da vreme kraja bude posle vremena pocetka (vremeKraja.isAfter(vremePocetka)).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class IzmeniSmenuSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public IzmeniSmenuSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public IzmeniSmenuSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za izmenu smene.
     *
     * @param param objekat koji mora biti tipa {@link Smena}
     * @throws Exception ako je param null ili nije Smena ("Sistem nije mogao da izmeni smenu."),
     *         ako naziv nije unet ili ima manje od 3 karaktera
     *         ("Naziv smene mora imati bar 3 karaktera."),
     *         ako vreme pocetka nije uneto ("Pocetak smene mora biti unet."),
     *         ako vreme kraja nije uneto ("Kraj smene mora biti unet."),
     *         ako vreme kraja nije posle vremena pocetka
     *         ("Kraj smene mora biti posle pocetka.")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da izmeni smenu.");
        }

        Smena s = (Smena) param;

        if (s.getNaziv() == null || s.getNaziv().isEmpty() || s.getNaziv().length() < 3) {
            throw new Exception("Naziv smene mora imati bar 3 karaktera.");
        }

        if (s.getVremePocetka()== null) {
            throw new Exception("Početak smene mora biti unet.");
        }

        if (s.getVremeKraja()== null) {
            throw new Exception("Kraj smene mora biti unet.");
        }

        if (!s.getVremeKraja().isAfter(s.getVremePocetka())) {
            throw new Exception("Kraj smene mora biti posle početka.");
        }
    }

    /**
     * Azurira postojecu smenu u bazi podataka preko brokera.
     *
     * @param param smena sa izmenjenim podacima
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako izmena u bazi ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.edit((Smena) param);
    }
}
