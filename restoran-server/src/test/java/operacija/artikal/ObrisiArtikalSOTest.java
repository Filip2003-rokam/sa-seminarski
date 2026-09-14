package operacija.artikal;

import domen.Artikal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ObrisiArtikalSOTest {

    private DbRepository broker;
    private ObrisiArtikalSO so;
    private Artikal artikal;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new ObrisiArtikalSO(broker);
        artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
    }

    @Test
    @DisplayName("Uspešno brisanje artikla poziva delete i commit")
    void testUspehBriseArtikalIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(artikal, null);

        verify(broker).delete(artikal);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri brisanju artikla radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).delete(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(artikal, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da obriše artikal", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije artikal", null));
        assertEquals("Sistem nije mogao da obriše artikal", ex.getMessage());
    }
}
