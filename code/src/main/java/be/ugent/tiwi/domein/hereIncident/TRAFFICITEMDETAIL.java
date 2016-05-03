
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TRAFFICITEMDETAIL {

    @SerializedName("ROADCLOSED")
    @Expose
    private Boolean ROADCLOSED;
    @SerializedName("INCIDENT")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.INCIDENT INCIDENT;

    /**
     * 
     * @return
     *     The ROADCLOSED
     */
    public Boolean getROADCLOSED() {
        return ROADCLOSED;
    }

    /**
     * 
     * @param ROADCLOSED
     *     The ROADCLOSED
     */
    public void setROADCLOSED(Boolean ROADCLOSED) {
        this.ROADCLOSED = ROADCLOSED;
    }

    /**
     * 
     * @return
     *     The INCIDENT
     */
    public be.ugent.tiwi.domein.hereIncident.INCIDENT getINCIDENT() {
        return INCIDENT;
    }

    /**
     * 
     * @param INCIDENT
     *     The INCIDENT
     */
    public void setINCIDENT(be.ugent.tiwi.domein.hereIncident.INCIDENT INCIDENT) {
        this.INCIDENT = INCIDENT;
    }

}
