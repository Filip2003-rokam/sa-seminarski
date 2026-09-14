package operacija.konobar;

import domen.Konobar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IzmeniKonobaraSOTest {

    private DbRepository broker;
    private IzmeniKonobaraSO so;
    private Konobar validanKonobar;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new IzmeniKonobaraSO(broker);
        validanKonobar = new Konobar(1, "Marko", "Markovic", "mmarkovic", "sifra123");
    }

    @Test
    @DisplayName("Uspešna izmena konobara poziva edit i commit")
    void testUspehMenjaKonobaraIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validanKonobar, null);

        verify(broker).edit(validanKonobar);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri izmeni konobara radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).edit(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da izmeni konobara!", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije konobar", null));
        assertEquals("Sistem nije mogao da izmeni konobara!", ex.getMessage());
    }

    @Test
    @DisplayName("Null ime baca GRESKA IME")
    void testNullImeBacaGresku() {
        validanKonobar.setIme(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA IME", ex.getMessage());
    }

    @Test
    @DisplayName("Prazno ime baca GRESKA IME")
    void testPraznoImeBacaGresku() {
        validanKonobar.setIme("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA IME", ex.getMessage());
    }

    @Test
    @DisplayName("Ime kraće od 2 karaktera baca GRESKA IME")
    void testKratkoImeBacaGresku() {
        validanKonobar.setIme("A");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA IME", ex.getMessage());
    }

    @Test
    @DisplayName("Null prezime baca GRESKA PREZIME")
    void testNullPrezimeBacaGresku() {
        validanKonobar.setPrezime(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA PREZIME", ex.getMessage());
    }

    @Test
    @DisplayName("Prazno prezime baca GRESKA PREZIME")
    void testPraznoPrezimeBacaGresku() {
        validanKonobar.setPrezime("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA PREZIME", ex.getMessage());
    }

    @Test
    @DisplayName("Prezime kraće od 2 karaktera baca GRESKA PREZIME")
    void testKratkoPrezimeBacaGresku() {
        validanKonobar.setPrezime("X");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA PREZIME", ex.getMessage());
    }

    @Test
    @DisplayName("Null korisničko ime baca GRESKA USERNAME")
    void testNullUsernameBacaGresku() {
        validanKonobar.setKorisnickoIme(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA USERNAME", ex.getMessage());
    }

    @Test
    @DisplayName("Prazno korisničko ime baca GRESKA USERNAME")
    void testPrazanUsernameBacaGresku() {
        validanKonobar.setKorisnickoIme("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA USERNAME", ex.getMessage());
    }

    @Test
    @DisplayName("Null šifra baca GRESKA PASSWORD")
    void testNullSifraBacaGresku() {
        validanKonobar.setSifra(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA PASSWORD", ex.getMessage());
    }

    @Test
    @DisplayName("Prazna šifra baca GRESKA PASSWORD")
    void testPraznaSifraBacaGresku() {
        validanKonobar.setSifra("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validanKonobar, null));
        assertEquals("GRESKA PASSWORD", ex.getMessage());
    }
}
