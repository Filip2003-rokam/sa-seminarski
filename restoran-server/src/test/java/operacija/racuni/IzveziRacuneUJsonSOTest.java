package operacija.racuni;

import domen.Artikal;
import domen.Gost;
import domen.KategorijaGosta;
import domen.Konobar;
import domen.Racun;
import domen.StavkaRacuna;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import repository.db.DbRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IzveziRacuneUJsonSOTest {

    private DbRepository broker;
    private IzveziRacuneUJsonSO so;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws Exception {
        broker = mock(DbRepository.class);
        doNothing().when(broker).connect();
        doNothing().when(broker).commit();
        doNothing().when(broker).rollback();
        so = new IzveziRacuneUJsonSO(broker, tempDir.toFile());
    }

    @AfterEach
    void tearDown() {
        broker = null;
        so = null;
    }

    @Test
    @DisplayName("Null parametar baca grešku")
    void testNullParametarBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(null, null));
        assertTrue(ex.getMessage().contains("nije prosledjena")
                || ex.getMessage().contains("nije prosleđena"));
        verify(broker).rollback();
    }

    @Test
    @DisplayName("Prazna lista baca grešku")
    void testPraznaListaBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi(new ArrayList<>(), null));
        assertTrue(ex.getMessage().contains("prazna"));
        verify(broker).rollback();
    }

    @Test
    @DisplayName("Pogrešan tip parametra baca grešku")
    void testPogresanTipBacaGresku() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> so.izvrsi("nije lista", null));
        assertTrue(ex.getMessage().contains("nije lista"));
        verify(broker).rollback();
    }

    @Test
    @DisplayName("Preduslovi prolaze za validnu listu računa")
    void testPredusloviProlazeZaValidnuListu() throws Exception {
        assertDoesNotThrow(() -> so.izvrsi(kreirajListuRacuna(), null));
        verify(broker).commit();
        verify(broker, never()).rollback();
    }

    @Test
    @DisplayName("IzvrsiOperaciju kreira fajl sa validnim JSON-om")
    void testIzvrsiKreiraFajlSaJsonPodacima() throws Exception {
        List<Racun> racuni = kreirajListuRacuna();
        so.izvrsi(racuni, null);

        String putanja = so.getPutanjaFajla();
        assertNotNull(putanja);
        File fajl = new File(putanja);
        assertTrue(fajl.exists());
        assertTrue(fajl.getName().startsWith("izvestaj_racuni_"));
        assertTrue(fajl.getName().endsWith(".json"));
        assertEquals(tempDir.toFile().getAbsolutePath(), fajl.getParentFile().getAbsolutePath());

        String sadrzaj = Files.readString(fajl.toPath());
        assertTrue(sadrzaj.contains("2024-05-10"));
        assertTrue(sadrzaj.contains("14:30:00"));
        assertTrue(sadrzaj.contains("Marko"));
        assertTrue(sadrzaj.contains("Petar"));
        assertTrue(sadrzaj.contains("Pizza"));
        assertTrue(sadrzaj.contains("ukupanIznos"));
    }

    private List<Racun> kreirajListuRacuna() {
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        StavkaRacuna stavka = new StavkaRacuna(0, 1, 2, 1700.0, 850.0, artikal);
        List<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavka);

        Gost gost = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Konobar konobar = new Konobar(1, "Petar", "Petrovic", "ppetar", "sifra");

        Racun racun = new Racun(0, LocalDate.of(2024, 5, 10), LocalTime.of(14, 30),
                1700.0, false, konobar, gost, stavke);
        return new ArrayList<>(Collections.singletonList(racun));
    }
}
