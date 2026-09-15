package operacija.racuni;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za izmenu postojeceg racuna u bazi podataka.
 * Radi nad domenom {@link Racun} i njegovim stavkama {@link StavkaRacuna}.
 * Preduslovi zahtevaju validan objekat tipa Racun sa validnim ID-jem
 * (&gt; 0) i listu sa barem jednom stavkom. Validacija atributa (gost, konobar,
 * datum, vreme, ukupanIznos, stavke) je u setterima domen klase.
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
     *         ako lista stavki ne postoji ili je prazna
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

        // 🔹 Proveri da li postoje stavke računa
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora sadržati barem jednu stavku.");
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
