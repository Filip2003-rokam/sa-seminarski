package domen;

import java.sql.ResultSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KonobarTest {

    private Konobar konobar;

    @BeforeEach
    void setUp() {
        konobar = new Konobar(1, "Petar", "Petrovic", "ppetrovic", "sifra123");
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

    @Test
    @DisplayName("Setter i getter za idKonobar rade ispravno")
    void testSetGetIdKonobar() {
        Konobar k = new Konobar();
        k.setIdKonobar(10);
        assertEquals(10, k.getIdKonobar());
    }

    @Test
    @DisplayName("Setter i getter za ime rade ispravno")
    void testSetGetIme() {
        Konobar k = new Konobar();
        k.setIme("Jovan");
        assertEquals("Jovan", k.getIme());
    }

    @Test
    @DisplayName("Setter i getter za prezime rade ispravno")
    void testSetGetPrezime() {
        Konobar k = new Konobar();
        k.setPrezime("Jovic");
        assertEquals("Jovic", k.getPrezime());
    }

    @Test
    @DisplayName("Setter i getter za korisnickoIme rade ispravno")
    void testSetGetKorisnickoIme() {
        Konobar k = new Konobar();
        k.setKorisnickoIme("jjovic");
        assertEquals("jjovic", k.getKorisnickoIme());
    }

    @Test
    @DisplayName("Setter i getter za sifru rade ispravno")
    void testSetGetSifra() {
        Konobar k = new Konobar();
        k.setSifra("novaSifra");
        assertEquals("novaSifra", k.getSifra());
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

    @Test
    @DisplayName("Dva konobara sa istim identifikatorom su jednaka i ako se ostala polja razlikuju")
    void testEqualsIstiIdRazlicitaOstalaPolja() {
        Konobar drugi = new Konobar(1, "DrugoIme", "DrugoPrezime", "drugi", "druga");
        assertTrue(konobar.equals(drugi));
    }

    @Test
    @DisplayName("Dva konobara sa različitim identifikatorom nisu jednaka")
    void testEqualsRazlicitId() {
        Konobar drugi = new Konobar(2, "Petar", "Petrovic", "ppetrovic", "sifra123");
        assertFalse(konobar.equals(drugi));
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
