package operacija.konobar;

import domen.Konobar;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje novog konobara u bazu podataka.
 * Radi nad domenskom klasom {@link Konobar}.
 * Preduslovi zahtevaju da parametar bude instanca Konobar, da ime i prezime
 * imaju najmanje 2 karaktera, te da korisnicko ime i sifra nisu prazni.
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
     * @throws Exception ako je param null ili nije Konobar ("Sistem nije mogao da doda konobara!"),
     *         ako ime nije uneto ili ima manje od 2 karaktera ("GRESKA IME"),
     *         ako prezime nije uneto ili ima manje od 2 karaktera ("GRESKA PREZIME"),
     *         ako korisnicko ime nije uneto ("GRESKA USERNAME"),
     *         ako sifra nije uneta ("GRESKA PASSWORD")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Konobar)) {
            throw new Exception("Sistem nije mogao da doda konobara!");
        }

        Konobar k = (Konobar) param;

        if (k.getIme() == null || k.getIme().isEmpty() || k.getIme().length() < 2) {
            throw new Exception("GRESKA IME");
        }

        if (k.getPrezime() == null || k.getPrezime().isEmpty() || k.getPrezime().length() < 2) {
            throw new Exception("GRESKA PREZIME");
        }

        if (k.getKorisnickoIme()== null || k.getKorisnickoIme().isEmpty()) {
            throw new Exception("GRESKA USERNAME");
        }

        if (k.getSifra()== null || k.getSifra().isEmpty()) {
            throw new Exception("GRESKA PASSWORD");
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
