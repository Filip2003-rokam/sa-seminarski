package operacija.artikal;

import domen.Artikal;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

public class ObrisiArtikalSO extends ApstraktnaGenerickaOperacija {

    

    public ObrisiArtikalSO() {
        super();
    }

    public ObrisiArtikalSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa Artikal
        if (param == null || !(param instanceof Artikal)) {
            throw new Exception("Sistem nije mogao da obriše artikal");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.delete((Artikal) param);
    }
}
