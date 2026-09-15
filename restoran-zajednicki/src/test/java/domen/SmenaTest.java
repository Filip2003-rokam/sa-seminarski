package domen;

import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
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

class SmenaTest {

    private Smena smena;

    @BeforeEach
    void setUp() {
        smena = new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
    }

    @AfterEach
    void tearDown() {
        smena = null;
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
        Smena s = new Smena(5, "Vecernja", LocalTime.of(16, 0), LocalTime.of(23, 0));
        assertEquals(5, s.getIdSmena());
        assertEquals("Vecernja", s.getNaziv());
        assertEquals(LocalTime.of(16, 0), s.getVremePocetka());
        assertEquals(LocalTime.of(23, 0), s.getVremeKraja());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za negativan id")
    void testPunKonstruktorBacaIzuzetakZaNegativanId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Smena(-1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        assertEquals("Id smene ne sme biti negativan.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za nevalidan naziv")
    void testPunKonstruktorBacaIzuzetakZaNevalidanNaziv() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Smena(1, "Ab", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        assertEquals("Naziv smene mora imati najmanje 3 karaktera.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null vreme pocetka")
    void testPunKonstruktorBacaIzuzetakZaNullVremePocetka() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Smena(1, "Jutarnja", null, LocalTime.of(16, 0)));
        assertEquals("Vreme pocetka smene ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null vreme kraja")
    void testPunKonstruktorBacaIzuzetakZaNullVremeKraja() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Smena(1, "Jutarnja", LocalTime.of(8, 0), null));
        assertEquals("Vreme kraja smene ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak kada kraj nije posle pocetka")
    void testPunKonstruktorBacaIzuzetakKadaKrajNijePoslePocetka() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Smena(1, "Jutarnja", LocalTime.of(16, 0), LocalTime.of(8, 0)));
        assertEquals("Kraj smene mora biti posle pocetka.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za idSmena rade ispravno")
    void testSetGetIdSmena() {
        Smena s = new Smena();
        s.setIdSmena(10);
        assertEquals(10, s.getIdSmena());
    }

