
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RDSTMC {

    @SerializedName("ORIGIN")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.ORIGIN ORIGIN;
    @SerializedName("DIRECTION")
    @Expose
    private String DIRECTION;
    @SerializedName("ALERTC")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.ALERTC ALERTC;

    /**
     * 
     * @return
     *     The ORIGIN
     */
    public be.ugent.tiwi.domein.hereIncident.ORIGIN getORIGIN() {
        return ORIGIN;
    }

    /**
     * 
     * @param ORIGIN
     *     The ORIGIN
     */
    public void setORIGIN(be.ugent.tiwi.domein.hereIncident.ORIGIN ORIGIN) {
        this.ORIGIN = ORIGIN;
    }

    /**
     * 
     * @return
     *     The DIRECTION
     */
    public String getDIRECTION() {
        return DIRECTION;
    }

    /**
     * 
     * @param DIRECTION
     *     The DIRECTION
     */
    public void setDIRECTION(String DIRECTION) {
        this.DIRECTION = DIRECTION;
    }

    /**
     * 
     * @return
     *     The ALERTC
     */
    public be.ugent.tiwi.domein.hereIncident.ALERTC getALERTC() {
        return ALERTC;
    }

    /**
     * 
     * @param ALERTC
     *     The ALERTC
     */
    public void setALERTC(be.ugent.tiwi.domein.hereIncident.ALERTC ALERTC) {
        this.ALERTC = ALERTC;
    }

}
