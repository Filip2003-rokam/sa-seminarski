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
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KonobarTest {

    private Konobar konobar;

    @BeforeEach
    void setUp() {
        konobar = new Konobar(1, "Petar", "Petrovic", "ppetrovic", "sifra123");
    }

    @AfterEach
    void tearDown() {
        konobar = null;
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat konobara")
    void testPrazanKonstruktorKreiraObjekat() {
        Konobar k = new Konobar();
        assertNotNull(k);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sve atribute konobara")
    void testPunKonstruktorPostavljaSvaPolja() {
        Konobar k = new Konobar(5, "Ana", "Anic", "aanic", "pass");
        assertEquals(5, k.getIdKonobar());
        assertEquals("Ana", k.getIme());
        assertEquals("Anic", k.getPrezime());
        assertEquals("aanic", k.getKorisnickoIme());
        assertEquals("pass", k.getSifra());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Pun konstruktor baca izuzetak za negativan id")
    void testPunKonstruktorBacaZaNegativanId(int id) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Konobar(id, "Petar", "Petrovic", "ppetrovic", "sifra123"));
        assertEquals("Id konobara ne sme biti negativan.", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a"})
    @DisplayName("Pun konstruktor baca izuzetak za nevalidno ime")
    void testPunKonstruktorBacaZaNevalidnoIme(String ime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Konobar(1, ime, "Petrovic", "ppetrovic", "sifra123"));
        assertEquals("Ime konobara mora imati najmanje 2 karaktera.", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a"})
    @DisplayName("Pun konstruktor baca izuzetak za nevalidno prezime")
    void testPunKonstruktorBacaZaNevalidnoPrezime(String prezime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Konobar(1, "Petar", prezime, "ppetrovic", "sifra123"));
        assertEquals("Prezime konobara mora imati najmanje 2 karaktera.", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Pun konstruktor baca izuzetak za prazno korisničko ime")
    void testPunKonstruktorBacaZaPraznoKorisnickoIme(String korisnickoIme) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Konobar(1, "Petar", "Petrovic", korisnickoIme, "sifra123"));
        assertEquals("Korisnicko ime konobara ne sme biti prazno.", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Pun konstruktor baca izuzetak za praznu šifru")
    void testPunKonstruktorBacaZaPraznuSifru(String sifra) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Konobar(1, "Petar", "Petrovic", "ppetrovic", sifra));
        assertEquals("Sifra konobara ne sme biti prazna.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za idKonobar rade ispravno")
    void testSetGetIdKonobar() {
        Konobar k = new Konobar();
        k.setIdKonobar(10);
        assertEquals(10, k.getIdKonobar());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100})
    @DisplayName("setIdKonobar prihvata validne vrednosti")
    void testSetIdKonobarPrihvataValidneVrednosti(int id) {
        Konobar k = new Konobar();
        k.setIdKonobar(id);
        assertEquals(id, k.getIdKonobar());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -100})
    @DisplayName("setIdKonobar baca izuzetak za negativan id")
    void testSetIdKonobarBacaZaNegativanId(int id) {
        Konobar k = new Konobar();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> k.setIdKonobar(id));
        assertEquals("Id konobara ne sme biti negativan.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za ime rade ispravno")
    void testSetGetIme() {
        Konobar k = new Konobar();
        k.setIme("Jovan");
        assertEquals("Jovan", k.getIme());
    }

    @ParameterizedTest
    @ValueSource(strings = {"An", "Ana", "Petar"})
    @DisplayName("setIme prihvata validne vrednosti")
    void testSetImePrihvataValidneVrednosti(String ime) {
        Konobar k = new Konobar();
        k.setIme(ime);
        assertEquals(ime, k.getIme());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a"})
    @DisplayName("setIme baca izuzetak za nevalidno ime")
    void testSetImeBacaZaNevalidnoIme(String ime) {
        Konobar k = new Konobar();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> k.setIme(ime));
        assertEquals("Ime konobara mora imati najmanje 2 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za prezime rade ispravno")
    void testSetGetPrezime() {
        Konobar k = new Konobar();
        k.setPrezime("Jovic");
        assertEquals("Jovic", k.getPrezime());
    }

    @ParameterizedTest
    @ValueSource(strings = {"An", "Anic", "Petrovic"})
    @DisplayName("setPrezime prihvata validne vrednosti")
    void testSetPrezimePrihvataValidneVrednosti(String prezime) {
        Konobar k = new Konobar();
        k.setPrezime(prezime);
        assertEquals(prezime, k.getPrezime());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a"})
    @DisplayName("setPrezime baca izuzetak za nevalidno prezime")
    void testSetPrezimeBacaZaNevalidnoPrezime(String prezime) {
        Konobar k = new Konobar();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> k.setPrezime(prezime));
        assertEquals("Prezime konobara mora imati najmanje 2 karaktera.", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Ana, Anic",
            "Petar, Petrovic",
            "Jo, Jo"
    })
    @DisplayName("setIme i setPrezime zajedno prihvataju validne kombinacije")
    void testSetImeIPrezimeValidneKombinacije(String ime, String prezime) {
        Konobar k = new Konobar();
        k.setIme(ime);
        k.setPrezime(prezime);
        assertEquals(ime, k.getIme());
        assertEquals(prezime, k.getPrezime());
    }

    @Test
    @DisplayName("Setter i getter za korisnickoIme rade ispravno")
    void testSetGetKorisnickoIme() {
        Konobar k = new Konobar();
        k.setKorisnickoIme("jjovic");
        assertEquals("jjovic", k.getKorisnickoIme());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ppetrovic", "user1"})
    @DisplayName("setKorisnickoIme prihvata validne vrednosti")
    void testSetKorisnickoImePrihvataValidneVrednosti(String korisnickoIme) {
        Konobar k = new Konobar();
        k.setKorisnickoIme(korisnickoIme);
        assertEquals(korisnickoIme, k.getKorisnickoIme());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("setKorisnickoIme baca izuzetak za null ili prazno")
    void testSetKorisnickoImeBacaZaPrazno(String korisnickoIme) {
        Konobar k = new Konobar();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> k.setKorisnickoIme(korisnickoIme));
        assertEquals("Korisnicko ime konobara ne sme biti prazno.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za sifru rade ispravno")
    void testSetGetSifra() {
        Konobar k = new Konobar();
        k.setSifra("novaSifra");
        assertEquals("novaSifra", k.getSifra());
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "pass", "sifra123"})
    @DisplayName("setSifra prihvata validne vrednosti")
    void testSetSifraPrihvataValidneVrednosti(String sifra) {
        Konobar k = new Konobar();
        k.setSifra(sifra);
        assertEquals(sifra, k.getSifra());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("setSifra baca izuzetak za null ili praznu šifru")
    void testSetSifraBacaZaPraznuSifru(String sifra) {
        Konobar k = new Konobar();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> k.setSifra(sifra));
        assertEquals("Sifra konobara ne sme biti prazna.", ex.getMessage());
    }

    @Test
    @DisplayName("proveriKredencijale vraća true za ispravno korisničko ime i šifru")
    void testProveriKredencijaleIspravni() {
        assertTrue(konobar.proveriKredencijale("ppetrovic", "sifra123"));
    }

    @Test
    @DisplayName("proveriKredencijale vraća false za pogrešnu šifru")
    void testProveriKredencijalePogresnaSifra() {
        assertFalse(konobar.proveriKredencijale("ppetrovic", "pogresna"));
    }

    @Test
    @DisplayName("proveriKredencijale vraća false za pogrešno korisničko ime")
    void testProveriKredencijalePogresnoKorisnickoIme() {
        assertFalse(konobar.proveriKredencijale("pogresno", "sifra123"));
    }

    @Test
    @DisplayName("proveriKredencijale vraća false kada su prosleđeni null parametri")
    void testProveriKredencijaleNullParametri() {
        assertFalse(konobar.proveriKredencijale(null, null));
        assertFalse(konobar.proveriKredencijale(null, "sifra123"));
        assertFalse(konobar.proveriKredencijale("ppetrovic", null));
    }

    @Test
    @DisplayName("equals vraća true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(konobar.equals(konobar));
    }

    @Test
    @DisplayName("equals vraća false kada se konobar poredi sa null")
    void testEqualsSaNull() {
        assertFalse(konobar.equals(null));
    }

    @Test
    @DisplayName("equals vraća false kada se konobar poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(konobar.equals("nije konobar"));
    }

    static Stream<Arguments> equalsIdBasedProvider() {
        return Stream.of(
                Arguments.of(
                        new Konobar(1, "Petar", "Petrovic", "ppetrovic", "sifra123"),
                        new Konobar(1, "DrugoIme", "DrugoPrezime", "drugi", "druga"),
                        true),
                Arguments.of(
                        new Konobar(1, "Petar", "Petrovic", "ppetrovic", "sifra123"),
                        new Konobar(2, "Petar", "Petrovic", "ppetrovic", "sifra123"),
                        false),
                Arguments.of(
                        new Konobar(5, "Ana", "Anic", "aanic", "pass"),
                        new Konobar(5, "Jovan", "Jovic", "jjovic", "xyz"),
                        true)
        );
    }

    @ParameterizedTest
    @MethodSource("equalsIdBasedProvider")
    @DisplayName("equals poredi konobare po identifikatoru")
    void testEqualsPoIdentifikatoru(Konobar k1, Konobar k2, boolean expected) {
        assertEquals(expected, k1.equals(k2));
    }

    @Test
    @DisplayName("Jednaki konobari imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        Konobar drugi = new Konobar(1, "DrugoIme", "DrugoPrezime", "x", "y");
        assertEquals(konobar.hashCode(), drugi.hashCode());
    }

    @Test
    @DisplayName("Višestruki pozivi hashCode na istom objektu vraćaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(konobar.hashCode(), konobar.hashCode());
    }

    @Test
    @DisplayName("toString vraća ime, razmak i prezime")
    void testToStringFormat() {
        assertEquals("Petar Petrovic", konobar.toString());
    }

    @Test
    @DisplayName("vratiNazivTabele vraća ime tabele konobar")
    void testVratiNazivTabele() {
        assertEquals("konobar", konobar.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraća uslov sa imenom tabele i identifikatorom")
    void testVratiPrimarniKljuc() {
        assertEquals("konobar.idKonobar=1", konobar.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraća nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("ime, prezime, korisnickoIme, sifra", konobar.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("'Petar', 'Petrovic', 'ppetrovic', 'sifra123'", konobar.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("ime='Petar', prezime='Petrovic', korisnickoIme='ppetrovic', sifra='sifra123'",
                konobar.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraća praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(konobar.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a koristeći kvalifikovana imena kolona")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("konobar.idKonobar")).thenReturn(1, 2);
        when(rs.getString("konobar.ime")).thenReturn("Petar", "Ana");
        when(rs.getString("konobar.prezime")).thenReturn("Petrovic", "Anic");
        when(rs.getString("konobar.korisnickoIme")).thenReturn("ppetrovic", "aanic");
        when(rs.getString("konobar.sifra")).thenReturn("sifra123", "pass");

        List<ApstraktniDomenskiObjekat> lista = konobar.vratiListu(rs);

        assertEquals(2, lista.size());
        Konobar prvi = (Konobar) lista.get(0);
        assertEquals(1, prvi.getIdKonobar());
        assertEquals("Petar", prvi.getIme());
        assertEquals("Petrovic", prvi.getPrezime());
        assertEquals("ppetrovic", prvi.getKorisnickoIme());
        assertEquals("sifra123", prvi.getSifra());
        Konobar drugi = (Konobar) lista.get(1);
        assertEquals(2, drugi.getIdKonobar());
        assertEquals("Ana", drugi.getIme());
        assertEquals("Anic", drugi.getPrezime());
        assertEquals("aanic", drugi.getKorisnickoIme());
        assertEquals("pass", drugi.getSifra());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira konobara iz jednog reda ResultSet-a sa nekvalifikovanim imenima")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idKonobar")).thenReturn(7);
        when(rs.getString("ime")).thenReturn("Jovan");
        when(rs.getString("prezime")).thenReturn("Jovic");
        when(rs.getString("korisnickoIme")).thenReturn("jjovic");
        when(rs.getString("sifra")).thenReturn("xyz");

        Konobar rezultat = (Konobar) konobar.vratiObjekatIzRS(rs);
        assertEquals(7, rezultat.getIdKonobar());
        assertEquals("Jovan", rezultat.getIme());
        assertEquals("Jovic", rezultat.getPrezime());
        assertEquals("jjovic", rezultat.getKorisnickoIme());
        assertEquals("xyz", rezultat.getSifra());
    }
}
