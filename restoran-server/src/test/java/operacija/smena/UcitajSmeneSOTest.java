package operacija.smena;

import domen.Smena;
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

class UcitajSmeneSOTest {

    private DbRepository broker;
    private UcitajSmeneSO so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajSmeneSO(broker);
    }

    @Test
    @DisplayName("Uspešno učitavanje smena poziva getAll sa null ključem")
    void testUspehUcitavaSmene() throws Exception {
        List<Smena> lista = Arrays.asList(
                new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0)),
                new Smena(2, "Vecernja", LocalTime.of(16, 0), LocalTime.of(23, 0))
        );
        when(broker.getAll(any(Smena.class), isNull())).thenReturn(lista);

        so.izvrsi(null, null);

        assertEquals(lista, so.getSmene());
        verify(broker).getAll(any(Smena.class), isNull());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Preduslovi prihvataju i neprazan parametar")
    void testPredusloviPrihvatajuNeprazanParametar() throws Exception {
        List<Smena> lista = Arrays.asList(
                new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0))
        );
        when(broker.getAll(any(Smena.class), isNull())).thenReturn(lista);

        so.izvrsi(new Smena(), null);

        assertEquals(lista, so.getSmene());
        verify(broker).getAll(any(Smena.class), isNull());
        verify(broker).commit();
    }

    @Test
    @DisplayName("Greška brokera pri učitavanju smena radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.getAll(any(Smena.class), isNull())).thenThrow(new Exception("db"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
