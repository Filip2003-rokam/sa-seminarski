package operacija.login;

import domen.Gost;
import domen.Konobar;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoginOperacijaTest {

    private DbRepository broker;
    private LoginOperacija so;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new LoginOperacija(broker);
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
    }

    @Test
    @DisplayName("preduslovi odbijaju null parametar")
    void testPredusloviOdbijajuNull() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Ne moze da se uloguje", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).connect();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("preduslovi odbijaju pogrešan tip parametra")
    void testPredusloviOdbijajuPogresanTip() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Gost(), null));
        assertEquals("Ne moze da se uloguje", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).connect();
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("ispravni kredencijali vraćaju konobara")
    void testIspravniKredencijaliVracajuKonobara() throws Exception {
        Konobar uBazi = new Konobar(1, "Petar", "Petrovic", "ppetrovic", "sifra123");
        when(broker.getAll(any(), isNull())).thenReturn(List.of(uBazi));

        Konobar unos = new Konobar();
        unos.setKorisnickoIme("ppetrovic");
        unos.setSifra("sifra123");
        so.izvrsi(unos, null);

        assertSame(uBazi, so.getKonobar());
        verify(broker).getAll(any(), isNull());
        verify(broker).connect();
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("pogrešna šifra vraća null")
    void testPogresnaSifraVracaNull() throws Exception {
        Konobar uBazi = new Konobar(1, "Petar", "Petrovic", "ppetrovic", "sifra123");
        when(broker.getAll(any(), isNull())).thenReturn(List.of(uBazi));

        Konobar unos = new Konobar();
        unos.setKorisnickoIme("ppetrovic");
        unos.setSifra("pogresna");
        so.izvrsi(unos, null);

        assertNull(so.getKonobar());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("korisnik koji nije u listi vraća null")
    void testKorisnikNijeUListiVracaNull() throws Exception {
        Konobar uBazi = new Konobar(1, "Petar", "Petrovic", "ppetrovic", "sifra123");
        when(broker.getAll(any(), isNull())).thenReturn(List.of(uBazi));

        Konobar unos = new Konobar();
        unos.setKorisnickoIme("nepoznat");
        unos.setSifra("sifra123");
        so.izvrsi(unos, null);

        assertNull(so.getKonobar());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("prazna lista konobara vraća null")
    void testPraznaListaVracaNull() throws Exception {
        when(broker.getAll(any(), isNull())).thenReturn(Collections.emptyList());

        Konobar unos = new Konobar();
        unos.setKorisnickoIme("ppetrovic");
        unos.setSifra("sifra123");
        so.izvrsi(unos, null);

        assertNull(so.getKonobar());
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("kada broker.getAll baci izuzetak, radi se rollback")
    void testBrokerGetAllBacaIzuzetakRadiRollback() throws Exception {
        when(broker.getAll(any(), isNull())).thenThrow(new Exception("DB greska"));

        Konobar unos = new Konobar();
        unos.setKorisnickoIme("ppetrovic");
        unos.setSifra("sifra123");

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(unos, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
