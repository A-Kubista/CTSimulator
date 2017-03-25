/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author LU
 */
public class MainWindow extends javax.swing.JFrame {

    private final CTSimulator ct;
    
    public MainWindow() {
        initComponents();
        ct = new CTSimulator();
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        filePane = new javax.swing.JPanel();
        fileLabel = new javax.swing.JLabel();
        fileText = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        errorFile = new javax.swing.JLabel();
        optionPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sensorsPerMeasurement = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        angleBetweenSensors = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        numberOfMeasurements = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        save = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CTSimulator");

        tabbedPane.setToolTipText("Option");

        filePane.setToolTipText("Select file");

        fileLabel.setText("Selected file:");

        fileText.setText("img/test.png");
        fileText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileTextActionPerformed(evt);
            }
        });

        jButton1.setText("Start simulation");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        errorFile.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout filePaneLayout = new javax.swing.GroupLayout(filePane);
        filePane.setLayout(filePaneLayout);
        filePaneLayout.setHorizontalGroup(
            filePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errorFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fileText, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(filePaneLayout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        filePaneLayout.setVerticalGroup(
            filePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePaneLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(filePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel)
                    .addComponent(fileText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Select file", filePane);

        optionPane.setToolTipText("Option");

        jLabel1.setText("Number of active sensors per measurement:");

        jLabel2.setText("Angle between sensors (degree):");

        jLabel3.setText("Number of measurements:");

        numberOfMeasurements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberOfMeasurementsActionPerformed(evt);
            }
        });

        jLabel6.setText("Active angle (degree):");

        jLabel7.setText("Number of sensors:");

        jLabel8.setText("Trenslation:");

        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout optionPaneLayout = new javax.swing.GroupLayout(optionPane);
        optionPane.setLayout(optionPaneLayout);
        optionPaneLayout.setHorizontalGroup(
            optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionPaneLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionPaneLayout.createSequentialGroup()
                        .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(numberOfMeasurements, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(angleBetweenSensors)
                            .addComponent(sensorsPerMeasurement, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(optionPaneLayout.createSequentialGroup()
                        .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))))
                .addContainerGap())
        );
        optionPaneLayout.setVerticalGroup(
            optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sensorsPerMeasurement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(angleBetweenSensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfMeasurements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(save)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Option", optionPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane)
                .addContainerGap())
        );

        tabbedPane.getAccessibleContext().setAccessibleName("Option");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String path = fileText.getText();
        try {
            FileInputStream fos = new FileInputStream(path);
            BufferedImage img = ImageIO.read(fos);
            fos.close();
            //if(img.getWidth()!=img.getHeight()) throw new Exception("Incorrect image size - "+img.getHeight()+"x"+img.getWidth());
            ct.setR((int)(img.getHeight()/2));
            java.awt.EventQueue.invokeLater(() -> {
                ResultWindow res = new ResultWindow(img,ct.clone());
                (new Thread(res)).start();
            });
            
        } catch (IOException ex) {
            this.errorFile.setText("Image "+path+" can't be found.");
        } catch (Exception ex) {
            this.errorFile.setText(ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void fileTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileTextActionPerformed

    private void numberOfMeasurementsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberOfMeasurementsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberOfMeasurementsActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField angleBetweenSensors;
    private javax.swing.JLabel errorFile;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JPanel filePane;
    private javax.swing.JTextField fileText;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField numberOfMeasurements;
    private javax.swing.JPanel optionPane;
    private javax.swing.JToggleButton save;
    private javax.swing.JTextField sensorsPerMeasurement;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
