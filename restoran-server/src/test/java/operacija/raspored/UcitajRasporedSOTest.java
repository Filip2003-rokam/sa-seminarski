package operacija.raspored;

import domen.Konobar;
import domen.KonobarSmena;
import domen.Smena;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UcitajRasporedSOTest {

    private static final String JOIN =
            " JOIN konobar ON konobarsmena.idKonobar = konobar.idKonobar " +
            " JOIN smena ON konobarsmena.idSmena = smena.idSmena";

    private DbRepository broker;
    private UcitajRasporedSO so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajRasporedSO(broker);
    }

    @Test
    @DisplayName("Uspešno učitavanje rasporeda poziva getAll sa JOIN-om")
    void testUspehUcitavaRaspored() throws Exception {
        Konobar k = new Konobar(1, "Marko", "Markovic", "mmarkovic", "sifra123");
        Smena s = new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
        List<KonobarSmena> lista = Arrays.asList(
                new KonobarSmena(k, s, LocalDate.of(2026, 3, 15))
        );
        when(broker.getAll(any(KonobarSmena.class), eq(JOIN))).thenReturn(lista);

        so.izvrsi(null, null);

        assertEquals(lista, so.getLista());
        verify(broker).getAll(any(KonobarSmena.class), eq(JOIN));
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Preduslovi prihvataju i neprazan parametar")
    void testPredusloviPrihvatajuNeprazanParametar() throws Exception {
        when(broker.getAll(any(KonobarSmena.class), eq(JOIN))).thenReturn(List.of());

        so.izvrsi(new KonobarSmena(), null);

        assertNotNull(so.getLista());
        assertTrue(so.getLista().isEmpty());
        verify(broker).getAll(any(KonobarSmena.class), eq(JOIN));
        verify(broker).commit();
    }

    @Test
    @DisplayName("Greška brokera pri učitavanju rasporeda radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.getAll(any(KonobarSmena.class), eq(JOIN))).thenThrow(new Exception("db"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
