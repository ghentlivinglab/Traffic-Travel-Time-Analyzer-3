
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class HereIncidents {

    @SerializedName("TRAFFICITEMS")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.TRAFFICITEMS TRAFFICITEMS;
    @SerializedName("TIMESTAMP")
    @Expose
    private String TIMESTAMP;
    @SerializedName("VERSION")
    @Expose
    private Double VERSION;

    /**
     * 
     * @return
     *     The TRAFFICITEMS
     */
    public be.ugent.tiwi.domein.hereIncident.TRAFFICITEMS getTRAFFICITEMS() {
        return TRAFFICITEMS;
    }

    /**
     * 
     * @param TRAFFICITEMS
     *     The TRAFFICITEMS
     */
    public void setTRAFFICITEMS(be.ugent.tiwi.domein.hereIncident.TRAFFICITEMS TRAFFICITEMS) {
        this.TRAFFICITEMS = TRAFFICITEMS;
    }

    /**
     * 
     * @return
     *     The TIMESTAMP
     */
    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    /**
     * 
     * @param TIMESTAMP
     *     The TIMESTAMP
     */
    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    /**
     * 
     * @return
     *     The VERSION
     */
    public Double getVERSION() {
        return VERSION;
    }

    /**
     * 
     * @param VERSION
     *     The VERSION
     */
    public void setVERSION(Double VERSION) {
        this.VERSION = VERSION;
    }

}
