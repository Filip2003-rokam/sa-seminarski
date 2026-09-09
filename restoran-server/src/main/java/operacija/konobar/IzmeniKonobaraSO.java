package operacija.konobar;

import domen.Konobar;
import operacija.ApstraktnaGenerickaOperacija;

public class IzmeniKonobaraSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Konobar)) {
            throw new Exception("Sistem nije mogao da izmeni konobara!");
        }

        Konobar k = (Konobar) param;

        if (k.getIme() == null || k.getIme().isEmpty() || k.getIme().length() < 2) {
            throw new Exception("GRESKA IME");
        }

        if (k.getPrezime() == null || k.getPrezime().isEmpty() || k.getPrezime().length() < 2) {
            throw new Exception("GRESKA PREZIME");
        }

        if (k.getKorisnickoIme()== null || k.getKorisnickoIme().isEmpty()) {
            throw new Exception("GRESKA USERNAME");
        }

        if (k.getSifra()== null || k.getSifra().isEmpty()) {
            throw new Exception("GRESKA PASSWORD");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.edit((Konobar) param);
    }
}
