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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GostTest {

    private Gost gost;
    private KategorijaGosta kategorija;

    @BeforeEach
    void setUp() {
        kategorija = new KategorijaGosta(1, "VIP", 10.0, true);
        gost = new Gost(1, "Marko", "Markovic", kategorija);
    }

    @AfterEach
    void tearDown() {
        gost = null;
        kategorija = null;
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat gosta")
    void testPrazanKonstruktorKreiraObjekat() {
        Gost g = new Gost();
        assertNotNull(g);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sve atribute gosta")
    void testPunKonstruktorPostavljaSvaPolja() {
        KategorijaGosta kg = new KategorijaGosta(2, "Regular", 0.0, false);
        Gost g = new Gost(5, "Ana", "Anic", kg);
        assertEquals(5, g.getIdGost());
        assertEquals("Ana", g.getIme());
        assertEquals("Anic", g.getPrezime());
        assertEquals(kg, g.getKategorijaGosta());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Pun konstruktor baca izuzetak za negativan id")
    void testPunKonstruktorBacaZaNegativanId(int id) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Gost(id, "Marko", "Markovic", kategorija));
        assertEquals("Id gosta ne sme biti negativan.", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a", "ab"})
    @DisplayName("Pun konstruktor baca izuzetak za nevalidno ime")
    void testPunKonstruktorBacaZaNevalidnoIme(String ime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Gost(1, ime, "Markovic", kategorija));
        assertEquals("Ime gosta mora imati najmanje 3 karaktera.", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a", "ab"})
    @DisplayName("Pun konstruktor baca izuzetak za nevalidno prezime")
    void testPunKonstruktorBacaZaNevalidnoPrezime(String prezime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Gost(1, "Marko", prezime, kategorija));
        assertEquals("Prezime gosta mora imati najmanje 3 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor dozvoljava null kategoriju")
    void testPunKonstruktorDozvoljavaNullKategoriju() {
        Gost g = new Gost(1, "Marko", "Markovic", null);
        assertNull(g.getKategorijaGosta());
    }

    @Test
    @DisplayName("Setter i getter za idGost rade ispravno")
    void testSetGetIdGost() {
        Gost g = new Gost();
        g.setIdGost(10);
        assertEquals(10, g.getIdGost());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100})
    @DisplayName("setIdGost prihvata validne vrednosti")
    void testSetIdGostPrihvataValidneVrednosti(int id) {
        Gost g = new Gost();
        g.setIdGost(id);
        assertEquals(id, g.getIdGost());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -100})
    @DisplayName("setIdGost baca izuzetak za negativan id")
    void testSetIdGostBacaZaNegativanId(int id) {
        Gost g = new Gost();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> g.setIdGost(id));
        assertEquals("Id gosta ne sme biti negativan.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za ime rade ispravno")
    void testSetGetIme() {
        Gost g = new Gost();
        g.setIme("Petar");
        assertEquals("Petar", g.getIme());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ana", "Marko", "Petar"})
    @DisplayName("setIme prihvata validne vrednosti")
    void testSetImePrihvataValidneVrednosti(String ime) {
        Gost g = new Gost();
        g.setIme(ime);
        assertEquals(ime, g.getIme());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a", "ab"})
    @DisplayName("setIme baca izuzetak za nevalidno ime")
    void testSetImeBacaZaNevalidnoIme(String ime) {
        Gost g = new Gost();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> g.setIme(ime));
        assertEquals("Ime gosta mora imati najmanje 3 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za prezime rade ispravno")
    void testSetGetPrezime() {
        Gost g = new Gost();
        g.setPrezime("Petrovic");
        assertEquals("Petrovic", g.getPrezime());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Anic", "Markovic", "Petrovic"})
    @DisplayName("setPrezime prihvata validne vrednosti")
    void testSetPrezimePrihvataValidneVrednosti(String prezime) {
        Gost g = new Gost();
        g.setPrezime(prezime);
        assertEquals(prezime, g.getPrezime());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a", "ab"})
    @DisplayName("setPrezime baca izuzetak za nevalidno prezime")
    void testSetPrezimeBacaZaNevalidnoPrezime(String prezime) {
        Gost g = new Gost();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> g.setPrezime(prezime));
        assertEquals("Prezime gosta mora imati najmanje 3 karaktera.", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Ana, Anic",
            "Marko, Markovic",
            "Petar, Petrovic"
    })
    @DisplayName("setIme i setPrezime zajedno prihvataju validne kombinacije")
    void testSetImeIPrezimeValidneKombinacije(String ime, String prezime) {
        Gost g = new Gost();
        g.setIme(ime);
        g.setPrezime(prezime);
        assertEquals(ime, g.getIme());
        assertEquals(prezime, g.getPrezime());
    }

    @Test
    @DisplayName("Setter i getter za kategoriju gosta rade ispravno")
    void testSetGetKategorijaGosta() {
        Gost g = new Gost();
        KategorijaGosta kg = new KategorijaGosta(3, "Student", 15.0, true);
        g.setKategorijaGosta(kg);
        assertEquals(kg, g.getKategorijaGosta());
        assertEquals(3, g.getKategorijaGosta().getIdKategorijaGosta());
        assertEquals("Student", g.getKategorijaGosta().getOpis());
        assertEquals(15.0, g.getKategorijaGosta().getPopust());
        assertTrue(g.getKategorijaGosta().isImaPopust());
    }

    @Test
    @DisplayName("setKategorijaGosta dozvoljava null vrednost")
    void testSetKategorijaGostaDozvoljavaNull() {
        Gost g = new Gost();
        g.setKategorijaGosta(null);
        assertNull(g.getKategorijaGosta());
    }

    @Test
    @DisplayName("equals vraća true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(gost.equals(gost));
    }

    @Test
    @DisplayName("equals vraća false kada se gost poredi sa null")
    void testEqualsSaNull() {
        assertFalse(gost.equals(null));
    }

    @Test
    @DisplayName("equals vraća false kada se gost poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(gost.equals("nije gost"));
    }

    static Stream<Arguments> equalsIdBasedProvider() {
        KategorijaGosta kg1 = new KategorijaGosta(1, "VIP", 10.0, true);
        KategorijaGosta kg2 = new KategorijaGosta(9, "Druga", 5.0, false);
        return Stream.of(
                Arguments.of(
                        new Gost(1, "Marko", "Markovic", kg1),
                        new Gost(1, "DrugoIme", "DrugoPrezime", kg2),
                        true),
                Arguments.of(
                        new Gost(1, "Marko", "Markovic", kg1),
                        new Gost(2, "Marko", "Markovic", kg1),
                        false),
                Arguments.of(
                        new Gost(5, "Ana", "Anic", null),
                        new Gost(5, "Petar", "Petrovic", kg1),
                        true)
        );
    }

    @ParameterizedTest
    @MethodSource("equalsIdBasedProvider")
    @DisplayName("equals poredi goste po identifikatoru")
    void testEqualsPoIdentifikatoru(Gost g1, Gost g2, boolean expected) {
        assertEquals(expected, g1.equals(g2));
    }

    @Test
    @DisplayName("Jednaki gosti imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        Gost drugi = new Gost(1, "DrugoIme", "DrugoPrezime", null);
        assertEquals(gost.hashCode(), drugi.hashCode());
    }

    @Test
    @DisplayName("Višestruki pozivi hashCode na istom objektu vraćaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(gost.hashCode(), gost.hashCode());
    }

    @Test
    @DisplayName("toString vraća ime, razmak i prezime")
    void testToStringFormat() {
        assertEquals("Marko Markovic", gost.toString());
    }

    @Test
    @DisplayName("vratiNazivTabele vraća ime tabele gost")
    void testVratiNazivTabele() {
        assertEquals("gost", gost.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraća uslov sa imenom tabele i identifikatorom")
    void testVratiPrimarniKljuc() {
        assertEquals("gost.idGost=1", gost.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraća nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("ime, prezime, idKategorijaGosta", gost.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("'Marko', 'Markovic', 1", gost.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("ime='Marko', prezime='Markovic', idKategorijaGosta=1", gost.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraća praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(gost.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dva gosta i kategorijama")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idKategorijaGosta")).thenReturn(1, 2);
        when(rs.getString("opis")).thenReturn("VIP", "Student");
        when(rs.getDouble("popust")).thenReturn(10.0, 15.0);
        when(rs.getInt("idGost")).thenReturn(1, 2);
        when(rs.getString("ime")).thenReturn("Marko", "Ana");
        when(rs.getString("prezime")).thenReturn("Markovic", "Anic");

        List<ApstraktniDomenskiObjekat> lista = gost.vratiListu(rs);

        assertEquals(2, lista.size());
        Gost prvi = (Gost) lista.get(0);
        assertEquals(1, prvi.getIdGost());
        assertEquals("Marko", prvi.getIme());
        assertEquals("Markovic", prvi.getPrezime());
        assertEquals(1, prvi.getKategorijaGosta().getIdKategorijaGosta());
        assertEquals("VIP", prvi.getKategorijaGosta().getOpis());
        assertEquals(10.0, prvi.getKategorijaGosta().getPopust());
        assertTrue(prvi.getKategorijaGosta().isImaPopust());
        Gost drugi = (Gost) lista.get(1);
        assertEquals(2, drugi.getIdGost());
        assertEquals("Ana", drugi.getIme());
        assertEquals("Anic", drugi.getPrezime());
        assertEquals(2, drugi.getKategorijaGosta().getIdKategorijaGosta());
        assertEquals("Student", drugi.getKategorijaGosta().getOpis());
        assertEquals(15.0, drugi.getKategorijaGosta().getPopust());
        assertTrue(drugi.getKategorijaGosta().isImaPopust());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira gosta iz jednog reda ResultSet-a bez kategorije")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idGost")).thenReturn(7);
        when(rs.getString("ime")).thenReturn("Jovan");
        when(rs.getString("prezime")).thenReturn("Jovic");

        Gost rezultat = (Gost) gost.vratiObjekatIzRS(rs);
        assertEquals(7, rezultat.getIdGost());
        assertEquals("Jovan", rezultat.getIme());
        assertEquals("Jovic", rezultat.getPrezime());
        assertNull(rezultat.getKategorijaGosta());
    }
}
