/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

/**
 *
 * @author wilek
 */
public class Params {

    /**
     * @return the nSensors
     */
    public int getnSensors() {
        return nSensors;
    }

    /**
     * @param nSensors the nSensors to set
     */
    public void setnSensors(int nSensors) {
        this.nSensors = nSensors;
    }

    /**
     * @return the tAngle
     */
    public double gettAngle() {
        return tAngle;
    }

    /**
     * @param tAngle the tAngle to set
     */
    public void settAngle(double tAngle) {
        this.tAngle = tAngle;
    }

    /**
     * @return the mAngle
     */
    public double getmAngle() {
        return mAngle;
    }

    /**
     * @param mAngle the mAngle to set
     */
    public void setmAngle(double mAngle) {
        this.mAngle = mAngle;
    }
     /**
    * @param nSensors liczba sensorów na pomiar
    * @param tAngle kąt translacji emitera między iteracjami
    * @param mAngle kąt rozstawu sensorów podczas pojedynczego pomiaru (<180.0)
    */
    
    private int nSensors = 200;
    private double tAngle = 1.0;
    private double mAngle = 150;
    
    
    private Params() {
    }
    
    public static Params getInstance() {
        return ParamsHolder.INSTANCE;
    }
    
    private static class ParamsHolder {

        private static final Params INSTANCE = new Params();
    }
}
