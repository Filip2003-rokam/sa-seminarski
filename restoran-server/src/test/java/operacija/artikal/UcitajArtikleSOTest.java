package operacija.artikal;

import domen.Artikal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UcitajArtikleSOTest {

    private DbRepository broker;
    private UcitajArtikleSO so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajArtikleSO(broker);
    }

    @Test
    @DisplayName("Uspešno učitavanje artikala sa null parametrom")
    void testUspehUcitavaArtikleSaNullParametrom() throws Exception {
        List<Artikal> lista = Arrays.asList(
                new Artikal(1, "Pizza", 850.0, "Jelo"),
                new Artikal(2, "Kola", 200.0, "Pice")
        );
        when(broker.getAll(any(Artikal.class), eq(""))).thenReturn(lista);

        so.izvrsi(null, null);

        assertEquals(lista, so.getArtikli());
        verify(broker).getAll(any(Artikal.class), eq(""));
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Neprazan parametar baca grešku za učitavanje")
    void testNeprazanParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Artikal(), null));
        assertEquals("Za učitavanje artikala ne treba parametar!", ex.getMessage());
    }

    @Test
    @DisplayName("Greška brokera pri učitavanju artikala radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.getAll(any(Artikal.class), eq(""))).thenThrow(new Exception("db"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
