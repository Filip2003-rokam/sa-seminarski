package operacija.konobar;

import domen.Konobar;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UcitajKonobareSOTest {

    private DbRepository broker;
    private UcitajKonobareSO so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new UcitajKonobareSO(broker);
    }

    @Test
    @DisplayName("Uspešno učitavanje konobara sa null parametrom")
    void testUspehUcitavaKonobareSaNullParametrom() throws Exception {
        List<Konobar> lista = Arrays.asList(
                new Konobar(1, "Marko", "Markovic", "mmarkovic", "sifra123"),
                new Konobar(2, "Ana", "Anic", "aanic", "sifra456")
        );
        when(broker.getAll(any(Konobar.class), eq(""))).thenReturn(lista);

        so.izvrsi(null, null);

        assertEquals(lista, so.getKonobari());
        verify(broker).getAll(any(Konobar.class), eq(""));
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Neprazan parametar baca grešku za učitavanje")
    void testNeprazanParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Konobar(), null));
        assertEquals("Za učitavanje konobara ne treba parametar!", ex.getMessage());
    }

    @Test
    @DisplayName("Greška brokera pri učitavanju konobara radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        when(broker.getAll(any(Konobar.class), eq(""))).thenThrow(new Exception("db"));

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
