/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.smena;

import domen.Smena;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Vukan
 */
public class UcitajSmeneSO extends ApstraktnaGenerickaOperacija {

    private List<Smena> smene;

    public List<Smena> getSmene() {
        return smene;
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema posebnih preduslova
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        // Učitavanje svih smena iz baze
        smene = broker.getAll(new Smena(), null);
    }
}
