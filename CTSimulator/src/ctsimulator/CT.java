/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa do generowania kolejnych obrazów symulacji
 * @author LU
 */
public class CT {
    
    /**
    * Klasa do reprezentacji punktów na obrazie
    */
    public class Point{
        int x;
        int y;
        double degree;
        double radian;
        /**
         * 
         * @param angle kąt od godziny 12:00 zgodnie z ruchem wskazówek zegara
         * @param r promień okręgu (1/2 boku obrazu)
         */
        public Point(double angle, int r){
            degree = angle;
            radian = angle/180.0 * Math.PI;
            x = (int) (Math.sin(radian)*r);
            y = (int) (Math.cos(radian)*r);
            normalization(r);
        }
        /**
         * przeliczenie współrzędnych (x,y) na układ współrzędnych obrazu
         * @param r promień okręgu (1/2 boku obrazu)
         */
        private void normalization(int r){
            y = (-1)*y+r;
            x = x + r;
            if(x<0) x = 0;
            if(x>2*r) x = 2*r;
            if(y<0) y = 0;
            if(y>2*r) y = 2*r;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double getDegree() {
            return degree;
        }

        public double getRadian() {
            return radian;
        }
    }
    /** liczba sensorów używanych podczas jednego pomiaru */
    private int numberOfSensors;
    /** kąt, o jaki obracany jest emiter */
    private double translationAngle;
    /** kąt, na którym rozłożone są sensory używane podczas jednego pomiaru */
    private double measurementAngle;
    
    /** oryginalny obraz umieszczony na czarnym tle */
    private BufferedImage orginalImg;
    /** obraz w skali odcieni szarości */
    private BufferedImage grayImg;
    /** grayImg z naniesionymi liniami symbolizującymi aktualny pomiar */
    private BufferedImage measurementImg;
    /** aktualny sinogram */
    private BufferedImage sinogramImg;
    /** aktualny przefiltrowany sinogram */
    private BufferedImage filteredSinogramImg;
    /** aktualny rezultat wygenerowany na podstawie sinogramu */
    private BufferedImage resultImg;
    /** aktualny rezultat wygenerowany na podstawie przefiltrowanego sinogramu */
    private BufferedImage filteredResultImg;
    
    /** promień CT (1/2 boku orginalImg */
    private int r;
    /** Zbiór punktów reprezentujących położenia sensorów w poszczególnych iteracjach */
    private List<List<Point>> sensors;
    /** Zbiór punktów reprezentujących położenie emitera w poszczególnych iteracjach */
    private List<Point> emiters;
    
    /**
    * @param img oryginalny obraz
    * @param nSensors liczba sensorów na pomiar
    * @param tAngle kąt translacji emitera między iteracjami
    * @param mAngle kąt rozstawu sensorów podczas pojedynczego pomiaru
    */
    public CT(BufferedImage img, int nSensors, double tAngle, double mAngle){
        numberOfSensors = nSensors;
        translationAngle = tAngle;
        measurementAngle = mAngle;
        
        int d = (int)Math.sqrt(Math.pow(img.getHeight(),2) + Math.pow(img.getWidth(),2));
        orginalImg = drawImage(createBackground(d,d),img);
        this.r = d/2;
        createGrayscaleImage();
        
    }
    
    /**
     * Generowanie całej symulacji. 
     * Wynik zostanie zapisany w odpowienich polach klasy.
     */
    public void generateSimulation(){
        
    }
    
    /**
     * Generowanie określonej liczby symulacji. 
     * Wynik zostanie zapisany w odpowienich polach klasy.
     * @param i liczba symulacji
     */
    public void generateSimulation(int i){
        
    }
    
    /**
     * Generowanie całej symulacji wraz z nanoszeniem pośrednich efektów na odpowiednie panele. 
     * Wynik zostanie zapisany w odpowienich polach klasy.
     * @param measurement panel, na który zostanie naniesiona symulacja kolejnych pomiarów
     * @param sinogram panel, na który zostanie naniesiony sinogram
     * @param fSinogram panel, na który zostanie naniesiony przefiltrowany sinogram
     * @param result panel, na który zostanie naniesiony rezultat
     * @param fResult panel, na który zostanie naniesiony przefiltrowany rezultat
     */
    public void generateSimulation(ImagePanel measurement, ImagePanel sinogram, ImagePanel fSinogram, ImagePanel result, ImagePanel fResult){
        
    }
    
