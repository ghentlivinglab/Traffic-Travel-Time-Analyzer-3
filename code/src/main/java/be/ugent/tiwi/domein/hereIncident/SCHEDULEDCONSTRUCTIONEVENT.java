
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SCHEDULEDCONSTRUCTIONEVENT {

    @SerializedName("SCHEDULEDCONSTRUCTIONTYPEDESC")
    @Expose
    private String SCHEDULEDCONSTRUCTIONTYPEDESC;
    @SerializedName("SCHEDULEDCONSTRUCTIONDETAIL")
    @Expose
    private String SCHEDULEDCONSTRUCTIONDETAIL;

    /**
     * 
     * @return
     *     The SCHEDULEDCONSTRUCTIONTYPEDESC
     */
    public String getSCHEDULEDCONSTRUCTIONTYPEDESC() {
        return SCHEDULEDCONSTRUCTIONTYPEDESC;
    }

    /**
     * 
     * @param SCHEDULEDCONSTRUCTIONTYPEDESC
     *     The SCHEDULEDCONSTRUCTIONTYPEDESC
     */
    public void setSCHEDULEDCONSTRUCTIONTYPEDESC(String SCHEDULEDCONSTRUCTIONTYPEDESC) {
        this.SCHEDULEDCONSTRUCTIONTYPEDESC = SCHEDULEDCONSTRUCTIONTYPEDESC;
    }

    /**
     * 
     * @return
     *     The SCHEDULEDCONSTRUCTIONDETAIL
     */
    public String getSCHEDULEDCONSTRUCTIONDETAIL() {
        return SCHEDULEDCONSTRUCTIONDETAIL;
    }

    /**
     * 
     * @param SCHEDULEDCONSTRUCTIONDETAIL
     *     The SCHEDULEDCONSTRUCTIONDETAIL
     */
    public void setSCHEDULEDCONSTRUCTIONDETAIL(String SCHEDULEDCONSTRUCTIONDETAIL) {
        this.SCHEDULEDCONSTRUCTIONDETAIL = SCHEDULEDCONSTRUCTIONDETAIL;
    }

}
