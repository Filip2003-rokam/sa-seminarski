package operacija.racuni;

import domen.Artikal;
import domen.Gost;
import domen.KategorijaGosta;
import domen.Konobar;
import domen.Racun;
import domen.StavkaRacuna;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UcitajRacuneSOTest {

    private DbRepository broker;
    private UcitajRacuneSO so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajRacuneSO(broker);
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
    }

    @Test
    @DisplayName("Parametar različit od null baca grešku")
    void testParametarNijeNullBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Racun(), null));
        assertEquals("Za učitavanje računa ne treba parametar!", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).getAll(any(), any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Uspešno učitava račune i vraća listu preko getRacuni")
    void testUspesnoUcitavaRacune() throws Exception {
        Konobar konobar = new Konobar(1, "Petar", "Petrovic", "ppetar", "sifra");
        Gost gost = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        StavkaRacuna stavka = new StavkaRacuna(1, 1, 2, 1700.0, 850.0, artikal);
        Racun r = new Racun(1, LocalDate.of(2024, 5, 10), LocalTime.of(14, 30),
                1700.0, true, konobar, gost, List.of(stavka));
        List<Racun> lista = List.of(r);
        when(broker.getAll(any(), any())).thenReturn(lista);

        so.izvrsi(null, null);

        assertSame(lista, so.getRacuni());
        assertEquals(1, so.getRacuni().size());
        verify(broker).connect();
        verify(broker).getAll(any(), any());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Uspešno učitava praznu listu računa")
    void testUcitavaPraznuListu() throws Exception {
        when(broker.getAll(any(), any())).thenReturn(List.of());

        so.izvrsi(null, null);

        assertNotNull(so.getRacuni());
        assertTrue(so.getRacuni().isEmpty());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri učitavanju radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.getAll(any(), any())).thenThrow(new Exception("DB greska"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
