package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility klasa za rad sa JSON formatom preko Gson biblioteke.
 * Konfigurisani {@link Gson} podrzava serijalizaciju i deserijalizaciju
 * tipova {@link LocalDate} i {@link LocalTime}, koje Gson podrazumevano ne podrzava.
 * Instanca se kreira jednom i kesira za ponovnu upotrebu.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public final class JsonUtil {

    /**
     * Kesirana Gson instanca sa registrovanim adapterima za java.time tipove
     * i ukljucenim pretty printing-om.
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
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
