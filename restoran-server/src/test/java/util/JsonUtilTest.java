package util;

import com.google.gson.Gson;
import domen.Artikal;
import domen.Gost;
import domen.KategorijaGosta;
import domen.Konobar;
import domen.Racun;
import domen.StavkaRacuna;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    private final Gson gson = JsonUtil.getGson();

    @Test
    @DisplayName("LocalDate se serijalizuje u ISO string")
    void testLocalDateSerijalizacija() {
        LocalDate datum = LocalDate.of(2024, 5, 10);
        String json = gson.toJson(datum);
        assertEquals("\"2024-05-10\"", json);
    }

    @Test
    @DisplayName("LocalDate se deserijalizuje nazad u isti objekat")
    void testLocalDateDeserijalizacija() {
        LocalDate original = LocalDate.of(2024, 5, 10);
        LocalDate rezultat = gson.fromJson("\"2024-05-10\"", LocalDate.class);
        assertEquals(original, rezultat);
    }

    @Test
    @DisplayName("LocalTime se serijalizuje u HH:mm:ss string")
    void testLocalTimeSerijalizacija() {
        LocalTime vreme = LocalTime.of(14, 30);
        String json = gson.toJson(vreme);
        assertEquals("\"14:30:00\"", json);
    }

    @Test
    @DisplayName("LocalTime se deserijalizuje nazad u isti objekat")
    void testLocalTimeDeserijalizacija() {
        LocalTime original = LocalTime.of(14, 30);
        LocalTime rezultat = gson.fromJson("\"14:30:00\"", LocalTime.class);
        assertEquals(original, rezultat);
    }

    @Test
    @DisplayName("Null LocalDate ne baca izuzetak")
    void testNullLocalDate() {
        assertDoesNotThrow(() -> {
            String json = gson.toJson((LocalDate) null);
            assertEquals("null", json);
            LocalDate rezultat = gson.fromJson("null", LocalDate.class);
            assertNull(rezultat);
        });
    }

    @Test
    @DisplayName("Null LocalTime ne baca izuzetak")
    void testNullLocalTime() {
        assertDoesNotThrow(() -> {
            String json = gson.toJson((LocalTime) null);
            assertEquals("null", json);
            LocalTime rezultat = gson.fromJson("null", LocalTime.class);
            assertNull(rezultat);
        });
    }

    @Test
    @DisplayName("Serijalizacija Racuna daje validan JSON sa ocekivanim poljima")
    void testRacunSerijalizacija() {
        Racun racun = kreirajRacun();
        String json = gson.toJson(racun);

        assertTrue(json.contains("\"idRacun\""));
        assertTrue(json.contains("\"datumIzdavanja\""));
        assertTrue(json.contains("2024-05-10"));
        assertTrue(json.contains("\"vremeIzdavanja\""));
        assertTrue(json.contains("14:30:00"));
        assertTrue(json.contains("\"ukupanIznos\""));
        assertTrue(json.contains("\"konobar\""));
        assertTrue(json.contains("Petar"));
        assertTrue(json.contains("\"gost\""));
        assertTrue(json.contains("Marko"));
        assertTrue(json.contains("\"stavke\""));
        assertTrue(json.contains("Pizza"));
        assertFalse(json.contains("\"sifra\""));
    }

    @Test
    @DisplayName("Serijalizovan Konobar ne sadrzi sifru")
    void testKonobarBezSifreUJson() {
        Konobar konobar = new Konobar(1, "Petar", "Petrovic", "ppetar", "tajnaSifra123");
        String json = gson.toJson(konobar);

        assertTrue(json.contains("Petar"));
        assertTrue(json.contains("ppetar"));
        assertFalse(json.contains("sifra"));
        assertFalse(json.contains("tajnaSifra123"));
    }

    @Test
    @DisplayName("Deserijalizacija Racuna vraca objekat sa istim vrednostima")
    void testRacunDeserijalizacija() {
        Racun original = kreirajRacun();
        String json = gson.toJson(original);
        Racun rezultat = gson.fromJson(json, Racun.class);

        assertEquals(original.getIdRacun(), rezultat.getIdRacun());
        assertEquals(original.getDatumIzdavanja(), rezultat.getDatumIzdavanja());
        assertEquals(original.getVremeIzdavanja(), rezultat.getVremeIzdavanja());
        assertEquals(original.getUkupanIznos(), rezultat.getUkupanIznos(), 0.001);
        assertEquals(original.isJeIzdat(), rezultat.isJeIzdat());

        assertNotNull(rezultat.getKonobar());
        assertEquals(original.getKonobar().getIdKonobar(), rezultat.getKonobar().getIdKonobar());
        assertEquals(original.getKonobar().getIme(), rezultat.getKonobar().getIme());
        assertEquals(original.getKonobar().getPrezime(), rezultat.getKonobar().getPrezime());

        assertNotNull(rezultat.getGost());
        assertEquals(original.getGost().getIdGost(), rezultat.getGost().getIdGost());
        assertEquals(original.getGost().getIme(), rezultat.getGost().getIme());
        assertEquals(original.getGost().getPrezime(), rezultat.getGost().getPrezime());
        assertNotNull(rezultat.getGost().getKategorijaGosta());
        assertEquals(original.getGost().getKategorijaGosta().getOpis(),
                rezultat.getGost().getKategorijaGosta().getOpis());

        assertNotNull(rezultat.getStavke());
        assertEquals(1, rezultat.getStavke().size());
        StavkaRacuna originalStavka = original.getStavke().get(0);
        StavkaRacuna rezultatStavka = rezultat.getStavke().get(0);
        assertEquals(originalStavka.getRb(), rezultatStavka.getRb());
        assertEquals(originalStavka.getKolicina(), rezultatStavka.getKolicina());
        assertEquals(originalStavka.getUkupanIznos(), rezultatStavka.getUkupanIznos(), 0.001);
        assertNotNull(rezultatStavka.getArtikal());
        assertEquals(originalStavka.getArtikal().getNaziv(), rezultatStavka.getArtikal().getNaziv());
        assertEquals(originalStavka.getArtikal().getCena(), rezultatStavka.getArtikal().getCena(), 0.001);
    }

    private Racun kreirajRacun() {
        Artikal artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
        StavkaRacuna stavka = new StavkaRacuna(0, 1, 2, 1700.0, 850.0, artikal);
        List<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavka);

        Gost gost = new Gost(1, "Marko", "Markovic", new KategorijaGosta(1, "VIP", 10.0, true));
        Konobar konobar = new Konobar(1, "Petar", "Petrovic", "ppetar", "sifra");

        return new Racun(0, LocalDate.of(2024, 5, 10), LocalTime.of(14, 30),
                1700.0, false, konobar, gost, stavke);
    }
}
