/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.artikal;

import domen.ApstraktniDomenskiObjekat;
import domen.Artikal;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 *
 * @author Cofara
 */
public class UcitajArtikleSO extends ApstraktnaGenerickaOperacija {

    private List<Artikal> artikli;
    
    

    public UcitajArtikleSO() {
        super();
    }

    public UcitajArtikleSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // ovde nema posebnih preduslova jer ne dobijaš parametar
        // ali možeš staviti check da je param null
        if (param != null) {
            throw new Exception("Za učitavanje artikala ne treba parametar!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        artikli = broker.getAll(new Artikal(), "");
    }

    public List<Artikal> getArtikli() {
        return artikli;
    }
}
