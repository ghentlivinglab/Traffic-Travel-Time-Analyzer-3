
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class NAVTECH {

    @SerializedName("EDGE")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.EDGE EDGE;
    @SerializedName("VERSIONID")
    @Expose
    private String VERSIONID;

    /**
     * 
     * @return
     *     The EDGE
     */
    public be.ugent.tiwi.domein.hereIncident.EDGE getEDGE() {
        return EDGE;
    }

    /**
     * 
     * @param EDGE
     *     The EDGE
     */
    public void setEDGE(be.ugent.tiwi.domein.hereIncident.EDGE EDGE) {
        this.EDGE = EDGE;
    }

    /**
     * 
     * @return
     *     The VERSIONID
     */
    public String getVERSIONID() {
        return VERSIONID;
    }

    /**
     * 
     * @param VERSIONID
     *     The VERSIONID
     */
    public void setVERSIONID(String VERSIONID) {
        this.VERSIONID = VERSIONID;
    }

}
