# Sistem za upravljanje restoranom

Klijent-server desktop aplikacija u Javi za upravljanje restoranom: gosti, artikli, računi, smene, raspored rada i konobari. Komunikacija između klijenta i servera odvija se preko soketa; poslovna logika je na serverskoj strani u obliku sistemskih operacija.

## Tehnologije

| Tehnologija | Verzija |
| --- | --- |
| Java (JDK) | 21 |
| Maven | 3.9+ |
| JUnit | 5.10.2 |
| Mockito | 5.11.0 |
| Gson | 2.10.1 |
| MySQL Connector/J | 8.4.0 |
| Swing | (JDK) |

## Struktura projekta

Projekat je Maven multi-module:

- **restoran-zajednicki** - domenske klase, komunikacioni objekti (`Zahtev`, `Odgovor`, `Operacija`, `Posiljalac`, `Primalac`) i zajednički ugovori koje koriste i klijent i server.
- **restoran-server** - serverska aplikacija: prijem zahteva, sistemske operacije, pristup bazi (repository), konfiguracija i Swing forma za pokretanje/zaustavljanje servera. Uključuje i izvoz računa u JSON (`JsonUtil`, `IzveziRacuneUJsonSO`).
- **restoran-klijent** - klijentska Swing aplikacija: forme, kontroleri, koordinator i fasada komunikacije prema serveru.

## Preduslovi

- JDK 21
- Maven 3.9+
- MySQL (pokrenut lokalno)

## Pokretanje

### 1. Kreiranje baze

Pokrenite skriptu [`a_restoran.sql`](a_restoran.sql) nad MySQL instancom (kreira šemu i početne podatke).

### 2. Podešavanje pristupa bazi i portu

Uredite fajl:

`restoran-server/src/main/resources/config.properties`

Tipična polja: `url`, `username`, `password`, `port` (port na kojem server prihvata klijentske veze).

### 3. Build

Iz root foldera projekta:

```bash
mvn clean install
```

### 4. Pokretanje servera

```bash
java -jar restoran-server/target/restoran-server.jar
```

U serverskoj formi kliknite **Pokreni server**.

### 5. Pokretanje klijenta

U drugom terminalu:

```bash
java -jar restoran-klijent/target/restoran-klijent.jar
```

### 6. Prijava

Podrazumevani nalog: korisničko ime `admin`, lozinka `admin`.

## Testovi

```bash
mvn test
```

Testovi pokrivaju domenske klase (zajednički modul) i sistemske operacije (serverski modul), uključujući Gson/`JsonUtil` i izvoz računa u JSON.

## JavaDoc dokumentacija

```bash
mvn javadoc:javadoc
```

Generisana dokumentacija nalazi se u:

- `restoran-zajednicki/target/site/apidocs`
- `restoran-server/target/site/apidocs`
- `restoran-klijent/target/site/apidocs`

Otvorite `index.html` u odgovarajućem folderu.

## Domenske klase

Paket `domen` u modulu `restoran-zajednicki`:

Validacija vrednosti atributa vrši se u seterima domenskih klasa. Za nedozvoljene vrednosti seteri bacaju `IllegalArgumentException`.

- `ApstraktniDomenskiObjekat`
- `Artikal`
- `Gost`
- `KategorijaGosta`
- `Konobar`
- `KonobarSmena`
- `Racun`
- `Smena`
- `StavkaRacuna`

### Validacija

Primeri pravila validacije:

- ime gosta mora imati najmanje 3 karaktera;
- cena artikla mora biti veća od nule;
- vreme početka i vreme kraja smene ne smeju biti jednaki.

Smena može prelaziti ponoć. Metoda `prelaziPonoc` proverava da li smena prelazi u sledeći dan, dok `vratiTrajanjeUMinutima` vraća ukupno trajanje smene u minutima.

## Sistemske operacije

Paket `operacija` u modulu `restoran-server` (šablon `ApstraktnaGenerickaOperacija`):

- **login** - `LoginOperacija`
- **gosti** - `UcitajGosteSO`, `DodajGostaSO`, `IzmeniGostaSO`, `ObrisiGostaSO`
- **artikal** - `UcitajArtikleSO`, `DodajArtikalSO`, `IzmeniArtikalSO`, `ObrisiArtikalSO`
- **kategorijagosta** - `UcitajKategorijeGostijuSO`, `DodajKategorijuGostaSO`, `IzmeniKategorijuGostaSO`, `ObrisiKategorijuGostaSO`
- **konobar** - `UcitajKonobareSO`, `DodajKonobaraSO`, `IzmeniKonobaraSO`, `ObrisiKonobaraSO`
- **racuni** - `UcitajRacuneSO`, `DodajRacunSO`, `IzmeniRacunSO`, `ObrisiRacunSO`, `IzveziRacuneUJsonSO`
- **stavke** - `UcitajStavkeRacunaSO`, `ObrisiStavkuRacunaSO`
- **smena** - `UcitajSmeneSO`, `DodajSmenuSO`, `IzmeniSmenuSO`, `ObrisiSmenuSO`
- **raspored** - `UcitajRasporedSO`, `DodajRasporedSO`, `IzmeniRasporedSO`, `ObrisiRasporedSO`

## Izvoz računa u JSON

Sa forme za prikaz računa dugme **Izvezi u JSON** šalje listu prikazanih računa serveru. Operacija `IzveziRacuneUJsonSO` serijalizuje listu preko Gson-a (`JsonUtil` sa adapterima za `LocalDate` / `LocalTime`) i upisuje fajl u folder `izvestaji` pored radnog direktorijuma serverske aplikacije.

Naziv fajla sadrži datum i vreme izvoza, npr. `izvestaj_racuni_2026-09-15_2230.json`.
