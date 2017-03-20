/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author LU
 */
public class DrawPane extends JPanel{
    private BufferedImage img;
    private static int preferedSize = 250;
    
    
    public DrawPane(){
        this.setPreferredSize(new Dimension(preferedSize,preferedSize));
        this.img = new BufferedImage(preferedSize,preferedSize,BufferedImage.TYPE_INT_ARGB);
        Graphics2D    graphics = img.createGraphics();
        graphics.setPaint ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setPaint ( new Color ( 0, 0, 0 ) );
        g2d.fillRect ( 0, 0, this.getWidth(), this.getHeight() );
        
        BufferedImage im = getResizedImage();
        g2d.drawImage(im,(this.getWidth()-im.getWidth())/2,(this.getHeight()-im.getHeight())/2, null);
    }
    
    public BufferedImage getResizedImage(){
        int ih = img.getHeight();
        int iw = img.getWidth();
        int h = this.getHeight();
        int w = this.getWidth();
        
        
        float scale = 1.0f;
        if(ih>iw) scale = ((float)h)/((float)ih);
        else scale = ((float)w)/((float)iw);
        
        return resizeImage(this.img, scale);
    }
    
    public BufferedImage resizeImage(BufferedImage img, float scale){
        int width = (int) (img.getWidth()*scale);
        int height = (int) (img.getHeight()*scale);    
        
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public BufferedImage getImg() {
        return img;
    }
    
    public static BufferedImage createCopy(BufferedImage src) {
        BufferedImage b;
        b = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        Graphics2D g = b.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return b;
    }
    
    public static BufferedImage prepareBackground(int h, int w){
        BufferedImage tmp = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = tmp.createGraphics();
        graphics.setPaint ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, tmp.getWidth(), tmp.getHeight() );
        return tmp;
    }
    
    public static int colorToRGB(int r, int g, int b) {
        int newPixel = 255;
        newPixel = newPixel << 8;
        newPixel += r;
        newPixel = newPixel << 8;
        newPixel += g;
        newPixel = newPixel << 8;
        newPixel += b;
        return newPixel;
    }
    
    public static int greyToRGB(int gray) {
        return colorToRGB(gray,gray,gray);
    }

    public void setImg(BufferedImage img) {
        this.img = img;
        repaint();
    }
    
    public static BufferedImage createImage(int[][] arr, int w, int h, int max){
        BufferedImage im = prepareBackground(h,w);
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                int color = (int)(((double) arr[x][y]/max)*255.0);
                if(color<0) color = 0;
                //System.out.println("Set color: "+color);
                im.setRGB(x, y, greyToRGB(color));
            }
        }
        return im;
    }

    public static int getPreferedSize() {
        return preferedSize;
    }
    
    
    
}
