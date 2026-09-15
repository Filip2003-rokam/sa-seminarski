package operacija.stavke;

import domen.StavkaRacuna;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje jedne stavke racuna iz baze podataka.
 * Radi nad domenom {@link StavkaRacuna}. Poslovna pravila zahtevaju validan
 * objekat tipa StavkaRacuna, ispravan id racuna (&gt; 0), validan redni broj
 * stavke rb (&gt; 0), izabran artikal, kao i kolicinu, cenu i ukupan iznos
 * vece od nule.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiStavkuRacunaSO extends ApstraktnaGenerickaOperacija {



    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public ObrisiStavkuRacunaSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Omogucava testiranje bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public ObrisiStavkuRacunaSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje stavke racuna.
     *
     * @param param objekat koji mora biti instanca klase {@link StavkaRacuna}
     * @throws Exception ako je param null ili nije StavkaRacuna,
     *         ako id racuna nije ispravan (idRacun &lt;= 0),
     *         ako redni broj stavke nije validan (rb &lt;= 0),
     *         ako stavka nema izabran artikal,
     *         ako je kolicina manja ili jednaka nuli,
     *         ako je cena manja ili jednaka nuli,
     *         ako je ukupan iznos manji ili jednak nuli
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof StavkaRacuna)) {
            throw new Exception("Sistem ne može da obriše stavku jer prosleđeni objekat nije validan.");
        }

        StavkaRacuna sr = (StavkaRacuna) param;

        // 🔹 Proveri da li je postavljen id računa
        if (sr.getIdRacun() <= 0) {
            throw new Exception("Račun kojem pripada stavka nije ispravan.");
        }

        // 🔹 Proveri da li je postavljen redni broj stavke
        if (sr.getRb() <= 0) {
            throw new Exception("Stavka računa mora imati validan redni broj (rb).");
        }

        // 🔹 Proveri da li stavka ima vezano artikal
        if (sr.getArtikal() == null) {
            throw new Exception("Stavka računa mora imati izabran artikal.");
        }

        // 🔹 Proveri da li su količina i cena validne
        if (sr.getKolicina() <= 0) {
            throw new Exception("Količina stavke mora biti veća od nule.");
        }
        if (sr.getCena() <= 0) {
            throw new Exception("Cena stavke mora biti veća od nule.");
        }

        // 🔹 Proveri ukupan iznos
        if (sr.getUkupanIznos() <= 0) {
            throw new Exception("Ukupan iznos stavke mora biti veći od nule.");
        }
    }

    /**
     * Brise prosledjenu stavku racuna iz baze podataka pozivom {@code delete}.
     *
     * @param param stavka koja se brise, tipa {@link StavkaRacuna}
     * @param kljuc dodatni uslov; nije koriscen u ovoj operaciji
     * @throws Exception ako brisanje stavke ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        StavkaRacuna sr = (StavkaRacuna) param;
        broker.delete(sr);
    }



}
