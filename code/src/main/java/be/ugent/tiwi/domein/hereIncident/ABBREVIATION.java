
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ABBREVIATION {

    @SerializedName("SHORTDESC")
    @Expose
    private String SHORTDESC;
    @SerializedName("DESCRIPTION")
    @Expose
    private String DESCRIPTION;

    /**
     * 
     * @return
     *     The SHORTDESC
     */
    public String getSHORTDESC() {
        return SHORTDESC;
    }

    /**
     * 
     * @param SHORTDESC
     *     The SHORTDESC
     */
    public void setSHORTDESC(String SHORTDESC) {
        this.SHORTDESC = SHORTDESC;
    }

    /**
     * 
     * @return
     *     The DESCRIPTION
     */
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    /**
     * 
     * @param DESCRIPTION
     *     The DESCRIPTION
     */
    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

}
