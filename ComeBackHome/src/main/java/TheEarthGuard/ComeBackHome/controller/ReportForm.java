package TheEarthGuard.ComeBackHome.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Timestamp;

public class ReportForm {

    private String witness_area;

    private Timestamp witness_time;

    private String witness_desc;

    private String witness_pic;

    public String getWitness_area() {
        return witness_area;
    }

    public void setWitness_area(String witness_area) {
        this.witness_area = witness_area;
    }

    public Timestamp getWitness_time() {
        return new Timestamp(witness_time.getTime());
    }

    @SuppressFBWarnings(justification = "Generated code")
    public void setWitness_time(Timestamp witness_time) {
        this.witness_time = witness_time;
    }

    public String getWitness_desc() {
        return witness_desc;
    }

    public void setWitness_desc(String witness_desc) {
        this.witness_desc = witness_desc;
    }

    public String getWitness_pic() {
        return witness_pic;
    }

    public void setWitness_pic(String witness_pic) {
        this.witness_pic = witness_pic;
    }
}
