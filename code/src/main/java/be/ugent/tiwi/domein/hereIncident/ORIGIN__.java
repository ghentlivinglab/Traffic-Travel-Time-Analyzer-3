
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ORIGIN__ {

    @SerializedName("LATITUDE")
    @Expose
    private Double LATITUDE;
    @SerializedName("LONGITUDE")
    @Expose
    private Double LONGITUDE;

    /**
     * 
     * @return
     *     The LATITUDE
     */
    public Double getLATITUDE() {
        return LATITUDE;
    }

    /**
     * 
     * @param LATITUDE
     *     The LATITUDE
     */
    public void setLATITUDE(Double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    /**
     * 
     * @return
     *     The LONGITUDE
     */
    public Double getLONGITUDE() {
        return LONGITUDE;
    }

    /**
     * 
     * @param LONGITUDE
     *     The LONGITUDE
     */
    public void setLONGITUDE(Double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

}
