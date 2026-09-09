package operacija.raspored;


import domen.KonobarSmena;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija za učitavanje rasporeda konobara po smenama.
 */
public class UcitajRasporedSO extends ApstraktnaGenerickaOperacija {

    private List<KonobarSmena> lista;

    public List<KonobarSmena> getLista() {
        return lista;
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema posebnih preduslova
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        lista = broker.getAll(
            new KonobarSmena(),
            " JOIN konobar ON konobarsmena.idKonobar = konobar.idKonobar " +
            " JOIN smena ON konobarsmena.idSmena = smena.idSmena"
        );
    }
}
