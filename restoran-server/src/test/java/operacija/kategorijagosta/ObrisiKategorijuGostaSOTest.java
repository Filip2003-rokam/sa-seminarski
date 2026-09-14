package operacija.kategorijagosta;

import domen.KategorijaGosta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ObrisiKategorijuGostaSOTest {

    private DbRepository broker;
    private ObrisiKategorijuGostaSO so;
    private KategorijaGosta kategorija;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new ObrisiKategorijuGostaSO(broker);
        kategorija = new KategorijaGosta(1, "VIP", 10.0, true);
    }

    @Test
    @DisplayName("Uspešno brisanje kategorije gosta poziva delete i commit")
    void testUspehBriseKategorijuIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(kategorija, null);

        verify(broker).delete(kategorija);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri brisanju kategorije radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).delete(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(kategorija, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da obriše kategoriju gosta!", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije kategorija", null));
        assertEquals("Sistem nije mogao da obriše kategoriju gosta!", ex.getMessage());
    }
}
