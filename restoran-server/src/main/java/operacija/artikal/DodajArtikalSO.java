package operacija.artikal;

import domen.Artikal;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

public class DodajArtikalSO extends ApstraktnaGenerickaOperacija {

    

    public DodajArtikalSO() {
        super();
    }

    public DodajArtikalSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa Artikal
        if (param == null || !(param instanceof Artikal)) {
            throw new Exception("Sistem nije mogao da doda artikal");
        }

        Artikal artikal = (Artikal) param;

        if (artikal.getNaziv() == null || artikal.getNaziv().isEmpty() || artikal.getNaziv().length() < 2) {
            throw new Exception("GRESKA NAZIV");
        }

        if (artikal.getTip() == null || artikal.getTip().isEmpty()) {
            throw new Exception("GRESKA TIP");
        }

        if (artikal.getCena() <= 0) {
            throw new Exception("GRESKA CENA");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((Artikal) param);
    }
}
