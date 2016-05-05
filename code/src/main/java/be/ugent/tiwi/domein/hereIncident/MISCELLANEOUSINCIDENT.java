
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class MISCELLANEOUSINCIDENT {

    @SerializedName("MISCELLANEOUSTYPEDESC")
    @Expose
    private String MISCELLANEOUSTYPEDESC;

    /**
     * 
     * @return
     *     The MISCELLANEOUSTYPEDESC
     */
    public String getMISCELLANEOUSTYPEDESC() {
        return MISCELLANEOUSTYPEDESC;
    }

    /**
     * 
     * @param MISCELLANEOUSTYPEDESC
     *     The MISCELLANEOUSTYPEDESC
     */
    public void setMISCELLANEOUSTYPEDESC(String MISCELLANEOUSTYPEDESC) {
        this.MISCELLANEOUSTYPEDESC = MISCELLANEOUSTYPEDESC;
    }

}
