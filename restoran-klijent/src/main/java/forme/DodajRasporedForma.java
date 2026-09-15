package forme;

import domen.Konobar;
import domen.Smena;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Forma za dodavanje novog ili izmenu postojeceg rasporeda rada.
 * Omogucava izbor konobara i smene, kao i unos datuma rasporeda.
 * Koristi se u dva moda koje kontroler postavlja pre prikaza.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajRasporedForma extends javax.swing.JFrame {

    /**
     * Kreira formu i inicijalizuje Swing komponente.
     */
    public DodajRasporedForma() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxSmena = new javax.swing.JComboBox<>();
        jComboBoxKonobar = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldDatum = new javax.swing.JTextField();
        jButtonDodaj = new javax.swing.JButton();
        jButtonIzmeni = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("konobar");

        jLabel2.setText("smena");

        jLabel3.setText("datum");

        jButtonDodaj.setText("dodaj");

        jButtonIzmeni.setText("izmeni");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxSmena, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxKonobar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(306, 306, 306))
            .addGroup(layout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(jButtonDodaj)
                .addGap(76, 76, 76)
                .addComponent(jButtonIzmeni)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxKonobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxSmena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDodaj)
                    .addComponent(jButtonIzmeni))
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
     * Vraca combo box za konobara.
     *
     * @return combo box za konobara
     */
    public JComboBox<Konobar> getjComboBoxKonobar() {
        return jComboBoxKonobar;
    }

    /**
     * Postavlja combo box za konobara.
     *
     * @param jComboBoxKonobar combo box za konobara
     */
    public void setjComboBoxKonobar(JComboBox<Konobar> jComboBoxKonobar) {
        this.jComboBoxKonobar = jComboBoxKonobar;
    }

    /**
     * Vraca combo box za smenu.
     *
     * @return combo box za smenu
     */
    public JComboBox<Smena> getjComboBoxSmena() {
        return jComboBoxSmena;
    }

    /**
     * Postavlja combo box za smenu.
     *
     * @param jComboBoxSmena combo box za smenu
     */
    public void setjComboBoxSmena(JComboBox<Smena> jComboBoxSmena) {
        this.jComboBoxSmena = jComboBoxSmena;
    }

    /**
     * Vraca tekstualno polje za datum.
     *
     * @return tekstualno polje za datum
     */
    public JTextField getjTextFieldDatum() {
        return jTextFieldDatum;
    }

    /**
     * Postavlja tekstualno polje za datum.
     *
     * @param jTextFieldDatum tekstualno polje za datum
     */
    public void setjTextFieldDatum(JTextField jTextFieldDatum) {
        this.jTextFieldDatum = jTextFieldDatum;
    }



    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    /** Swing komponenta. */
    private javax.swing.JButton jButtonDodaj;
    /** Swing komponenta. */
    private javax.swing.JButton jButtonIzmeni;
    /** Swing komponenta. */
    private javax.swing.JComboBox<Konobar> jComboBoxKonobar;
    /** Swing komponenta. */
    private javax.swing.JComboBox<Smena> jComboBoxSmena;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel1;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel2;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel3;
    /** Swing komponenta. */
    private javax.swing.JTextField jTextFieldDatum;
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
