package TheEarthGuard.ComeBackHome.controller;

import java.sql.Timestamp;

public class ReportForm {

    private String witness_area;

    private Timestamp witness_time;

    private String witness_txt;

    private String witness_pic;

    public String getWitness_area() {
        return witness_area;
    }

    public void setWitness_area(String witness_area) {
        this.witness_area = witness_area;
    }

    public Timestamp getWitness_time() {
        return witness_time;
    }

    public void setWitness_time(Timestamp witness_time) {
        this.witness_time = witness_time;
    }

    public String getWitness_txt() {
        return witness_txt;
    }

    public void setWitness_txt(String witness_txt) {
        this.witness_txt = witness_txt;
    }

    public String getWitness_pic() {
        return witness_pic;
    }

    public void setWitness_pic(String witness_pic) {
        this.witness_pic = witness_pic;
    }
}