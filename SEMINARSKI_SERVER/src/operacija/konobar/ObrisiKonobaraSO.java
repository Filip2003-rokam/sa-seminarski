package operacija.konobar;

import domen.Konobar;
import operacija.ApstraktnaGenerickaOperacija;

public class ObrisiKonobaraSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Konobar)) {
            throw new Exception("Sistem nije mogao da obriše konobara!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.delete((Konobar) param);
    }
}
