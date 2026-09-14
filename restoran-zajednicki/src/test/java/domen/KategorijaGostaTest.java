package domen;

import java.sql.ResultSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KategorijaGostaTest {

    private KategorijaGosta kategorija;

    @BeforeEach
    void setUp() {
        kategorija = new KategorijaGosta(1, "VIP", 10.0, true);
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat kategorije gosta")
    void testPrazanKonstruktorKreiraObjekat() {
        KategorijaGosta kg = new KategorijaGosta();
        assertNotNull(kg);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sve atribute kategorije gosta")
    void testPunKonstruktorPostavljaSvaPolja() {
        KategorijaGosta kg = new KategorijaGosta(5, "Student", 15.0, true);
        assertEquals(5, kg.getIdKategorijaGosta());
        assertEquals("Student", kg.getOpis());
        assertEquals(15.0, kg.getPopust());
        assertTrue(kg.isImaPopust());
    }

    @Test
    @DisplayName("Setter i getter za idKategorijaGosta rade ispravno")
    void testSetGetIdKategorijaGosta() {
        KategorijaGosta kg = new KategorijaGosta();
        kg.setIdKategorijaGosta(10);
        assertEquals(10, kg.getIdKategorijaGosta());
    }

    @Test
    @DisplayName("Setter i getter za opis rade ispravno")
    void testSetGetOpis() {
        KategorijaGosta kg = new KategorijaGosta();
        kg.setOpis("Regular");
        assertEquals("Regular", kg.getOpis());
    }

    @Test
    @DisplayName("Setter i getter za popust rade ispravno")
    void testSetGetPopust() {
        KategorijaGosta kg = new KategorijaGosta();
        kg.setPopust(20.5);
        assertEquals(20.5, kg.getPopust());
    }

    @Test
    @DisplayName("Setter i getter za imaPopust rade ispravno")
    void testSetGetImaPopust() {
        KategorijaGosta kg = new KategorijaGosta();
        kg.setImaPopust(false);
        assertFalse(kg.isImaPopust());
        kg.setImaPopust(true);
        assertTrue(kg.isImaPopust());
    }

    @Test
    @DisplayName("equals vraća true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(kategorija.equals(kategorija));
    }

    @Test
    @DisplayName("equals vraća false kada se kategorija poredi sa null")
    void testEqualsSaNull() {
        assertFalse(kategorija.equals(null));
    }

    @Test
    @DisplayName("equals vraća false kada se kategorija poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(kategorija.equals("nije kategorija"));
    }

    @Test
    @DisplayName("Dve kategorije sa istim identifikatorom su jednake i ako se ostala polja razlikuju")
    void testEqualsIstiIdRazlicitaOstalaPolja() {
        KategorijaGosta druga = new KategorijaGosta(1, "Drugi opis", 99.0, false);
        assertTrue(kategorija.equals(druga));
    }

    @Test
    @DisplayName("Dve kategorije sa različitim identifikatorom nisu jednake")
    void testEqualsRazlicitId() {
        KategorijaGosta druga = new KategorijaGosta(2, "VIP", 10.0, true);
        assertFalse(kategorija.equals(druga));
    }

    @Test
    @DisplayName("Jednake kategorije imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        KategorijaGosta druga = new KategorijaGosta(1, "Drugi opis", 1.0, false);
        assertEquals(kategorija.hashCode(), druga.hashCode());
    }

    @Test
    @DisplayName("Višestruki pozivi hashCode na istom objektu vraćaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(kategorija.hashCode(), kategorija.hashCode());
    }

    @Test
    @DisplayName("toString vraća samo opis kategorije")
    void testToStringFormat() {
        assertEquals("VIP", kategorija.toString());
    }

    @Test
    @DisplayName("vratiNazivTabele vraća ime tabele kategorijagosta")
    void testVratiNazivTabele() {
        assertEquals("kategorijagosta", kategorija.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraća uslov sa imenom tabele i identifikatorom")
    void testVratiPrimarniKljuc() {
        assertEquals("kategorijagosta.idKategorijaGosta=1", kategorija.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraća nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("opis, popust, imaPopust", kategorija.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća vrednosti u SQL formatu za INSERT sa boolean true")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("'VIP', 10.0, true", kategorija.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća boolean false kada kategorija nema popust")
    void testVratiVrednostiZaUbacivanjeBezPopusta() {
        KategorijaGosta kg = new KategorijaGosta(2, "Regular", 0.0, false);
        assertEquals("'Regular', 0.0, false", kg.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("opis='VIP', popust=10.0, imaPopust=true", kategorija.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća boolean false kada kategorija nema popust")
    void testVratiVrednostiZaIzmenuBezPopusta() {
        KategorijaGosta kg = new KategorijaGosta(2, "Regular", 0.0, false);
        assertEquals("opis='Regular', popust=0.0, imaPopust=false", kg.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraća praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(kategorija.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dve kategorije")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idKategorijaGosta")).thenReturn(1, 2);
        when(rs.getString("opis")).thenReturn("VIP", "Student");
        when(rs.getDouble("popust")).thenReturn(10.0, 15.0);
        when(rs.getBoolean("imaPopust")).thenReturn(true, true);

        List<ApstraktniDomenskiObjekat> lista = kategorija.vratiListu(rs);

        assertEquals(2, lista.size());
        KategorijaGosta prva = (KategorijaGosta) lista.get(0);
        assertEquals(1, prva.getIdKategorijaGosta());
        assertEquals("VIP", prva.getOpis());
        assertEquals(10.0, prva.getPopust());
        assertTrue(prva.isImaPopust());
        KategorijaGosta druga = (KategorijaGosta) lista.get(1);
        assertEquals(2, druga.getIdKategorijaGosta());
        assertEquals("Student", druga.getOpis());
        assertEquals(15.0, druga.getPopust());
        assertTrue(druga.isImaPopust());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira kategoriju gosta iz jednog reda ResultSet-a")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idKategorijaGosta")).thenReturn(7);
        when(rs.getString("opis")).thenReturn("Penzioner");
        when(rs.getDouble("popust")).thenReturn(20.0);
        when(rs.getBoolean("imaPopust")).thenReturn(true);

        KategorijaGosta rezultat = (KategorijaGosta) kategorija.vratiObjekatIzRS(rs);
        assertEquals(7, rezultat.getIdKategorijaGosta());
        assertEquals("Penzioner", rezultat.getOpis());
        assertEquals(20.0, rezultat.getPopust());
        assertTrue(rezultat.isImaPopust());
    }
}
