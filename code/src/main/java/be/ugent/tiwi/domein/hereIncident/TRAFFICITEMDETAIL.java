
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TRAFFICITEMDETAIL {

    @SerializedName("ROADCLOSED")
    @Expose
    private Boolean ROADCLOSED;
    @SerializedName("EVENT")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.EVENT EVENT;

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
     *     The EVENT
     */
    public be.ugent.tiwi.domein.hereIncident.EVENT getEVENT() {
        return EVENT;
    }

    /**
     * 
     * @param EVENT
     *     The EVENT
     */
    public void setEVENT(be.ugent.tiwi.domein.hereIncident.EVENT EVENT) {
        this.EVENT = EVENT;
    }

}
