package operacija.smene;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija za brisanje smene iz baze.
 * @author Cofara
 */
public class ObrisiSmenuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Validacija ulaznog parametra
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da obriše smenu - neispravan parametar.");
        }

        Smena s = (Smena) param;
        if (s.getIdSmena() <= 0) {
            throw new Exception("Smena nema ispravan ID i ne može biti obrisana.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        // Poziv ka brokeru baze podataka za brisanje smene
        broker.delete((Smena) param);
    }
}
