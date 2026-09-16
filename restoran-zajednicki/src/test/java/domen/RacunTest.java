package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RacunTest {

    private Konobar konobar;
    private Gost gost;
    private List<StavkaRacuna> stavke;
    private LocalDate datumIzdavanja;
    private LocalTime vremeIzdavanja;
    private Racun racun;

    @BeforeEach
    void setUp() {
        konobar = new Konobar(1, "Marko", "Markovic", "marko", "sifra1");
        gost = new Gost(2, "Petar", "Petrovic", null);
        Artikal artikal = new Artikal(5, "Pizza", 250.0, "Jelo");
        stavke = new ArrayList<>();
        stavke.add(new StavkaRacuna(10, 1, 2, 500.0, 250.0, artikal));
        datumIzdavanja = LocalDate.of(2024, 5, 15);
        vremeIzdavanja = LocalTime.of(14, 30, 0);
        racun = new Racun(10, datumIzdavanja, vremeIzdavanja, 1500.0, true, konobar, gost, stavke);
    }

    @AfterEach
    void tearDown() {
        konobar = null;
        gost = null;
        stavke = null;
        datumIzdavanja = null;
        vremeIzdavanja = null;
        racun = null;
    }

    private Racun kreirajValidanRacun(int id) {
        Artikal artikal = new Artikal(5, "Pizza", 250.0, "Jelo");
        List<StavkaRacuna> lista = new ArrayList<>();
        lista.add(new StavkaRacuna(id, 1, 1, 250.0, 250.0, artikal));
        return new Racun(
                id,
                LocalDate.of(2024, 1, 1),
                LocalTime.of(10, 0),
                250.0,
                false,
                new Konobar(1, "Marko", "Markovic", "marko", "sifra1"),
                new Gost(2, "Petar", "Petrovic", null),
                lista
        );
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat racuna")
    void testPrazanKonstruktorKreiraObjekat() {
        Racun r = new Racun();
        assertNotNull(r);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sva polja racuna")
    void testPunKonstruktorPostavljaSvaPolja() {
        assertEquals(10, racun.getIdRacun());
        assertEquals(datumIzdavanja, racun.getDatumIzdavanja());
        assertEquals(vremeIzdavanja, racun.getVremeIzdavanja());
        assertEquals(1500.0, racun.getUkupanIznos());
        assertTrue(racun.isJeIzdat());
        assertEquals(konobar, racun.getKonobar());
        assertEquals(gost, racun.getGost());
        assertEquals(stavke, racun.getStavke());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za negativan id")
    void testPunKonstruktorBacaIzuzetakZaNegativanId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Racun(-1, datumIzdavanja, vremeIzdavanja, 1500.0, true, konobar, gost, stavke));
        assertEquals("Id racuna ne sme biti negativan.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null datum")
    void testPunKonstruktorBacaIzuzetakZaNullDatum() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Racun(10, null, vremeIzdavanja, 1500.0, true, konobar, gost, stavke));
        assertEquals("Datum izdavanja racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null vreme")
    void testPunKonstruktorBacaIzuzetakZaNullVreme() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Racun(10, datumIzdavanja, null, 1500.0, true, konobar, gost, stavke));
        assertEquals("Vreme izdavanja racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za nevalidan iznos")
    void testPunKonstruktorBacaIzuzetakZaNevalidanIznos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Racun(10, datumIzdavanja, vremeIzdavanja, 0.0, true, konobar, gost, stavke));
        assertEquals("Ukupan iznos racuna mora biti veci od nule.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null konobara")
    void testPunKonstruktorBacaIzuzetakZaNullKonobara() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Racun(10, datumIzdavanja, vremeIzdavanja, 1500.0, true, null, gost, stavke));
        assertEquals("Konobar racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null gosta")
    void testPunKonstruktorBacaIzuzetakZaNullGosta() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Racun(10, datumIzdavanja, vremeIzdavanja, 1500.0, true, konobar, null, stavke));
        assertEquals("Gost racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za prazne stavke")
    void testPunKonstruktorBacaIzuzetakZaPrazneStavke() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Racun(10, datumIzdavanja, vremeIzdavanja, 1500.0, true, konobar, gost, new ArrayList<>()));
        assertEquals("Racun mora sadrzati barem jednu stavku.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za idRacun rade ispravno")
    void testSetGetIdRacun() {
        Racun r = new Racun();
        r.setIdRacun(20);
        assertEquals(20, r.getIdRacun());
    }

    @ParameterizedTest
    @MethodSource("nevalidniIdRacun")
    @DisplayName("setIdRacun baca izuzetak za negativan id")
    void testSetIdRacunBacaIzuzetakZaNegativanId(int id) {
        Racun r = new Racun();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> r.setIdRacun(id));
        assertEquals("Id racuna ne sme biti negativan.", ex.getMessage());
    }

    static Stream<Arguments> nevalidniIdRacun() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(-50)
        );
    }

    @Test
    @DisplayName("Setter i getter za datumIzdavanja rade ispravno")
    void testSetGetDatumIzdavanja() {
        Racun r = new Racun();
        LocalDate datum = LocalDate.of(2025, 1, 1);
        r.setDatumIzdavanja(datum);
        assertEquals(datum, r.getDatumIzdavanja());
    }

    @Test
    @DisplayName("setDatumIzdavanja baca izuzetak za null")
    void testSetDatumIzdavanjaBacaIzuzetakZaNull() {
        Racun r = new Racun();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> r.setDatumIzdavanja(null));
        assertEquals("Datum izdavanja racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za vremeIzdavanja rade ispravno")
    void testSetGetVremeIzdavanja() {
        Racun r = new Racun();
        LocalTime vreme = LocalTime.of(18, 45);
        r.setVremeIzdavanja(vreme);
        assertEquals(vreme, r.getVremeIzdavanja());
    }

    @Test
    @DisplayName("setVremeIzdavanja baca izuzetak za null")
    void testSetVremeIzdavanjaBacaIzuzetakZaNull() {
        Racun r = new Racun();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> r.setVremeIzdavanja(null));
        assertEquals("Vreme izdavanja racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za ukupanIznos rade ispravno")
    void testSetGetUkupanIznos() {
        Racun r = new Racun();
        r.setUkupanIznos(999.5);
        assertEquals(999.5, r.getUkupanIznos());
    }

    @ParameterizedTest
    @MethodSource("nevalidniUkupanIznos")
    @DisplayName("setUkupanIznos baca izuzetak za nevalidan iznos")
    void testSetUkupanIznosBacaIzuzetakZaNevalidanIznos(double iznos) {
        Racun r = new Racun();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> r.setUkupanIznos(iznos));
        assertEquals("Ukupan iznos racuna mora biti veci od nule.", ex.getMessage());
    }

    static Stream<Arguments> nevalidniUkupanIznos() {
        return Stream.of(
                Arguments.of(0.0),
                Arguments.of(-1.0),
                Arguments.of(-100.5)
        );
    }

    @Test
    @DisplayName("Setter i getter za jeIzdat rade ispravno")
    void testSetGetJeIzdat() {
        Racun r = new Racun();
        r.setJeIzdat(false);
        assertFalse(r.isJeIzdat());
        r.setJeIzdat(true);
        assertTrue(r.isJeIzdat());
    }

    @Test
    @DisplayName("Setter i getter za ugnjezdeni Konobar rade ispravno")
    void testSetGetKonobar() {
        Racun r = new Racun();
        Konobar k = new Konobar(5, "Ana", "Anic", "ana", "pass");
        r.setKonobar(k);
        assertEquals(k, r.getKonobar());
        assertEquals(5, r.getKonobar().getIdKonobar());
    }

    @Test
    @DisplayName("setKonobar baca izuzetak za null")
    void testSetKonobarBacaIzuzetakZaNull() {
        Racun r = new Racun();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> r.setKonobar(null));
        assertEquals("Konobar racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za ugnjezdeni Gost rade ispravno")
    void testSetGetGost() {
        Racun r = new Racun();
        Gost g = new Gost(8, "Jovan", "Jovic", null);
        r.setGost(g);
        assertEquals(g, r.getGost());
        assertEquals(8, r.getGost().getIdGost());
    }

    @Test
    @DisplayName("setGost baca izuzetak za null")
    void testSetGostBacaIzuzetakZaNull() {
        Racun r = new Racun();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> r.setGost(null));
        assertEquals("Gost racuna ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za listu stavki rade ispravno")
    void testSetGetStavke() {
        Racun r = new Racun();
        Artikal a = new Artikal(1, "Sok", 100.0, "Pice");
        List<StavkaRacuna> lista = new ArrayList<>();
        lista.add(new StavkaRacuna(1, 1, 1, 100.0, 100.0, a));
        r.setStavke(lista);
        assertEquals(lista, r.getStavke());
        assertEquals(1, r.getStavke().size());
    }

    @ParameterizedTest
    @MethodSource("nevalidneStavke")
    @DisplayName("setStavke baca izuzetak za null ili praznu listu")
    void testSetStavkeBacaIzuzetakZaNevalidnuListu(List<StavkaRacuna> lista) {
        Racun r = new Racun();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> r.setStavke(lista));
        assertEquals("Racun mora sadrzati barem jednu stavku.", ex.getMessage());
    }

    static Stream<Arguments> nevalidneStavke() {
        return Stream.of(
                Arguments.of((List<StavkaRacuna>) null),
                Arguments.of(Collections.emptyList()),
                Arguments.of(new ArrayList<StavkaRacuna>())
        );
    }

    @Test
    @DisplayName("equals vraca true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(racun.equals(racun));
    }

    @Test
    @DisplayName("equals vraca false kada se racun poredi sa null")
    void testEqualsSaNull() {
        assertFalse(racun.equals(null));
    }

    @Test
    @DisplayName("equals vraca false kada se racun poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(racun.equals("nije racun"));
    }

    @ParameterizedTest
    @MethodSource("podaciZaEquals")
    @DisplayName("equals poredi racune po identifikatoru")
    void testEqualsPoId(Racun prvi, Racun drugi, boolean ocekivano) {
        assertEquals(ocekivano, prvi.equals(drugi));
    }

    static Stream<Arguments> podaciZaEquals() {
        Artikal artikal = new Artikal(5, "Pizza", 250.0, "Jelo");
        List<StavkaRacuna> stavkeA = new ArrayList<>();
        stavkeA.add(new StavkaRacuna(10, 1, 1, 250.0, 250.0, artikal));
        List<StavkaRacuna> stavkeB = new ArrayList<>();
        stavkeB.add(new StavkaRacuna(11, 1, 2, 500.0, 250.0, artikal));
        Konobar k = new Konobar(1, "Marko", "Markovic", "marko", "sifra1");
        Gost g = new Gost(2, "Petar", "Petrovic", null);
        return Stream.of(
                Arguments.of(
                        new Racun(10, LocalDate.of(2024, 5, 15), LocalTime.of(14, 30), 1500.0, true, k, g, stavkeA),
                        new Racun(10, LocalDate.of(2020, 1, 1), LocalTime.of(10, 0), 1.0, false, k, g, stavkeB),
                        true
                ),
                Arguments.of(
                        new Racun(10, LocalDate.of(2024, 5, 15), LocalTime.of(14, 30), 1500.0, true, k, g, stavkeA),
                        new Racun(11, LocalDate.of(2024, 5, 15), LocalTime.of(14, 30), 1500.0, true, k, g, stavkeA),
                        false
                )
        );
    }

    @Test
    @DisplayName("Jednaki racuni imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        Racun drugi = kreirajValidanRacun(10);
        assertEquals(racun.hashCode(), drugi.hashCode());
    }

    @Test
    @DisplayName("Visestruki pozivi hashCode na istom objektu vracaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(racun.hashCode(), racun.hashCode());
    }

    @Test
    @DisplayName("toString sadrzi id, datum, vreme, ukupanIznos i jeIzdat")
    void testToStringFormat() {
        String rezultat = racun.toString();
        assertTrue(rezultat.contains("id=10"));
        assertTrue(rezultat.contains("datum=" + datumIzdavanja));
        assertTrue(rezultat.contains("vreme=" + vremeIzdavanja));
        assertTrue(rezultat.contains("ukupanIznos=1500.0"));
        assertTrue(rezultat.contains("jeIzdat=true"));
    }

    @Test
    @DisplayName("vratiNazivTabele vraca ime tabele racun")
    void testVratiNazivTabele() {
        assertEquals("racun", racun.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraca uslov racun.idRacun bez razmaka u imenu tabele")
    void testVratiPrimarniKljuc() {
        assertEquals("racun.idRacun=10", racun.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraca nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals(
                "datumIzdavanja, vremeIzdavanja, ukupanIznos, jeIzdat, idKonobar, idGost",
                racun.vratiKoloneZaUbacivanje()
        );
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraca SQL Date/Time i jeIzdat kao 1 ili 0")
    void testVratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum = java.sql.Date.valueOf(datumIzdavanja);
        java.sql.Time sqlVreme = java.sql.Time.valueOf(vremeIzdavanja);
        assertEquals(
                "'" + sqlDatum + "', '" + sqlVreme + "', 1500.0, 1, 1, 2",
                racun.vratiVrednostiZaUbacivanje()
        );
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje koristi 0 kada racun nije izdat")
    void testVratiVrednostiZaUbacivanjeJeIzdatFalse() {
        racun.setJeIzdat(false);
        java.sql.Date sqlDatum = java.sql.Date.valueOf(datumIzdavanja);
        java.sql.Time sqlVreme = java.sql.Time.valueOf(vremeIzdavanja);
        assertEquals(
                "'" + sqlDatum + "', '" + sqlVreme + "', 1500.0, 0, 1, 2",
                racun.vratiVrednostiZaUbacivanje()
        );
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraca SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals(
                "datumIzdavanja='2024-05-15', vremeIzdavanja='14:30', ukupanIznos=1500.0, jeIzdat=true, idKonobar=1, idGost=2",
                racun.vratiVrednostiZaIzmenu()
        );
    }

    @Test
    @DisplayName("vratiListu vraca praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(racun.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dva racuna")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idKonobar")).thenReturn(1, 3);
        when(rs.getString("k.ime")).thenReturn("Marko", "Ana");
        when(rs.getString("k.prezime")).thenReturn("Markovic", "Anic");
        when(rs.getString("k.korisnickoIme")).thenReturn("marko", "ana");
        when(rs.getString("k.sifra")).thenReturn("s1", "s2");
        when(rs.getInt("idGost")).thenReturn(2, 4);
        when(rs.getString("g.ime")).thenReturn("Petar", "Jovan");
        when(rs.getString("g.prezime")).thenReturn("Petrovic", "Jovic");
        when(rs.getInt("idRacun")).thenReturn(10, 11);
        when(rs.getDate("datumIzdavanja")).thenReturn(
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 15)),
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 15)),
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 16)),
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 16))
        );
        when(rs.getTime("vremeIzdavanja")).thenReturn(
                java.sql.Time.valueOf(LocalTime.of(14, 30, 0)),
                java.sql.Time.valueOf(LocalTime.of(14, 30, 0)),
                java.sql.Time.valueOf(LocalTime.of(15, 0, 0)),
                java.sql.Time.valueOf(LocalTime.of(15, 0, 0))
        );
        when(rs.getDouble("ukupanIznos")).thenReturn(1500.0, 800.0);
        when(rs.getBoolean("jeIzdat")).thenReturn(true, false);

        List<ApstraktniDomenskiObjekat> lista = racun.vratiListu(rs);

        assertEquals(2, lista.size());
        Racun prvi = (Racun) lista.get(0);
        assertEquals(10, prvi.getIdRacun());
        assertEquals(LocalDate.of(2024, 5, 15), prvi.getDatumIzdavanja());
        assertEquals(LocalTime.of(14, 30, 0), prvi.getVremeIzdavanja());
        assertEquals(1500.0, prvi.getUkupanIznos());
        assertTrue(prvi.isJeIzdat());
        assertEquals(1, prvi.getKonobar().getIdKonobar());
        assertEquals("Marko", prvi.getKonobar().getIme());
        assertEquals("Markovic", prvi.getKonobar().getPrezime());
        assertEquals("marko", prvi.getKonobar().getKorisnickoIme());
        assertEquals("s1", prvi.getKonobar().getSifra());
        assertEquals(2, prvi.getGost().getIdGost());
        assertEquals("Petar", prvi.getGost().getIme());
        assertEquals("Petrovic", prvi.getGost().getPrezime());
        assertNull(prvi.getStavke());

        Racun drugi = (Racun) lista.get(1);
        assertEquals(11, drugi.getIdRacun());
        assertEquals(LocalDate.of(2024, 5, 16), drugi.getDatumIzdavanja());
        assertEquals(LocalTime.of(15, 0, 0), drugi.getVremeIzdavanja());
        assertEquals(800.0, drugi.getUkupanIznos());
        assertFalse(drugi.isJeIzdat());
        assertEquals(3, drugi.getKonobar().getIdKonobar());
        assertEquals("Ana", drugi.getKonobar().getIme());
        assertEquals(4, drugi.getGost().getIdGost());
        assertEquals("Jovan", drugi.getGost().getIme());
        assertNull(drugi.getStavke());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira racun bez ugnjezdenih objekata")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idRacun")).thenReturn(7);
        when(rs.getDate("datumIzdavanja")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2024, 6, 1)));
        when(rs.getTime("vremeIzdavanja")).thenReturn(java.sql.Time.valueOf(LocalTime.of(12, 0, 0)));
        when(rs.getDouble("ukupanIznos")).thenReturn(420.0);
        when(rs.getBoolean("jeIzdat")).thenReturn(true);

        Racun rezultat = (Racun) racun.vratiObjekatIzRS(rs);

        assertEquals(7, rezultat.getIdRacun());
        assertEquals(LocalDate.of(2024, 6, 1), rezultat.getDatumIzdavanja());
        assertEquals(LocalTime.of(12, 0, 0), rezultat.getVremeIzdavanja());
        assertEquals(420.0, rezultat.getUkupanIznos());
        assertTrue(rezultat.isJeIzdat());
        assertNull(rezultat.getKonobar());
        assertNull(rezultat.getGost());
        assertNull(rezultat.getStavke());
    }
}
