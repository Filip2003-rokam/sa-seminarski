package operacija.smena;

import domen.Smena;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IzmeniSmenuSOTest {

    private DbRepository broker;
    private IzmeniSmenuSO so;
    private Smena validnaSmena;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new IzmeniSmenuSO(broker);
        validnaSmena = new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
        validnaSmena = null;
    }

    @Test
    @DisplayName("Uspešna izmena smene poziva edit i commit")
    void testUspehMenjaSmenuIPotvrdjujeTransakciju() throws Exception {
        so.izvrsi(validnaSmena, null);

        verify(broker).edit(validnaSmena);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri izmeni smene radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("db")).when(broker).edit(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaSmena, null));
        assertEquals("db", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da izmeni smenu.", ex.getMessage());
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije smena", null));
        assertEquals("Sistem nije mogao da izmeni smenu.", ex.getMessage());
    }

    @ParameterizedTest
    @MethodSource("nevalidniVremenskiIntervali")
    @DisplayName("Izmena odbija jednaka vremena")
    void testNevalidanVremenskiIntervalBacaGresku(
            LocalTime pocetak, LocalTime kraj) throws Exception {
        Smena nevalidnaSmena = new Smena();
        nevalidnaSmena.setIdSmena(1);
        nevalidnaSmena.setNaziv("Jutarnja");
        nevalidnaSmena.setVremePocetka(pocetak);
        nevalidnaSmena.setVremeKraja(kraj);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> so.izvrsi(nevalidnaSmena, null));

        assertEquals("Vreme pocetka i kraja smene ne sme biti jednako.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).edit(any());
        verify(broker, never()).commit();
    }

    static Stream<Arguments> nevalidniVremenskiIntervali() {
        return Stream.of(
                Arguments.of(LocalTime.of(12, 0), LocalTime.of(12, 0))
        );
    }

    @Test
    @DisplayName("Izmena prihvata smenu koja prelazi ponoc")
    void testIzmenaPrihvataSmenuKojaPrelaziPonoc() throws Exception {
        Smena nocna = new Smena(2, "Nocna", LocalTime.of(22, 0), LocalTime.of(6, 0));

        so.izvrsi(nocna, null);

        verify(broker).edit(nocna);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }
}
