/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forme;

import domen.Gost;
import domen.Konobar;
import domen.Racun;
import domen.Smena;
import domen.StavkaRacuna;
import forme.model.ModelTabeleRacuni;
import forme.model.ModelTabeleStavkeRacuna;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import komunikacija.Komunikacija;

/**
 *
 * @author Cofara
 */
public class PrikazRacunaForma extends javax.swing.JFrame {

    
    public PrikazRacunaForma() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRacuni = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableStavkaRacuna = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonObrisi = new javax.swing.JButton();
        jButtonIzmeni = new javax.swing.JButton();
        jButtonPretrazi = new javax.swing.JButton();
        jButtonResetuj = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxSmena = new javax.swing.JComboBox<>();
        jComboBoxKonobar = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldDatum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxGost = new javax.swing.JComboBox<>();
        jButtonIzmeniStavku = new javax.swing.JButton();
        jButtonObrisiStavku = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTableRacuni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableRacuni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRacuniMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableRacuni);

        jTableStavkaRacuna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableStavkaRacuna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableStavkaRacunaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableStavkaRacuna);

        jLabel1.setText("Racuni");

        jLabel2.setText("Stavke racuna");

        jButtonObrisi.setText("obrisi");
        jButtonObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonObrisiActionPerformed(evt);
            }
        });

        jButtonIzmeni.setText("izmeni");
        jButtonIzmeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIzmeniActionPerformed(evt);
            }
        });

        jButtonPretrazi.setText("pretrazi");
        jButtonPretrazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPretraziActionPerformed(evt);
            }
        });

        jButtonResetuj.setText("restartuj pretragu");
        jButtonResetuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetujActionPerformed(evt);
            }
        });

        jLabel3.setText("konobar");

        jLabel4.setText("smena");

        jLabel5.setText("datum");

        jLabel6.setText("gost");

        jButtonIzmeniStavku.setText("izmeni stavku");

        jButtonObrisiStavku.setText("obrisi stavku");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonIzmeni, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonObrisi, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonObrisiStavku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonIzmeniStavku, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButtonPretrazi, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonResetuj))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(70, 70, 70)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jComboBoxSmena, 0, 165, Short.MAX_VALUE)
                                                .addComponent(jComboBoxKonobar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jComboBoxGost, 0, 165, Short.MAX_VALUE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(129, 129, 129)
                                                .addComponent(jTextFieldDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBoxKonobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBoxSmena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBoxGost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonPretrazi)
                            .addComponent(jButtonResetuj))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonIzmeni)
                            .addComponent(jButtonObrisi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)))
                .addGap(0, 39, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonIzmeniStavku)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonObrisiStavku)))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableStavkaRacunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStavkaRacunaMouseClicked
        
        
        
    }//GEN-LAST:event_jTableStavkaRacunaMouseClicked

    private void jTableRacuniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRacuniMouseClicked
        int red = jTableRacuni.getSelectedRow();
        ModelTabeleRacuni mtp = (ModelTabeleRacuni) jTableRacuni.getModel();
        Racun p = mtp.getRacuni().get(red);
        
        List<StavkaRacuna> stavke = Komunikacija.getInstance().ucitajStavke(p);
        ModelTabeleStavkeRacuna mts = new ModelTabeleStavkeRacuna(stavke);
        jTableStavkaRacuna.setModel(mts);
    }//GEN-LAST:event_jTableRacuniMouseClicked

    public JButton getjButtonIzmeni() {
        return jButtonIzmeni;
    }

    public void setjButtonIzmeni(JButton jButtonIzmeni) {
        this.jButtonIzmeni = jButtonIzmeni;
    }

    public JButton getjButtonObrisi() {
        return jButtonObrisi;
    }

    public void setjButtonObrisi(JButton jButtonObrisi) {
        this.jButtonObrisi = jButtonObrisi;
    }

    public JButton getjButtonPretrazi() {
        return jButtonPretrazi;
    }

    public void setjButtonPretrazi(JButton jButtonPretrazi) {
        this.jButtonPretrazi = jButtonPretrazi;
    }

    public JButton getjButtonResetuj() {
        return jButtonResetuj;
    }

    public void setjButtonResetuj(JButton jButtonResetuj) {
        this.jButtonResetuj = jButtonResetuj;
    }

    public JComboBox<Gost> getjComboBoxGost() {
        return jComboBoxGost;
    }

    public void setjComboBoxGost(JComboBox<Gost> jComboBoxGost) {
        this.jComboBoxGost = jComboBoxGost;
    }

    public JComboBox<Konobar> getjComboBoxKonobar() {
        return jComboBoxKonobar;
    }

    public void setjComboBoxKonobar(JComboBox<Konobar> jComboBoxKonobar) {
        this.jComboBoxKonobar = jComboBoxKonobar;
    }

    public JComboBox<Smena> getjComboBoxSmena() {
        return jComboBoxSmena;
    }

    public void setjComboBoxSmena(JComboBox<Smena> jComboBoxSmena) {
        this.jComboBoxSmena = jComboBoxSmena;
    }

    public JTable getjTableStavkaRacuna() {
        return jTableStavkaRacuna;
    }

    public void setjTableStavkaRacuna(JTable jTableStavkaRacuna) {
        this.jTableStavkaRacuna = jTableStavkaRacuna;
    }

    public JTextField getjTextFieldDatum() {
        return jTextFieldDatum;
    }

    public void setjTextFieldDatum(JTextField jTextFieldDatum) {
        this.jTextFieldDatum = jTextFieldDatum;
    }

    public JButton getjButtonIzmeniStavku() {
        return jButtonIzmeniStavku;
    }

    public void setjButtonIzmeniStavku(JButton jButtonIzmeniStavku) {
        this.jButtonIzmeniStavku = jButtonIzmeniStavku;
    }

    public JButton getjButtonObrisiStavku() {
        return jButtonObrisiStavku;
    }

    public void setjButtonObrisiStavku(JButton jButtonObrisiStavku) {
        this.jButtonObrisiStavku = jButtonObrisiStavku;
    }
    
    

    
    
    private void jButtonObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonObrisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonObrisiActionPerformed

    private void jButtonIzmeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIzmeniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonIzmeniActionPerformed

    private void jButtonPretraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPretraziActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonPretraziActionPerformed

    private void jButtonResetujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetujActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonResetujActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIzmeni;
    private javax.swing.JButton jButtonIzmeniStavku;
    private javax.swing.JButton jButtonObrisi;
    private javax.swing.JButton jButtonObrisiStavku;
    private javax.swing.JButton jButtonPretrazi;
    private javax.swing.JButton jButtonResetuj;
    private javax.swing.JComboBox<Gost> jComboBoxGost;
    private javax.swing.JComboBox<Konobar> jComboBoxKonobar;
    private javax.swing.JComboBox<Smena> jComboBoxSmena;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableRacuni;
    private javax.swing.JTable jTableStavkaRacuna;
    private javax.swing.JTextField jTextFieldDatum;
    // End of variables declaration//GEN-END:variables

    public JTable getjTableRacuni() {
        return jTableRacuni;
    }

    public void setjTableRacuni(JTable jTableRacuni) {
        this.jTableRacuni = jTableRacuni;
    }

        public void addBtnObrisiActionListener(ActionListener actionListener) {
            jButtonObrisi.addActionListener(actionListener);
        }

        public void addBtnIzmeniActionListener(ActionListener actionListener) {
            jButtonIzmeni.addActionListener(actionListener);
        }

        public void addBtnPretraziActionListener(ActionListener actionListener) {
            jButtonPretrazi.addActionListener(actionListener);
        }

        public void addBtnResetujActionListener(ActionListener actionListener) {
            jButtonResetuj.addActionListener(actionListener);
        }
        
        public void addBtnObrisiStavkuActionListener(ActionListener actionListener) {
            jButtonObrisiStavku.addActionListener(actionListener);
        }

        public void addBtnIzmeniStavkuActionListener(ActionListener actionListener) {
            jButtonIzmeniStavku.addActionListener(actionListener);
        }

    
    
}
