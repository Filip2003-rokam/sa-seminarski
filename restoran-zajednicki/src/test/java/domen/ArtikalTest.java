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
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtikalTest {

    private Artikal artikal;

    @BeforeEach
    void setUp() {
        artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
    }

    @AfterEach
    void tearDown() {
        artikal = null;
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat artikla")
    void testPrazanKonstruktorKreiraObjekat() {
        Artikal a = new Artikal();
        assertNotNull(a);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sve atribute artikla")
    void testPunKonstruktorPostavljaSvaPolja() {
        Artikal a = new Artikal(5, "Coca Cola", 250.0, "Pice");
        assertEquals(5, a.getIdArtikal());
        assertEquals("Coca Cola", a.getNaziv());
        assertEquals(250.0, a.getCena());
        assertEquals("Pice", a.getTip());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Pun konstruktor baca izuzetak za negativan id")
    void testPunKonstruktorBacaZaNegativanId(int id) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Artikal(id, "Pizza", 850.0, "Jelo"));
        assertEquals("Id artikla ne sme biti negativan.", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a"})
    @DisplayName("Pun konstruktor baca izuzetak za nevalidan naziv")
    void testPunKonstruktorBacaZaNevalidanNaziv(String naziv) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Artikal(1, naziv, 850.0, "Jelo"));
        assertEquals("Naziv artikla mora imati najmanje 2 karaktera.", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.5})
    @DisplayName("Pun konstruktor baca izuzetak za nevalidnu cenu")
    void testPunKonstruktorBacaZaNevalidnuCenu(double cena) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Artikal(1, "Pizza", cena, "Jelo"));
        assertEquals("Cena artikla mora biti veca od nule.", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Pun konstruktor baca izuzetak za prazan tip")
    void testPunKonstruktorBacaZaPrazanTip(String tip) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Artikal(1, "Pizza", 850.0, tip));
        assertEquals("Tip artikla ne sme biti prazan.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za idArtikal rade ispravno")
    void testSetGetIdArtikal() {
        Artikal a = new Artikal();
        a.setIdArtikal(10);
        assertEquals(10, a.getIdArtikal());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100})
    @DisplayName("setIdArtikal prihvata validne vrednosti")
    void testSetIdArtikalPrihvataValidneVrednosti(int id) {
        Artikal a = new Artikal();
        a.setIdArtikal(id);
        assertEquals(id, a.getIdArtikal());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -100})
    @DisplayName("setIdArtikal baca izuzetak za negativan id")
    void testSetIdArtikalBacaZaNegativanId(int id) {
        Artikal a = new Artikal();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> a.setIdArtikal(id));
        assertEquals("Id artikla ne sme biti negativan.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za naziv rade ispravno")
    void testSetGetNaziv() {
        Artikal a = new Artikal();
        a.setNaziv("Pasta");
        assertEquals("Pasta", a.getNaziv());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pi", "Pizza", "Coca Cola"})
    @DisplayName("setNaziv prihvata validne vrednosti")
    void testSetNazivPrihvataValidneVrednosti(String naziv) {
        Artikal a = new Artikal();
        a.setNaziv(naziv);
        assertEquals(naziv, a.getNaziv());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a"})
    @DisplayName("setNaziv baca izuzetak za nevalidan naziv")
    void testSetNazivBacaZaNevalidanNaziv(String naziv) {
        Artikal a = new Artikal();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> a.setNaziv(naziv));
        assertEquals("Naziv artikla mora imati najmanje 2 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za cenu rade ispravno")
    void testSetGetCena() {
        Artikal a = new Artikal();
        a.setCena(420.5);
        assertEquals(420.5, a.getCena());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1.0, 850.0, 9999.99})
    @DisplayName("setCena prihvata validne vrednosti")
    void testSetCenaPrihvataValidneVrednosti(double cena) {
        Artikal a = new Artikal();
        a.setCena(cena);
        assertEquals(cena, a.getCena());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -1.0, -100.0})
    @DisplayName("setCena baca izuzetak za cenu manju ili jednaku nuli")
    void testSetCenaBacaZaNevalidnuCenu(double cena) {
        Artikal a = new Artikal();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> a.setCena(cena));
        assertEquals("Cena artikla mora biti veca od nule.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za tip rade ispravno")
    void testSetGetTip() {
        Artikal a = new Artikal();
        a.setTip("Pice");
        assertEquals("Pice", a.getTip());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jelo", "Pice", "Desert"})
    @DisplayName("setTip prihvata validne vrednosti")
    void testSetTipPrihvataValidneVrednosti(String tip) {
        Artikal a = new Artikal();
        a.setTip(tip);
        assertEquals(tip, a.getTip());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("setTip baca izuzetak za null ili prazan tip")
    void testSetTipBacaZaPrazanTip(String tip) {
        Artikal a = new Artikal();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> a.setTip(tip));
        assertEquals("Tip artikla ne sme biti prazan.", ex.getMessage());
    }

    @Test
    @DisplayName("equals vraća true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(artikal.equals(artikal));
    }

    @Test
    @DisplayName("equals vraća false kada se artikal poredi sa null")
    void testEqualsSaNull() {
        assertFalse(artikal.equals(null));
    }

    @Test
    @DisplayName("equals vraća false kada se artikal poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(artikal.equals("nije artikal"));
    }

    static Stream<Arguments> equalsIdBasedProvider() {
        return Stream.of(
                Arguments.of(
                        new Artikal(1, "Pizza", 850.0, "Jelo"),
                        new Artikal(1, "Drugi naziv", 99.0, "Pice"),
                        true),
                Arguments.of(
                        new Artikal(1, "Pizza", 850.0, "Jelo"),
                        new Artikal(2, "Pizza", 850.0, "Jelo"),
                        false),
                Arguments.of(
                        new Artikal(5, "Sok", 200.0, "Pice"),
                        new Artikal(5, "Voda", 100.0, "Pice"),
                        true)
        );
    }

    @ParameterizedTest
    @MethodSource("equalsIdBasedProvider")
    @DisplayName("equals poredi artikle po identifikatoru")
    void testEqualsPoIdentifikatoru(Artikal a1, Artikal a2, boolean expected) {
        assertEquals(expected, a1.equals(a2));
    }

    @Test
    @DisplayName("Jednaki artikli imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        Artikal drugi = new Artikal(1, "Drugi naziv", 10.0, "Pice");
        assertEquals(artikal.hashCode(), drugi.hashCode());
    }

    @Test
    @DisplayName("Višestruki pozivi hashCode na istom objektu vraćaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(artikal.hashCode(), artikal.hashCode());
    }

    @Test
    @DisplayName("toString vraća format naziv, razmak, zarez, razmak i cena")
    void testToStringFormat() {
        assertEquals("Pizza , 850.0", artikal.toString());
    }

    @Test
    @DisplayName("vratiNazivTabele vraća ime tabele artikal")
    void testVratiNazivTabele() {
        assertEquals("artikal", artikal.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraća uslov sa imenom tabele i identifikatorom")
    void testVratiPrimarniKljuc() {
        assertEquals("artikal.idArtikal=1", artikal.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraća nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("naziv, cena, tip", artikal.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("'Pizza', 850.0, 'Jelo'", artikal.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("naziv='Pizza', cena=850.0, tip='Jelo'", artikal.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraća praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(artikal.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dva artikla")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idArtikal")).thenReturn(1, 2);
        when(rs.getString("naziv")).thenReturn("Pizza", "Sok");
        when(rs.getDouble("cena")).thenReturn(850.0, 200.0);
        when(rs.getString("tip")).thenReturn("Jelo", "Pice");

        List<ApstraktniDomenskiObjekat> lista = artikal.vratiListu(rs);

        assertEquals(2, lista.size());
        Artikal prvi = (Artikal) lista.get(0);
        assertEquals(1, prvi.getIdArtikal());
        assertEquals("Pizza", prvi.getNaziv());
        assertEquals(850.0, prvi.getCena());
        assertEquals("Jelo", prvi.getTip());
        Artikal drugi = (Artikal) lista.get(1);
        assertEquals(2, drugi.getIdArtikal());
        assertEquals("Sok", drugi.getNaziv());
        assertEquals(200.0, drugi.getCena());
        assertEquals("Pice", drugi.getTip());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira artikal iz jednog reda ResultSet-a")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idArtikal")).thenReturn(7);
        when(rs.getString("naziv")).thenReturn("Salata");
        when(rs.getDouble("cena")).thenReturn(450.0);
        when(rs.getString("tip")).thenReturn("Jelo");

        Artikal rezultat = (Artikal) artikal.vratiObjekatIzRS(rs);
        assertEquals(7, rezultat.getIdArtikal());
        assertEquals("Salata", rezultat.getNaziv());
        assertEquals(450.0, rezultat.getCena());
        assertEquals("Jelo", rezultat.getTip());
    }
}
