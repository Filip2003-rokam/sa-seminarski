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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ObrisiRacunSOTest {

    private DbRepository broker;
    private ObrisiRacunSO so;
    private Racun validanRacun;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new ObrisiRacunSO(broker);
        validanRacun = kreirajValidanRacun();
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
        validanRacun = null;
    }

    private Racun kreirajValidanRacun() {
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        StavkaRacuna stavka1 = new StavkaRacuna(5, 1, 2, 1700.0, 850.0, artikal);
        StavkaRacuna stavka2 = new StavkaRacuna(5, 2, 1, 500.0, 500.0, new Artikal(2, "Sok", 500.0, "Pice"));
        List<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavka1);
        stavke.add(stavka2);

        Gost gost = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Konobar konobar = new Konobar(1, "Petar", "Petrovic", "ppetar", "sifra");

        return new Racun(5, LocalDate.of(2024, 5, 10), LocalTime.of(14, 30),
                2200.0, false, konobar, gost, stavke);
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem ne može da obriše račun", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).delete(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije racun", null));
        assertEquals("Sistem ne može da obriše račun", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).delete(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Uspešno brisanje: prvo stavke pa račun, zatim commit")
    void testUspehBriseStavkePaRacunIPotvrdjujeTransakciju() throws Exception {
        doNothing().when(broker).delete(any());
        List<StavkaRacuna> stavke = validanRacun.getStavke();
        assertNotNull(stavke);
        assertFalse(stavke.isEmpty());

        so.izvrsi(validanRacun, null);

        InOrder inOrder = inOrder(broker);
        inOrder.verify(broker).connect();
        for (StavkaRacuna s : stavke) {
            inOrder.verify(broker).delete(s);
        }
        inOrder.verify(broker).delete(validanRacun);
        inOrder.verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri brisanju računa radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("DB greska")).when(broker).delete(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
