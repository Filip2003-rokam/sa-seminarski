package operacija.kategorijagosta;



import domen.KategorijaGosta;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Cofara
 */
public class ObrisiKategorijuGostaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        // mora da postoji objekat i da je tipa KategorijaGosta
        if (param == null || !(param instanceof KategorijaGosta)) {
            throw new Exception("Sistem nije mogao da obriše kategoriju gosta!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.delete((KategorijaGosta) param);
    }
}
