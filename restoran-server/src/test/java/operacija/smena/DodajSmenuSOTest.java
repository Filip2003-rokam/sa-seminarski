package operacija.smena;

import domen.Smena;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DodajSmenuSOTest {

    private DbRepository broker;
    private DodajSmenuSO so;
    private Smena validnaSmena;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new DodajSmenuSO(broker);
        validnaSmena = new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
    }

    @Test
    @DisplayName("Uspešno dodavanje smene poziva add i commit")
    void testUspehDodajeSmenuIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validnaSmena, null);

        verify(broker).add(validnaSmena);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri dodavanju smene radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).add(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da doda smenu.", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije smena", null));
        assertEquals("Sistem nije mogao da doda smenu.", ex.getMessage());
    }

    @Test
    @DisplayName("Null naziv baca grešku o dužini")
    void testNullNazivBacaGresku() {
        validnaSmena.setNaziv(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("Naziv smene mora imati bar 3 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Prazan naziv baca grešku o dužini")
    void testPrazanNazivBacaGresku() {
        validnaSmena.setNaziv("");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("Naziv smene mora imati bar 3 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Naziv kraći od 3 karaktera baca grešku")
    void testKratakNazivBacaGresku() {
        validnaSmena.setNaziv("Ab");
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("Naziv smene mora imati bar 3 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Null vreme početka baca grešku o vremenima")
    void testNullVremePocetkaBacaGresku() {
        validnaSmena.setVremePocetka(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("Vreme početka i kraja moraju biti uneti.", ex.getMessage());
    }

    @Test
    @DisplayName("Null vreme kraja baca grešku o vremenima")
    void testNullVremeKrajaBacaGresku() {
        validnaSmena.setVremeKraja(null);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("Vreme početka i kraja moraju biti uneti.", ex.getMessage());
    }

    @Test
    @DisplayName("Kraj pre početka baca grešku o redosledu")
    void testKrajPrePocetkaBacaGresku() {
        validnaSmena.setVremePocetka(LocalTime.of(16, 0));
        validnaSmena.setVremeKraja(LocalTime.of(8, 0));
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("Kraj smene mora biti posle početka.", ex.getMessage());
    }

    @Test
    @DisplayName("Jednaka vremena početka i kraja baca grešku o redosledu")
    void testJednakaVremenaBacaGresku() {
        LocalTime isto = LocalTime.of(10, 0);
        validnaSmena.setVremePocetka(isto);
        validnaSmena.setVremeKraja(isto);
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("Kraj smene mora biti posle početka.", ex.getMessage());
    }
}
