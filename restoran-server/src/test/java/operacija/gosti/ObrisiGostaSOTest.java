package operacija.gosti;

import domen.Gost;
import domen.KategorijaGosta;
import domen.Konobar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ObrisiGostaSOTest {

    private Gost validanGost() {
        return new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
    }

    @SuppressWarnings("unchecked")
    private DbRepository stubBroker() throws Exception {
        DbRepository broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        return broker;
    }

    @Test
    @DisplayName("preduslovi odbijaju null parametar")
    void testPredusloviOdbijajuNull() throws Exception {
        DbRepository broker = stubBroker();
        ObrisiGostaSO so = new ObrisiGostaSO(broker);

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
        DbRepository broker = stubBroker();
        ObrisiGostaSO so = new ObrisiGostaSO(broker);

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Konobar(), null));
        assertEquals("Sistem nije mogao da obriše gosta", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).delete(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("validan gost se uspešno briše i radi se commit")
    void testValidanGostSeBrise() throws Exception {
        DbRepository broker = stubBroker();
        doNothing().when(broker).delete(any());
        ObrisiGostaSO so = new ObrisiGostaSO(broker);
        Gost g = validanGost();

        so.izvrsi(g, null);

        verify(broker).connect();
        verify(broker).delete(g);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("kada broker.delete baci izuzetak, radi se rollback")
    void testBrokerDeleteBacaIzuzetakRadiRollback() throws Exception {
        DbRepository broker = stubBroker();
        doThrow(new Exception("DB greska")).when(broker).delete(any());
        ObrisiGostaSO so = new ObrisiGostaSO(broker);
        Gost g = validanGost();

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).delete(g);
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
