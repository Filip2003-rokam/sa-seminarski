package domen;

import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SmenaTest {

    private Smena smena;

    @BeforeEach
    void setUp() {
        smena = new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat smene")
    void testPrazanKonstruktorKreiraObjekat() {
        Smena s = new Smena();
        assertNotNull(s);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sve atribute smene")
    void testPunKonstruktorPostavljaSvaPolja() {
        Smena s = new Smena(5, "Vecernja", LocalTime.of(16, 0), LocalTime.of(0, 0));
        assertEquals(5, s.getIdSmena());
        assertEquals("Vecernja", s.getNaziv());
        assertEquals(LocalTime.of(16, 0), s.getVremePocetka());
        assertEquals(LocalTime.of(0, 0), s.getVremeKraja());
    }

    @Test
    @DisplayName("Setter i getter za idSmena rade ispravno")
    void testSetGetIdSmena() {
        Smena s = new Smena();
        s.setIdSmena(10);
        assertEquals(10, s.getIdSmena());
    }

    @Test
    @DisplayName("Setter i getter za naziv rade ispravno")
    void testSetGetNaziv() {
        Smena s = new Smena();
        s.setNaziv("Popodnevna");
        assertEquals("Popodnevna", s.getNaziv());
    }

    @Test
    @DisplayName("Setter i getter za vremePocetka rade ispravno")
    void testSetGetVremePocetka() {
        Smena s = new Smena();
        LocalTime pocetak = LocalTime.of(9, 30);
        s.setVremePocetka(pocetak);
        assertEquals(pocetak, s.getVremePocetka());
    }

    @Test
    @DisplayName("Setter i getter za vremeKraja rade ispravno")
    void testSetGetVremeKraja() {
        Smena s = new Smena();
        LocalTime kraj = LocalTime.of(17, 0);
        s.setVremeKraja(kraj);
        assertEquals(kraj, s.getVremeKraja());
    }

    @Test
    @DisplayName("equals vraća true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(smena.equals(smena));
    }

    @Test
    @DisplayName("equals vraća false kada se smena poredi sa null")
    void testEqualsSaNull() {
        assertFalse(smena.equals(null));
    }

    @Test
    @DisplayName("equals vraća false kada se smena poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(smena.equals("nije smena"));
    }

    @Test
    @DisplayName("Dve smene sa istim identifikatorom su jednake i ako se ostala polja razlikuju")
    void testEqualsIstiIdRazlicitaOstalaPolja() {
        Smena druga = new Smena(1, "Drugi naziv", LocalTime.of(1, 0), LocalTime.of(2, 0));
        assertTrue(smena.equals(druga));
    }

    @Test
    @DisplayName("Dve smene sa različitim identifikatorom nisu jednake")
    void testEqualsRazlicitId() {
        Smena druga = new Smena(2, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
        assertFalse(smena.equals(druga));
    }

    @Test
    @DisplayName("Jednake smene imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        Smena druga = new Smena(1, "Drugi naziv", LocalTime.of(1, 0), null);
        assertEquals(smena.hashCode(), druga.hashCode());
    }

    @Test
    @DisplayName("Višestruki pozivi hashCode na istom objektu vraćaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(smena.hashCode(), smena.hashCode());
    }

    @Test
    @DisplayName("toString vraća naziv, zarez, razmak, vreme početka, crticu i vreme kraja")
    void testToStringFormat() {
        assertEquals("Jutarnja, 08:00-16:00", smena.toString());
    }

    @Test
    @DisplayName("vratiNazivTabele vraća ime tabele smena")
    void testVratiNazivTabele() {
        assertEquals("smena", smena.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraća uslov sa imenom tabele i identifikatorom")
    void testVratiPrimarniKljuc() {
        assertEquals("smena.idSmena=1", smena.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraća nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("naziv, vremePocetka, vremeKraja", smena.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("'Jutarnja', '08:00', '16:00'", smena.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća SQL NULL kada je vreme kraja null")
    void testVratiVrednostiZaUbacivanjeNullVremeKraja() {
        Smena s = new Smena(2, "Otvorena", LocalTime.of(10, 0), null);
        assertEquals("'Otvorena', '10:00', NULL", s.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("naziv='Jutarnja', vremePocetka='08:00', vremeKraja='16:00'",
                smena.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SQL NULL kada je vreme kraja null")
    void testVratiVrednostiZaIzmenuNullVremeKraja() {
        Smena s = new Smena(2, "Otvorena", LocalTime.of(10, 0), null);
        assertEquals("naziv='Otvorena', vremePocetka='10:00', vremeKraja=NULL",
                s.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraća praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(smena.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dve smene")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        Time pocetak1 = Time.valueOf(LocalTime.of(8, 0));
        Time kraj1 = Time.valueOf(LocalTime.of(16, 0));
        Time pocetak2 = Time.valueOf(LocalTime.of(16, 0));
        Time kraj2 = Time.valueOf(LocalTime.of(0, 0));

        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idSmena")).thenReturn(1, 2);
        when(rs.getString("naziv")).thenReturn("Jutarnja", "Vecernja");
        when(rs.getTime("vremePocetka")).thenReturn(pocetak1, pocetak2);
        // getTime("vremeKraja") se poziva dva puta po redu: provera null i toLocalTime
        when(rs.getTime("vremeKraja")).thenReturn(kraj1, kraj1, kraj2, kraj2);

        List<ApstraktniDomenskiObjekat> lista = smena.vratiListu(rs);

        assertEquals(2, lista.size());
        Smena prva = (Smena) lista.get(0);
        assertEquals(1, prva.getIdSmena());
        assertEquals("Jutarnja", prva.getNaziv());
        assertEquals(LocalTime.of(8, 0), prva.getVremePocetka());
        assertEquals(LocalTime.of(16, 0), prva.getVremeKraja());
        Smena druga = (Smena) lista.get(1);
        assertEquals(2, druga.getIdSmena());
        assertEquals("Vecernja", druga.getNaziv());
        assertEquals(LocalTime.of(16, 0), druga.getVremePocetka());
        assertEquals(LocalTime.of(0, 0), druga.getVremeKraja());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira smenu iz jednog reda ResultSet-a")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        Time pocetak = Time.valueOf(LocalTime.of(9, 0));
        Time kraj = Time.valueOf(LocalTime.of(17, 0));
        when(rs.getInt("idSmena")).thenReturn(7);
        when(rs.getString("naziv")).thenReturn("Popodnevna");
        when(rs.getTime("vremePocetka")).thenReturn(pocetak);
        // getTime("vremeKraja") se poziva dva puta: provera null i toLocalTime
        when(rs.getTime("vremeKraja")).thenReturn(kraj, kraj);

        Smena rezultat = (Smena) smena.vratiObjekatIzRS(rs);
        assertEquals(7, rezultat.getIdSmena());
        assertEquals("Popodnevna", rezultat.getNaziv());
        assertEquals(LocalTime.of(9, 0), rezultat.getVremePocetka());
        assertEquals(LocalTime.of(17, 0), rezultat.getVremeKraja());
    }

    @Test
    @DisplayName("vratiObjekatIzRS postavlja vreme kraja na null kada ResultSet vraća null")
    void testVratiObjekatIzRSNullVremeKraja() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        Time pocetak = Time.valueOf(LocalTime.of(10, 0));
        when(rs.getInt("idSmena")).thenReturn(8);
        when(rs.getString("naziv")).thenReturn("Otvorena");
        when(rs.getTime("vremePocetka")).thenReturn(pocetak);
        when(rs.getTime("vremeKraja")).thenReturn(null);

        Smena rezultat = (Smena) smena.vratiObjekatIzRS(rs);
        assertEquals(8, rezultat.getIdSmena());
        assertEquals("Otvorena", rezultat.getNaziv());
        assertEquals(LocalTime.of(10, 0), rezultat.getVremePocetka());
        assertNull(rezultat.getVremeKraja());
    }
}
