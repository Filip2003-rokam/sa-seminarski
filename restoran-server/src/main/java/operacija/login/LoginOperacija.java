
package operacija.login;

import domen.Gost;
import domen.Konobar;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Cofara
 */
public class LoginOperacija extends ApstraktnaGenerickaOperacija {

    Konobar konobar;
    
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
        
        List<Konobar> sviKonobari = broker.getAll((Konobar) param, null);
        System.out.println("KLASA LoginOperacija SO " + sviKonobari);

        for (Konobar z : sviKonobari) {
            if (z.equals((Konobar) param)) {
                konobar = z;
                return;
            }
        }

        konobar = null;

        
    }
    
}
