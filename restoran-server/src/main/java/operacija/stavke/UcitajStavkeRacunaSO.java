package operacija.stavke;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 *
 * @author Vukan
 */
public class UcitajStavkeRacunaSO extends ApstraktnaGenerickaOperacija {

    private List<StavkaRacuna> stavke;

    

    public UcitajStavkeRacunaSO() {
        super();
    }

    public UcitajStavkeRacunaSO(DbRepository broker) {
        super(broker);
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema posebnih preduslova
    }

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
