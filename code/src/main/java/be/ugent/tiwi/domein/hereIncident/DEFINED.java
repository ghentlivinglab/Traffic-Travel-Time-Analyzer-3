
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DEFINED {

    @SerializedName("ORIGIN")
    @Expose
    private ORIGIN_ ORIGIN;
    @SerializedName("TO")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.TO TO;

    /**
     * 
     * @return
     *     The ORIGIN
     */
    public ORIGIN_ getORIGIN() {
        return ORIGIN;
    }

    /**
     * 
     * @param ORIGIN
     *     The ORIGIN
     */
    public void setORIGIN(ORIGIN_ ORIGIN) {
        this.ORIGIN = ORIGIN;
    }

    /**
     * 
     * @return
     *     The TO
     */
    public be.ugent.tiwi.domein.hereIncident.TO getTO() {
        return TO;
    }

    /**
     * 
     * @param TO
     *     The TO
     */
    public void setTO(be.ugent.tiwi.domein.hereIncident.TO TO) {
        this.TO = TO;
    }

}
