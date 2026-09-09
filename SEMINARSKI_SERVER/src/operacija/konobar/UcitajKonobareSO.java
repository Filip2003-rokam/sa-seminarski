package operacija.konobar;

import domen.Konobar;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

public class UcitajKonobareSO extends ApstraktnaGenerickaOperacija {

    private List<Konobar> konobari;

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param != null) {
            throw new Exception("Za učitavanje konobara ne treba parametar!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        konobari = broker.getAll(new Konobar(), "");
    }

    public List<Konobar> getKonobari() {
        return konobari;
    }
}
