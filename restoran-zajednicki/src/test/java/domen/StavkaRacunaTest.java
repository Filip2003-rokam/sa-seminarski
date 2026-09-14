package domen;

import java.sql.ResultSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Test
    @DisplayName("Prazan konstruktor kreira objekat stavke računa")
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
    @DisplayName("Setter i getter za idRacun rade ispravno")
    void testSetGetIdRacun() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setIdRacun(20);
        assertEquals(20, sr.getIdRacun());
    }

    @Test
    @DisplayName("Setter i getter za rb rade ispravno")
    void testSetGetRb() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setRb(3);
        assertEquals(3, sr.getRb());
    }

    @Test
    @DisplayName("Setter i getter za kolicina rade ispravno")
    void testSetGetKolicina() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setKolicina(5);
        assertEquals(5, sr.getKolicina());
    }

    @Test
    @DisplayName("Setter i getter za ukupanIznos rade ispravno")
    void testSetGetUkupanIznos() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setUkupanIznos(300.0);
        assertEquals(300.0, sr.getUkupanIznos());
    }

    @Test
    @DisplayName("Setter i getter za cenu rade ispravno")
    void testSetGetCena() {
        StavkaRacuna sr = new StavkaRacuna();
        sr.setCena(150.0);
        assertEquals(150.0, sr.getCena());
    }

    @Test
    @DisplayName("Setter i getter za ugnježdeni Artikal rade ispravno")
    void testSetGetArtikal() {
        StavkaRacuna sr = new StavkaRacuna();
        Artikal a = new Artikal(9, "Sok", 200.0, "Pice");
        sr.setArtikal(a);
        assertEquals(a, sr.getArtikal());
        assertEquals(9, sr.getArtikal().getIdArtikal());
        assertEquals("Sok", sr.getArtikal().getNaziv());
    }

    @Test
    @DisplayName("equals vraća true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(stavka.equals(stavka));
    }

    @Test
    @DisplayName("equals vraća false kada se stavka poredi sa null")
    void testEqualsSaNull() {
        assertFalse(stavka.equals(null));
    }

    @Test
    @DisplayName("equals vraća false kada se stavka poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(stavka.equals("nije stavka"));
    }

    @Test
    @DisplayName("Dve stavke sa istim idRacun i rb su jednake i ako se ostala polja razlikuju")
    void testEqualsIstiIdRacunIRb() {
        StavkaRacuna druga = new StavkaRacuna(10, 1, 99, 1.0, 1.0, null);
        assertTrue(stavka.equals(druga));
    }

    @Test
    @DisplayName("Dve stavke sa različitim idRacun ili rb nisu jednake")
    void testEqualsRazlicitIdRacunIliRb() {
        StavkaRacuna drugaRb = new StavkaRacuna(10, 2, 2, 1700.0, 850.0, artikal);
        StavkaRacuna drugiRacun = new StavkaRacuna(11, 1, 2, 1700.0, 850.0, artikal);
        assertFalse(stavka.equals(drugaRb));
        assertFalse(stavka.equals(drugiRacun));
    }

    @Test
    @DisplayName("Jednake stavke imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        StavkaRacuna druga = new StavkaRacuna(10, 1, 0, 0.0, 0.0, null);
        assertEquals(stavka.hashCode(), druga.hashCode());
    }

    @Test
    @DisplayName("Višestruki pozivi hashCode na istom objektu vraćaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(stavka.hashCode(), stavka.hashCode());
    }

    @Test
    @DisplayName("toString sadrži idRacun, rb, kolicina, cena i ukupanIznos")
    void testToStringFormat() {
        String rezultat = stavka.toString();
        assertTrue(rezultat.contains("idRacun=10"));
        assertTrue(rezultat.contains("rb=1"));
        assertTrue(rezultat.contains("kolicina=2"));
        assertTrue(rezultat.contains("cena=850.0"));
        assertTrue(rezultat.contains("ukupanIznos=1700.0"));
    }

    @Test
    @DisplayName("vratiNazivTabele vraća ime tabele stavkaracuna")
    void testVratiNazivTabele() {
        assertEquals("stavkaracuna", stavka.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraća uslov sa razmakom na početku")
    void testVratiPrimarniKljuc() {
        assertEquals(" rb=1 AND idRacun=10", stavka.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraća nazive kolona za INSERT")
    void testVratiKoloneZaUbacivanje() {
        assertEquals("idRacun, rb, kolicina, cena, ukupanIznos, idArtikal", stavka.vratiKoloneZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća vrednosti u SQL formatu za INSERT")
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("10, 1, 2, 850.0, 1700.0, 5", stavka.vratiVrednostiZaUbacivanje());
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals("kolicina=2, cena=850.0, ukupanIznos=1700.0, idArtikal=5", stavka.vratiVrednostiZaIzmenu());
    }

    @Test
    @DisplayName("vratiListu vraća praznu listu kada ResultSet nema redova")
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
    @DisplayName("vratiObjekatIzRS kreira stavku bez ugnježdenog artikla")
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
