package forme;

/**
 * Rezim rada forme za dodavanje ili izmenu entiteta.
 * Koristi se pri otvaranju formi kako bi se razlikovalo da li se
 * kreira novi zapis ili menja postojeci.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public enum FormaMod {
    /** Rezim dodavanja novog entiteta. */
    DODAJ,
    /** Rezim izmene postojeceg entiteta. */
    IZMENI
}
