package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojece smene u bazi podataka.
 * Radi nad domenskom klasom {@link Smena}.
 * Preduslovi zahtevaju da parametar bude instanca Smena i da vremenski
 * interval bude ispravan. Pojedinacne vrednosti atributa proveravaju setteri.
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
     * @throws Exception ako je param null ili nije Smena
     * @throws IllegalArgumentException ako neko vreme nije postavljeno ili su
     *         vreme pocetka i vreme kraja jednaki
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da izmeni smenu.");
        }
        ((Smena) param).proveriVremena();
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
