package operacija.gosti;

import domen.Gost;
import domen.KategorijaGosta;
import domen.Konobar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DodajGostaSOTest {

    private DbRepository broker;
    private DodajGostaSO so;
    private Gost validanGost;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new DodajGostaSO(broker);
        validanGost = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
        validanGost = null;
    }

    @Test
    @DisplayName("preduslovi odbijaju null parametar")
    void testPredusloviOdbijajuNull() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da doda gosta", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).connect();
        verify(broker, never()).add(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("preduslovi odbijaju pogrešan tip parametra")
    void testPredusloviOdbijajuPogresanTip() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Konobar(), null));
        assertEquals("Sistem nije mogao da doda gosta", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("validan gost se uspešno dodaje i radi se commit")
    void testValidanGostSeDodaje() throws Exception {
        doNothing().when(broker).add(any());

        so.izvrsi(validanGost, null);

        verify(broker).connect();
        verify(broker).add(validanGost);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("kada broker.add baci izuzetak, radi se rollback")
    void testBrokerAddBacaIzuzetakRadiRollback() throws Exception {
        doThrow(new Exception("DB greska")).when(broker).add(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanGost, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).add(validanGost);
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
