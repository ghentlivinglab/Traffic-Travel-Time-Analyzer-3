
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ALERTC {

    @SerializedName("TRAFFICCODE")
    @Expose
    private Long TRAFFICCODE;
    @SerializedName("QUANTIFIERS")
    @Expose
    private Long QUANTIFIERS;
    @SerializedName("DESCRIPTION")
    @Expose
    private String DESCRIPTION;
    @SerializedName("ALERTCDURATION")
    @Expose
    private String ALERTCDURATION;
    @SerializedName("ALERTCDIRECTION")
    @Expose
    private Long ALERTCDIRECTION;
    @SerializedName("UPDATECLASS")
    @Expose
    private Long UPDATECLASS;
    @SerializedName("PHRASECODE")
    @Expose
    private String PHRASECODE;
    @SerializedName("EXTENT")
    @Expose
    private String EXTENT;
    @SerializedName("DURATION")
    @Expose
    private Long DURATION;

    /**
     * 
     * @return
     *     The TRAFFICCODE
     */
    public Long getTRAFFICCODE() {
        return TRAFFICCODE;
    }

    /**
     * 
     * @param TRAFFICCODE
     *     The TRAFFICCODE
     */
    public void setTRAFFICCODE(Long TRAFFICCODE) {
        this.TRAFFICCODE = TRAFFICCODE;
    }

    /**
     * 
     * @return
     *     The QUANTIFIERS
     */
    public Long getQUANTIFIERS() {
        return QUANTIFIERS;
    }

    /**
     * 
     * @param QUANTIFIERS
     *     The QUANTIFIERS
     */
    public void setQUANTIFIERS(Long QUANTIFIERS) {
        this.QUANTIFIERS = QUANTIFIERS;
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

    /**
     * 
     * @return
     *     The ALERTCDURATION
     */
    public String getALERTCDURATION() {
        return ALERTCDURATION;
    }

    /**
     * 
     * @param ALERTCDURATION
     *     The ALERTCDURATION
     */
    public void setALERTCDURATION(String ALERTCDURATION) {
        this.ALERTCDURATION = ALERTCDURATION;
    }

    /**
     * 
     * @return
     *     The ALERTCDIRECTION
     */
    public Long getALERTCDIRECTION() {
        return ALERTCDIRECTION;
    }

    /**
     * 
     * @param ALERTCDIRECTION
     *     The ALERTCDIRECTION
     */
    public void setALERTCDIRECTION(Long ALERTCDIRECTION) {
        this.ALERTCDIRECTION = ALERTCDIRECTION;
    }

    /**
     * 
     * @return
     *     The UPDATECLASS
     */
    public Long getUPDATECLASS() {
        return UPDATECLASS;
    }

    /**
     * 
     * @param UPDATECLASS
     *     The UPDATECLASS
     */
    public void setUPDATECLASS(Long UPDATECLASS) {
        this.UPDATECLASS = UPDATECLASS;
    }

    /**
     * 
     * @return
     *     The PHRASECODE
     */
    public String getPHRASECODE() {
        return PHRASECODE;
    }

    /**
     * 
     * @param PHRASECODE
     *     The PHRASECODE
     */
    public void setPHRASECODE(String PHRASECODE) {
        this.PHRASECODE = PHRASECODE;
    }

    /**
     * 
     * @return
     *     The EXTENT
     */
    public String getEXTENT() {
        return EXTENT;
    }

    /**
     * 
     * @param EXTENT
     *     The EXTENT
     */
    public void setEXTENT(String EXTENT) {
        this.EXTENT = EXTENT;
    }

    /**
     * 
     * @return
     *     The DURATION
     */
    public Long getDURATION() {
        return DURATION;
    }

    /**
     * 
     * @param DURATION
     *     The DURATION
     */
    public void setDURATION(Long DURATION) {
        this.DURATION = DURATION;
    }

}
