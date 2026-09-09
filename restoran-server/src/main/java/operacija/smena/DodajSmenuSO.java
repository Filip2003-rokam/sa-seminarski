package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 * Sistemska operacija za dodavanje nove smene.
 * @author Cofara
 */
public class DodajSmenuSO extends ApstraktnaGenerickaOperacija {

    

    public DodajSmenuSO() {
        super();
    }

    public DodajSmenuSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        System.out.println("[SO] Pozvani preduslovi za dodavanje smene...");
        if (param == null || !(param instanceof Smena)) {
            throw new Exception("Sistem nije mogao da doda smenu.");
        }

        Smena s = (Smena) param;
        System.out.println("[SO] Validacija: " + s);

        if (s.getNaziv() == null || s.getNaziv().isEmpty() || s.getNaziv().length() < 3) {
            throw new Exception("Naziv smene mora imati bar 3 karaktera.");
        }

        if (s.getVremePocetka() == null || s.getVremeKraja() == null) {
            throw new Exception("Vreme početka i kraja moraju biti uneti.");
        }

        if (!s.getVremeKraja().isAfter(s.getVremePocetka())) {
            throw new Exception("Kraj smene mora biti posle početka.");
        }
    }


    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((Smena) param);
    }
}
