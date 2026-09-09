/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import domen.Konobar;
import forme.LoginForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Cofara
 */
public class LoginController {
    
    private final LoginForma lf;

    public LoginController(LoginForma lf) {
        this.lf = lf;
        addActionListeners();
    }

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

    public void otvoriFormu() {
        
        lf.setVisible(true);
        
    }
    

    
}
