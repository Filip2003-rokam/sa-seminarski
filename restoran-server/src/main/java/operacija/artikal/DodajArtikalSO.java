package operacija.artikal;

import domen.Artikal;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje novog artikla u sistem.
 * Radi nad domen klasom {@link Artikal}. Preduslovi zahtevaju da objekat bude
 * tipa Artikal. Validacija atributa (naziv, tip, cena) je u setterima domen klase.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajArtikalSO extends ApstraktnaGenerickaOperacija {

    

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom.
     */
    public DodajArtikalSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom (za testiranje).
     *
     * @param broker repozitorijum za pristup podacima
     */
    public DodajArtikalSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za dodavanje artikla.
     *
     * @param param objekat koji mora biti tipa {@link Artikal}
     * @throws Exception ako je param null ili nije tipa {@link Artikal}
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa Artikal
        if (param == null || !(param instanceof Artikal)) {
            throw new Exception("Sistem nije mogao da doda artikal");
        }
    }

    /**
     * Dodaje novi artikal u bazu podataka.
     *
     * @param param objekat tipa {@link Artikal} koji se dodaje
     * @param kljuc dodatni uslov, nije koriscen
     * @throws Exception ako dodavanje u bazu ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((Artikal) param);
    }
}
