
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class EVENT {

    @SerializedName("EVENTITEMCANCELLED")
    @Expose
    private Boolean EVENTITEMCANCELLED;
    @SerializedName("SCHEDULEDCONSTRUCTIONEVENT")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.SCHEDULEDCONSTRUCTIONEVENT SCHEDULEDCONSTRUCTIONEVENT;

    /**
     * 
     * @return
     *     The EVENTITEMCANCELLED
     */
    public Boolean getEVENTITEMCANCELLED() {
        return EVENTITEMCANCELLED;
    }

    /**
     * 
     * @param EVENTITEMCANCELLED
     *     The EVENTITEMCANCELLED
     */
    public void setEVENTITEMCANCELLED(Boolean EVENTITEMCANCELLED) {
        this.EVENTITEMCANCELLED = EVENTITEMCANCELLED;
    }

    /**
     * 
     * @return
     *     The SCHEDULEDCONSTRUCTIONEVENT
     */
    public be.ugent.tiwi.domein.hereIncident.SCHEDULEDCONSTRUCTIONEVENT getSCHEDULEDCONSTRUCTIONEVENT() {
        return SCHEDULEDCONSTRUCTIONEVENT;
    }

    /**
     * 
     * @param SCHEDULEDCONSTRUCTIONEVENT
     *     The SCHEDULEDCONSTRUCTIONEVENT
     */
    public void setSCHEDULEDCONSTRUCTIONEVENT(be.ugent.tiwi.domein.hereIncident.SCHEDULEDCONSTRUCTIONEVENT SCHEDULEDCONSTRUCTIONEVENT) {
        this.SCHEDULEDCONSTRUCTIONEVENT = SCHEDULEDCONSTRUCTIONEVENT;
    }

}
