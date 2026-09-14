package operacija.stavke;

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

class UcitajStavkeRacunaSOTest {

    private DbRepository broker;
    private UcitajStavkeRacunaSO so;
    private Racun validanRacun;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajStavkeRacunaSO(broker);
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
    @DisplayName("Preduslovi ne bacaju grešku za bilo koji parametar")
    void testPredusloviPrazni() throws Exception {
        when(broker.getAll(any(), any())).thenReturn(List.of());

        assertDoesNotThrow(() -> so.izvrsi(null, null));
        assertDoesNotThrow(() -> so.izvrsi(validanRacun, null));
        assertDoesNotThrow(() -> so.izvrsi("nesto", null));
    }

    @Test
    @DisplayName("Kada je parametar Racun, učitava stavke i vraća ih preko getStavke")
    void testUcitavaStavkeZaRacun() throws Exception {
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        StavkaRacuna s1 = new StavkaRacuna(5, 1, 2, 1700.0, 850.0, artikal);
        StavkaRacuna s2 = new StavkaRacuna(5, 2, 1, 500.0, 500.0, new Artikal(2, "Sok", 500.0, "Pice"));
        List<StavkaRacuna> lista = List.of(s1, s2);
        when(broker.getAll(any(), any())).thenReturn(lista);

        so.izvrsi(validanRacun, null);

        assertSame(lista, so.getStavke());
        assertEquals(2, so.getStavke().size());
        verify(broker).connect();
        verify(broker).getAll(any(), any());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Uspešno učitava praznu listu stavki za račun")
    void testUcitavaPraznuListu() throws Exception {
        when(broker.getAll(any(), any())).thenReturn(List.of());

        so.izvrsi(validanRacun, null);

        assertNotNull(so.getStavke());
        assertTrue(so.getStavke().isEmpty());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri učitavanju stavki radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.getAll(any(), any())).thenThrow(new Exception("DB greska"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRacun, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
