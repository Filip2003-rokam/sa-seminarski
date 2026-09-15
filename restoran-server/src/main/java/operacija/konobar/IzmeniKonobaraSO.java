package operacija.konobar;

import domen.Konobar;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojeceg konobara u bazi podataka.
 * Radi nad domenskom klasom {@link Konobar}.
 * Preduslovi zahtevaju da parametar bude instanca Konobar.
 * Validacija atributa (ime, prezime, korisnicko ime, sifra) je u setterima domen klase.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class IzmeniKonobaraSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public IzmeniKonobaraSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public IzmeniKonobaraSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za izmenu konobara.
     *
     * @param param objekat koji mora biti tipa {@link Konobar}
     * @throws Exception ako je param null ili nije Konobar ("Sistem nije mogao da izmeni konobara!")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Konobar)) {
            throw new Exception("Sistem nije mogao da izmeni konobara!");
        }
    }

    /**
     * Azurira postojeceg konobara u bazi podataka preko brokera.
     *
     * @param param konobar sa izmenjenim podacima
     * @param kljuc dodatni uslov operacije, ne koristi se
     * @throws Exception ako izmena u bazi ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.edit((Konobar) param);
    }
}
