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
import org.mockito.InOrder;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IzmeniRacunSOTest {

    private DbRepository broker;
    private IzmeniRacunSO so;
    private Racun validanRacun;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new IzmeniRacunSO(broker);
        validanRacun = kreirajValidanRacun();
    }

    private Racun kreirajValidanRacun() {
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        StavkaRacuna stavka = new StavkaRacuna(5, 1, 2, 1700.0, 850.0, artikal);
        List<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavka);

        Gost gost = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Konobar konobar = new Konobar(1, "Petar", "Petrovic", "ppetar", "sifra");

        return new Racun(5, LocalDate.of(2024, 5, 10), LocalTime.of(14, 30),
                1700.0, false, konobar, gost, stavke);
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem ne može da izmeni račun jer prosleđeni objekat nije validan.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije racun", null));
        assertEquals("Sistem ne može da izmeni račun jer prosleđeni objekat nije validan.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Nevalidan ID računa baca grešku")
    void testNevalidanIdRacunaBacaGresku() throws Exception {
        validanRacun.setIdRacun(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati validan ID kako bi se mogao izmeniti.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Null datum izdavanja baca grešku")
    void testNullDatumBacaGresku() throws Exception {
        validanRacun.setDatumIzdavanja(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati unet datum izdavanja.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Null vreme izdavanja baca grešku")
    void testNullVremeBacaGresku() throws Exception {
        validanRacun.setVremeIzdavanja(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati uneto vreme izdavanja.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Null konobar baca grešku o dodeljenom konobaru")
    void testNullKonobarBacaGresku() throws Exception {
        validanRacun.setKonobar(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati dodeljenog konobara.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Null gost baca grešku o dodeljenom gostu")
    void testNullGostBacaGresku() throws Exception {
        validanRacun.setGost(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora imati dodeljenog gosta.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Ukupan iznos računa manji ili jednak nuli baca grešku")
    void testUkupanIznosRacunaManjiIliJednakNuliBacaGresku() throws Exception {
        validanRacun.setUkupanIznos(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Ukupan iznos računa mora biti veći od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Prazna lista stavki baca grešku")
    void testPrazneStavkeBacaGresku() throws Exception {
        validanRacun.setStavke(new ArrayList<>());
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora sadržati barem jednu stavku.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Null lista stavki baca grešku")
    void testNullStavkeBacaGresku() throws Exception {
        validanRacun.setStavke(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Račun mora sadržati barem jednu stavku.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Stavka bez artikla baca grešku")
    void testStavkaBezArtiklaBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setArtikal(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Svaka stavka mora imati izabrano artikal.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Količina stavke manja ili jednaka nuli baca grešku")
    void testKolicinaManjaIliJednakaNuliBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setKolicina(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Količina svake stavke mora biti veća od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Cena stavke manja ili jednaka nuli baca grešku")
    void testCenaManjaIliJednakaNuliBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setCena(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Cena svake stavke mora biti veća od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Ukupan iznos stavke manji ili jednak nuli baca grešku")
    void testUkupanIznosStavkeManjiIliJednakNuliBacaGresku() throws Exception {
        validanRacun.getStavke().get(0).setUkupanIznos(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("Ukupan iznos svake stavke mora biti veći od nule.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
    }

    @Test
    @DisplayName("Uspešna izmena: edit, briše stare stavke, dodaje nove i commit")
    void testUspehMenjaRacunIStavkeIPotvrdjujeTransakciju() throws Exception {
        Artikal stariArtikal = new Artikal(2, "Pasta", 600.0, "Jelo");
        StavkaRacuna staraStavka = new StavkaRacuna(5, 1, 1, 600.0, 600.0, stariArtikal);
        List<StavkaRacuna> stareStavke = List.of(staraStavka);

        when(broker.getAll(any(), any())).thenReturn(stareStavke);
        doNothing().when(broker).edit(any());
        doNothing().when(broker).delete(any());
        doNothing().when(broker).add(any());

        so.izvrsi(validanRacun, null);

        InOrder inOrder = inOrder(broker);
        inOrder.verify(broker).connect();
        inOrder.verify(broker).edit(validanRacun);
        inOrder.verify(broker).getAll(any(), any());
        inOrder.verify(broker).delete(staraStavka);
        for (StavkaRacuna nova : validanRacun.getStavke()) {
            assertEquals(5, nova.getIdRacun());
            inOrder.verify(broker).add(nova);
        }
        inOrder.verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri izmeni računa radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("DB greska")).when(broker).edit(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).edit(validanRacun);
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
