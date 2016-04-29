
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ALERTC {

    @SerializedName("TRAFFICCODE")
    @Expose
    private Integer TRAFFICCODE;
    @SerializedName("QUANTIFIERS")
    @Expose
    private Integer QUANTIFIERS;
    @SerializedName("DESCRIPTION")
    @Expose
    private String DESCRIPTION;
    @SerializedName("ALERTCDURATION")
    @Expose
    private String ALERTCDURATION;
    @SerializedName("ALERTCDIRECTION")
    @Expose
    private Integer ALERTCDIRECTION;
    @SerializedName("URGENCY")
    @Expose
    private String URGENCY;
    @SerializedName("UPDATECLASS")
    @Expose
    private Integer UPDATECLASS;
    @SerializedName("PHRASECODE")
    @Expose
    private String PHRASECODE;
    @SerializedName("EXTENT")
    @Expose
    private String EXTENT;
    @SerializedName("DURATION")
    @Expose
    private Integer DURATION;

    /**
     * 
     * @return
     *     The TRAFFICCODE
     */
    public Integer getTRAFFICCODE() {
        return TRAFFICCODE;
    }

    /**
     * 
     * @param TRAFFICCODE
     *     The TRAFFICCODE
     */
    public void setTRAFFICCODE(Integer TRAFFICCODE) {
        this.TRAFFICCODE = TRAFFICCODE;
    }

    /**
     * 
     * @return
     *     The QUANTIFIERS
     */
    public Integer getQUANTIFIERS() {
        return QUANTIFIERS;
    }

    /**
     * 
     * @param QUANTIFIERS
     *     The QUANTIFIERS
     */
    public void setQUANTIFIERS(Integer QUANTIFIERS) {
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
    public Integer getALERTCDIRECTION() {
        return ALERTCDIRECTION;
    }

    /**
     * 
     * @param ALERTCDIRECTION
     *     The ALERTCDIRECTION
     */
    public void setALERTCDIRECTION(Integer ALERTCDIRECTION) {
        this.ALERTCDIRECTION = ALERTCDIRECTION;
    }

    /**
     * 
     * @return
     *     The URGENCY
     */
    public String getURGENCY() {
        return URGENCY;
    }

    /**
     * 
     * @param URGENCY
     *     The URGENCY
     */
    public void setURGENCY(String URGENCY) {
        this.URGENCY = URGENCY;
    }

    /**
     * 
     * @return
     *     The UPDATECLASS
     */
    public Integer getUPDATECLASS() {
        return UPDATECLASS;
    }

    /**
     * 
     * @param UPDATECLASS
     *     The UPDATECLASS
     */
    public void setUPDATECLASS(Integer UPDATECLASS) {
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
    public Integer getDURATION() {
        return DURATION;
    }

    /**
     * 
     * @param DURATION
     *     The DURATION
     */
    public void setDURATION(Integer DURATION) {
        this.DURATION = DURATION;
    }

}
