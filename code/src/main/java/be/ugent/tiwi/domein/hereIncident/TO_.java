
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TO_ {

    @SerializedName("ROADWAY")
    @Expose
    private ROADWAY_ ROADWAY;
    @SerializedName("POINT")
    @Expose
    private POINT_ POINT;
    @SerializedName("DIRECTION")
    @Expose
    private DIRECTION_ DIRECTION;
    @SerializedName("PROXIMITY")
    @Expose
    private PROXIMITY_ PROXIMITY;

    /**
     * 
     * @return
     *     The ROADWAY
     */
    public ROADWAY_ getROADWAY() {
        return ROADWAY;
    }

    /**
     * 
     * @param ROADWAY
     *     The ROADWAY
     */
    public void setROADWAY(ROADWAY_ ROADWAY) {
        this.ROADWAY = ROADWAY;
    }

    /**
     * 
     * @return
     *     The POINT
     */
    public POINT_ getPOINT() {
        return POINT;
    }

    /**
     * 
     * @param POINT
     *     The POINT
     */
    public void setPOINT(POINT_ POINT) {
        this.POINT = POINT;
    }

    /**
     * 
     * @return
     *     The DIRECTION
     */
    public DIRECTION_ getDIRECTION() {
        return DIRECTION;
    }

    /**
     * 
     * @param DIRECTION
     *     The DIRECTION
     */
    public void setDIRECTION(DIRECTION_ DIRECTION) {
        this.DIRECTION = DIRECTION;
    }

    /**
     * 
     * @return
     *     The PROXIMITY
     */
    public PROXIMITY_ getPROXIMITY() {
        return PROXIMITY;
    }

    /**
     * 
     * @param PROXIMITY
     *     The PROXIMITY
     */
    public void setPROXIMITY(PROXIMITY_ PROXIMITY) {
        this.PROXIMITY = PROXIMITY;
    }

}
