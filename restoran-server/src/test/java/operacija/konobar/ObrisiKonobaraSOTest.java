package operacija.konobar;

import domen.Konobar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ObrisiKonobaraSOTest {

    private DbRepository broker;
    private ObrisiKonobaraSO so;
    private Konobar validanKonobar;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new ObrisiKonobaraSO(broker);
        validanKonobar = new Konobar(1, "Marko", "Markovic", "mmarkovic", "sifra123");
    }

    @Test
    @DisplayName("Uspešno brisanje konobara poziva delete i commit")
    void testUspehBriseKonobaraIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validanKonobar, null);

        verify(broker).delete(validanKonobar);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri brisanju konobara radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).delete(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da obriše konobara!", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije konobar", null));
        assertEquals("Sistem nije mogao da obriše konobara!", ex.getMessage());
    }
}
