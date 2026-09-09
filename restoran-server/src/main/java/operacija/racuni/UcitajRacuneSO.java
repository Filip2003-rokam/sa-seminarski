package operacija.racuni;

import domen.ApstraktniDomenskiObjekat;
import domen.Racun;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

public class UcitajRacuneSO extends ApstraktnaGenerickaOperacija {

    private List<Racun> racuni;

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Za učitavanje računa ne treba parametar
        if (param != null) {
            throw new Exception("Za učitavanje računa ne treba parametar!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        String upit = 
    " JOIN gost g ON racun.idGost = g.idGost " +
    "JOIN konobar k ON racun.idKonobar = k.idKonobar";

                    racuni = broker.getAll(new Racun(), upit);
    }

    public List<Racun> getRacuni() {
        return racuni;
    }
}
