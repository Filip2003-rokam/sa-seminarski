package forme;

import domen.KategorijaGosta;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Forma za dodavanje novog ili izmenu postojeceg gosta.
 * Koristi se u dva moda (dodavanje i izmena) i omogucava unos imena,
 * prezimena i izbor kategorije gosta. Kontroler vezuje akcije dugmadi.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class DodajGostaForma extends javax.swing.JFrame {

    /**
     * Kreira formu i inicijalizuje Swing komponente.
     */
    public DodajGostaForma() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldIme = new javax.swing.JTextField();
        jTextFieldPrezime = new javax.swing.JTextField();
        jButtonDodaj = new javax.swing.JButton();
        jButtonIzmeni = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxKategorijaGosta = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        jButtonIzmeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIzmeniActionPerformed(evt);
            }
        });

        jLabel3.setText("id");

        jLabel4.setText("kategorija");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldIme)
                            .addComponent(jTextFieldPrezime)
                            .addComponent(jTextFieldId, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(jComboBoxKategorijaGosta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(jButtonIzmeni)
                        .addGap(37, 37, 37)
                        .addComponent(jButtonDodaj)))
                .addContainerGap(169, Short.MAX_VALUE))
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
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxKategorijaGosta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDodaj)
                    .addComponent(jButtonIzmeni))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Vraca combo box za kategoriju gosta.
     *
     * @return combo box za kategoriju gosta
     */
    public JComboBox<KategorijaGosta> getjComboBoxKategorijaGosta() {
        return jComboBoxKategorijaGosta;
    }

    /**
     * Postavlja combo box za kategoriju gosta.
     *
     * @param jComboBoxKategorijaGosta combo box za kategoriju gosta
     */
    public void setjComboBoxKategorijaGosta(JComboBox<KategorijaGosta> jComboBoxKategorijaGosta) {
        this.jComboBoxKategorijaGosta = jComboBoxKategorijaGosta;
    }
    
    
    private void jButtonDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDodajActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDodajActionPerformed

    private void jButtonIzmeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIzmeniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonIzmeniActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDodaj;
    private javax.swing.JButton jButtonIzmeni;
    private javax.swing.JComboBox<KategorijaGosta> jComboBoxKategorijaGosta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldIme;
    private javax.swing.JTextField jTextFieldPrezime;
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


    
}
