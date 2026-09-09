package operacija.raspored;

import domen.KonobarSmena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

public class ObrisiRasporedSO extends ApstraktnaGenerickaOperacija {

    

    public ObrisiRasporedSO() {
        super();
    }

    public ObrisiRasporedSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof KonobarSmena)) {
            throw new Exception("Sistem nije mogao da obriše raspored (neispravan objekat)!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        KonobarSmena ks = (KonobarSmena) param;
        broker.delete(ks);
    }
}
