package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje nove smene u bazu podataka.
 * Radi nad domenskom klasom {@link Smena}.
 * Preduslovi zahtevaju da parametar bude instanca Smena, da naziv ima
 * najmanje 3 karaktera, da vreme pocetka i kraja budu uneti, te da
 * vreme kraja bude posle vremena pocetka (vremeKraja.isAfter(vremePocetka)).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajSmenuSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public DodajSmenuSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public DodajSmenuSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za dodavanje smene.
     *
     * @param param objekat koji mora biti tipa {@link Smena}
     * @throws Exception ako je param null ili nije Smena ("Sistem nije mogao da doda smenu."),
     *         ako naziv nije unet ili ima manje od 3 karaktera
     *         ("Naziv smene mora imati bar 3 karaktera."),
     *         ako vreme pocetka ili kraja nije uneto
     *         ("Vreme pocetka i kraja moraju biti uneti."),
     *         ako vreme kraja nije posle vremena pocetka
     *         ("Kraj smene mora biti posle pocetka.")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        System.out.println("[SO] Pozvani preduslovi za dodavanje smene...");
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da doda smenu.");
        }

        Smena s = (Smena) param;
        System.out.println("[SO] Validacija: " + s);

        if (s.getNaziv() == null || s.getNaziv().isEmpty() || s.getNaziv().length() < 3) {
            throw new Exception("Naziv smene mora imati bar 3 karaktera.");
        }

        if (s.getVremePocetka() == null || s.getVremeKraja() == null) {
            throw new Exception("Vreme početka i kraja moraju biti uneti.");
        }

        if (!s.getVremeKraja().isAfter(s.getVremePocetka())) {
            throw new Exception("Kraj smene mora biti posle početka.");
        }
    }


    /**
     * Dodaje novu smenu u bazu podataka preko brokera.
     *
     * @param param smena koja se dodaje
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako dodavanje u bazu ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((Smena) param);
    }
}
