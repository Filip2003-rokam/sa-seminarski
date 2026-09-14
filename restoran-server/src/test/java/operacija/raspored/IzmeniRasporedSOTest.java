package operacija.raspored;

import domen.Konobar;
import domen.KonobarSmena;
import domen.Smena;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IzmeniRasporedSOTest {

    private DbRepository broker;
    private IzmeniRasporedSO so;
    private KonobarSmena validanRaspored;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new IzmeniRasporedSO(broker);
        Konobar k = new Konobar(1, "Marko", "Markovic", "mmarkovic", "sifra123");
        Smena s = new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
        validanRaspored = new KonobarSmena(k, s, LocalDate.of(2026, 3, 15));
    }

    @Test
    @DisplayName("Uspešna izmena rasporeda poziva edit i commit")
    void testUspehMenjaRasporedIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validanRaspored, null);

        verify(broker).edit(validanRaspored);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri izmeni rasporeda radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).edit(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRaspored, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da izmeni raspored.", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije raspored", null));
        assertEquals("Sistem nije mogao da izmeni raspored.", ex.getMessage());
    }

    @Test
    @DisplayName("Null konobar baca grešku o izboru")
    void testNullKonobarBacaGresku() {
        validanRaspored.setKonobar(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRaspored, null));
        assertEquals("Morate izabrati konobara.", ex.getMessage());
    }

    @Test
    @DisplayName("Null smena baca grešku o izboru")
    void testNullSmenaBacaGresku() {
        validanRaspored.setSmena(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRaspored, null));
        assertEquals("Morate izabrati smenu.", ex.getMessage());
    }

    @Test
    @DisplayName("Null datum smene baca grešku o izboru")
    void testNullDatumBacaGresku() {
        validanRaspored.setDatumSmene(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanRaspored, null));
        assertEquals("Morate izabrati datum smene.", ex.getMessage());
    }
}
