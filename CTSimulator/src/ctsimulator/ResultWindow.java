/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import java.awt.Color;
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
            CT c = new CT(img,1, 1.0, 1.0);
            images.get("Orginal").refreshImage(c.getOrginalImg());
            images.get("GImage").refreshImage(c.getGrayImg());
            //images.get("Orginal").refreshImage(img);
            //prepareGrayscaleImage();
            //prepareSinogram();
            //prepareResult("Result","Sinogram");
        }

        private void prepareGrayscaleImage(){
            SignedPane pane = images.get("GImage");
            int h = img.getHeight();
            int w = img.getWidth();
            BufferedImage gray = DrawPane.prepareBackground(h,w);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    Color c = new Color(img.getRGB(x, y)); 
                    int avg = (c.getRed()+c.getBlue()+c.getGreen())/3;
                    gray.setRGB(x, y, DrawPane.greyToRGB(avg));
                    pane.refreshImage(gray);
                }
            }
        }

        private void prepareSinogram(){
            SignedPane outPane = images.get("Sinogram");
            BufferedImage out = DrawPane.prepareBackground(ct.getNumberOfMeasurements(),ct.getSensorsPerMeasurement());
            outPane.refreshImage(out);
            
            SignedPane greyPane = images.get("GImage");
            BufferedImage greyCopy = images.get("GImage").getPane().getImg();
            
            for(int i = 0;i<ct.getNumberOfMeasurements();i++){
                Sensor src = ct.getSensor(i*ct.getTranslation());
                double center = src.getDegree()+180.0;
                if(center>360.0) center = center-360.0;
                BufferedImage grey = DrawPane.createCopy(greyCopy);
                for(int j = ct.getSensorsPerMeasurement()/2*(-1);j<=ct.getSensorsPerMeasurement()/2;j++){
                    int sum = 0;
                    Sensor dest = ct.getSensor(center+j*ct.getAngleBetweenSensors());

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
                        if(x>=greyCopy.getHeight()) x--;
                        if(y>=greyCopy.getHeight()) y--;
                        if(steep){
                            sum = sum + (new Color(greyCopy.getRGB(y, x))).getRed();
                            grey.setRGB(y, x, DrawPane.colorToRGB(200,0,0));
                        }
                        else{
                            sum = sum + (new Color(greyCopy.getRGB(x, y))).getRed();
                            grey.setRGB(x, y, DrawPane.colorToRGB(200,0,0));
                        }

                        error = error - dy;
                        if(error < 0)
                        {
                            y += ystep;
                            error += dx;
                        }
                    }
                    
                    //int avg = sum/(maxX-x1);
                    int avg = sum/greyCopy.getHeight();
                    //System.out.println(sum+"//"+(maxX-x1)+"="+avg);
                    out.setRGB(j+ct.getSensorsPerMeasurement()/2,i,DrawPane.greyToRGB(avg));

                    //greyPane.refreshImage(grey);
                    //outPane.refreshImage(out);
                }
                greyPane.refreshImage(grey);
                outPane.refreshImage(out);
            }
            greyPane.refreshImage(greyCopy);
        }
        
        private void prepareResult(String resultId, String sinogramId){
            SignedPane outPane = images.get(resultId);
            int h = img.getHeight();
            int w = img.getWidth();
            BufferedImage out = DrawPane.prepareBackground(h,w);
            outPane.refreshImage(out);
            
            BufferedImage in = images.get(sinogramId).getPane().getImg();
            
            int[][] array = new int[w][h];
            for(int[] r: array){
                for(int f: r){
                    f = 0;
                }
            }
            int max = -1;
            int min = 0;
            
            for(int i = 0;i<in.getHeight();i++){
                Sensor src = ct.getSensor(i*ct.getTranslation());
                double center = src.getDegree()+180.0;
                if(center>360.0) center = center-360.0;
                
                for(int j = ct.getSensorsPerMeasurement()/2*(-1);j<=ct.getSensorsPerMeasurement()/2;j++){
                    
                    Sensor dest = ct.getSensor(center+j*ct.getAngleBetweenSensors());
                    int grey = (new Color(in.getRGB(j+ct.getSensorsPerMeasurement()/2, i))).getRed();

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
                            //out.setRGB(x, y, DrawPane.greyToRGB(grey));
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
                outPane.refreshImage(DrawPane.createImage(array, w, h, max));
            }
        }

    @Override
    public void run() {
        generateSimulation();
    }
    
}
