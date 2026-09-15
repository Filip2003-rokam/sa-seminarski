package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
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

class KonobarSmenaTest {

    private Konobar konobar;
    private Smena smena;
    private LocalDate datumSmene;
    private KonobarSmena konobarSmena;

    @BeforeEach
    void setUp() {
        konobar = new Konobar(1, "Marko", "Markovic", "marko", "sifra1");
        smena = new Smena(2, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
        datumSmene = LocalDate.of(2024, 5, 15);
        konobarSmena = new KonobarSmena(konobar, smena, datumSmene);
    }

    @AfterEach
    void tearDown() {
        konobar = null;
        smena = null;
        datumSmene = null;
        konobarSmena = null;
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat KonobarSmena")
    void testPrazanKonstruktorKreiraObjekat() {
        KonobarSmena ks = new KonobarSmena();
        assertNotNull(ks);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sve atribute")
    void testPunKonstruktorPostavljaSvaPolja() {
        assertEquals(konobar, konobarSmena.getKonobar());
        assertEquals(smena, konobarSmena.getSmena());
        assertEquals(datumSmene, konobarSmena.getDatumSmene());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null konobara")
    void testPunKonstruktorBacaIzuzetakZaNullKonobara() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new KonobarSmena(null, smena, datumSmene));
        assertEquals("Konobar u rasporedu ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null smenu")
    void testPunKonstruktorBacaIzuzetakZaNullSmenu() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new KonobarSmena(konobar, null, datumSmene));
        assertEquals("Smena u rasporedu ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Pun konstruktor baca izuzetak za null datum")
    void testPunKonstruktorBacaIzuzetakZaNullDatum() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new KonobarSmena(konobar, smena, null));
        assertEquals("Datum smene ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za konobar rade ispravno")
    void testSetGetKonobar() {
        KonobarSmena ks = new KonobarSmena();
        Konobar k = new Konobar(3, "Ana", "Anic", "ana", "sifra2");
        ks.setKonobar(k);
        assertEquals(k, ks.getKonobar());
    }

    @Test
    @DisplayName("setKonobar baca izuzetak za null")
    void testSetKonobarBacaIzuzetakZaNull() {
        KonobarSmena ks = new KonobarSmena();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ks.setKonobar(null));
        assertEquals("Konobar u rasporedu ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za smenu rade ispravno")
    void testSetGetSmena() {
        KonobarSmena ks = new KonobarSmena();
        Smena s = new Smena(4, "Vecernja", LocalTime.of(16, 0), LocalTime.of(23, 0));
        ks.setSmena(s);
        assertEquals(s, ks.getSmena());
    }

    @Test
    @DisplayName("setSmena baca izuzetak za null")
    void testSetSmenaBacaIzuzetakZaNull() {
        KonobarSmena ks = new KonobarSmena();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ks.setSmena(null));
        assertEquals("Smena u rasporedu ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("Setter i getter za datumSmene rade ispravno")
    void testSetGetDatumSmene() {
        KonobarSmena ks = new KonobarSmena();
        LocalDate datum = LocalDate.of(2025, 1, 10);
        ks.setDatumSmene(datum);
        assertEquals(datum, ks.getDatumSmene());
    }

    @Test
    @DisplayName("setDatumSmene baca izuzetak za null")
    void testSetDatumSmeneBacaIzuzetakZaNull() {
        KonobarSmena ks = new KonobarSmena();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ks.setDatumSmene(null));
        assertEquals("Datum smene ne sme biti null.", ex.getMessage());
    }

    @Test
    @DisplayName("equals vraca true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(konobarSmena.equals(konobarSmena));
    }

    @Test
    @DisplayName("equals vraca false kada se objekat poredi sa null")
    void testEqualsSaNull() {
        assertFalse(konobarSmena.equals(null));
    }

    @Test
    @DisplayName("equals vraca false kada se objekat poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(konobarSmena.equals("nije KonobarSmena"));
    }

    @ParameterizedTest
    @MethodSource("podaciZaEquals")
    @DisplayName("equals poredi po konobaru, smeni i datumu")
    void testEqualsPoKompozitnomKljucu(KonobarSmena prvi, KonobarSmena drugi, boolean ocekivano) {
        assertEquals(ocekivano, prvi.equals(drugi));
    }

    static Stream<Arguments> podaciZaEquals() {
        Konobar k1 = new Konobar(1, "Marko", "Markovic", "marko", "sifra1");
        Konobar k2 = new Konobar(1, "Drugo", "Ime", "x", "y");
        Smena s1 = new Smena(2, "Jutarnja", LocalTime.of(8, 0), LocalTime.of(16, 0));
        Smena s2 = new Smena(2, "Druga", LocalTime.of(9, 0), LocalTime.of(17, 0));
        LocalDate d1 = LocalDate.of(2024, 5, 15);
        LocalDate d2 = LocalDate.of(2024, 6, 1);
        return Stream.of(
                Arguments.of(
                        new KonobarSmena(k1, s1, d1),
                        new KonobarSmena(k2, s2, d1),
                        true
                ),
                Arguments.of(
                        new KonobarSmena(k1, s1, d1),
                        new KonobarSmena(k1, s1, d2),
                        false
                )
        );
    }

    @Test
    @DisplayName("Jednaki objekti imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        KonobarSmena drugi = new KonobarSmena(
                new Konobar(1, "Xx", "Yy", "ab", "cd"),
                new Smena(2, "Zzz", LocalTime.of(8, 0), LocalTime.of(16, 0)),
                LocalDate.of(2024, 5, 15)
        );
        assertEquals(konobarSmena.hashCode(), drugi.hashCode());
    }

