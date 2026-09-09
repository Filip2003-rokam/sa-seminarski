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
public class IzmeniRacunSO extends ApstraktnaGenerickaOperacija{

    

    public IzmeniRacunSO() {
        super();
    }

    public IzmeniRacunSO(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Racun)) {
            throw new Exception("Sistem ne može da izmeni račun jer prosleđeni objekat nije validan.");
        }

        Racun r = (Racun) param;

        // 🔹 Proveri da li račun ima validan ID
        if (r.getIdRacun() <= 0) {
            throw new Exception("Račun mora imati validan ID kako bi se mogao izmeniti.");
        }

        // 🔹 Proveri da li su datum i vreme popunjeni
        if (r.getDatumIzdavanja() == null) {
            throw new Exception("Račun mora imati unet datum izdavanja.");
        }
        if (r.getVremeIzdavanja() == null) {
            throw new Exception("Račun mora imati uneto vreme izdavanja.");
        }

        // 🔹 Proveri da li račun ima konobara i gosta
        if (r.getKonobar() == null) {
            throw new Exception("Račun mora imati dodeljenog konobara.");
        }
        if (r.getGost() == null) {
            throw new Exception("Račun mora imati dodeljenog gosta.");
        }

        // 🔹 Proveri da li ukupan iznos ima smisla
        if (r.getUkupanIznos() <= 0) {
            throw new Exception("Ukupan iznos računa mora biti veći od nule.");
        }

        // 🔹 Proveri da li postoje stavke računa
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora sadržati barem jednu stavku.");
        }

        // 🔹 Proveri svaku stavku računa
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
                throw new Exception("Ukupan iznos svake stavke mora biti veći od nule.");
            }
        }
    }


    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Racun r = (Racun) param;
        
        broker.edit(r);

        // r.getStavke() //dodajemo
        String uslov = 
            " JOIN racun ON racun.idRacun = stavkaracuna.idRacun " +
            " JOIN artikal ON artikal.idArtikal = stavkaracuna.idArtikal " +
            " WHERE racun.idRacun = " + r.getIdRacun();


        List<StavkaRacuna> stareStavke = broker.getAll(new StavkaRacuna(), uslov);
        
        for (StavkaRacuna sr : stareStavke) {
            broker.delete(sr);
        }

        List<StavkaRacuna> noveStavke = r.getStavke();
        for (StavkaRacuna sr : noveStavke) {
            sr.setIdRacun(r.getIdRacun());
            broker.add(sr);
        }

    }

    
}