    /**
     * Generowanie określonej liczby symulacji wraz z nanoszeniem pośrednich efektów na odpowiednie panele.
     * Wynik zostanie zapisany w odpowienich polach klasy.
     * @param i liczba symulacji, która ma zostać wykonana
     * @param measurement panel, na który zostanie naniesiona symulacja kolejnych pomiarów
     * @param sinogram panel, na który zostanie naniesiony sinogram
     * @param fSinogram panel, na który zostanie naniesiony przefiltrowany sinogram
     * @param result panel, na który zostanie naniesiony rezultat
     * @param fResult panel, na który zostanie naniesiony przefiltrowany rezultat
     */
    public void generateSimulation(int i, ImagePanel measurement, ImagePanel sinogram, ImagePanel fSinogram, ImagePanel result, ImagePanel fResult){
        
    }
    
    /**
     * Tworzenie zbioru punktów reprezentujących pozycje emiterów i sensorów
     */
    private void createPoints(){
        emiters = new ArrayList<>();
        sensors = new ArrayList<>();
        
        double angleBetweenSensors = this.measurementAngle/(this.numberOfSensors-1);
        
        double eAngle = 0.0;
        int iterator = 0;
        
        while(eAngle<360.0){
            emiters.add(new Point(eAngle,r));
            List<Point> tmpSensors = new ArrayList<>();
            
            double sAngle = eAngle - this.measurementAngle/2;
            for(int i=0;i<this.numberOfSensors;i++){
                tmpSensors.add(new Point(sAngle,r));
                sAngle+=angleBetweenSensors;
            }
            
            sensors.add(tmpSensors);
            eAngle+=this.translationAngle;
        }
    }
    
    /**
     * 
     * @param angle
     * @return kąt z przedziału <0.0;360.0)
     */
    private double normalizeAngle(double angle){
        return 0.0;
    }
    
    /**
     * 
    */
    private void createGrayscaleImage(){
        int h = orginalImg.getHeight();
        int w = orginalImg.getWidth();
        grayImg = createBackground(h,w);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color c = new Color(orginalImg.getRGB(x, y)); 
                int avg = (c.getRed()+c.getBlue()+c.getGreen())/3;
                grayImg.setRGB(x, y, greyToRGB(avg));
            }
        }
    }
    
    /**
     * 
     * @param r
     * @param g
     * @param b
     * @return 
     */
    private int colorToRGB(int r, int g, int b) {
        int newPixel = 255;
        newPixel = newPixel << 8;
        newPixel += r;
        newPixel = newPixel << 8;
        newPixel += g;
        newPixel = newPixel << 8;
        newPixel += b;
        return newPixel;
    }
    
    /**
     * 
     * @param gray
     * @return 
     */
    private int greyToRGB(int gray) {
        return colorToRGB(gray,gray,gray);
    }
    
    /**
     * 
     * @param h
     * @param w
     * @return 
     */
    private BufferedImage createBackground(int h, int w){
        BufferedImage tmp = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = tmp.createGraphics();
        graphics.setPaint ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, tmp.getWidth(), tmp.getHeight() );
        return tmp;
    }
    
    /**
     * 
     * @param background
     * @param img
     * @return 
     */
    private BufferedImage drawImage(BufferedImage background, BufferedImage img){
        Graphics2D graphic = background.createGraphics();
        graphic.drawImage(img,(background.getWidth()-img.getWidth())/2,(background.getHeight()-img.getHeight())/2, null);
        return background;
    }

    public BufferedImage getOrginalImg() {
        return orginalImg;
    }

    public BufferedImage getGrayImg() {
        return grayImg;
    }

    public BufferedImage getMeasurementImg() {
        return measurementImg;
    }

    public BufferedImage getSinogramImg() {
        return sinogramImg;
    }

    public BufferedImage getFilteredSinogramImg() {
        return filteredSinogramImg;
    }

    public BufferedImage getResultImg() {
        return resultImg;
    }

    public BufferedImage getFilteredResultImg() {
        return filteredResultImg;
    }
    
}
