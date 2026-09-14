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

class DodajGostaSOTest {

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
        DodajGostaSO so = new DodajGostaSO(broker);

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem nije mogao da doda gosta", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).connect();
        verify(broker, never()).add(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("preduslovi odbijaju pogrešan tip parametra")
    void testPredusloviOdbijajuPogresanTip() throws Exception {
        DbRepository broker = stubBroker();
        DodajGostaSO so = new DodajGostaSO(broker);

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new Konobar(), null));
        assertEquals("Sistem nije mogao da doda gosta", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("preduslovi odbijaju ime kraće od 3 karaktera")
    void testPredusloviOdbijajuKratkoIme() throws Exception {
        DbRepository broker = stubBroker();
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();
        g.setIme("Ab");

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("GRESKA IME", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
    }

    @Test
    @DisplayName("preduslovi odbijaju null ime")
    void testPredusloviOdbijajuNullIme() throws Exception {
        DbRepository broker = stubBroker();
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();
        g.setIme(null);

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("GRESKA IME", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
    }

    @Test
    @DisplayName("preduslovi odbijaju prazno ime")
    void testPredusloviOdbijajuPraznoIme() throws Exception {
        DbRepository broker = stubBroker();
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();
        g.setIme("");

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("GRESKA IME", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
    }

    @Test
    @DisplayName("preduslovi odbijaju prezime kraće od 3 karaktera")
    void testPredusloviOdbijajuKratkoPrezime() throws Exception {
        DbRepository broker = stubBroker();
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();
        g.setPrezime("Xy");

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("GRESKA PREZIME", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
    }

    @Test
    @DisplayName("preduslovi odbijaju null prezime")
    void testPredusloviOdbijajuNullPrezime() throws Exception {
        DbRepository broker = stubBroker();
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();
        g.setPrezime(null);

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("GRESKA PREZIME", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
    }

    @Test
    @DisplayName("preduslovi odbijaju prazno prezime")
    void testPredusloviOdbijajuPraznoPrezime() throws Exception {
        DbRepository broker = stubBroker();
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();
        g.setPrezime("");

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("GRESKA PREZIME", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).add(any());
    }

    @Test
    @DisplayName("validan gost se uspešno dodaje i radi se commit")
    void testValidanGostSeDodaje() throws Exception {
        DbRepository broker = stubBroker();
        doNothing().when(broker).add(any());
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();

        so.izvrsi(g, null);

        verify(broker).connect();
        verify(broker).add(g);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("kada broker.add baci izuzetak, radi se rollback")
    void testBrokerAddBacaIzuzetakRadiRollback() throws Exception {
        DbRepository broker = stubBroker();
        doThrow(new Exception("DB greska")).when(broker).add(any());
        DodajGostaSO so = new DodajGostaSO(broker);
        Gost g = validanGost();

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(g, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).add(g);
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
