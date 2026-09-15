package operacija.raspored;

import domen.KonobarSmena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje novog rasporeda (veze konobar-smena) u bazu.
 * Radi nad domenskom klasom {@link KonobarSmena}.
 * Preduslovi zahtevaju da parametar bude instanca KonobarSmena, te da budu
 * postavljeni konobar, smena i datum smene.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajRasporedSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public DodajRasporedSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public DodajRasporedSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za dodavanje rasporeda.
     *
     * @param param objekat koji mora biti tipa {@link KonobarSmena}
     * @throws Exception ako je param null ili nije KonobarSmena
     *         ("Sistem nije mogao da zapamti raspored."),
     *         ako konobar nije izabran ("Morate izabrati konobara."),
     *         ako smena nije izabrana ("Morate izabrati smenu."),
     *         ako datum smene nije izabran ("Morate izabrati datum smene.")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof KonobarSmena)) {
            throw new Exception("Sistem nije mogao da zapamti raspored.");
        }

        KonobarSmena ks = (KonobarSmena) param;

        if (ks.getKonobar() == null) {
            throw new Exception("Morate izabrati konobara.");
        }

        if (ks.getSmena() == null) {
            throw new Exception("Morate izabrati smenu.");
        }

        if (ks.getDatumSmene() == null) {
            throw new Exception("Morate izabrati datum smene.");
        }
    }

    /**
     * Dodaje novi raspored (KonobarSmena) u bazu podataka preko brokera.
     *
     * @param param raspored koji se dodaje
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako dodavanje u bazu ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((KonobarSmena) param);
    }
}
