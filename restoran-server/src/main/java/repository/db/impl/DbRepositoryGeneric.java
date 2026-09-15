package repository.db.impl;

import domen.ApstraktniDomenskiObjekat;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 * Genericka implementacija {@link DbRepository} nad {@link ApstraktniDomenskiObjekat}.
 * SQL upiti se grade dinamicki pozivanjem ADO metoda:
 * <ul>
 *   <li>{@code vratiNazivTabele()} – ime tabele</li>
 *   <li>{@code vratiKoloneZaUbacivanje()} – lista kolona za INSERT</li>
 *   <li>{@code vratiVrednostiZaUbacivanje()} – vrednosti za INSERT</li>
 *   <li>{@code vratiVrednostiZaIzmenu()} – SET deo za UPDATE</li>
 *   <li>{@code vratiPrimarniKljuc()} – WHERE uslov po PK</li>
 *   <li>{@code vratiListu(ResultSet)} – mapiranje ResultSet-a u listu objekata</li>
 * </ul>
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DbRepositoryGeneric implements DbRepository<ApstraktniDomenskiObjekat> {

    /**
     * Kreira genericki repozitorijum koji radi nad MySQL bazom
     * preko {@link DbConnectionFactory}.
     */
    public DbRepositoryGeneric() {
    }

    /**
     * Ucitava sve redove iz tabele koju odredjuje {@code param.vratiNazivTabele()}.
     * Ako je {@code uslov} razlicit od null, nadovezuje se na SELECT upit
     * (npr. WHERE / JOIN klauzule koje isporucuje sistemska operacija).
     * Rezultat se mapira preko {@code param.vratiListu(ResultSet)}.
     *
     * @param param ADO objekat koji odredjuje tabelu i mapiranje
     * @param uslov opcioni SQL nastavak (WHERE itd.) ili null
     * @return lista ucitanih domenskih objekata
     * @throws Exception ako izvrsavanje SELECT upita ne uspe
     */
    @Override
    public List<ApstraktniDomenskiObjekat> getAll(ApstraktniDomenskiObjekat param, String uslov) throws Exception {

        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        String upit = "SELECT * FROM " + param.vratiNazivTabele();
        if(uslov != null) { // TODO
            upit = upit + uslov;
        }
        System.out.println(upit);
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);

        lista = param.vratiListu(rs);

        rs.close();
        st.close();

        return lista;


    }


    /**
     * Ubaci novi red: {@code INSERT INTO tabela (kolone) VALUES (vrednosti)},
     * gde se kolone i vrednosti dobijaju iz {@code vratiKoloneZaUbacivanje()}
     * i {@code vratiVrednostiZaUbacivanje()}.
     *
     * @param param objekat koji se ubacuje
     * @throws Exception ako INSERT ne uspe
     */
    @Override
    public void add(ApstraktniDomenskiObjekat param) throws Exception {

        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaUbacivanje() +
                      ") VALUES (" + param.vratiVrednostiZaUbacivanje() + ")";

        System.out.println("SQL UPIT KOJI SE SALJE: " + upit);

        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();


    }

    /**
     * Menja postojeci red: {@code UPDATE tabela SET ... WHERE pk},
     * gde SET deo daje {@code vratiVrednostiZaIzmenu()}, a WHERE
     * {@code vratiPrimarniKljuc()}.
     *
     * @param param objekat sa novim vrednostima
     * @throws Exception ako UPDATE ne uspe
     */
    @Override
    public void edit(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "UPDATE " + param.vratiNazivTabele() + " SET " +
                       param.vratiVrednostiZaIzmenu() +
                       " WHERE " + param.vratiPrimarniKljuc();

        System.out.println(upit); // čisto da vidiš šta se šalje u bazu

        Statement st = DbConnectionFactory.getInstance()
                                          .getConnection()
                                          .createStatement();
        st.executeUpdate(upit);
        st.close();
    }


    /**
     * Brise red: {@code DELETE FROM tabela WHERE pk},
     * gde WHERE uslov daje {@code vratiPrimarniKljuc()}.
     *
     * @param param objekat koji se brise
     * @throws Exception ako DELETE ne uspe
     */
    @Override
    public void delete(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "DELETE FROM " + param.vratiNazivTabele() + " WHERE " +
                       param.vratiPrimarniKljuc();
        System.out.println(upit);
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }


    /**
     * Varijanta bez parametara – nije podrzana u ovoj implementaciji.
     *
     * @return nikad ne vraca vrednost
     * @throws UnsupportedOperationException uvek
     */
    @Override
    public List<ApstraktniDomenskiObjekat> getAll() { //TODO
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Isto kao {@link #add}, ali koristi {@link PreparedStatement} sa
     * {@code RETURN_GENERATED_KEYS} i vraca generisani ID.
     * SQL se i dalje gradi preko {@code vratiNazivTabele()},
     * {@code vratiKoloneZaUbacivanje()} i {@code vratiVrednostiZaUbacivanje()}.
     *
     * @param param objekat koji se ubacuje
     * @return generisani primarni kljuc, ili -1 ako baza nije vratila kljuc
     * @throws Exception ako INSERT ne uspe
     */
    @Override
    public int addReturnKey(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "INSERT INTO " + param.vratiNazivTabele() +
              " (" + param.vratiKoloneZaUbacivanje() +
              ") VALUES (" + param.vratiVrednostiZaUbacivanje() + ")";

        PreparedStatement ps = DbConnectionFactory.getInstance()
                .getConnection()
                .prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int generatedId = -1;
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return generatedId;

    }



}
