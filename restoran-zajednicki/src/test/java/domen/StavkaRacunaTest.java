package domen;

import java.sql.ResultSet;
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

class StavkaRacunaTest {

    private Artikal artikal;
    private StavkaRacuna stavka;

    @BeforeEach
    void setUp() {
        artikal = new Artikal(5, "Pizza", 850.0, "Jelo");
        stavka = new StavkaRacuna(10, 1, 2, 1700.0, 850.0, artikal);
    }

    @AfterEach
    void tearDown() {
        artikal = null;
        stavka = null;
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat stavke racuna")
    void testPrazanKonstruktorKreiraObjekat() {
        StavkaRacuna sr = new StavkaRacuna();
        assertNotNull(sr);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sva polja stavke")
    void testPunKonstruktorPostavljaSvaPolja() {
        assertEquals(10, stavka.getIdRacun());
        assertEquals(1, stavka.getRb());
        assertEquals(2, stavka.getKolicina());
        assertEquals(1700.0, stavka.getUkupanIznos());
        assertEquals(850.0, stavka.getCena());
        assertEquals(artikal, stavka.getArtikal());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za negativan id racuna")
    void testPunKonstruktorBacaIzuzetakZaNegativanIdRacun() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new StavkaRacuna(-1, 1, 2, 1700.0, 850.0, artikal));
        assertEquals("Id racuna na stavci ne sme biti negativan.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za negativan rb")
    void testPunKonstruktorBacaIzuzetakZaNegativanRb() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new StavkaRacuna(10, -1, 2, 1700.0, 850.0, artikal));
        assertEquals("Redni broj stavke ne sme biti negativan.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za nevalidnu kolicinu")
    void testPunKonstruktorBacaIzuzetakZaNevalidnuKolicinu() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new StavkaRacuna(10, 1, 0, 1700.0, 850.0, artikal));
        assertEquals("Kolicina stavke mora biti veca od nule.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za nevalidan ukupan iznos")
    void testPunKonstruktorBacaIzuzetakZaNevalidanUkupanIznos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new StavkaRacuna(10, 1, 2, 0.0, 850.0, artikal));
        assertEquals("Ukupan iznos stavke mora biti veci od nule.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za nevalidnu cenu")
    void testPunKonstruktorBacaIzuzetakZaNevalidnuCenu() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new StavkaRacuna(10, 1, 2, 1700.0, 0.0, artikal));
        assertEquals("Cena stavke mora biti veca od nule.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null artikal")
    void testPunKonstruktorBacaIzuzetakZaNullArtikal() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new StavkaRacuna(10, 1, 2, 1700.0, 850.0, null));
        assertEquals("Artikal stavke ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za idRacun rade ispravno")
    void testSetGetIdRacun() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setIdRacun(20);
        assertEquals(20, sr.getIdRacun());
    }

    @ParameterizedTest
    @MethodSource("nevalidniIdRacun")
    @DisplayName("setIdRacun baca izuzetak za negativan id")
    void testSetIdRacunBacaIzuzetakZaNegativanId(int id) {
        StavkaRacuna sr = new StavkaRacuna();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> sr.setIdRacun(id));
        assertEquals("Id racuna na stavci ne sme biti negativan.", ex.getMessage());
    }

    static Stream<Arguments> nevalidniIdRacun() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(-10)
        );
    }

    @Test
    @DisplayName("Setter i getter za rb rade ispravno")
    void testSetGetRb() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setRb(3);
        assertEquals(3, sr.getRb());
    }

    @ParameterizedTest
    @MethodSource("nevalidniRb")
    @DisplayName("setRb baca izuzetak za negativan rb")
    void testSetRbBacaIzuzetakZaNegativanRb(int rb) {
        StavkaRacuna sr = new StavkaRacuna();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> sr.setRb(rb));
        assertEquals("Redni broj stavke ne sme biti negativan.", ex.getMessage());
    }

    static Stream<Arguments> nevalidniRb() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(-5)
        );
    }

    @Test
    @DisplayName("Setter i getter za kolicina rade ispravno")
    void testSetGetKolicina() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setKolicina(5);
        assertEquals(5, sr.getKolicina());
    }

    @ParameterizedTest
    @MethodSource("nevalidneKolicine")
    @DisplayName("setKolicina baca izuzetak za nevalidnu kolicinu")
    void testSetKolicinaBacaIzuzetakZaNevalidnuKolicinu(int kolicina) {
        StavkaRacuna sr = new StavkaRacuna();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> sr.setKolicina(kolicina));
        assertEquals("Kolicina stavke mora biti veca od nule.", ex.getMessage());
    }

    static Stream<Arguments> nevalidneKolicine() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of(-100)
        );
    }

    @Test
    @DisplayName("Setter i getter za ukupanIznos rade ispravno")
    void testSetGetUkupanIznos() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setUkupanIznos(300.0);
        assertEquals(300.0, sr.getUkupanIznos());
    }

    @ParameterizedTest
    @MethodSource("nevalidniUkupanIznos")
    @DisplayName("setUkupanIznos baca izuzetak za nevalidan iznos")
    void testSetUkupanIznosBacaIzuzetakZaNevalidanIznos(double iznos) {
        StavkaRacuna sr = new StavkaRacuna();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> sr.setUkupanIznos(iznos));
        assertEquals("Ukupan iznos stavke mora biti veci od nule.", ex.getMessage());
    }

    static Stream<Arguments> nevalidniUkupanIznos() {
        return Stream.of(
                Arguments.of(0.0),
                Arguments.of(-1.0),
                Arguments.of(-50.5)
        );
    }

    @Test
    @DisplayName("Setter i getter za cenu rade ispravno")
    void testSetGetCena() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setCena(150.0);
        assertEquals(150.0, sr.getCena());
    }

    @ParameterizedTest
    @MethodSource("nevalidneCene")
    @DisplayName("setCena baca izuzetak za nevalidnu cenu")
    void testSetCenaBacaIzuzetakZaNevalidnuCenu(double cena) {
        StavkaRacuna sr = new StavkaRacuna();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> sr.setCena(cena));
        assertEquals("Cena stavke mora biti veca od nule.", ex.getMessage());
    }

    static Stream<Arguments> nevalidneCene() {
        return Stream.of(
                Arguments.of(0.0),
                Arguments.of(-1.0),
                Arguments.of(-100.5)
        );
    }

    @Test
    @DisplayName("Setter i getter za ugnjezdeni Artikal rade ispravno")
    void testSetGetArtikal() {
        StavkaRacuna sr = new StavkaRacuna();
        Artikal a = new Artikal(9, "Sok", 200.0, "Pice");
        sr.setArtikal(a);
        assertEquals(a, sr.getArtikal());
        assertEquals(9, sr.getArtikal().getIdArtikal());
        assertEquals("Sok", sr.getArtikal().getNaziv());
    }

    @Test
    @DisplayName("setArtikal baca izuzetak za null")
    void testSetArtikalBacaIzuzetakZaNull() {
        StavkaRacuna sr = new StavkaRacuna();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> sr.setArtikal(null));
        assertEquals("Artikal stavke ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("equals vraca true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(stavka.equals(stavka));
    }

    @Test
    @DisplayName("equals vraca false kada se stavka poredi sa null")
    void testEqualsSaNull() {
        assertFalse(stavka.equals(null));
    }

    @Test
    @DisplayName("equals vraca false kada se stavka poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(stavka.equals("nije stavka"));
    }

    @ParameterizedTest
    @MethodSource("podaciZaEquals")
    @DisplayName("equals poredi stavke po idRacun i rb")
    void testEqualsPoKompozitnomKljucu(StavkaRacuna prva, StavkaRacuna druga, boolean ocekivano) {
        assertEquals(ocekivano, prva.equals(druga));
    }

    static Stream<Arguments> podaciZaEquals() {
        Artikal a1 = new Artikal(5, "Pizza", 850.0, "Jelo");
        Artikal a2 = new Artikal(6, "Sok", 200.0, "Pice");
        return Stream.of(
                Arguments.of(
                        new StavkaRacuna(10, 1, 2, 1700.0, 850.0, a1),
                        new StavkaRacuna(10, 1, 99, 1.0, 1.0, a2),
                        true
                ),
                Arguments.of(
                        new StavkaRacuna(10, 1, 2, 1700.0, 850.0, a1),
                        new StavkaRacuna(10, 2, 2, 1700.0, 850.0, a1),
                        false
                ),
                Arguments.of(
                        new StavkaRacuna(10, 1, 2, 1700.0, 850.0, a1),
                        new StavkaRacuna(11, 1, 2, 1700.0, 850.0, a1),
                        false
                )
        );
    }

    @Test
    @DisplayName("Jednake stavke imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        StavkaRacuna druga = new StavkaRacuna(10, 1, 3, 100.0, 50.0, new Artikal(9, "Sok", 50.0, "Pice"));
        assertEquals(stavka.hashCode(), druga.hashCode());
    }

    @Test
    @DisplayName("Visestruki pozivi hashCode na istom objektu vracaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(stavka.hashCode(), stavka.hashCode());
    }

    @Test
    @DisplayName("toString sadrzi idRacun, rb, kolicina, cena i ukupanIznos")
    void testToStringFormat() {
        String rezultat = stavka.toString();
        assertTrue(rezultat.contains("idRacun=10"));
        assertTrue(rezultat.contains("rb=1"));
        assertTrue(rezultat.contains("kolicina=2"));
        assertTrue(rezultat.contains("cena=850.0"));
        assertTrue(rezultat.contains("ukupanIznos=1700.0"));
    }

    @Test
    @DisplayName("vratiNazivTabele vraca ime tabele stavkaracuna")
    void testVratiNazivTabele() {
        assertEquals("stavkaracuna", stavka.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraca uslov sa razmakom na pocetku")
    void testVratiPrimarniKljuc() {
        assertEquals(" rb=1 AND idRacun=10", stavka.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraca nazive kolona za INSERT")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("idRacun, rb, kolicina, cena, ukupanIznos, idArtikal", stavka.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraca vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("10, 1, 2, 850.0, 1700.0, 5", stavka.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraca SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("kolicina=2, cena=850.0, ukupanIznos=1700.0, idArtikal=5", stavka.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraca praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(stavka.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dve stavke")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idArtikal")).thenReturn(5, 6);
        when(rs.getString("naziv")).thenReturn("Pizza", "Sok");
        when(rs.getDouble("artikal.cena")).thenReturn(850.0, 200.0);
        when(rs.getString("tip")).thenReturn("Jelo", "Pice");
        when(rs.getInt("idRacun")).thenReturn(10, 10);
        when(rs.getInt("rb")).thenReturn(1, 2);
        when(rs.getInt("kolicina")).thenReturn(2, 3);
        when(rs.getDouble("ukupanIznos")).thenReturn(1700.0, 600.0);
        when(rs.getDouble("cena")).thenReturn(850.0, 200.0);

        List<ApstraktniDomenskiObjekat> lista = stavka.vratiListu(rs);

        assertEquals(2, lista.size());
        StavkaRacuna prva = (StavkaRacuna) lista.get(0);
        assertEquals(10, prva.getIdRacun());
        assertEquals(1, prva.getRb());
        assertEquals(2, prva.getKolicina());
        assertEquals(1700.0, prva.getUkupanIznos());
        assertEquals(850.0, prva.getCena());
        assertEquals(5, prva.getArtikal().getIdArtikal());
        assertEquals("Pizza", prva.getArtikal().getNaziv());
        assertEquals(850.0, prva.getArtikal().getCena());
        assertEquals("Jelo", prva.getArtikal().getTip());

        StavkaRacuna druga = (StavkaRacuna) lista.get(1);
        assertEquals(10, druga.getIdRacun());
        assertEquals(2, druga.getRb());
        assertEquals(3, druga.getKolicina());
        assertEquals(600.0, druga.getUkupanIznos());
        assertEquals(200.0, druga.getCena());
        assertEquals(6, druga.getArtikal().getIdArtikal());
        assertEquals("Sok", druga.getArtikal().getNaziv());
        assertEquals(200.0, druga.getArtikal().getCena());
        assertEquals("Pice", druga.getArtikal().getTip());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira stavku bez ugnjezdenog artikla")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idRacun")).thenReturn(10);
        when(rs.getInt("rb")).thenReturn(1);
        when(rs.getInt("kolicina")).thenReturn(2);
        when(rs.getDouble("ukupanIznos")).thenReturn(1700.0);
        when(rs.getDouble("cena")).thenReturn(850.0);

        StavkaRacuna rezultat = (StavkaRacuna) stavka.vratiObjekatIzRS(rs);

        assertEquals(10, rezultat.getIdRacun());
        assertEquals(1, rezultat.getRb());
        assertEquals(2, rezultat.getKolicina());
        assertEquals(1700.0, rezultat.getUkupanIznos());
        assertEquals(850.0, rezultat.getCena());
        assertNull(rezultat.getArtikal());
    }
}
