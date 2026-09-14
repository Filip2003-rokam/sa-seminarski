package operacija.racuni;

import domen.Artikal;
import domen.Gost;
import domen.KategorijaGosta;
import domen.Konobar;
import domen.Racun;
import domen.StavkaRacuna;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DodajRacunSOTest {

    private DbRepository broker;
    private DodajRacunSO so;
    private Racun validanRacun;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new DodajRacunSO(broker);
        validanRacun = kreirajValidanRacun();
    }

    private Racun kreirajValidanRacun() {
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        StavkaRacuna stavka = new StavkaRacuna(0, 1, 2, 1700.0, 850.0, artikal);
        List<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavka);

        Gost gost = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Konobar konobar = new Konobar(1, "Petar", "Petrovic", "ppetar", "sifra");

        return new Racun(0, LocalDate.of(2024, 5, 10), LocalTime.of(14, 30),
                1700.0, false, konobar, gost, stavke);
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem ne može da doda račun jer prosleđeni objekat nije validan.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije racun", null));
        assertEquals("Sistem ne može da doda račun jer prosleđeni objekat nije validan.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null gost baca grešku")
    void testNullGostBacaGresku() throws Exception {
        validanRacun.setGost(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati izabranog gosta.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Null konobar baca grešku")
    void testNullKonobarBacaGresku() throws Exception {
        validanRacun.setKonobar(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati izabranog konobara.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Null datum izdavanja baca grešku")
    void testNullDatumBacaGresku() throws Exception {
        validanRacun.setDatumIzdavanja(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati unet datum izdavanja.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Null vreme izdavanja baca grešku")
    void testNullVremeBacaGresku() throws Exception {
        validanRacun.setVremeIzdavanja(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati uneto vreme izdavanja.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Prazna lista stavki baca grešku")
    void testPrazneStavkeBacaGresku() throws Exception {
        validanRacun.setStavke(new ArrayList<>());
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora sadržati barem jednu stavku.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Null lista stavki baca grešku")
    void testNullStavkeBacaGresku() throws Exception {
        validanRacun.setStavke(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora sadržati barem jednu stavku.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Stavka bez artikla baca grešku")
    void testStavkaBezArtiklaBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setArtikal(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Svaka stavka mora imati izabrano artikal.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Količina stavke manja ili jednaka nuli baca grešku")
    void testKolicinaManjaIliJednakaNuliBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setKolicina(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Količina svake stavke mora biti veća od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Cena stavke manja ili jednaka nuli baca grešku")
    void testCenaManjaIliJednakaNuliBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setCena(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Cena svake stavke mora biti veća od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Ukupan iznos stavke manji ili jednak nuli baca grešku")
    void testUkupanIznosStavkeManjiIliJednakNuliBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setUkupanIznos(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Ukupan iznos stavke mora biti veći od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Ukupan iznos računa manji ili jednak nuli baca grešku")
    void testUkupanIznosRacunaManjiIliJednakNuliBacaGresku() throws Exception {
        validanRacun.setUkupanIznos(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati ukupan iznos veći od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).addReturnKey(any());
    }

    @Test
    @DisplayName("Uspešno dodavanje računa poziva addReturnKey, add za stavke i commit")
    void testUspehDodajeRacunIStavkeIPotvrdjujeTransakciju() throws Exception {
        when(broker.addReturnKey(any())).thenReturn(1);
        doNothing().when(broker).add(any());

        so.izvrsi(validanRacun, null);

        verify(broker).connect();
        verify(broker).addReturnKey(validanRacun);
        for (StavkaRacuna s : validanRacun.getStavke()) {
            assertEquals(1, s.getIdRacun());
            verify(broker).add(s);
        }
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri dodavanju računa radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.addReturnKey(any())).thenThrow(new Exception("DB greska"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).addReturnKey(validanRacun);
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
