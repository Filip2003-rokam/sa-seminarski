package komunikacija;

import java.io.Serializable;

/**
 * Enumeracija svih operacija koje klijent moze da zahteva od servera.
 * Svaka konstanta predstavlja jednu konkretnu akciju u sistemu restorana
 * (prijava, CRUD nad gostima, artiklima, racunima, smenama i ostalim entitetima).
 * Koristi se u objektu {@link Zahtev} radi identifikacije trazene operacije.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public enum Operacija implements Serializable {

    /** Prijava konobara u sistem (login). */
    LOGIN,
    /** Ucitavanje liste svih gostiju. */
    UCITAJ_GOSTE,
    /** Brisanje postojeceg gosta. */
    OBRISI_GOSTA,
    /** Dodavanje novog gosta. */
    DODAJ_GOSTA,
    /** Izmena podataka postojeceg gosta. */
    IZMENI_GOSTA,
    /** Ucitavanje liste svih racuna. */
    UCITAJ_RACUNE,
    /** Ucitavanje liste svih artikala. */
    UCITAJ_ARTIKLE,
    /** Dodavanje novog artikla. */
    DODAJ_ARTIKAL,
    /** Izmena podataka postojeceg artikla. */
    IZMENI_ARTIKAL,
    /** Brisanje postojeceg artikla. */
    OBRISI_ARTIKAL,
    /** Dodavanje nove kategorije gosta. */
    DODAJ_KATEGORIJU_GOSTA,
    /** Ucitavanje liste svih kategorija gostiju. */
    UCITAJ_KATEGORIJE_GOSTIJU,
    /** Izmena postojece kategorije gosta. */
    IZMENI_KATEGORIJU_GOSTA,
    /** Brisanje postojece kategorije gosta. */
    OBRISI_KATEGORIJU_GOSTA,
    /** Izmena podataka postojeceg konobara. */
    IZMENI_KONOBARA,
    /** Dodavanje novog konobara. */
    DODAJ_KONOBARA,
    /** Ucitavanje liste svih konobara. */
    UCITAJ_KONOBARA,
    /** Brisanje postojeceg konobara. */
    OBRISI_KONOBARA,
    /** Ucitavanje stavki racuna. */
    UCITAJ_STAVKE,
    /** Ucitavanje liste svih smena. */
    UCITAJ_SMENE,
    /** Dodavanje nove smene. */
    DODAJ_SMENU,
    /** Izmena postojece smene. */
    IZMENI_SMENU,
    /** Brisanje postojece smene. */
    OBRISI_SMENU,
    /** Ucitavanje rasporeda rada. */
    UCITAJ_RASPORED,
    /** Izmena postojeceg rasporeda rada. */
    IZMENI_RASPORED,
    /** Dodavanje novog rasporeda rada. */
    DODAJ_RASPORED,
    /** Brisanje postojeceg rasporeda rada. */
    OBRISI_RASPORED,
    /** Brisanje postojeceg racuna. */
    OBRISI_RACUN,
    /** Dodavanje novog racuna. */
    DODAJ_RACUN,
    /** Brisanje stavke racuna. */
    OBRISI_STAVKU,
    /** Izmena postojeceg racuna. */
    IZMENI_RACUN,
    /** Izvoz liste racuna u JSON fajl. */
    IZVEZI_RACUNE

}
