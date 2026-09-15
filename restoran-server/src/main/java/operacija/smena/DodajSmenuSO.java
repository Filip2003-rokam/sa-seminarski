package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje nove smene u bazu podataka.
 * Radi nad domenskom klasom {@link Smena}.
 * Preduslovi zahtevaju da parametar bude instanca Smena.
 * Validacija atributa (naziv, vremena, redosled) je u setterima domen klase.
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
     * @throws Exception ako je param null ili nije Smena ("Sistem nije mogao da doda smenu.")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        System.out.println("[SO] Pozvani preduslovi za dodavanje smene...");
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da doda smenu.");
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
