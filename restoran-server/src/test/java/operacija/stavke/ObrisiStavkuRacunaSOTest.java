package operacija.stavke;

import domen.Artikal;
import domen.StavkaRacuna;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ObrisiStavkuRacunaSOTest {

    private DbRepository broker;
    private ObrisiStavkuRacunaSO so;
    private StavkaRacuna validnaStavka;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new ObrisiStavkuRacunaSO(broker);
        validnaStavka = kreirajValidnuStavku();
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
        validnaStavka = null;
    }

    private StavkaRacuna kreirajValidnuStavku() {
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        return new StavkaRacuna(5, 1, 2, 1700.0, 850.0, artikal);
    }

    @Test
    @DisplayName("Null parametar baca grešku tipa")
    void testNullParametarBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertEquals("Sistem ne može da obriše stavku jer prosleđeni objekat nije validan.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).delete(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Parametar pogrešnog tipa baca grešku tipa")
    void testPogresanTipParametaraBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije stavka", null));
        assertEquals("Sistem ne može da obriše stavku jer prosleđeni objekat nije validan.", ex.getMessage());
        verify(broker).rollback();
        verify(broker, never()).delete(any());
        verify(broker, never()).commit();
    }

    @Test
    @DisplayName("Uspešno brisanje stavke poziva delete i commit")
    void testUspehBriseStavkuIPotvrdjujeTransakciju() throws Exception {
        doNothing().when(broker).delete(any());

        so.izvrsi(validnaStavka, null);

        verify(broker).connect();
        verify(broker).delete(validnaStavka);
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("Greška brokera pri brisanju stavke radi rollback")
    void testBrokerGreskaRadiRollback() throws Exception {
        doThrow(new Exception("DB greska")).when(broker).delete(any());

        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(validnaStavka, null));
        assertEquals("DB greska", ex.getMessage());
        verify(broker).delete(validnaStavka);
        verify(broker).rollback();
        verify(broker, never()).commit();
    }
}
