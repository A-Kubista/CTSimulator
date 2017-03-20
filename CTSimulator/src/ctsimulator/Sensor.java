/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

/**
 *
 * @author LU
 */
public class Sensor {
    private int x;
    private int y;
    private final double degree;
    private final double radian;
    
    public Sensor(double ang, int r){
        degree = ang;
        radian = ang/180.0 * Math.PI;
        x = (int) (Math.sin(radian)*r);
        y = (int) (Math.cos(radian)*r);
        normalization(r);
    }
    
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
