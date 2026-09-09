package operacija.kategorijagosta;


import domen.KategorijaGosta;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 *
 * @author Cofara
 */
public class UcitajKategorijeGostijuSO extends ApstraktnaGenerickaOperacija {

    private List<KategorijaGosta> kategorije;

    

    public UcitajKategorijeGostijuSO() {
        super();
    }

    public UcitajKategorijeGostijuSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // ovde nema posebnih preduslova jer ne dobijaš parametar
        if (param != null) {
            throw new Exception("Za učitavanje kategorija gostiju ne treba parametar!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        kategorije = broker.getAll(new KategorijaGosta(), "");
    }

    public List<KategorijaGosta> getKategorije() {
        return kategorije;
    }
}
