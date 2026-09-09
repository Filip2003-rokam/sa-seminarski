/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.gosti;

import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;
import repository.Repository;
import repository.db.impl.DbRepositoryGeneric;

/**
 *
 * @author Cofara
 */
public class DodajGostaSO extends ApstraktnaGenerickaOperacija{

    
    //@Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Gost)){
            throw new Exception("Sistem nije mogao da doda gosta");
        }
        
        Gost g = (Gost) param;
        
        if (g.getIme() == null || g.getIme().isEmpty() || g.getIme().length() < 3) {
            throw new Exception("GRESKA IME");
        }
        
        if (g.getPrezime() == null || g.getPrezime().isEmpty() || g.getPrezime().length() < 3) {
            throw new Exception("GRESKA PREZIME");
        }
    

    }

    //@Override
    public void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        broker.add((Gost)param);
        
    }

    
}
