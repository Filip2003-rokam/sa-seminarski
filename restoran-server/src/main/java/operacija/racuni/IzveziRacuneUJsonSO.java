package operacija.racuni;

import domen.Racun;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;
import repository.db.impl.DbRepositoryGeneric;
import util.JsonUtil;

/**
 * Sistemska operacija za izvoz liste racuna u JSON fajl.
 * Serijalizuje prosledjenu listu preko {@link JsonUtil#getGson()} i upisuje
 * rezultat u folder {@code izvestaji} pored aplikacije. Naziv fajla sadrzi
 * datum i vreme izvoza (npr. {@code izvestaj_racuni_2026-09-15_2230.json}).
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class IzveziRacuneUJsonSO extends ApstraktnaGenerickaOperacija {

    /** Format datuma i vremena u nazivu fajla. */
    private static final DateTimeFormatter NAZIV_FAJLA_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");

    /** Direktorijum u koji se upisuje JSON fajl. */
    private final File izlazniDirektorijum;

    /** Apsolutna putanja kreiranog fajla nakon uspesnog izvoza. */
    private String putanjaFajla;

    /**
     * Kreira operaciju sa podrazumevanim repozitorijumom i folderom {@code izvestaji}.
     */
    public IzveziRacuneUJsonSO() {
        this(null, new File("izvestaji"));
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom i folderom {@code izvestaji}.
     * Omogucava testiranje bez stvarne baze podataka.
     *
     * @param broker repozitorijum koji operacija koristi
     */
    public IzveziRacuneUJsonSO(DbRepository broker) {
        this(broker, new File("izvestaji"));
    }

    /**
     * Kreira operaciju sa prosledjenim repozitorijumom i izlaznim direktorijumom.
     * Koristi se u testovima sa {@code @TempDir} da se fajlovi ne prave u projektu.
     *
     * @param broker repozitorijum koji operacija koristi; moze biti null
     *               (tada se koristi podrazumevani)
     * @param izlazniDirektorijum folder u koji se upisuje JSON fajl
     */
    public IzveziRacuneUJsonSO(DbRepository broker, File izlazniDirektorijum) {
        super(broker != null ? broker : new DbRepositoryGeneric());
        this.izlazniDirektorijum = izlazniDirektorijum;
    }

    /**
     * Proverava preduslove za izvoz racuna.
     * Parametar mora biti ne-null i ne-prazna lista racuna.
     *
     * @param param lista objekata tipa {@link Racun}
     * @throws Exception ako je param null, nije lista ili je lista prazna
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null) {
            throw new Exception("Sistem ne moze da izveze racune jer lista nije prosledjena.");
        }
        if (!(param instanceof List<?>)) {
            throw new Exception("Sistem ne moze da izveze racune jer prosledjeni objekat nije lista.");
        }
        List<?> lista = (List<?>) param;
        if (lista.isEmpty()) {
            throw new Exception("Sistem ne moze da izveze racune jer je lista prazna.");
        }
        for (Object element : lista) {
            if (!(element instanceof Racun)) {
                throw new Exception("Sistem ne moze da izveze racune jer lista ne sadrzi iskljucivo racune.");
            }
        }
    }

    /**
     * Serijalizuje listu racuna u JSON i upisuje je u fajl u izlaznom direktorijumu.
     * Folder se kreira ako ne postoji. Putanja kreiranog fajla cuva se u
     * {@link #putanjaFajla}.
     *
     * @param param lista racuna za izvoz
     * @param kljuc nije koriscen u ovoj operaciji
     * @throws Exception ako kreiranje foldera ili upis fajla ne uspe
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        List<Racun> racuni = (List<Racun>) param;

        if (!izlazniDirektorijum.exists() && !izlazniDirektorijum.mkdirs()) {
            throw new Exception("Sistem ne moze da kreira folder za izvestaje: "
                    + izlazniDirektorijum.getAbsolutePath());
        }

        String naziv = "izvestaj_racuni_" + LocalDateTime.now().format(NAZIV_FAJLA_FORMAT) + ".json";
        File fajl = new File(izlazniDirektorijum, naziv);
        String json = JsonUtil.getGson().toJson(racuni);
        Files.writeString(fajl.toPath(), json, StandardCharsets.UTF_8);
        putanjaFajla = fajl.getAbsolutePath();
    }

    /**
     * Vraca apsolutnu putanju JSON fajla kreiranog poslednjim izvrsavanjem.
     *
     * @return putanja fajla, ili null ako operacija jos nije uspesno izvrsena
     */
    public String getPutanjaFajla() {
        return putanjaFajla;
    }
}
