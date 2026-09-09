/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.gosti;

import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 *
 * @author Cofara
 */
public class ObrisiGostaSO extends ApstraktnaGenerickaOperacija {

    

    public ObrisiGostaSO() {
        super();
    }

    public ObrisiGostaSO(DbRepository broker) {
        super(broker);
    }

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
