/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import java.awt.image.BufferedImage;

/**
 * Panel, na którym będzie odświerzany obraz podczas symulacji
 * @author LU
 */
public interface ImagePanel {
    /**
    * @param img obraz, który ma być narysowany na panelu (może wymagać przeskalowania)
    */
    public void refreshImage(BufferedImage img);
    
}
