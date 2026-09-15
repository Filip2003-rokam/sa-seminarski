package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje smene iz baze podataka.
 * Radi nad domenskom klasom {@link Smena}.
 * Preduslovi zahtevaju da parametar bude instanca Smena i da idSmena bude
 * veci od nule.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiSmenuSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public ObrisiSmenuSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public ObrisiSmenuSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje smene.
     *
     * @param param objekat koji mora biti tipa {@link Smena}
     * @throws Exception ako je param null ili nije Smena
     *         ("Sistem nije mogao da obrise smenu - neispravan parametar."),
     *         ako idSmena nije veci od nule
     *         ("Smena nema ispravan ID i ne moze biti obrisana.")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // Validacija ulaznog parametra
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da obriše smenu - neispravan parametar.");
        }

        Smena s = (Smena) param;
        if (s.getIdSmena() <= 0) {
            throw new Exception("Smena nema ispravan ID i ne može biti obrisana.");
        }
    }

    /**
     * Brise smenu iz baze podataka preko brokera.
     *
     * @param param smena koja se brise
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako brisanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        // Poziv ka brokeru baze podataka za brisanje smene
        broker.delete((Smena) param);
    }
}
