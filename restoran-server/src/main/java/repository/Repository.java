package repository;

import java.util.List;

/**
 * Genericki interfejs spremista (repository) za CRUD operacije nad entitetima.
 *
 * @param <T> tip entitetskog objekta nad kojim se vrse operacije
 * @author Filip Oketic
 * @version 1.0
 */
public interface Repository<T> {

    /**
     * Vraca listu objekata iz spremista uz opcioni uslov.
     *
     * @param param objekat koji odredjuje tip/tabelu i mapiranje rezultata
     * @param uslov SQL uslov (npr. WHERE klauzula) ili null ako uslov nije potreban
     * @return lista objekata tipa T
     * @throws Exception ako dodje do greske pri citanju
     */
    List<T> getAll(T param, String uslov) throws Exception;

    /**
     * Dodaje novi objekat u spremiste.
     *
     * @param param objekat koji se ubacuje
     * @throws Exception ako dodje do greske pri ubacivanju
     */
    void add(T param) throws Exception;

    /**
     * Menja postojeci objekat u spremistu.
     *
     * @param param objekat sa novim vrednostima
     * @throws Exception ako dodje do greske pri izmeni
     */
    void edit(T param) throws Exception;

    /**
     * Brise objekat iz spremista.
     *
     * @param param objekat koji se brise
     * @throws Exception ako dodje do greske pri brisanju
     */
    void delete(T param) throws Exception;

    /**
     * Vraca sve objekte iz spremista bez dodatnog uslova.
     *
     * @return lista svih objekata tipa T
     */
    List<T> getAll();

    /**
     * Dodaje objekat u spremiste i vraca generisani primarni kljuc.
     *
     * @param param objekat koji se ubacuje
     * @return generisani kljuc (ID) ubacenog reda
     * @throws Exception ako dodje do greske pri ubacivanju
     */
    int addReturnKey(T param) throws Exception;

}
