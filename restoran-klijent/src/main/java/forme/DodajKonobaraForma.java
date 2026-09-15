package forme;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Forma za dodavanje novog ili izmenu postojeceg konobara.
 * Omogucava unos identifikatora, imena, prezimena, korisnickog imena
 * i lozinke. Koristi se u dva moda koje kontroler postavlja pre prikaza.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajKonobaraForma extends javax.swing.JFrame {

    /**
     * Kreira formu i inicijalizuje Swing komponente.
     */
    public DodajKonobaraForma() {
        initComponents();
    }

    /**
     * Vraca dugme za dodavanje.
     *
     * @return dugme za dodavanje
     */
    public JButton getjButtonDodaj() {
        return jButtonDodaj;
    }

    /**
     * Postavlja dugme za dodavanje.
     *
     * @param jButtonDodaj dugme za dodavanje
     */
    public void setjButtonDodaj(JButton jButtonDodaj) {
        this.jButtonDodaj = jButtonDodaj;
    }

    /**
     * Vraca dugme za izmenu.
     *
     * @return dugme za izmenu
     */
    public JButton getjButtonIzmeni() {
        return jButtonIzmeni;
    }

    /**
     * Postavlja dugme za izmenu.
     *
     * @param jButtonIzmeni dugme za izmenu
     */
    public void setjButtonIzmeni(JButton jButtonIzmeni) {
        this.jButtonIzmeni = jButtonIzmeni;
    }

    /**
     * Vraca tekstualno polje za identifikator.
     *
     * @return tekstualno polje za identifikator
     */
    public JTextField getjTextFieldId() {
        return jTextFieldId;
    }

    /**
     * Postavlja tekstualno polje za identifikator.
     *
     * @param jTextFieldId tekstualno polje za identifikator
     */
    public void setjTextFieldId(JTextField jTextFieldId) {
        this.jTextFieldId = jTextFieldId;
    }

    /**
     * Vraca tekstualno polje za ime.
     *
     * @return tekstualno polje za ime
     */
    public JTextField getjTextFieldIme() {
        return jTextFieldIme;
    }

    /**
     * Postavlja tekstualno polje za ime.
     *
     * @param jTextFieldIme tekstualno polje za ime
     */
    public void setjTextFieldIme(JTextField jTextFieldIme) {
        this.jTextFieldIme = jTextFieldIme;
    }

    /**
     * Vraca tekstualno polje za prezime.
     *
     * @return tekstualno polje za prezime
     */
    public JTextField getjTextFieldPrezime() {
        return jTextFieldPrezime;
    }

    /**
     * Postavlja tekstualno polje za prezime.
     *
     * @param jTextFieldPrezime tekstualno polje za prezime
     */
    public void setjTextFieldPrezime(JTextField jTextFieldPrezime) {
        this.jTextFieldPrezime = jTextFieldPrezime;
    }

    /**
     * Vraca tekstualno polje za korisnicko ime.
     *
     * @return tekstualno polje za korisnicko ime
     */
    public JTextField getjTextFieldUsername() {
        return jTextFieldUsername;
    }

    /**
     * Postavlja tekstualno polje za korisnicko ime.
     *
     * @param jTextFieldUsername tekstualno polje za korisnicko ime
     */
    public void setjTextFieldUsername(JTextField jTextFieldUsername) {
        this.jTextFieldUsername = jTextFieldUsername;
    }

    /**
     * Vraca polje za lozinku.
     *
     * @return polje za lozinku
     */
    public JPasswordField getjPasswordField1() {
        return jPasswordField1;
    }

    /**
     * Postavlja polje za lozinku.
     *
     * @param jPasswordField1 polje za lozinku
     */
    public void setjPasswordField1(JPasswordField jPasswordField1) {
        this.jPasswordField1 = jPasswordField1;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldIme = new javax.swing.JTextField();
        jTextFieldPrezime = new javax.swing.JTextField();
        jButtonDodaj = new javax.swing.JButton();
        jButtonIzmeni = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setText("id");

        jLabel1.setText("ime");

        jLabel2.setText("prezime");

        jTextFieldIme.setText("Nata");

        jTextFieldPrezime.setText("Oketic");

        jButtonDodaj.setText("dodaj");
        jButtonDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDodajActionPerformed(evt);
            }
        });

        jButtonIzmeni.setText("izmeni");

        jLabel4.setText("username");

        jTextFieldUsername.setText("oketic1");

        jPasswordField1.setText("oketic123");

        jLabel5.setText("lozinka");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldIme)
                            .addComponent(jTextFieldPrezime)
                            .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField1)
                            .addComponent(jTextFieldUsername))))
                .addContainerGap(122, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonIzmeni)
                .addGap(62, 62, 62)
                .addComponent(jButtonDodaj)
                .addGap(58, 58, 58))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldIme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldPrezime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIzmeni)
                    .addComponent(jButtonDodaj))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDodajActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDodajActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    /** Swing komponenta. */
    private javax.swing.JButton jButtonDodaj;
    /** Swing komponenta. */
    private javax.swing.JButton jButtonIzmeni;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel1;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel2;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel3;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel4;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel5;
    /** Swing komponenta. */
    private javax.swing.JPasswordField jPasswordField1;
    /** Swing komponenta. */
    private javax.swing.JTextField jTextFieldId;
    /** Swing komponenta. */
    private javax.swing.JTextField jTextFieldIme;
    /** Swing komponenta. */
    private javax.swing.JTextField jTextFieldPrezime;
    /** Swing komponenta. */
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables


    /**
     * Dodaje ActionListener na dugme za dodavanje.
     *
     * @param actionListener osluskivac koji se poziva pri kliku na dugme za dodavanje
     */
    public void dodajAddActionListener(ActionListener actionListener) {
        jButtonDodaj.addActionListener(actionListener);
    }
    
    /**
     * Dodaje ActionListener na dugme za izmenu.
     *
     * @param actionListener osluskivac koji se poziva pri kliku na dugme za izmenu
     */
    public void izmeniAddActionListener(ActionListener actionListener) {
        jButtonIzmeni.addActionListener(actionListener);
    }
    
}
