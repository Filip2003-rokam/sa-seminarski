package operacija.kategorijagosta;

import domen.KategorijaGosta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IzmeniKategorijuGostaSOTest {

    private DbRepository broker;
    private IzmeniKategorijuGostaSO so;
    private KategorijaGosta validnaKategorija;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new IzmeniKategorijuGostaSO(broker);
        validnaKategorija = new KategorijaGosta(1, "VIP", 10.0, true);
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
        validnaKategorija = null;
    }

    @Test
    @DisplayName("Uspešna izmena kategorije gosta poziva edit i commit")
    void testUspehMenjaKategorijuIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validnaKategorija, null);

        verify(broker).edit(validnaKategorija);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri izmeni kategorije radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).edit(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaKategorija, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da izmeni kategoriju gosta!", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(42, null));
        assertEquals("Sistem nije mogao da izmeni kategoriju gosta!", ex.getMessage());
    }

    @Test
    @DisplayName("Popust nula je dozvoljen pri izmeni")
    void testPopustNulaJeDozvoljen() throws Exception {
        validnaKategorija.setPopust(0);
        so.izvrsi(validnaKategorija, null);
        verify(broker).edit(validnaKategorija);
        verify(broker).commit();
    }
}
