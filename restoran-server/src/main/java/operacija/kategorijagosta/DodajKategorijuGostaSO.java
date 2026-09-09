package operacija.kategorijagosta;

import domen.KategorijaGosta;
import operacija.ApstraktnaGenerickaOperacija;

public class DodajKategorijuGostaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof KategorijaGosta)) {
            throw new Exception("Sistem nije mogao da doda kategoriju gosta!");
        }

        KategorijaGosta kg = (KategorijaGosta) param;

        if (kg.getOpis()== null || kg.getOpis().isEmpty() || kg.getOpis().length() < 2) {
            throw new Exception("GRESKA NAZIV");
        }

        if (kg.getPopust() < 0) {
            throw new Exception("GRESKA POPUST - mora biti >= 0");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((KategorijaGosta) param);
    }
}
