package operacija.kategorijagosta;

import domen.KategorijaGosta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DodajKategorijuGostaSOTest {

    private DbRepository broker;
    private DodajKategorijuGostaSO so;
    private KategorijaGosta validnaKategorija;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new DodajKategorijuGostaSO(broker);
        validnaKategorija = new KategorijaGosta(1, "VIP", 10.0, true);
    }

    @Test
    @DisplayName("Uspešno dodavanje kategorije gosta poziva add i commit")
    void testUspehDodajeKategorijuIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validnaKategorija, null);

        verify(broker).add(validnaKategorija);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri dodavanju kategorije radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).add(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaKategorija, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da doda kategoriju gosta!", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije kategorija", null));
        assertEquals("Sistem nije mogao da doda kategoriju gosta!", ex.getMessage());
    }

    @Test
    @DisplayName("Null opis baca GRESKA NAZIV")
    void testNullOpisBacaGresku() {
        validnaKategorija.setOpis(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaKategorija, null));
        assertEquals("GRESKA NAZIV", ex.getMessage());
    }

    @Test
    @DisplayName("Prazan opis baca GRESKA NAZIV")
    void testPrazanOpisBacaGresku() {
        validnaKategorija.setOpis("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaKategorija, null));
        assertEquals("GRESKA NAZIV", ex.getMessage());
    }

    @Test
    @DisplayName("Opis kraći od 2 karaktera baca GRESKA NAZIV")
    void testKratakOpisBacaGresku() {
        validnaKategorija.setOpis("A");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaKategorija, null));
        assertEquals("GRESKA NAZIV", ex.getMessage());
    }

    @Test
    @DisplayName("Negativan popust baca GRESKA POPUST")
    void testNegativanPopustBacaGresku() {
        validnaKategorija.setPopust(-1);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaKategorija, null));
        assertEquals("GRESKA POPUST - mora biti >= 0", ex.getMessage());
    }

    @Test
    @DisplayName("Popust nula je dozvoljen pri dodavanju")
    void testPopustNulaJeDozvoljen() throws Exception {
        validnaKategorija.setPopust(0);
        so.izvrsi(validnaKategorija, null);
        verify(broker).add(validnaKategorija);
        verify(broker).commit();
    }
}
