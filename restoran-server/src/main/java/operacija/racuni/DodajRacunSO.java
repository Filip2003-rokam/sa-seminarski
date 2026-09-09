/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.racuni;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 *
 * @author Cofara
 */
public class DodajRacunSO extends ApstraktnaGenerickaOperacija{

    public DodajRacunSO() {
        super();
    }

    public DodajRacunSO(DbRepository broker) {
        super(broker);
    }

    @Override
protected void preduslovi(Object param) throws Exception {
    
        if (param == null || !(param instanceof Racun)) {
            throw new Exception("Sistem ne može da doda račun jer prosleđeni objekat nije validan.");
        }

        Racun r = (Racun) param;

        // 🔹 Provera gosta
        if (r.getGost() == null) {
            throw new Exception("Račun mora imati izabranog gosta.");
        }

        // 🔹 Provera konobara
        if (r.getKonobar() == null) {
            throw new Exception("Račun mora imati izabranog konobara.");
        }

        // 🔹 Provera datuma i vremena
        if (r.getDatumIzdavanja() == null) {
            throw new Exception("Račun mora imati unet datum izdavanja.");
        }
        if (r.getVremeIzdavanja() == null) {
            throw new Exception("Račun mora imati uneto vreme izdavanja.");
        }

        // 🔹 Provera liste stavki
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora sadržati barem jednu stavku.");
        }

        // 🔹 Provera svake stavke ponaosob
        for (StavkaRacuna sr : r.getStavke()) {
            if (sr.getArtikal() == null) {
                throw new Exception("Svaka stavka mora imati izabrano artikal.");
            }
            if (sr.getKolicina() <= 0) {
                throw new Exception("Količina svake stavke mora biti veća od nule.");
            }
            if (sr.getCena() <= 0) {
                throw new Exception("Cena svake stavke mora biti veća od nule.");
            }
            if (sr.getUkupanIznos() <= 0) {
                throw new Exception("Ukupan iznos stavke mora biti veći od nule.");
            }
        }

        // 🔹 Provera ukupnog iznosa računa
        if (r.getUkupanIznos() <= 0) {
            throw new Exception("Račun mora imati ukupan iznos veći od nule.");
        }
    }


    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        Racun r = (Racun) param;
        int idRacun = broker.addReturnKey(r);

        List<StavkaRacuna> stavke = r.getStavke();
        for (StavkaRacuna s : stavke) {
            s.setIdRacun(idRacun);
            broker.add(s);
        }

        
    }
    
    
    
}
