package operacija.konobar;

import domen.Konobar;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje novog konobara u bazu podataka.
 * Radi nad domenskom klasom {@link Konobar}.
 * Preduslovi zahtevaju da parametar bude instanca Konobar.
 * Validacija atributa (ime, prezime, korisnicko ime, sifra) je u setterima domen klase.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajKonobaraSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public DodajKonobaraSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public DodajKonobaraSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za dodavanje konobara.
     *
     * @param param objekat koji mora biti tipa {@link Konobar}
     * @throws Exception ako je param null ili nije Konobar ("Sistem nije mogao da doda konobara!")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Konobar)) {
            throw new Exception("Sistem nije mogao da doda konobara!");
        }
    }

    /**
     * Dodaje novog konobara u bazu podataka preko brokera.
     *
     * @param param konobar koji se dodaje
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako dodavanje u bazu ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((Konobar) param);
    }
}
