/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.stavke;

import domen.StavkaRacuna;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Cofara
 */
public class ObrisiStavkuRacunaSO extends ApstraktnaGenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof StavkaRacuna)) {
            throw new Exception("Sistem ne može da obriše stavku jer prosleđeni objekat nije validan.");
        }

        StavkaRacuna sr = (StavkaRacuna) param;

        // 🔹 Proveri da li je postavljen id računa
        if (sr.getIdRacun() <= 0) {
            throw new Exception("Račun kojem pripada stavka nije ispravan.");
        }

        // 🔹 Proveri da li je postavljen redni broj stavke
        if (sr.getRb() <= 0) {
            throw new Exception("Stavka računa mora imati validan redni broj (rb).");
        }

        // 🔹 Proveri da li stavka ima vezano artikal
        if (sr.getArtikal() == null) {
            throw new Exception("Stavka računa mora imati izabrano artikal.");
        }

        // 🔹 Proveri da li su količina i cena validne
        if (sr.getKolicina() <= 0) {
            throw new Exception("Količina stavke mora biti veća od nule.");
        }
        if (sr.getCena() <= 0) {
            throw new Exception("Cena stavke mora biti veća od nule.");
        }

        // 🔹 Proveri ukupan iznos
        if (sr.getUkupanIznos() <= 0) {
            throw new Exception("Ukupan iznos stavke mora biti veći od nule.");
        }
    }


    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        StavkaRacuna sr = (StavkaRacuna) param;
        broker.delete(sr);
    }
    
    
    
}