    @Test
    @DisplayName("Visestruki pozivi hashCode na istom objektu vracaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(konobarSmena.hashCode(), konobarSmena.hashCode());
    }

    @Test
    @DisplayName("toString sadrzi konobar, smenu i datumSmene")
    void testToStringFormat() {
        String rezultat = konobarSmena.toString();
        assertTrue(rezultat.contains("konobar=" + konobar));
        assertTrue(rezultat.contains("smena=" + smena));
        assertTrue(rezultat.contains("datumSmene=" + datumSmene));
    }

    @Test
    @DisplayName("vratiNazivTabele vraca ime tabele konobarsmena")
    void testVratiNazivTabele() {
        assertEquals("konobarsmena", konobarSmena.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraca kompozitni uslov sa idKonobar, idSmena i datumSmene")
    void testVratiPrimarniKljuc() {
        assertEquals(
                "idKonobar=1 AND idSmena=2 AND datumSmene='2024-05-15'",
                konobarSmena.vratiPrimarniKljuc()
        );
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraca nazive kolona za INSERT")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("idKonobar, idSmena, datumSmene", konobarSmena.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraca vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("1, 2, '2024-05-15'", konobarSmena.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraca SET deo SQL upita za datumSmene")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("datumSmene='2024-05-15'", konobarSmena.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraca praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(konobarSmena.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dve veze konobar-smena")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idKonobar")).thenReturn(1, 3);
        when(rs.getString("ime")).thenReturn("Marko", "Ana");
        when(rs.getString("prezime")).thenReturn("Markovic", "Anic");
        when(rs.getString("korisnickoIme")).thenReturn("marko", "ana");
        when(rs.getString("sifra")).thenReturn("s1", "s2");
        when(rs.getInt("idSmena")).thenReturn(2, 4);
        when(rs.getString("naziv")).thenReturn("Jutarnja", "Vecernja");
        when(rs.getTime("vremePocetka")).thenReturn(
                java.sql.Time.valueOf(LocalTime.of(8, 0)),
                java.sql.Time.valueOf(LocalTime.of(16, 0))
        );
        when(rs.getTime("vremeKraja")).thenReturn(
                java.sql.Time.valueOf(LocalTime.of(16, 0)),
                java.sql.Time.valueOf(LocalTime.of(23, 0))
        );
        when(rs.getDate("datumSmene")).thenReturn(
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 15)),
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 16))
        );

        List<ApstraktniDomenskiObjekat> lista = konobarSmena.vratiListu(rs);

        assertEquals(2, lista.size());
        KonobarSmena prvi = (KonobarSmena) lista.get(0);
        assertEquals(1, prvi.getKonobar().getIdKonobar());
        assertEquals("Marko", prvi.getKonobar().getIme());
        assertEquals("Markovic", prvi.getKonobar().getPrezime());
        assertEquals("marko", prvi.getKonobar().getKorisnickoIme());
        assertEquals("s1", prvi.getKonobar().getSifra());
        assertEquals(2, prvi.getSmena().getIdSmena());
        assertEquals("Jutarnja", prvi.getSmena().getNaziv());
        assertEquals(LocalTime.of(8, 0), prvi.getSmena().getVremePocetka());
        assertEquals(LocalTime.of(16, 0), prvi.getSmena().getVremeKraja());
        assertEquals(LocalDate.of(2024, 5, 15), prvi.getDatumSmene());

        KonobarSmena drugi = (KonobarSmena) lista.get(1);
        assertEquals(3, drugi.getKonobar().getIdKonobar());
        assertEquals("Ana", drugi.getKonobar().getIme());
        assertEquals(4, drugi.getSmena().getIdSmena());
        assertEquals("Vecernja", drugi.getSmena().getNaziv());
        assertEquals(LocalTime.of(16, 0), drugi.getSmena().getVremePocetka());
        assertEquals(LocalTime.of(23, 0), drugi.getSmena().getVremeKraja());
        assertEquals(LocalDate.of(2024, 5, 16), drugi.getDatumSmene());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira KonobarSmena iz ResultSet-a sa kolonama pocetak i kraj")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idKonobar")).thenReturn(1);
        when(rs.getString("ime")).thenReturn("Marko");
        when(rs.getString("prezime")).thenReturn("Markovic");
        when(rs.getInt("idSmena")).thenReturn(2);
        when(rs.getString("naziv")).thenReturn("Jutarnja");
        when(rs.getTime("pocetak")).thenReturn(java.sql.Time.valueOf(LocalTime.of(8, 0)));
        when(rs.getTime("kraj")).thenReturn(java.sql.Time.valueOf(LocalTime.of(16, 0)));
        when(rs.getDate("datumSmene")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2024, 5, 15)));

        KonobarSmena rezultat = (KonobarSmena) konobarSmena.vratiObjekatIzRS(rs);

        assertEquals(1, rezultat.getKonobar().getIdKonobar());
        assertEquals("Marko", rezultat.getKonobar().getIme());
        assertEquals("Markovic", rezultat.getKonobar().getPrezime());
        assertEquals(2, rezultat.getSmena().getIdSmena());
        assertEquals("Jutarnja", rezultat.getSmena().getNaziv());
        assertEquals(LocalTime.of(8, 0), rezultat.getSmena().getVremePocetka());
        assertEquals(LocalTime.of(16, 0), rezultat.getSmena().getVremeKraja());
        assertEquals(LocalDate.of(2024, 5, 15), rezultat.getDatumSmene());
    }
}
