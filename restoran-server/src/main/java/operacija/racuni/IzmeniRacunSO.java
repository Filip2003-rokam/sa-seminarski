package operacija.racuni;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojeceg racuna u bazi podataka.
 * Radi nad domenom {@link Racun} i njegovim stavkama {@link StavkaRacuna}.
 * Poslovna pravila zahtevaju validan objekat tipa Racun sa validnim ID-jem
 * (&gt; 0), unet datum i vreme izdavanja, dodeljenog konobara i gosta,
 * ukupan iznos veci od nule, listu sa barem jednom stavkom, kao i za svaku
 * stavku artikal i vrednosti kolicine, cene i ukupnog iznosa vece od nule.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class IzmeniRacunSO extends ApstraktnaGenerickaOperacija {



    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public IzmeniRacunSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Omogucava testiranje bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public IzmeniRacunSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za izmenu racuna.
     *
     * @param param objekat koji mora biti instanca klase {@link Racun}
     * @throws Exception ako je param null ili nije Racun,
     *         ako racun nema validan ID (idRacun &lt;= 0),
     *         ako nije unet datum izdavanja,
     *         ako nije uneto vreme izdavanja,
     *         ako racun nema dodeljenog konobara,
     *         ako racun nema dodeljenog gosta,
     *         ako je ukupan iznos racuna manji ili jednak nuli,
     *         ako lista stavki ne postoji ili je prazna,
     *         ako neka stavka nema izabran artikal,
     *         ako je kolicina neke stavke manja ili jednaka nuli,
     *         ako je cena neke stavke manja ili jednaka nuli,
     *         ako je ukupan iznos neke stavke manji ili jednak nuli
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Racun)) {
            throw new Exception("Sistem ne može da izmeni račun jer prosleđeni objekat nije validan.");
        }

        Racun r = (Racun) param;

        // 🔹 Proveri da li račun ima validan ID
        if (r.getIdRacun() <= 0) {
            throw new Exception("Račun mora imati validan ID kako bi se mogao izmeniti.");
        }

        // 🔹 Proveri da li su datum i vreme popunjeni
        if (r.getDatumIzdavanja() == null) {
            throw new Exception("Račun mora imati unet datum izdavanja.");
        }
        if (r.getVremeIzdavanja() == null) {
            throw new Exception("Račun mora imati uneto vreme izdavanja.");
        }

        // 🔹 Proveri da li račun ima konobara i gosta
        if (r.getKonobar() == null) {
            throw new Exception("Račun mora imati dodeljenog konobara.");
        }
        if (r.getGost() == null) {
            throw new Exception("Račun mora imati dodeljenog gosta.");
        }

        // 🔹 Proveri da li ukupan iznos ima smisla
        if (r.getUkupanIznos() <= 0) {
            throw new Exception("Ukupan iznos računa mora biti veći od nule.");
        }

        // 🔹 Proveri da li postoje stavke računa
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora sadržati barem jednu stavku.");
        }

        // 🔹 Proveri svaku stavku računa
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
                throw new Exception("Ukupan iznos svake stavke mora biti veći od nule.");
            }
        }
    }

    /**
     * Azurira podatke racuna pozivom {@code edit}, ucitava stare stavke
     * iz baze, brise ih, pa dodaje nove stavke sa id-jem racuna.
     *
     * @param param racun koji se menja, tipa {@link Racun}
     * @param kljuc dodatni uslov; nije koriscen u ovoj operaciji
     * @throws Exception ako izmena racuna, brisanje starih ili upis novih
     *         stavki ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Racun r = (Racun) param;

        broker.edit(r);

        // r.getStavke() //dodajemo
        String uslov =
            " JOIN racun ON racun.idRacun = stavkaracuna.idRacun " +
            " JOIN artikal ON artikal.idArtikal = stavkaracuna.idArtikal " +
            " WHERE racun.idRacun = " + r.getIdRacun();


        List<StavkaRacuna> stareStavke = broker.getAll(new StavkaRacuna(), uslov);

        for (StavkaRacuna sr : stareStavke) {
            broker.delete(sr);
        }

        List<StavkaRacuna> noveStavke = r.getStavke();
        for (StavkaRacuna sr : noveStavke) {
            sr.setIdRacun(r.getIdRacun());
            broker.add(sr);
        }

    }


}
