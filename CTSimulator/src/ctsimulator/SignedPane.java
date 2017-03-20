/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LU
 */
public class SignedPane extends JPanel implements ImagePanel{
    private DrawPane pane;
    private JLabel sign;
    private String title;
    
    public SignedPane(String title){
        this.setPreferredSize(new Dimension(DrawPane.getPreferedSize(),DrawPane.getPreferedSize()+30));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.pane = new DrawPane();
        this.title = title;
        this.sign = new JLabel(title);
        this.add(pane);
        this.add(sign);
    }

    public DrawPane getPane() {
        return pane;
    }

    public JLabel getSign() {
        return sign;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.sign.setText(title);
    }
    
    @Override
    public void refreshImage(BufferedImage img){
        java.awt.EventQueue.invokeLater(() -> {
            pane.setImg(img);
        });
    }
                
    
}
