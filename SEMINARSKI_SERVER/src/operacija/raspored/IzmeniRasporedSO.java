package operacija.raspored;

import domen.KonobarSmena;
import operacija.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija za izmenu postojećeg rasporeda (Konobar-Smena)
 */
public class IzmeniRasporedSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof KonobarSmena)) {
            throw new Exception("Sistem nije mogao da izmeni raspored.");
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
        broker.edit((KonobarSmena) param);
    }
}
