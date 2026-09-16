package operacija.kategorijagosta;

import domen.KategorijaGosta;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UcitajKategorijeGostijuSOTest {

    private DbRepository broker;
    private UcitajKategorijeGostijuSO so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajKategorijeGostijuSO(broker);
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
    }

    @Test
    @DisplayName("Uspešno učitavanje kategorija gostiju sa null parametrom")
    void testUspehUcitavaKategorijeSaNullParametrom() throws Exception {
        List<KategorijaGosta> lista = Arrays.asList(
                new KategorijaGosta(1, "VIP", 10.0, true),
                new KategorijaGosta(2, "Obican", 0.0, false)
        );
        when(broker.getAll(any(KategorijaGosta.class), eq(""))).thenReturn(lista);

        so.izvrsi(null, null);

        assertEquals(lista, so.getKategorije());
        verify(broker).getAll(any(KategorijaGosta.class), eq(""));
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Neprazan parametar baca grešku za učitavanje")
    void testNeprazanParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new KategorijaGosta(), null));
        assertEquals("Za učitavanje kategorija gostiju ne treba parametar!", ex.getMessage());
    }

    @Test
    @DisplayName("Greška brokera pri učitavanju kategorija radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.getAll(any(KategorijaGosta.class), eq(""))).thenThrow(new Exception("db"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
