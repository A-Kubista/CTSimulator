/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LU
 */
public class ResultWindow extends javax.swing.JFrame implements Runnable{
    
    private final BufferedImage img;
    private Map<String,SignedPane> images;
    private CTSimulator ct;
    
    public ResultWindow(BufferedImage img, CTSimulator ct){
        super("Simulation");
        this.img = img;
        this.ct = ct;
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2,3,20,5));
        
        images = new HashMap();
        images.put("Orginal", new SignedPane("Orginal image"));
        images.put("Sinogram", new SignedPane("Sinogram"));
        images.put("FSinogram", new SignedPane("Filtered sinogram"));
        images.put("GImage", new SignedPane("Grayscale image"));
        images.put("Result", new SignedPane("Result image"));
        images.put("FResult", new SignedPane("Filtered result image"));
        
        add(images.get("Orginal"));
        add(images.get("Sinogram"));
        add(images.get("FSinogram"));
        add(images.get("GImage"));
        add(images.get("Result"));
        add(images.get("FResult"));
        
        pack();
        this.setVisible(true);
    }
    
      
        public void generateSimulation(){
            CT c = new CT(img,200, 1.0, 150.0, images.get("Orginal"), images.get("GImage"));
            c.generateSimulation(images.get("GImage"), images.get("Sinogram"), images.get("FSinogram"), images.get("Result"), images.get("FResult"), false);
        }

    @Override
    public void run() {
        generateSimulation();
    }
    
}
