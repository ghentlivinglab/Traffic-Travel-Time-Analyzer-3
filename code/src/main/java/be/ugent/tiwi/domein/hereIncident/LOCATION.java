
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class LOCATION {

    @SerializedName("DEFINED")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.DEFINED DEFINED;
    @SerializedName("GEOLOC")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.GEOLOC GEOLOC;
    @SerializedName("NAVTECH")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.NAVTECH NAVTECH;

    /**
     * 
     * @return
     *     The DEFINED
     */
    public be.ugent.tiwi.domein.hereIncident.DEFINED getDEFINED() {
        return DEFINED;
    }

    /**
     * 
     * @param DEFINED
     *     The DEFINED
     */
    public void setDEFINED(be.ugent.tiwi.domein.hereIncident.DEFINED DEFINED) {
        this.DEFINED = DEFINED;
    }

    /**
     * 
     * @return
     *     The GEOLOC
     */
    public be.ugent.tiwi.domein.hereIncident.GEOLOC getGEOLOC() {
        return GEOLOC;
    }

    /**
     * 
     * @param GEOLOC
     *     The GEOLOC
     */
    public void setGEOLOC(be.ugent.tiwi.domein.hereIncident.GEOLOC GEOLOC) {
        this.GEOLOC = GEOLOC;
    }

    /**
     * 
     * @return
     *     The NAVTECH
     */
    public be.ugent.tiwi.domein.hereIncident.NAVTECH getNAVTECH() {
        return NAVTECH;
    }

    /**
     * 
     * @param NAVTECH
     *     The NAVTECH
     */
    public void setNAVTECH(be.ugent.tiwi.domein.hereIncident.NAVTECH NAVTECH) {
        this.NAVTECH = NAVTECH;
    }

}
