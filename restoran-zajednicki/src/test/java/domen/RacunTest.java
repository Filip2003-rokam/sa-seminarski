package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RacunTest {

    private Konobar konobar;
    private Gost gost;
    private List<StavkaRacuna> stavke;
    private LocalDate datumIzdavanja;
    private LocalTime vremeIzdavanja;
    private Racun racun;

    @BeforeEach
    void setUp() {
        konobar = new Konobar(1, "Marko", "Markovic", "marko", "sifra1");
        gost = new Gost(2, "Petar", "Petrovic", null);
        stavke = new ArrayList<>();
        stavke.add(new StavkaRacuna(10, 1, 2, 500.0, 250.0, null));
        datumIzdavanja = LocalDate.of(2024, 5, 15);
        vremeIzdavanja = LocalTime.of(14, 30, 0);
        racun = new Racun(10, datumIzdavanja, vremeIzdavanja, 1500.0, true, konobar, gost, stavke);
    }

    @Test
    @DisplayName("Prazan konstruktor kreira objekat računa")
    void testPrazanKonstruktorKreiraObjekat() {
        Racun r = new Racun();
        assertNotNull(r);
    }

    @Test
    @DisplayName("Pun konstruktor postavlja sva polja računa")
    void testPunKonstruktorPostavljaSvaPolja() {
        assertEquals(10, racun.getIdRacun());
        assertEquals(datumIzdavanja, racun.getDatumIzdavanja());
        assertEquals(vremeIzdavanja, racun.getVremeIzdavanja());
        assertEquals(1500.0, racun.getUkupanIznos());
        assertTrue(racun.isJeIzdat());
        assertEquals(konobar, racun.getKonobar());
        assertEquals(gost, racun.getGost());
        assertEquals(stavke, racun.getStavke());
    }

    @Test
    @DisplayName("Setter i getter za idRacun rade ispravno")
    void testSetGetIdRacun() {
        Racun r = new Racun();
        r.setIdRacun(20);
        assertEquals(20, r.getIdRacun());
    }

    @Test
    @DisplayName("Setter i getter za datumIzdavanja rade ispravno")
    void testSetGetDatumIzdavanja() {
        Racun r = new Racun();
        LocalDate datum = LocalDate.of(2025, 1, 1);
        r.setDatumIzdavanja(datum);
        assertEquals(datum, r.getDatumIzdavanja());
    }

    @Test
    @DisplayName("Setter i getter za vremeIzdavanja rade ispravno")
    void testSetGetVremeIzdavanja() {
        Racun r = new Racun();
        LocalTime vreme = LocalTime.of(18, 45);
        r.setVremeIzdavanja(vreme);
        assertEquals(vreme, r.getVremeIzdavanja());
    }

    @Test
    @DisplayName("Setter i getter za ukupanIznos rade ispravno")
    void testSetGetUkupanIznos() {
        Racun r = new Racun();
        r.setUkupanIznos(999.5);
        assertEquals(999.5, r.getUkupanIznos());
    }

    @Test
    @DisplayName("Setter i getter za jeIzdat rade ispravno")
    void testSetGetJeIzdat() {
        Racun r = new Racun();
        r.setJeIzdat(false);
        assertFalse(r.isJeIzdat());
        r.setJeIzdat(true);
        assertTrue(r.isJeIzdat());
    }

    @Test
    @DisplayName("Setter i getter za ugnježdeni Konobar rade ispravno")
    void testSetGetKonobar() {
        Racun r = new Racun();
        Konobar k = new Konobar(5, "Ana", "Anic", "ana", "pass");
        r.setKonobar(k);
        assertEquals(k, r.getKonobar());
        assertEquals(5, r.getKonobar().getIdKonobar());
    }

    @Test
    @DisplayName("Setter i getter za ugnježdeni Gost rade ispravno")
    void testSetGetGost() {
        Racun r = new Racun();
        Gost g = new Gost(8, "Jovan", "Jovic", null);
        r.setGost(g);
        assertEquals(g, r.getGost());
        assertEquals(8, r.getGost().getIdGost());
    }

    @Test
    @DisplayName("Setter i getter za listu stavki rade ispravno")
    void testSetGetStavke() {
        Racun r = new Racun();
        List<StavkaRacuna> lista = new ArrayList<>();
        lista.add(new StavkaRacuna(1, 1, 1, 100.0, 100.0, null));
        r.setStavke(lista);
        assertEquals(lista, r.getStavke());
        assertEquals(1, r.getStavke().size());
    }

    @Test
    @DisplayName("equals vraća true kada se objekat poredi sam sa sobom")
    void testEqualsIstiObjekat() {
        assertTrue(racun.equals(racun));
    }

    @Test
    @DisplayName("equals vraća false kada se račun poredi sa null")
    void testEqualsSaNull() {
        assertFalse(racun.equals(null));
    }

    @Test
    @DisplayName("equals vraća false kada se račun poredi sa objektom drugog tipa")
    void testEqualsSaDrugimTipom() {
        assertFalse(racun.equals("nije racun"));
    }

    @Test
    @DisplayName("Dva računa sa istim identifikatorom su jednaka i ako se ostala polja razlikuju")
    void testEqualsIstiIdRazlicitaOstalaPolja() {
        Racun drugi = new Racun(10, LocalDate.of(2020, 1, 1), LocalTime.of(10, 0), 1.0, false, null, null, null);
        assertTrue(racun.equals(drugi));
    }

    @Test
    @DisplayName("Dva računa sa različitim identifikatorom nisu jednaka")
    void testEqualsRazlicitId() {
        Racun drugi = new Racun(11, datumIzdavanja, vremeIzdavanja, 1500.0, true, konobar, gost, stavke);
        assertFalse(racun.equals(drugi));
    }

    @Test
    @DisplayName("Jednaki računi imaju isti hashCode")
    void testHashCodeJednakiObjektiImajuIstiHashCode() {
        Racun drugi = new Racun(10, null, null, 0.0, false, null, null, null);
        assertEquals(racun.hashCode(), drugi.hashCode());
    }

    @Test
    @DisplayName("Višestruki pozivi hashCode na istom objektu vraćaju istu vrednost")
    void testHashCodeKonzistentnost() {
        assertEquals(racun.hashCode(), racun.hashCode());
    }

    @Test
    @DisplayName("toString sadrži id, datum, vreme, ukupanIznos i jeIzdat")
    void testToStringFormat() {
        String rezultat = racun.toString();
        assertTrue(rezultat.contains("id=10"));
        assertTrue(rezultat.contains("datum=" + datumIzdavanja));
        assertTrue(rezultat.contains("vreme=" + vremeIzdavanja));
        assertTrue(rezultat.contains("ukupanIznos=1500.0"));
        assertTrue(rezultat.contains("jeIzdat=true"));
    }

    @Test
    @DisplayName("vratiNazivTabele vraća ime tabele racun sa razmakom na kraju")
    void testVratiNazivTabele() {
        assertEquals("racun ", racun.vratiNazivTabele());
    }

    @Test
    @DisplayName("vratiPrimarniKljuc vraća uslov racun.idRacun bez razmaka u imenu tabele")
    void testVratiPrimarniKljuc() {
        assertEquals("racun.idRacun=10", racun.vratiPrimarniKljuc());
    }

    @Test
    @DisplayName("vratiKoloneZaUbacivanje vraća nazive kolona bez identifikatora")
    void testVratiKoloneZaUbacivanje() {
        assertEquals(
                "datumIzdavanja, vremeIzdavanja, ukupanIznos, jeIzdat, idKonobar, idGost",
                racun.vratiKoloneZaUbacivanje()
        );
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje vraća SQL Date/Time i jeIzdat kao 1 ili 0")
    void testVratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum = java.sql.Date.valueOf(datumIzdavanja);
        java.sql.Time sqlVreme = java.sql.Time.valueOf(vremeIzdavanja);
        assertEquals(
                "'" + sqlDatum + "', '" + sqlVreme + "', 1500.0, 1, 1, 2",
                racun.vratiVrednostiZaUbacivanje()
        );
    }

    @Test
    @DisplayName("vratiVrednostiZaUbacivanje koristi 0 kada račun nije izdat")
    void testVratiVrednostiZaUbacivanjeJeIzdatFalse() {
        racun.setJeIzdat(false);
        java.sql.Date sqlDatum = java.sql.Date.valueOf(datumIzdavanja);
        java.sql.Time sqlVreme = java.sql.Time.valueOf(vremeIzdavanja);
        assertEquals(
                "'" + sqlDatum + "', '" + sqlVreme + "', 1500.0, 0, 1, 2",
                racun.vratiVrednostiZaUbacivanje()
        );
    }

    @Test
    @DisplayName("vratiVrednostiZaIzmenu vraća SET deo SQL upita")
    void testVratiVrednostiZaIzmenu() {
        assertEquals(
                "datumIzdavanja='2024-05-15', vremeIzdavanja='14:30', ukupanIznos=1500.0, jeIzdat=true, idKonobar=1, idGost=2",
                racun.vratiVrednostiZaIzmenu()
        );
    }

    @Test
    @DisplayName("vratiListu vraća praznu listu kada ResultSet nema redova")
    void testVratiListuPrazanResultSet() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        assertTrue(racun.vratiListu(rs).isEmpty());
    }

    @Test
    @DisplayName("vratiListu mapira dva reda ResultSet-a u listu sa dva računa")
    void testVratiListuDvaReda() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("idKonobar")).thenReturn(1, 3);
        when(rs.getString("k.ime")).thenReturn("Marko", "Ana");
        when(rs.getString("k.prezime")).thenReturn("Markovic", "Anic");
        when(rs.getString("k.korisnickoIme")).thenReturn("marko", "ana");
        when(rs.getString("k.sifra")).thenReturn("s1", "s2");
        when(rs.getInt("idGost")).thenReturn(2, 4);
        when(rs.getString("g.ime")).thenReturn("Petar", "Jovan");
        when(rs.getString("g.prezime")).thenReturn("Petrovic", "Jovic");
        when(rs.getInt("idRacun")).thenReturn(10, 11);
        when(rs.getDate("datumIzdavanja")).thenReturn(
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 15)),
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 15)),
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 16)),
                java.sql.Date.valueOf(LocalDate.of(2024, 5, 16))
        );
        when(rs.getTime("vremeIzdavanja")).thenReturn(
                java.sql.Time.valueOf(LocalTime.of(14, 30, 0)),
                java.sql.Time.valueOf(LocalTime.of(14, 30, 0)),
                java.sql.Time.valueOf(LocalTime.of(15, 0, 0)),
                java.sql.Time.valueOf(LocalTime.of(15, 0, 0))
        );
        when(rs.getDouble("ukupanIznos")).thenReturn(1500.0, 800.0);
        when(rs.getBoolean("jeIzdat")).thenReturn(true, false);

        List<ApstraktniDomenskiObjekat> lista = racun.vratiListu(rs);

        assertEquals(2, lista.size());
        Racun prvi = (Racun) lista.get(0);
        assertEquals(10, prvi.getIdRacun());
        assertEquals(LocalDate.of(2024, 5, 15), prvi.getDatumIzdavanja());
        assertEquals(LocalTime.of(14, 30, 0), prvi.getVremeIzdavanja());
        assertEquals(1500.0, prvi.getUkupanIznos());
        assertTrue(prvi.isJeIzdat());
        assertEquals(1, prvi.getKonobar().getIdKonobar());
        assertEquals("Marko", prvi.getKonobar().getIme());
        assertEquals("Markovic", prvi.getKonobar().getPrezime());
        assertEquals("marko", prvi.getKonobar().getKorisnickoIme());
        assertEquals("s1", prvi.getKonobar().getSifra());
        assertEquals(2, prvi.getGost().getIdGost());
        assertEquals("Petar", prvi.getGost().getIme());
        assertEquals("Petrovic", prvi.getGost().getPrezime());
        assertNull(prvi.getStavke());

        Racun drugi = (Racun) lista.get(1);
        assertEquals(11, drugi.getIdRacun());
        assertEquals(LocalDate.of(2024, 5, 16), drugi.getDatumIzdavanja());
        assertEquals(LocalTime.of(15, 0, 0), drugi.getVremeIzdavanja());
        assertEquals(800.0, drugi.getUkupanIznos());
        assertFalse(drugi.isJeIzdat());
        assertEquals(3, drugi.getKonobar().getIdKonobar());
        assertEquals("Ana", drugi.getKonobar().getIme());
        assertEquals(4, drugi.getGost().getIdGost());
        assertEquals("Jovan", drugi.getGost().getIme());
        assertNull(drugi.getStavke());
    }

    @Test
    @DisplayName("vratiObjekatIzRS kreira račun bez ugnježdenih objekata")
    void testVratiObjekatIzRS() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("idRacun")).thenReturn(7);
        when(rs.getDate("datumIzdavanja")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2024, 6, 1)));
        when(rs.getTime("vremeIzdavanja")).thenReturn(java.sql.Time.valueOf(LocalTime.of(12, 0, 0)));
        when(rs.getDouble("ukupanIznos")).thenReturn(420.0);
        when(rs.getBoolean("jeIzdat")).thenReturn(true);

        Racun rezultat = (Racun) racun.vratiObjekatIzRS(rs);

        assertEquals(7, rezultat.getIdRacun());
        assertEquals(LocalDate.of(2024, 6, 1), rezultat.getDatumIzdavanja());
        assertEquals(LocalTime.of(12, 0, 0), rezultat.getVremeIzdavanja());
        assertEquals(420.0, rezultat.getUkupanIznos());
        assertTrue(rezultat.isJeIzdat());
        assertNull(rezultat.getKonobar());
        assertNull(rezultat.getGost());
        assertNull(rezultat.getStavke());
    }
}
