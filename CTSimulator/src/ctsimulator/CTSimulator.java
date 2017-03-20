/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LU
 */
public class CTSimulator {

    private int R;
    private Map<String,Sensor> sensors;
    
    private int sensorsPerMeasurement = 325;
    private double angleBetweenSensors = 1.0;
    private int numberOfMeasurements = 360;
    
    private double translation = 1.0;
    private int numberOfSensors = 360;
    
    public CTSimulator()
    {
        sensors = new HashMap();
    }
    
    @Override
    public CTSimulator clone(){
        CTSimulator copy = new CTSimulator();
        copy.setAngleBetweenSensors(angleBetweenSensors);
        copy.setNumberOfMeasurements(numberOfMeasurements);
        copy.setSensorsPerMeasurement(sensorsPerMeasurement);
        copy.setR(R);
        return copy;
    }

    public int getR() {
        return R;
    }

    public void setR(int R) {
        sensors.clear();
        this.R = R;
        double a = 0.0;
        while(a<360.0){
            String id = Double.toString(a);
            sensors.put(id, new Sensor(a,R));
            //System.out.println(id);
            a = a+this.angleBetweenSensors;
        }
    }
    
    public Sensor getSensor(double id){
        if(id<0.0) id = id + 360.0;
        if(id>=360.0) id = id - 360.0;
        //System.out.println(Double.toString(id));
        return sensors.get(Double.toString(id));
    }

    public int getSensorsPerMeasurement() {
        return sensorsPerMeasurement;
    }

    public void setSensorsPerMeasurement(int sensorsPerMeasurement) {
        this.sensorsPerMeasurement = sensorsPerMeasurement;
    }

    public double getAngleBetweenSensors() {
        return angleBetweenSensors;
    }

    public void setAngleBetweenSensors(double angleBetweenSensors) {
        this.angleBetweenSensors = angleBetweenSensors;
        this.numberOfSensors = (int) (360.0/angleBetweenSensors);
    }

    public int getNumberOfMeasurements() {
        return numberOfMeasurements;
    }

    public void setNumberOfMeasurements(int numberOfMeasurements) {
        this.numberOfMeasurements = numberOfMeasurements;
        this.translation = 360.0/numberOfMeasurements;
    }

    public double getTranslation() {
        return translation;
    }

    public int getNumberOfSensors() {
        return numberOfSensors;
    }
    
    
    
}
