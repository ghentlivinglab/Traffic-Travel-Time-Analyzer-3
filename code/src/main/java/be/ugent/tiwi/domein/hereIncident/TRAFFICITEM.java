
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TRAFFICITEM {

    @SerializedName("TRAFFICITEMID")
    @Expose
    private Long TRAFFICITEMID;
    @SerializedName("ORIGINALTRAFFICITEMID")
    @Expose
    private Long ORIGINALTRAFFICITEMID;
    @SerializedName("TRAFFICITEMSTATUSSHORTDESC")
    @Expose
    private String TRAFFICITEMSTATUSSHORTDESC;
    @SerializedName("TRAFFICITEMTYPEDESC")
    @Expose
    private String TRAFFICITEMTYPEDESC;
    @SerializedName("STARTTIME")
    @Expose
    private String STARTTIME;
    @SerializedName("ENDTIME")
    @Expose
    private String ENDTIME;
    @SerializedName("ENTRYTIME")
    @Expose
    private String ENTRYTIME;
    @SerializedName("CRITICALITY")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.CRITICALITY CRITICALITY;
    @SerializedName("VERIFIED")
    @Expose
    private Boolean VERIFIED;
    @SerializedName("ABBREVIATION")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.ABBREVIATION ABBREVIATION;
    @SerializedName("RDSTMCLOCATIONS")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.RDSTMCLOCATIONS RDSTMCLOCATIONS;
    @SerializedName("LOCATION")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.LOCATION LOCATION;
    @SerializedName("TRAFFICITEMDETAIL")
    @Expose
    private be.ugent.tiwi.domein.hereIncident.TRAFFICITEMDETAIL TRAFFICITEMDETAIL;
    @SerializedName("TRAFFICITEMDESCRIPTION")
    @Expose
    private List<be.ugent.tiwi.domein.hereIncident.TRAFFICITEMDESCRIPTION> TRAFFICITEMDESCRIPTION = new ArrayList<be.ugent.tiwi.domein.hereIncident.TRAFFICITEMDESCRIPTION>();
    @SerializedName("COMMENTS")
    @Expose
    private String COMMENTS;

    /**
     * 
     * @return
     *     The TRAFFICITEMID
     */
    public Long getTRAFFICITEMID() {
        return TRAFFICITEMID;
    }

    /**
     * 
     * @param TRAFFICITEMID
     *     The TRAFFICITEMID
     */
    public void setTRAFFICITEMID(Long TRAFFICITEMID) {
        this.TRAFFICITEMID = TRAFFICITEMID;
    }

    /**
     * 
     * @return
     *     The ORIGINALTRAFFICITEMID
     */
    public Long getORIGINALTRAFFICITEMID() {
        return ORIGINALTRAFFICITEMID;
    }

    /**
     * 
     * @param ORIGINALTRAFFICITEMID
     *     The ORIGINALTRAFFICITEMID
     */
    public void setORIGINALTRAFFICITEMID(Long ORIGINALTRAFFICITEMID) {
        this.ORIGINALTRAFFICITEMID = ORIGINALTRAFFICITEMID;
    }

    /**
     * 
     * @return
     *     The TRAFFICITEMSTATUSSHORTDESC
     */
    public String getTRAFFICITEMSTATUSSHORTDESC() {
        return TRAFFICITEMSTATUSSHORTDESC;
    }

    /**
     * 
     * @param TRAFFICITEMSTATUSSHORTDESC
     *     The TRAFFICITEMSTATUSSHORTDESC
     */
    public void setTRAFFICITEMSTATUSSHORTDESC(String TRAFFICITEMSTATUSSHORTDESC) {
        this.TRAFFICITEMSTATUSSHORTDESC = TRAFFICITEMSTATUSSHORTDESC;
    }

    /**
     * 
     * @return
     *     The TRAFFICITEMTYPEDESC
     */
    public String getTRAFFICITEMTYPEDESC() {
        return TRAFFICITEMTYPEDESC;
    }

    /**
     * 
     * @param TRAFFICITEMTYPEDESC
     *     The TRAFFICITEMTYPEDESC
     */
    public void setTRAFFICITEMTYPEDESC(String TRAFFICITEMTYPEDESC) {
        this.TRAFFICITEMTYPEDESC = TRAFFICITEMTYPEDESC;
    }

    /**
     * 
     * @return
     *     The STARTTIME
     */
    public String getSTARTTIME() {
        return STARTTIME;
    }

    /**
     * 
     * @param STARTTIME
     *     The STARTTIME
     */
    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    /**
     * 
     * @return
     *     The ENDTIME
     */
    public String getENDTIME() {
        return ENDTIME;
    }

    /**
     * 
     * @param ENDTIME
     *     The ENDTIME
     */
    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    /**
     * 
     * @return
     *     The ENTRYTIME
     */
    public String getENTRYTIME() {
        return ENTRYTIME;
    }

    /**
     * 
     * @param ENTRYTIME
     *     The ENTRYTIME
     */
    public void setENTRYTIME(String ENTRYTIME) {
        this.ENTRYTIME = ENTRYTIME;
    }

    /**
     * 
     * @return
     *     The CRITICALITY
     */
    public be.ugent.tiwi.domein.hereIncident.CRITICALITY getCRITICALITY() {
        return CRITICALITY;
    }

    /**
     * 
     * @param CRITICALITY
     *     The CRITICALITY
     */
    public void setCRITICALITY(be.ugent.tiwi.domein.hereIncident.CRITICALITY CRITICALITY) {
        this.CRITICALITY = CRITICALITY;
    }

    /**
     * 
     * @return
     *     The VERIFIED
     */
    public Boolean getVERIFIED() {
        return VERIFIED;
    }

    /**
     * 
     * @param VERIFIED
     *     The VERIFIED
     */
    public void setVERIFIED(Boolean VERIFIED) {
        this.VERIFIED = VERIFIED;
    }

    /**
     * 
     * @return
     *     The ABBREVIATION
     */
    public be.ugent.tiwi.domein.hereIncident.ABBREVIATION getABBREVIATION() {
        return ABBREVIATION;
    }

    /**
     * 
     * @param ABBREVIATION
     *     The ABBREVIATION
     */
    public void setABBREVIATION(be.ugent.tiwi.domein.hereIncident.ABBREVIATION ABBREVIATION) {
        this.ABBREVIATION = ABBREVIATION;
    }

    /**
     * 
     * @return
     *     The RDSTMCLOCATIONS
     */
    public be.ugent.tiwi.domein.hereIncident.RDSTMCLOCATIONS getRDSTMCLOCATIONS() {
        return RDSTMCLOCATIONS;
    }

    /**
     * 
     * @param RDSTMCLOCATIONS
     *     The RDSTMCLOCATIONS
     */
    public void setRDSTMCLOCATIONS(be.ugent.tiwi.domein.hereIncident.RDSTMCLOCATIONS RDSTMCLOCATIONS) {
        this.RDSTMCLOCATIONS = RDSTMCLOCATIONS;
    }

    /**
     * 
     * @return
     *     The LOCATION
     */
    public be.ugent.tiwi.domein.hereIncident.LOCATION getLOCATION() {
        return LOCATION;
    }

    /**
     * 
     * @param LOCATION
     *     The LOCATION
     */
    public void setLOCATION(be.ugent.tiwi.domein.hereIncident.LOCATION LOCATION) {
        this.LOCATION = LOCATION;
    }

    /**
     * 
     * @return
     *     The TRAFFICITEMDETAIL
     */
    public be.ugent.tiwi.domein.hereIncident.TRAFFICITEMDETAIL getTRAFFICITEMDETAIL() {
        return TRAFFICITEMDETAIL;
    }

    /**
     * 
     * @param TRAFFICITEMDETAIL
     *     The TRAFFICITEMDETAIL
     */
    public void setTRAFFICITEMDETAIL(be.ugent.tiwi.domein.hereIncident.TRAFFICITEMDETAIL TRAFFICITEMDETAIL) {
        this.TRAFFICITEMDETAIL = TRAFFICITEMDETAIL;
    }

    /**
     * 
     * @return
     *     The TRAFFICITEMDESCRIPTION
     */
    public List<be.ugent.tiwi.domein.hereIncident.TRAFFICITEMDESCRIPTION> getTRAFFICITEMDESCRIPTION() {
        return TRAFFICITEMDESCRIPTION;
    }

    /**
     * 
     * @param TRAFFICITEMDESCRIPTION
     *     The TRAFFICITEMDESCRIPTION
     */
    public void setTRAFFICITEMDESCRIPTION(List<be.ugent.tiwi.domein.hereIncident.TRAFFICITEMDESCRIPTION> TRAFFICITEMDESCRIPTION) {
        this.TRAFFICITEMDESCRIPTION = TRAFFICITEMDESCRIPTION;
    }

    /**
     * 
     * @return
     *     The COMMENTS
     */
    public String getCOMMENTS() {
        return COMMENTS;
    }

    /**
     * 
     * @param COMMENTS
     *     The COMMENTS
     */
    public void setCOMMENTS(String COMMENTS) {
        this.COMMENTS = COMMENTS;
    }

}
