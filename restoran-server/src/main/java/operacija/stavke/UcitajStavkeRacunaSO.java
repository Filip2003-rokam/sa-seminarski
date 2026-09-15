package operacija.stavke;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za ucitavanje stavki racuna iz baze podataka.
 * Radi nad domenom {@link StavkaRacuna}. Nema posebnih preduslova u
 * metodi {@code preduslovi}. Izvrsavanje ima dva puta: ako je parametar
 * tipa {@link Racun}, ucitavaju se stavke tog racuna (JOIN na racun i
 * artikal, filter po idRacun, sortiranje po rb); ako je kljuc jednak
 * "svi", ucitavaju se sve stavke svih racuna (sortirano po idRacun i rb).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class UcitajStavkeRacunaSO extends ApstraktnaGenerickaOperacija {

    /**
     * Lista ucitanih stavki racuna nakon uspesnog izvrsavanja operacije.
     */
    private List<StavkaRacuna> stavke;



    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom nad bazom podataka.
     */
    public UcitajStavkeRacunaSO() {
        super();
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom.
     * Omogucava testiranje bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi za pristup podacima
     */
    public UcitajStavkeRacunaSO(DbRepository broker) {
        super(broker);
    }

    /**
     * Vraca listu stavki ucitanih poslednjim izvrsavanjem operacije.
     *
     * @return lista objekata tipa {@link StavkaRacuna}, ili null ako
     *         operacija jos nije izvrsena odnosno ako nije pogodjen nijedan put
     */
    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    /**
     * Proverava preduslove za ucitavanje stavki racuna.
     * Trenutna implementacija nema posebnih preduslova.
     *
     * @param param objekat nad kojim se operacija izvrsava; nije validiran
     * @throws Exception nije bacan u trenutnoj implementaciji
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema posebnih preduslova
    }

    /**
     * Ucitava stavke racuna na jedan od dva nacina.
     * Ako je {@code param} tipa {@link Racun}, ucitava stavke tog racuna
     * (JOIN racun i artikal, WHERE po idRacun, ORDER BY rb).
     * Inace, ako je {@code kljuc} jednak "svi", ucitava sve stavke svih
     * racuna (JOIN racun i artikal, ORDER BY idRacun i rb).
     *
     * @param param racun cije se stavke ucitavaju, ili null/drugi tip
     * @param kljuc ako je "svi" i param nije Racun, ucitavaju se sve stavke
     * @throws Exception ako ucitavanje iz baze ne uspe
     */
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        // Ako je prosleđen konkretan račun — učitaj njegove stavke
        if (param instanceof Racun) {
            Racun r = (Racun) param;

            stavke = broker.getAll(new StavkaRacuna(),
                " JOIN racun ON racun.idRacun = stavkaracuna.idRacun " +
                " JOIN artikal ON artikal.idArtikal = stavkaracuna.idArtikal " +
                " WHERE racun.idRacun = " + r.getIdRacun() +
                " ORDER BY stavkaracuna.rb ASC");

            return;
        }

        // Ako je prosleđen neki drugi ključ, možeš po potrebi dodati logiku
        if (kljuc != null && kljuc.equals("svi")) {
            stavke = broker.getAll(new StavkaRacuna(),
                    " JOIN racun ON racun.idRacun = stavkaracuna.idRacun \n" +
                    " JOIN artikal ON artikal.idArtikal = stavkaracuna.idArtikal \n" +
                    " ORDER BY racun.idRacun, stavkaracuna.rb ASC;");
        }
    }

}