    @ParameterizedTest
    @MethodSource("nevalidniIdSmena")
    @DisplayName("setIdSmena baca izuzetak za negativan id")
    void testSetIdSmenaBacaIzuzetakZaNegativanId(int id) {
        Smena s = new Smena();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> s.setIdSmena(id));
        assertEquals("Id smene ne sme biti negativan.", ex.getMessage());
    }

    static Stream<Arguments> nevalidniIdSmena() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(-100)
        );
    }

    @Test
    @DisplayName("Setter i getter za naziv rade ispravno")
    void testSetGetNaziv() {
        Smena s = new Smena();
        s.setNaziv("Popodnevna");
        assertEquals("Popodnevna", s.getNaziv());
    }

    @ParameterizedTest
    @MethodSource("nevalidniNaziviSmene")
    @DisplayName("setNaziv baca izuzetak za nevalidan naziv")
    void testSetNazivBacaIzuzetakZaNevalidanNaziv(String naziv) {
        Smena s = new Smena();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> s.setNaziv(naziv));
        assertEquals("Naziv smene mora imati najmanje 3 karaktera.", ex.getMessage());
    }

    static Stream<Arguments> nevalidniNaziviSmene() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("A"),
                Arguments.of("Ab")
        );
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
    @DisplayName("setVremePocetka baca izuzetak za null")
    void testSetVremePocetkaBacaIzuzetakZaNull() {
        Smena s = new Smena();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> s.setVremePocetka(null));
        assertEquals("Vreme pocetka smene ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("setVremePocetka baca izuzetak kada je pocetak posle vec postavljenog kraja")
    void testSetVremePocetkaBacaIzuzetakKadaJePocetakPosleKraja() {
        Smena s = new Smena();
        s.setVremeKraja(LocalTime.of(12, 0));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> s.setVremePocetka(LocalTime.of(14, 0)));
        assertEquals("Kraj smene mora biti posle pocetka.", ex.getMessage());
    }

    @Test
    @DisplayName("setVremePocetka prihvata vrednost kada kraj nije postavljen")
    void testSetVremePocetkaPrihvataVrednostKadaKrajNijePostavljen() {
        Smena s = new Smena();
        LocalTime pocetak = LocalTime.of(8, 0);
        s.setVremePocetka(pocetak);
        assertEquals(pocetak, s.getVremePocetka());
        assertNull(s.getVremeKraja());
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
    @DisplayName("setVremeKraja baca izuzetak za null")
    void testSetVremeKrajaBacaIzuzetakZaNull() {
        Smena s = new Smena();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> s.setVremeKraja(null));
        assertEquals("Vreme kraja smene ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("setVremeKraja baca izuzetak kada je kraj pre pocetka")
    void testSetVremeKrajaBacaIzuzetakKadaJeKrajPrePocetka() {
        Smena s = new Smena();
        s.setVremePocetka(LocalTime.of(12, 0));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> s.setVremeKraja(LocalTime.of(10, 0)));
        assertEquals("Kraj smene mora biti posle pocetka.", ex.getMessage());
    }

    @Test
    @DisplayName("setVremeKraja baca izuzetak kada su kraj i pocetak jednaki")
    void testSetVremeKrajaBacaIzuzetakKadaSuKrajIPocetakJednaki() {
        Smena s = new Smena();
        s.setVremePocetka(LocalTime.of(12, 0));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> s.setVremeKraja(LocalTime.of(12, 0)));
        assertEquals("Kraj smene mora biti posle pocetka.", ex.getMessage());
    }

    @Test
    @DisplayName("setVremeKraja prihvata vrednost kada pocetak nije postavljen")
    void testSetVremeKrajaPrihvataVrednostKadaPocetakNijePostavljen() {
        Smena s = new Smena();
        LocalTime kraj = LocalTime.of(16, 0);
        s.setVremeKraja(kraj);
        assertEquals(kraj, s.getVremeKraja());
        assertNull(s.getVremePocetka());
    }

    @Test
    @DisplayName("equals vraca true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(smena.equals(smena));
    }

    @Test
    @DisplayName("equals vraca false kada se smena poredi sa null")
    void testEqualsSaNull() {
        assertFalse(smena.equals(null));
    }

    @Test
    @DisplayName("equals vraca false kada se smena poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(smena.equals("nije smena"));
    }

    @ParameterizedTest
    @MethodSource("podaciZaEquals")
    @DisplayName("equals poredi smene po identifikatoru")
    void testEqualsPoId(Smena prva, Smena druga, boolean ocekivano) {
        assertEquals(ocekivano, prva.equals(druga));
    }

    static Stream<Arguments> podaciZaEquals() {
        return Stream.of(
                Arguments.of(
                        new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0)),
                        new Smena(1, "Drugi naziv", LocalTime.of(1, 0), LocalTime.of(2, 0)),
                        true
                ),
                Arguments.of(
                        new Smena(1, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0)),
                        new Smena(2, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0)),
                        false
                )
        );
    }

    @Test
    @DisplayName("Jednake smene imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        Smena druga = new Smena(1, "Drugi naziv", LocalTime.of(1, 0), LocalTime.of(2, 0));
        assertEquals(smena.hashCode(), druga.hashCode());
    }

    @Test
    @DisplayName("Visestruki pozivi hashCode na istom objektu vracaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(smena.hashCode(), smena.hashCode());
    }

    @Test
    @DisplayName("toString vraca naziv, zarez, razmak, vreme pocetka, crticu i vreme kraja")
    void testToStringFormat() {
        assertEquals("Jutarnja, 08:00-16:00", smena.toString());
    }

    @Test
    @DisplayName("vratiNazivTabele vraca ime tabele smena")
    void testVratiNazivTabele() {
        assertEquals("smena", smena.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraca uslov sa imenom tabele i identifikatorom")
    void testVratiPrimarniKljuc() {
        assertEquals("smena.idSmena=1", smena.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraca nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("naziv, vremePocetka, vremeKraja", smena.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraca vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("'Jutarnja', '08:00', '16:00'", smena.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraca SQL NULL kada je vreme kraja null")
    void testVratiVrednostiZaUbacivanjeNullVremeKraja() {
        Smena s = new Smena();
        s.setIdSmena(2);
        s.setNaziv("Otvorena");
        s.setVremePocetka(LocalTime.of(10, 0));
        assertEquals("'Otvorena', '10:00', NULL", s.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraca SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("naziv='Jutarnja', vremePocetka='08:00', vremeKraja='16:00'",
                smena.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraca SQL NULL kada je vreme kraja null")
    void testVratiVrednostiZaIzmenuNullVremeKraja() {
        Smena s = new Smena();
        s.setIdSmena(2);
        s.setNaziv("Otvorena");
        s.setVremePocetka(LocalTime.of(10, 0));
        assertEquals("naziv='Otvorena', vremePocetka='10:00', vremeKraja=NULL",
                s.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraca praznu listu kada ResultSet nema redova")
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
        Time kraj2 = Time.valueOf(LocalTime.of(23, 0));

        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idSmena")).thenReturn(1, 2);
        when(rs.getString("naziv")).thenReturn("Jutarnja", "Vecernja");
        when(rs.getTime("vremePocetka")).thenReturn(pocetak1, pocetak2);
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
        assertEquals(LocalTime.of(23, 0), druga.getVremeKraja());
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
        when(rs.getTime("vremeKraja")).thenReturn(kraj, kraj);

        Smena rezultat = (Smena) smena.vratiObjekatIzRS(rs);
        assertEquals(7, rezultat.getIdSmena());
        assertEquals("Popodnevna", rezultat.getNaziv());
        assertEquals(LocalTime.of(9, 0), rezultat.getVremePocetka());
        assertEquals(LocalTime.of(17, 0), rezultat.getVremeKraja());
    }

    @Test
    @DisplayName("vratiObjekatIzRS postavlja vreme kraja na null kada ResultSet vraca null")
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
