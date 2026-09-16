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

class ObrisiGostaSOTest {

    private DbRepository broker;
    private ObrisiGostaSO so;
    private Gost validanGost;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new ObrisiGostaSO(broker);
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
        assertEquals("Sistem nije mogao da obriše gosta", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).connect();
        verify(broker, never()).delete(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("preduslovi odbijaju pogrešan tip parametra")
    void testPredusloviOdbijajuPogresanTip() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Konobar(), null));
        assertEquals("Sistem nije mogao da obriše gosta", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).delete(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("validan gost se uspešno briše i radi se commit")
    void testValidanGostSeBrise() throws Exception {
        doNothing().when(broker).delete(any());

        so.izvrsi(validanGost, null);

        verify(broker).connect();
        verify(broker).delete(validanGost);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("kada broker.delete baci izuzetak, radi se rollback")
    void testBrokerDeleteBacaIzuzetakRadiRollback() throws Exception {
        doThrow(new Exception("DB greska")).when(broker).delete(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanGost, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).delete(validanGost);
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
