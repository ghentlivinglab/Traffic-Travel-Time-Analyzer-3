
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class INCIDENT {

    @SerializedName("RESPONSEVEHICLES")
    @Expose
    private Boolean RESPONSEVEHICLES;
    @SerializedName("MISCELLANEOUSINCIDENT")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.MISCELLANEOUSINCIDENT MISCELLANEOUSINCIDENT;

    /**
     * 
     * @return
     *     The RESPONSEVEHICLES
     */
    public Boolean getRESPONSEVEHICLES() {
        return RESPONSEVEHICLES;
    }

    /**
     * 
     * @param RESPONSEVEHICLES
     *     The RESPONSEVEHICLES
     */
    public void setRESPONSEVEHICLES(Boolean RESPONSEVEHICLES) {
        this.RESPONSEVEHICLES = RESPONSEVEHICLES;
    }

    /**
     * 
     * @return
     *     The MISCELLANEOUSINCIDENT
     */
    public be.ugent.tiwi.domein.hereIncident.MISCELLANEOUSINCIDENT getMISCELLANEOUSINCIDENT() {
        return MISCELLANEOUSINCIDENT;
    }

    /**
     * 
     * @param MISCELLANEOUSINCIDENT
     *     The MISCELLANEOUSINCIDENT
     */
    public void setMISCELLANEOUSINCIDENT(be.ugent.tiwi.domein.hereIncident.MISCELLANEOUSINCIDENT MISCELLANEOUSINCIDENT) {
        this.MISCELLANEOUSINCIDENT = MISCELLANEOUSINCIDENT;
    }

}
