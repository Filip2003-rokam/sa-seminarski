
package operacija.login;

import domen.Gost;
import domen.Konobar;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbRepository;

/**
 *
 * @author Cofara
 */
public class LoginOperacija extends ApstraktnaGenerickaOperacija {

    Konobar konobar;
    
    

    public LoginOperacija() {
        super();
    }

    public LoginOperacija(DbRepository broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object param) throws Exception {
        if(param == null || !(param instanceof Konobar)){
            throw new Exception("Ne moze da se uloguje");
        }
    }

    public Konobar getKonobar() {
        return konobar;
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        
        Konobar trazeni = (Konobar) param;
        List<Konobar> sviKonobari = broker.getAll(trazeni, null);
        System.out.println("KLASA LoginOperacija SO " + sviKonobari);

        for (Konobar k : sviKonobari) {
            if (k.proveriKredencijale(trazeni.getKorisnickoIme(), trazeni.getSifra())) {
                konobar = k;
                return;
            }
        }

        konobar = null;

        
    }
    
}
