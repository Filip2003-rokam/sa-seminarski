package operacija.konobar;

import domen.Konobar;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

public class UcitajKonobareSO extends ApstraktnaGenerickaOperacija {

    private List<Konobar> konobari;

    

    public UcitajKonobareSO() {
        super();
    }

    public UcitajKonobareSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param != null) {
            throw new Exception("Za učitavanje konobara ne treba parametar!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        konobari = broker.getAll(new Konobar(), "");
    }

    public List<Konobar> getKonobari() {
        return konobari;
    }
}
