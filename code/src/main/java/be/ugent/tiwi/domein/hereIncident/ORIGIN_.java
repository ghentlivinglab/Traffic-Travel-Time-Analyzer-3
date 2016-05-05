
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ORIGIN_ {

    @SerializedName("ROADWAY")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.ROADWAY ROADWAY;
    @SerializedName("POINT")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.POINT POINT;
    @SerializedName("DIRECTION")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.DIRECTION DIRECTION;
    @SerializedName("PROXIMITY")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.PROXIMITY PROXIMITY;

    /**
     * 
     * @return
     *     The ROADWAY
     */
    public be.ugent.tiwi.domein.hereIncident.ROADWAY getROADWAY() {
        return ROADWAY;
    }

    /**
     * 
     * @param ROADWAY
     *     The ROADWAY
     */
    public void setROADWAY(be.ugent.tiwi.domein.hereIncident.ROADWAY ROADWAY) {
        this.ROADWAY = ROADWAY;
    }

    /**
     * 
     * @return
     *     The POINT
     */
    public be.ugent.tiwi.domein.hereIncident.POINT getPOINT() {
        return POINT;
    }

    /**
     * 
     * @param POINT
     *     The POINT
     */
    public void setPOINT(be.ugent.tiwi.domein.hereIncident.POINT POINT) {
        this.POINT = POINT;
    }

    /**
     * 
     * @return
     *     The DIRECTION
     */
    public be.ugent.tiwi.domein.hereIncident.DIRECTION getDIRECTION() {
        return DIRECTION;
    }

    /**
     * 
     * @param DIRECTION
     *     The DIRECTION
     */
    public void setDIRECTION(be.ugent.tiwi.domein.hereIncident.DIRECTION DIRECTION) {
        this.DIRECTION = DIRECTION;
    }

    /**
     * 
     * @return
     *     The PROXIMITY
     */
    public be.ugent.tiwi.domein.hereIncident.PROXIMITY getPROXIMITY() {
        return PROXIMITY;
    }

    /**
     * 
     * @param PROXIMITY
     *     The PROXIMITY
     */
    public void setPROXIMITY(be.ugent.tiwi.domein.hereIncident.PROXIMITY PROXIMITY) {
        this.PROXIMITY = PROXIMITY;
    }

}
