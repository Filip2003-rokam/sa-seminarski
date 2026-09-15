package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import forme.LoginForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 * Kontroler forme za prijavu na sistem ({@link forme.LoginForma}).
 * U klijentskom MVC-u povezuje login formu sa komunikacionim slojem:
 * ucitava korisnicko ime i lozinku, salje zahtev za prijavu i
 * po uspehu otvara glavnu formu preko {@link cordinator.Cordinator}-a.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class LoginController {
    
    /** Forma za prijavu kojom ovaj kontroler upravlja. */
    private final LoginForma lf;

    /**
     * Kreira kontroler i registruje listenere na formi.
     *
     * @param lf forma za prijavu
     */
    public LoginController(LoginForma lf) {
        this.lf = lf;
        addActionListeners();
    }

    /**
     * Registruje listener za dugme prijave.
     */
    private void addActionListeners() {

        lf.loginActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                prijava(e);
                
            }

            private void prijava(ActionEvent e) {
                
                String ki = lf.getjTextFieldUsername().getText().trim();
                String pass = String.valueOf(lf.getjPasswordField1().getPassword());

                Komunikacija.getInstance().konekcija();
                Konobar ulogovani = Komunikacija.getInstance().login(ki,pass);

                if(ulogovani == null){
                    
                    JOptionPane.showMessageDialog(lf, "Neuspesnja prijava na sistem","GRESKA", JOptionPane.ERROR_MESSAGE);
                    
                }else{
                    Cordinator.getInstance().setUlogovani(ulogovani);
                    JOptionPane.showMessageDialog(lf, "Uspesnja prijava na sistem","Uspesno", JOptionPane.INFORMATION_MESSAGE);
                    Cordinator.getInstance().otvoriGlavnuFormu();
                    lf.dispose();
                }
                
            }
            
        });
        
    }

    /**
     * Prikazuje formu za prijavu.
     */
    public void otvoriFormu() {
        
        lf.setVisible(true);
        
    }
    

    
}
