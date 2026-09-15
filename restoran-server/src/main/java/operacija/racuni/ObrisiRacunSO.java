package operacija.racuni;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za brisanje racuna iz baze podataka.
 * Radi nad domenom {@link Racun}. Preduslov je da prosledjeni objekat
 * bude validna instanca klase Racun; u suprotnom se baca izuzetak sa
 * porukom "Sistem ne moze da obrise racun". Prvo se brisu sve stavke
 * racuna, a zatim sam racun.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class ObrisiRacunSO extends ApstraktnaGenerickaOperacija {



    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public ObrisiRacunSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Omogucava testiranje bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public ObrisiRacunSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Proverava preduslove za brisanje racuna.
     *
     * @param param objekat koji mora biti instanca klase {@link Racun}
     * @throws Exception ako je param null ili nije Racun
     *         (poruka: "Sistem ne moze da obrise racun")
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if(param == null || !(param instanceof Racun)){
            throw new Exception("Sistem ne može da obriše račun");
        }

    }

    /**
     * Brise sve stavke prosledjenog racuna, a zatim brise i sam racun
     * iz baze podataka.
     *
     * @param param racun koji se brise, tipa {@link Racun}
     * @param kljuc dodatni uslov; nije koriscen u ovoj operaciji
     * @throws Exception ako brisanje stavki ili racuna ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        Racun r = (Racun) param;

        List<StavkaRacuna> stavke = r.getStavke();
        for (StavkaRacuna s : stavke) {
            s.setIdRacun(r.getIdRacun());
            broker.delete(s);
        }

        broker.delete(r);


    }



}
