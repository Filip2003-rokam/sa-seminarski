package domen;

import java.sql.ResultSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtikalTest {

    private Artikal artikal;

    @BeforeEach
    void setUp() {
        artikal = new Artikal(1, "Pizza", 850.0, "Jelo");
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

    @Test
    @DisplayName("Setter i getter za idArtikal rade ispravno")
    void testSetGetIdArtikal() {
        Artikal a = new Artikal();
        a.setIdArtikal(10);
        assertEquals(10, a.getIdArtikal());
    }

    @Test
    @DisplayName("Setter i getter za naziv rade ispravno")
    void testSetGetNaziv() {
        Artikal a = new Artikal();
        a.setNaziv("Pasta");
        assertEquals("Pasta", a.getNaziv());
    }

    @Test
    @DisplayName("Setter i getter za cenu rade ispravno")
    void testSetGetCena() {
        Artikal a = new Artikal();
        a.setCena(420.5);
        assertEquals(420.5, a.getCena());
    }

    @Test
    @DisplayName("Setter i getter za tip rade ispravno")
    void testSetGetTip() {
        Artikal a = new Artikal();
        a.setTip("Pice");
        assertEquals("Pice", a.getTip());
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

    @Test
    @DisplayName("Dva artikla sa istim identifikatorom su jednaka i ako se ostala polja razlikuju")
    void testEqualsIstiIdRazlicitaOstalaPolja() {
        Artikal drugi = new Artikal(1, "Drugi naziv", 99.0, "Pice");
        assertTrue(artikal.equals(drugi));
    }

    @Test
    @DisplayName("Dva artikla sa različitim identifikatorom nisu jednaka")
    void testEqualsRazlicitId() {
        Artikal drugi = new Artikal(2, "Pizza", 850.0, "Jelo");
        assertFalse(artikal.equals(drugi));
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
