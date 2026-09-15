package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import domen.Konobar;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility klasa za rad sa JSON formatom preko Gson biblioteke.
 * Konfigurisani {@link Gson} podrzava serijalizaciju i deserijalizaciju
 * tipova {@link LocalDate} i {@link LocalTime}, koje Gson podrazumevano ne podrzava.
 * Polje {@code sifra} klase {@link Konobar} se iskljucuje iz JSON izlaza
 * (bezbednosni razlozi), bez uticaja na Java serijalizaciju preko soketa.
 * Instanca se kreira jednom i kesira za ponovnu upotrebu.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public final class JsonUtil {

    /**
     * Kesirana Gson instanca sa registrovanim adapterima za java.time tipove,
     * iskljucivanjem sifre konobara i ukljucenim pretty printing-om.
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .addSerializationExclusionStrategy(new KonobarSifraExclusionStrategy())
            .create();

    /**
     * Privatni konstruktor – utility klasa se ne instancira.
     */
    private JsonUtil() {
    }

    /**
     * Vraca konfigurisanu {@link Gson} instancu spremnu za rad sa
     * {@link LocalDate} i {@link LocalTime} tipovima.
     *
     * @return kesirana Gson instanca
     */
    public static Gson getGson() {
        return GSON;
    }

    /**
     * Strategija koja pri serijalizaciji preskace polje {@code sifra}
     * klase {@link Konobar}, kako sifra ne bi zavrsila u JSON izvestajima.
     */
    private static final class KonobarSifraExclusionStrategy implements ExclusionStrategy {

        /**
         * Odredjuje da li se polje preskace pri serijalizaciji.
         *
         * @param f atributi polja koje se razmatra
         * @return {@code true} ako je polje sifra klase Konobar
         */
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getDeclaringClass() == Konobar.class && "sifra".equals(f.getName());
        }

        /**
         * Ne iskljucuje nijednu klasu u celini.
         *
         * @param clazz klasa koja se razmatra
         * @return uvek {@code false}
         */
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }

    /**
     * TypeAdapter za {@link LocalDate} u ISO formatu {@code yyyy-MM-dd}.
     * Podrzava null vrednosti u oba smera bez bacanja izuzetka.
     */
    private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {

        /** Format datuma: yyyy-MM-dd. */
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

        /**
         * Serijalizuje {@link LocalDate} u JSON string ili null.
         *
         * @param out JSON writer
         * @param value datum ili null
         * @throws IOException ako upis u writer ne uspe
         */
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.format(FORMATTER));
            }
        }

        /**
         * Deserijalizuje JSON string u {@link LocalDate} ili vraca null.
         *
         * @param in JSON reader
         * @return LocalDate objekat ili null
         * @throws IOException ako citanje iz reader-a ne uspe
         */
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDate.parse(in.nextString(), FORMATTER);
        }
    }

    /**
     * TypeAdapter za {@link LocalTime} u formatu {@code HH:mm:ss}.
     * Podrzava null vrednosti u oba smera bez bacanja izuzetka.
     */
    private static final class LocalTimeAdapter extends TypeAdapter<LocalTime> {

        /** Format vremena: HH:mm:ss. */
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

        /**
         * Serijalizuje {@link LocalTime} u JSON string ili null.
         *
         * @param out JSON writer
         * @param value vreme ili null
         * @throws IOException ako upis u writer ne uspe
         */
        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.format(FORMATTER));
            }
        }

        /**
         * Deserijalizuje JSON string u {@link LocalTime} ili vraca null.
         *
         * @param in JSON reader
         * @return LocalTime objekat ili null
         * @throws IOException ako citanje iz reader-a ne uspe
         */
        @Override
        public LocalTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalTime.parse(in.nextString(), FORMATTER);
        }
    }
}
