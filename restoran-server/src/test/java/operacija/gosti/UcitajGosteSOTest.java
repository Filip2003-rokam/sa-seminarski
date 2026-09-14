package operacija.gosti;

import domen.Gost;
import domen.KategorijaGosta;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UcitajGosteSOTest {

    @SuppressWarnings("unchecked")
    private DbRepository stubBroker() throws Exception {
        DbRepository broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        return broker;
    }

    @Test
    @DisplayName("preduslovi prihvataju null parametar")
    void testPredusloviPrihvatajuNull() throws Exception {
        DbRepository broker = stubBroker();
        List<Gost> lista = List.of(
                new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true))
        );
        when(broker.getAll(any(), any())).thenReturn(lista);

        UcitajGosteSO so = new UcitajGosteSO(broker);
        so.izvrsi(null, null);

        assertEquals(lista, so.getGosti());
        verify(broker).connect();
        verify(broker).getAll(any(), any());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("uspešno učitava goste i vraća listu preko getGosti")
    void testUspesnoUcitavaGoste() throws Exception {
        DbRepository broker = stubBroker();
        Gost g1 = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Gost g2 = new Gost(2, "Ana", "Anic", new KategorijaGosta(2, "Regular", 0.0, false));
        List<Gost> lista = List.of(g1, g2);
        when(broker.getAll(any(), any())).thenReturn(lista);

        UcitajGosteSO so = new UcitajGosteSO(broker);
        so.izvrsi(new Gost(), null);

        assertSame(lista, so.getGosti());
        assertEquals(2, so.getGosti().size());
        verify(broker).getAll(any(), any());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("uspešno učitava praznu listu gostiju")
    void testUcitavaPraznuListu() throws Exception {
        DbRepository broker = stubBroker();
        when(broker.getAll(any(), any())).thenReturn(List.of());

        UcitajGosteSO so = new UcitajGosteSO(broker);
        so.izvrsi(null, null);

        assertNotNull(so.getGosti());
        assertTrue(so.getGosti().isEmpty());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("kada broker.getAll baci izuzetak, radi se rollback")
    void testBrokerGetAllBacaIzuzetakRadiRollback() throws Exception {
        DbRepository broker = stubBroker();
        when(broker.getAll(any(), any())).thenThrow(new Exception("DB greska"));

        UcitajGosteSO so = new UcitajGosteSO(broker);

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
