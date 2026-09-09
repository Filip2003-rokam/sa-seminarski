package operacija.gosti;

import java.util.List;
import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;

public class UcitajGosteSO extends ApstraktnaGenerickaOperacija {

    private List<Gost> gosti;

    public List<Gost> getGosti() {
        return gosti;
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema posebnih preduslova
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        gosti = broker.getAll(new Gost(),
                " JOIN kategorijagosta ON gost.idKategorijaGosta = kategorijagosta.idKategorijaGosta");
    }
}
