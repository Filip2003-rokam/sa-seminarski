package operacija.raspored;

import domen.KonobarSmena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje veze između konobara i smene (raspored)
 */
public class DodajRasporedSO extends ApstraktnaGenerickaOperacija {

    

    public DodajRasporedSO() {
        super();
    }

    public DodajRasporedSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof KonobarSmena)) {
            throw new Exception("Sistem nije mogao da zapamti raspored.");
        }

        KonobarSmena ks = (KonobarSmena) param;

        if (ks.getKonobar() == null) {
            throw new Exception("Morate izabrati konobara.");
        }

        if (ks.getSmena() == null) {
            throw new Exception("Morate izabrati smenu.");
        }

        if (ks.getDatumSmene() == null) {
            throw new Exception("Morate izabrati datum smene.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((KonobarSmena) param);
    }
}
