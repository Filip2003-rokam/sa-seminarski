package operacija.racuni;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje novog racuna u bazu podataka.
 * Radi nad domenom {@link Racun} i njegovim stavkama {@link StavkaRacuna}.
 * Poslovna pravila zahtevaju validan objekat tipa Racun, izabranog gosta i
 * konobara, unet datum i vreme izdavanja, listu sa barem jednom stavkom,
 * za svaku stavku artikal i vrednosti kolicine, cene i ukupnog iznosa vece
 * od nule, kao i ukupan iznos racuna veci od nule.
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
     *         ako racun nema izabranog gosta,
     *         ako racun nema izabranog konobara,
     *         ako nije unet datum izdavanja,
     *         ako nije uneto vreme izdavanja,
     *         ako lista stavki ne postoji ili je prazna,
     *         ako neka stavka nema izabran artikal,
     *         ako je kolicina neke stavke manja ili jednaka nuli,
     *         ako je cena neke stavke manja ili jednaka nuli,
     *         ako je ukupan iznos neke stavke manji ili jednak nuli,
     *         ako je ukupan iznos racuna manji ili jednak nuli
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Racun)) {
            throw new Exception("Sistem ne može da doda račun jer prosleđeni objekat nije validan.");
        }

        Racun r = (Racun) param;

        // 🔹 Provera gosta
        if (r.getGost() == null) {
            throw new Exception("Račun mora imati izabranog gosta.");
        }

        // 🔹 Provera konobara
        if (r.getKonobar() == null) {
            throw new Exception("Račun mora imati izabranog konobara.");
        }

        // 🔹 Provera datuma i vremena
        if (r.getDatumIzdavanja() == null) {
            throw new Exception("Račun mora imati unet datum izdavanja.");
        }
        if (r.getVremeIzdavanja() == null) {
            throw new Exception("Račun mora imati uneto vreme izdavanja.");
        }

        // 🔹 Provera liste stavki
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora sadržati barem jednu stavku.");
        }

        // 🔹 Provera svake stavke ponaosob
        for (StavkaRacuna sr : r.getStavke()) {
            if (sr.getArtikal() == null) {
                throw new Exception("Svaka stavka mora imati izabran artikal.");
            }
            if (sr.getKolicina() <= 0) {
                throw new Exception("Količina svake stavke mora biti veća od nule.");
            }
            if (sr.getCena() <= 0) {
                throw new Exception("Cena svake stavke mora biti veća od nule.");
            }
            if (sr.getUkupanIznos() <= 0) {
                throw new Exception("Ukupan iznos stavke mora biti veći od nule.");
            }
        }

        // 🔹 Provera ukupnog iznosa računa
        if (r.getUkupanIznos() <= 0) {
            throw new Exception("Račun mora imati ukupan iznos veći od nule.");
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
