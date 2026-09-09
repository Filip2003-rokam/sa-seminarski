/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.gosti;

import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Cofara
 */
public class ObrisiGostaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        
        if(param == null || !(param instanceof Gost)){
            throw new Exception("Sistem nije mogao da obrise gosta");
        }
        
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        broker.delete((Gost)param);
        
    }
    
    
    
}
