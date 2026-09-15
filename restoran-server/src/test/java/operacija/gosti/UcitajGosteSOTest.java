package operacija.gosti;

import domen.Gost;
import domen.KategorijaGosta;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UcitajGosteSOTest {

    private DbRepository broker;
    private UcitajGosteSO so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajGosteSO(broker);
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
    }

    @Test
    @DisplayName("preduslovi prihvataju null parametar")
    void testPredusloviPrihvatajuNull() throws Exception {
        List<Gost> lista = List.of(
                new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true))
        );
        when(broker.getAll(any(), any())).thenReturn(lista);

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
        Gost g1 = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Gost g2 = new Gost(2, "Ana", "Anic", new KategorijaGosta(2, "Regular", 0.0, false));
        List<Gost> lista = List.of(g1, g2);
        when(broker.getAll(any(), any())).thenReturn(lista);

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
        when(broker.getAll(any(), any())).thenReturn(List.of());

        so.izvrsi(null, null);

        assertNotNull(so.getGosti());
        assertTrue(so.getGosti().isEmpty());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("kada broker.getAll baci izuzetak, radi se rollback")
    void testBrokerGetAllBacaIzuzetakRadiRollback() throws Exception {
        when(broker.getAll(any(), any())).thenThrow(new Exception("DB greska"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
