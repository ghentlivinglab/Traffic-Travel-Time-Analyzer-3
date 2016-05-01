
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TO {

    @SerializedName("EBUCOUNTRYCODE")
    @Expose
    private String EBUCOUNTRYCODE;
    @SerializedName("TABLEID")
    @Expose
    private Long TABLEID;
    @SerializedName("LOCATIONID")
    @Expose
    private String LOCATIONID;
    @SerializedName("LOCATIONDESC")
    @Expose
    private String LOCATIONDESC;
    @SerializedName("RDSDIRECTION")
    @Expose
    private String RDSDIRECTION;

    /**
     * 
     * @return
     *     The EBUCOUNTRYCODE
     */
    public String getEBUCOUNTRYCODE() {
        return EBUCOUNTRYCODE;
    }

    /**
     * 
     * @param EBUCOUNTRYCODE
     *     The EBUCOUNTRYCODE
     */
    public void setEBUCOUNTRYCODE(String EBUCOUNTRYCODE) {
        this.EBUCOUNTRYCODE = EBUCOUNTRYCODE;
    }

    /**
     * 
     * @return
     *     The TABLEID
     */
    public Long getTABLEID() {
        return TABLEID;
    }

    /**
     * 
     * @param TABLEID
     *     The TABLEID
     */
    public void setTABLEID(Long TABLEID) {
        this.TABLEID = TABLEID;
    }

    /**
     * 
     * @return
     *     The LOCATIONID
     */
    public String getLOCATIONID() {
        return LOCATIONID;
    }

    /**
     * 
     * @param LOCATIONID
     *     The LOCATIONID
     */
    public void setLOCATIONID(String LOCATIONID) {
        this.LOCATIONID = LOCATIONID;
    }

    /**
     * 
     * @return
     *     The LOCATIONDESC
     */
    public String getLOCATIONDESC() {
        return LOCATIONDESC;
    }

    /**
     * 
     * @param LOCATIONDESC
     *     The LOCATIONDESC
     */
    public void setLOCATIONDESC(String LOCATIONDESC) {
        this.LOCATIONDESC = LOCATIONDESC;
    }

    /**
     * 
     * @return
     *     The RDSDIRECTION
     */
    public String getRDSDIRECTION() {
        return RDSDIRECTION;
    }

    /**
     * 
     * @param RDSDIRECTION
     *     The RDSDIRECTION
     */
    public void setRDSDIRECTION(String RDSDIRECTION) {
        this.RDSDIRECTION = RDSDIRECTION;
    }

}
