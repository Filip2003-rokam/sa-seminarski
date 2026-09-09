/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.gosti;

import domen.Gost;
import operacija.ApstraktnaGenerickaOperacija;
import repository.Repository;
import repository.db.DbRepository;
import repository.db.impl.DbRepositoryGeneric;

/**
 *
 * @author Cofara
 */
public class IzmeniGostaSO  {

    private final Repository broker;

    public IzmeniGostaSO() {
        this.broker = new DbRepositoryGeneric();
    }
    
    private void preduslovi(Object param) throws Exception {
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

    private void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        broker.edit((Gost)param);
        
    }
    
        public final void izvrsi(Object objekat, String kljuc) throws Exception {
        try {
            preduslovi(objekat);
            zapocniTransakciju();
            izvrsiOperaciju(objekat, kljuc);
            potvrdiTransakciju();
        } catch (Exception e) {
            ponistiTransakciju();
            throw e;
        } finally {
            //ugasiKonekciju();
        }
    }
        
    private void zapocniTransakciju() throws Exception {
        ((DbRepository) broker).connect();
    }

    private void potvrdiTransakciju() throws Exception {
        ((DbRepository) broker).commit();
    }

    private void ponistiTransakciju() throws Exception {
        ((DbRepository) broker).rollback();
    }

    
}
