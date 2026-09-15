package operacija.racuni;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje novog racuna u bazu podataka.
 * Radi nad domenom {@link Racun} i njegovim stavkama {@link StavkaRacuna}.
 * Preduslovi zahtevaju validan objekat tipa Racun i listu sa barem jednom stavkom.
 * Validacija atributa (gost, konobar, datum, vreme, ukupanIznos, stavke) je u
 * setterima domen klase.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajRacunSO extends ApstraktnaGenerickaOperacija {

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public DodajRacunSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Omogucava testiranje bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public DodajRacunSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za dodavanje racuna.
     *
     * @param param objekat koji mora biti instanca klase {@link Racun}
     * @throws Exception ako je param null ili nije Racun,
     *         ako lista stavki ne postoji ili je prazna
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Racun)) {
            throw new Exception("Sistem ne može da doda račun jer prosleđeni objekat nije validan.");
        }

        Racun r = (Racun) param;

        // 🔹 Provera liste stavki
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora sadržati barem jednu stavku.");
        }
    }

    /**
     * Dodaje racun u bazu preko {@code addReturnKey}, zatim za svaku stavku
     * postavlja dobijeni id racuna i cuva je pozivom {@code add}.
     *
     * @param param racun koji se dodaje, tipa {@link Racun}
     * @param kljuc dodatni uslov; nije koriscen u ovoj operaciji
     * @throws Exception ako upis racuna ili bilo koje stavke ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        Racun r = (Racun) param;
        int idRacun = broker.addReturnKey(r);

        List<StavkaRacuna> stavke = r.getStavke();
        for (StavkaRacuna s : stavke) {
            s.setIdRacun(idRacun);
            broker.add(s);
        }


    }



}
