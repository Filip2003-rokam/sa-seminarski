package operacija.artikal;

import domen.Artikal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IzmeniArtikalSOTest {

    private DbRepository broker;
    private IzmeniArtikalSO so;
    private Artikal validanArtikal;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new IzmeniArtikalSO(broker);
        validanArtikal = new Artikal(1, "Pizza", 850.0, "Jelo");
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
        validanArtikal = null;
    }

    @Test
    @DisplayName("Uspešna izmena artikla poziva edit i commit")
    void testUspehMenjaArtikalIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validanArtikal, null);

        verify(broker).edit(validanArtikal);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri izmeni artikla radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).edit(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da izmeni artikal", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(123, null));
        assertEquals("Sistem nije mogao da izmeni artikal", ex.getMessage());
    }
}
