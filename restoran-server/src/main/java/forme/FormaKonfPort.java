package forme;

import javax.swing.JOptionPane;

/**
 * Dijalog za konfiguraciju porta na kojem server slusa klijente.
 * Uneta vrednost se cuva preko {@link konfiguracija.Konfiguracija}.
 *
 * @author Filip Oketic
 * @version 1.0
 */
public class FormaKonfPort extends javax.swing.JDialog {

    /**
     * Kreira dijalog za podesavanje porta.
     *
     * @param parent roditeljski frejm
     * @param modal da li je dijalog modalni
     */
    public FormaKonfPort(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("port");

        jButton1.setText("Sacuvaj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(82, 82, 82))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(63, 63, 63))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        int port;
        try {
            port = Integer.parseInt(jTextFieldPort.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "GRESKA, port mora da bude broj",
                "GRESKA", JOptionPane.ERROR_MESSAGE);
            return;
        }

//
        if(port >= 0 && port <= 65535) {
            konfiguracija.Konfiguracija.getInstanca().setProperty("port", port + "");
            konfiguracija.Konfiguracija.getInstanca().sacuvajIzmene();
            JOptionPane.showMessageDialog(this, "param je sacuvan",
                    "USPEH", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "GRESKA, port nije u dobrom opsegu",
                    "GRESKA", JOptionPane.ERROR_MESSAGE);
        }

        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    /** Swing komponenta. */
    private javax.swing.JButton jButton1;
    /** Swing komponenta. */
    private javax.swing.JLabel jLabel1;
    /** Swing komponenta. */
    private javax.swing.JTextField jTextFieldPort;
    // End of variables declaration//GEN-END:variables
}
