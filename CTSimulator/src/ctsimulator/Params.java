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
     * @return the patient_id
     */
    public String getPatient_id() {
        return patient_id;
    }

    /**
     * @param patient_id the patient_id to set
     */
    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    /**
     * @return the patient_name
     */
    public String getPatient_name() {
        return patient_name;
    }

    /**
     * @param patient_name the patient_name to set
     */
    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

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
    private String patient_id = "pesel";
    private String patient_name = "imie i nazwisko";
    private String date = "data";
    
    private Params() {
    }
    
    public static Params getInstance() {
        return ParamsHolder.INSTANCE;
    }

    
    private static class ParamsHolder {

        private static final Params INSTANCE = new Params();
    }
}
