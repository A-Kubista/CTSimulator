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
    
    /** oryginalny obraz*/
    private BufferedImage img;
    /** oryginalny obraz umieszczony na czarnym tle */
    private BufferedImage orginalImg;
    /** obraz w skali odcieni szarości */
    private BufferedImage grayImg;
    /** aktualny sinogram */
    private BufferedImage sinogramImg;
    /** aktualny przefiltrowany sinogram */
    private BufferedImage filteredSinogramImg;
    /** aktualny rezultat wygenerowany na podstawie sinogramu */
    private BufferedImage resultImg;
    /** aktualny rezultat wygenerowany na podstawie przefiltrowanego sinogramu */
    private BufferedImage filteredResultImg;
    /** różnica między oryginalnym obrazem a niefiltrowanym rezultatem */
    private double resultDiff = 0.0;
    /** różnica między oryginalnym obrazem a filtrowanym rezultatem */
    private double filteredResultDiff = 0.0;
    
    /** promień CT (1/2 boku orginalImg */
    private int r;
    /** średnica CT (bok orginalImg */
    private int d;
    /** Zbiór punktów reprezentujących położenia sensorów w poszczególnych iteracjach */
    private List<List<Point>> sensors;
    /** Zbiór punktów reprezentujących położenie emitera w poszczególnych iteracjach */
    private List<Point> emiters;
    /** Maksymalna liczba iteracji */
    private int maxIterations;
    
    /**
    * @param img oryginalny obraz
    * @param nSensors liczba sensorów na pomiar
    * @param tAngle kąt translacji emitera między iteracjami
    * @param mAngle kąt rozstawu sensorów podczas pojedynczego pomiaru (<180.0)
    */
    public CT(BufferedImage img, int nSensors, double tAngle, double mAngle){
        
        numberOfSensors = (nSensors>1)? nSensors : 2;
        translationAngle = (tAngle>0.0)? tAngle : 1.0;
        measurementAngle = (mAngle<180.0) ? 2*mAngle : 360.0;
        
        d = (int)Math.sqrt(Math.pow(img.getHeight(),2) + Math.pow(img.getWidth(),2));
        orginalImg = drawImage(createBackground(d,d),img);
        r = d/2;
        createGrayscaleImage();
        
        createPoints();
        
        this.img = img;
        this.resultImg = createBackground(d, d);
        this.filteredResultImg = createBackground(d, d);
        this.sinogramImg = createBackground(this.maxIterations, this.numberOfSensors);
        this.filteredSinogramImg = createBackground(this.maxIterations, this.numberOfSensors);
    }
    
    /**
    * @param img oryginalny obraz
    * @param nSensors liczba sensorów na pomiar
    * @param tAngle kąt translacji emitera między iteracjami
    * @param mAngle kąt rozstawu sensorów podczas pojedynczego pomiaru (<180.0)
    * @param orginal panel, na który ma zostać naniesiony oryginalny obraz
    * @param gray panel, na który ma zostać naniesiony czarnobiały obraz
    */
    public CT(BufferedImage img, int nSensors, double tAngle, double mAngle, ImagePanel orginal, ImagePanel gray){
        this(img,nSensors,tAngle,mAngle);
        orginal.refreshImage(this.orginalImg);
        gray.refreshImage(this.grayImg);
    }
    
    /**
     * Generowanie całej symulacji. 
     * Wynik zostanie zapisany w odpowienich polach klasy.
     */
    public void generateSimulation(){
        generateSimulation(this.maxIterations);
    }
    
    /**
     * Generowanie określonej liczby symulacji. 
     * Wynik zostanie zapisany w odpowienich polach klasy.
     * @param i liczba symulacji
     */
    public void generateSimulation(int i){
        generateSimulation(i,null,null,null,null,null, true);
    }
    
    /**
     * Generowanie całej symulacji wraz z nanoszeniem pośrednich efektów na odpowiednie panele. 
     * Wynik zostanie zapisany w odpowienich polach klasy.
     * @param measurement panel, na który zostanie naniesiona symulacja kolejnych pomiarów
     * @param sinogram panel, na który zostanie naniesiony sinogram
     * @param fSinogram panel, na który zostanie naniesiony przefiltrowany sinogram
     * @param result panel, na który zostanie naniesiony rezultat
     * @param fResult panel, na który zostanie naniesiony przefiltrowany rezultat
     * @param onlyResult pokazywany ma być jedynie końcowy rezultat danego obrazu (sinogramu, przefiltrowanego sinogramu itp.), bez kroków pośrwdnich
     */
    public void generateSimulation(ImagePanel measurement, ImagePanel sinogram, ImagePanel fSinogram, ImagePanel result, ImagePanel fResult, boolean onlyResult){
        generateSimulation(this.maxIterations,measurement, sinogram, fSinogram, result, fResult, onlyResult);
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
     * @param onlyResult pokazywany ma być jedynie końcowy rezultat danego obrazu (sinogramu, przefiltrowanego sinogramu itp.), bez kroków pośrwdnich
     */
    public void generateSimulation(int i, ImagePanel measurement, ImagePanel sinogram, ImagePanel fSinogram, ImagePanel result, ImagePanel fResult, boolean onlyResult){
        if(i>this.maxIterations) i = this.maxIterations;
        generateSinogram(i, measurement, sinogram, onlyResult);
        generateResult(i,sinogram,result,onlyResult,false);
        
        
        this.resultDiff = countDiff(this.grayImg,this.resultImg);
        this.filteredResultDiff = countDiff(this.grayImg,this.filteredResultImg);
        
        System.out.println("Result: "+this.resultDiff+"%");
        System.out.println("Filtered result: "+this.filteredResultDiff+"%");
    }
    
    /**
     * Wykonywanie pierwszych n iteracji tworzenia sinogramu
     * @param n liczba iteracji
     * @param measurement panel, na który nanoszona jest symulacja 
     * @param sinogram panel, na który nanoszony ma być sinogram
     * @param onlyResult pokazywany ma być jedynie końcowy rezultat sinogramu), bez kroków pośrednich
     */
    private void generateSinogram(int n, ImagePanel measurement, ImagePanel sinogram, boolean onlyResult){
        
            BufferedImage measurementImg = createCopy(this.grayImg);
            for(int i = 0;i<n;i++){
                Point src = this.emiters.get(i);
                List<Point> dests = this.sensors.get(i);
                measurementImg = createCopy(this.grayImg);
                int iterator = 0;
                for(Point dest : dests){
                    int sum = 0;

                    //Bresenham's line algorithm
                    //https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#C.2B.2B
                    int x1 = src.getX();
                    int y1 = src.getY();
                    int x2 = dest.getX();
                    int y2 = dest.getY();

                    //System.out.println(x1+"\t"+y1+"\t"+x2+"\t"+y2);

                    boolean steep = (Math.abs(y2 - y1) > Math.abs(x2 - x1));
                    if(steep){
                        //swap x and y
                        int tmp = x1;
                        x1 = y1;
                        y1 = tmp;
                        tmp = x2;
                        x2 = y2;
                        y2 = tmp;
                    }

                    if(x1 > x2){
                        //swap xs and ys
                        int tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                        tmp = y1;
                        y1 = y2;
                        y2 = tmp;
                    }

                    int dx = x2 - x1;
                    int dy = Math.abs(y2 - y1);

                    float error = dx / 2.0f;
                    int ystep = (y1 < y2) ? 1 : -1;
                    int y = y1;
                    int maxX = x2;

                    for(int x=x1; x<maxX; x++)
                    {
                        if(x>=this.grayImg.getHeight()) x--;
                        if(y>=this.grayImg.getHeight()) y--;
                        if(steep){
                            sum = sum + (new Color(this.grayImg.getRGB(y, x))).getRed();
                            measurementImg.setRGB(y, x, colorToRGB(200,0,0));
                        }
                        else{
                            sum = sum + (new Color(this.grayImg.getRGB(x, y))).getRed();
                            measurementImg.setRGB(x, y, colorToRGB(200,0,0));
                        }

                        error = error - dy;
                        if(error < 0)
                        {
                            y += ystep;
                            error += dx;
                        }
                    }
                    
                    int avg = (maxX-x1>0) ? sum/(maxX-x1) : sum;
                    //int avg = sum/greyCopy.getHeight();
                    this.sinogramImg.setRGB(iterator,i,greyToRGB(avg));
                    iterator++;

                    //greyPane.refreshImage(grey);
                    //outPane.refreshImage(out);
                }
                if(measurement!=null && !onlyResult) measurement.refreshImage(measurementImg);
                if(sinogram!=null && !onlyResult) sinogram.refreshImage(this.sinogramImg);
            }
            if(measurement!=null) measurement.refreshImage(this.grayImg);
            if(sinogram!=null) sinogram.refreshImage(this.sinogramImg);
    }
    
    /**
     * Wykonywanie pierwszych n iteracji generowania rezultatu z sinogramu
     * @param n liczba iteracji
     * @param sinogram panel, na który nanoszona jest symulacja 
     * @param result panel, na który nanoszony ma być rezultat
     * @param onlyResult pokazywany ma być jedynie końcowy rezultat, bez kroków pośrednich
     * @param fSinogram obraz ma być tworzony na podstawie przefiltrowanego sinogramu
     */
    private void generateResult(int n, ImagePanel sinogram, ImagePanel result, boolean onlyResult, boolean fSinogram){
        int h = resultImg.getHeight();
        int w = resultImg.getWidth();
            
        int[][] array = new int[w][h];
        for(int[] r: array){
            for(int f: r){
                f = 0;
            }
        }
        int max = -1;
            
        for(int i = 0;i<n;i++){
            Point src = this.emiters.get(i);
            BufferedImage sin = createCopy((fSinogram)? this.filteredSinogramImg : this.sinogramImg);
            for(int j = 0;j<this.numberOfSensors;j++){
                Point dest = this.sensors.get(i).get(j);
                int grey = (new Color(sin.getRGB(j, i))).getRed();
                sin.setRGB(j, i, colorToRGB(200,0,0));

                //Bresenham's line algorithm
                //https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#C.2B.2B
                    int x1 = src.getX();
                    int y1 = src.getY();
                    int x2 = dest.getX();
                    int y2 = dest.getY();

                    //System.out.println(x1+"\t"+y1+"\t"+x2+"\t"+y2);

                    boolean steep = (Math.abs(y2 - y1) > Math.abs(x2 - x1));
                    if(steep){
                        //swap x and y
                        int tmp = x1;
                        x1 = y1;
                        y1 = tmp;
                        tmp = x2;
                        x2 = y2;
                        y2 = tmp;
                    }

                    if(x1 > x2){
                        //swap xs and ys
                        int tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                        tmp = y1;
                        y1 = y2;
                        y2 = tmp;
                    }

                    int dx = x2 - x1;
                    int dy = Math.abs(y2 - y1);

                    float error = dx / 2.0f;
                    int ystep = (y1 < y2) ? 1 : -1;
                    int y = y1;
                    int maxX = x2;

                    for(int x=x1; x<maxX; x++)
                    {
                        if(x>=h) x--;
                        if(y>=h) y--;
                        if(steep){
                            array[y][x] += grey;
                            if(array[y][x]>max) max = array[y][x];
                        }
                        else{
                            array[x][y] += grey;
                            if(array[x][y]>max) max = array[x][y];
                        }

                        error = error - dy;
                        if(error < 0)
                        {
                            y += ystep;
                            error += dx;
                        }
                    }
                    
                }
                if(!onlyResult){
                    sinogram.refreshImage(sin);
                    result.refreshImage(createImage(array, w, h, max, false));
                }
            }
        
        if(fSinogram){
            this.filteredResultImg = createImage(array, w, h, max, false);
            result.refreshImage(this.filteredResultImg);
            sinogram.refreshImage(this.filteredSinogramImg);
        }
        else{
            this.resultImg = createImage(array, w, h, max, true);
            result.refreshImage(this.resultImg);
            sinogram.refreshImage(this.sinogramImg);
        }
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
            //System.out.println("Emiter "+iterator+": "+eAngle);
            List<Point> tmpSensors = new ArrayList<>();
            
            double sAngle = normalizeAngle(eAngle + 180.0 - this.measurementAngle/2);
            for(int i=0;i<this.numberOfSensors;i++){
                //System.out.println("\tSensor "+i+": "+sAngle);
                tmpSensors.add(new Point(sAngle,r));
                sAngle+=angleBetweenSensors;
                sAngle = normalizeAngle(sAngle);
            }
            
            sensors.add(tmpSensors);
            eAngle+=this.translationAngle;
            iterator++;
        }
        this.maxIterations = iterator;
    }
    
    /**
     * 
     * @param angle
     * @return kąt z przedziału <0.0;360.0)
     */
    private double normalizeAngle(double angle){
        if(angle<0.0) angle+=360.0;
        if(angle>=360.0) angle-=360.0;
        return angle;
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
    
    public static double countDiff(BufferedImage img1, BufferedImage img2){
        int h = img1.getHeight();
        int w = img2.getWidth();
        int r = (int)(((double)w)/2.0);
        int iter = 0;
        double sum = 0.0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if(pointInCircle(x,y,r)){
                    iter++;
                    double diff = (double)((new Color(img1.getRGB(x, y))).getRed()-(new Color(img2.getRGB(x, y))).getRed());
                    sum+=Math.abs(diff/255.0);
                }
            }
        }
        return sum/iter*100.0;
    }
    
    public static BufferedImage createImage(int[][] arr, int w, int h, int max, boolean withNormalization){
        BufferedImage im = createBackground(h,w);
        int minColor = 255;
        int maxColor = 0;
        int r = (int) (((double)h)*0.1);
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                int color = (int)(((double) arr[x][y]/max)*255.0);
                if(color<0) color = 0;
                im.setRGB(x, y, greyToRGB(color));
                if(color<minColor && pointInCircle(x,y,r)) minColor = color;
                if(color>maxColor) maxColor = color;
            }
        }
        if(withNormalization) imageNormalization(im, maxColor, minColor);
        return im;
    }
    
    public static boolean pointInCircle(int x, int y, int r){
        return Math.sqrt(Math.pow(x-r, 2)+Math.pow(y-r,2))<=r;
    }
    
    public static void imageNormalization(BufferedImage img, int max, int min){
        System.out.println(max+" "+min);
        min = 75;
        int h = img.getHeight();
        int w = img.getWidth();
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                int color = (new Color(img.getRGB(x,y))).getRed();
                color = (int)(((double)(color - min))/((double)(max-min))*255.0);
                if(color<0) color = 0;
                if(color>255) color = 255;
                img.setRGB(x, y, greyToRGB(color));
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
    private static int colorToRGB(int r, int g, int b) {
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
    private static int greyToRGB(int gray) {
        return colorToRGB(gray,gray,gray);
    }
    
    public static BufferedImage createCopy(BufferedImage src) {
        BufferedImage b;
        b = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        Graphics2D g = b.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return b;
    }
    
    /**
     * 
     * @param h
     * @param w
     * @return 
     */
    private static BufferedImage createBackground(int h, int w){
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
    private static BufferedImage drawImage(BufferedImage background, BufferedImage img){
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

    public double getResultDiff() {
        return resultDiff;
    }

    public double getFilteredResultDiff() {
        return filteredResultDiff;
    }
    
}
