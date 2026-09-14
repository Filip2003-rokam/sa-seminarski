package operacija.artikal;

import domen.Artikal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DodajArtikalSOTest {

    private DbRepository broker;
    private DodajArtikalSO so;
    private Artikal validanArtikal;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new DodajArtikalSO(broker);
        validanArtikal = new Artikal(1, "Pizza", 850.0, "Jelo");
    }

    @Test
    @DisplayName("Uspešno dodavanje artikla poziva add i commit")
    void testUspehDodajeArtikalIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validanArtikal, null);

        verify(broker).add(validanArtikal);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri dodavanju artikla radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).add(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da doda artikal", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije artikal", null));
        assertEquals("Sistem nije mogao da doda artikal", ex.getMessage());
    }

    @Test
    @DisplayName("Null naziv baca GRESKA NAZIV")
    void testNullNazivBacaGresku() {
        validanArtikal.setNaziv(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("GRESKA NAZIV", ex.getMessage());
    }

    @Test
    @DisplayName("Prazan naziv baca GRESKA NAZIV")
    void testPrazanNazivBacaGresku() {
        validanArtikal.setNaziv("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("GRESKA NAZIV", ex.getMessage());
    }

    @Test
    @DisplayName("Naziv kraći od 2 karaktera baca GRESKA NAZIV")
    void testKratakNazivBacaGresku() {
        validanArtikal.setNaziv("A");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("GRESKA NAZIV", ex.getMessage());
    }

    @Test
    @DisplayName("Null tip baca GRESKA TIP")
    void testNullTipBacaGresku() {
        validanArtikal.setTip(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("GRESKA TIP", ex.getMessage());
    }

    @Test
    @DisplayName("Prazan tip baca GRESKA TIP")
    void testPrazanTipBacaGresku() {
        validanArtikal.setTip("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("GRESKA TIP", ex.getMessage());
    }

    @Test
    @DisplayName("Cena nula baca GRESKA CENA")
    void testCenaNulaBacaGresku() {
        validanArtikal.setCena(0);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("GRESKA CENA", ex.getMessage());
    }

    @Test
    @DisplayName("Negativna cena baca GRESKA CENA")
    void testNegativnaCenaBacaGresku() {
        validanArtikal.setCena(-10);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanArtikal, null));
        assertEquals("GRESKA CENA", ex.getMessage());
    }
}
