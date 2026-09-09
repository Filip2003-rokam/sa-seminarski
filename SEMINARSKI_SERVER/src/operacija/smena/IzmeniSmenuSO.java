package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija za izmenu postojeće smene.
 * @author Cofara
 */
public class IzmeniSmenuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da izmeni smenu.");
        }

        Smena s = (Smena) param;

        if (s.getNaziv() == null || s.getNaziv().isEmpty() || s.getNaziv().length() < 3) {
            throw new Exception("Naziv smene mora imati bar 3 karaktera.");
        }

        if (s.getVremePocetka()== null) {
            throw new Exception("Početak smene mora biti unet.");
        }

        if (s.getVremeKraja()== null) {
            throw new Exception("Kraj smene mora biti unet.");
        }

        if (!s.getVremeKraja().isAfter(s.getVremePocetka())) {
            throw new Exception("Kraj smene mora biti posle početka.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.edit((Smena) param);
    }
}
