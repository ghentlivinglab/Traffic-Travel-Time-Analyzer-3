
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ROADWAY_ {

    @SerializedName("DESCRIPTION")
    @Expose
    private List<DESCRIPTION___> DESCRIPTION = new ArrayList<DESCRIPTION___>();
    @SerializedName("ID")
    @Expose
    private Long ID;

    /**
     * 
     * @return
     *     The DESCRIPTION
     */
    public List<DESCRIPTION___> getDESCRIPTION() {
        return DESCRIPTION;
    }

    /**
     * 
     * @param DESCRIPTION
     *     The DESCRIPTION
     */
    public void setDESCRIPTION(List<DESCRIPTION___> DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    /**
     * 
     * @return
     *     The ID
     */
    public Long getID() {
        return ID;
    }

    /**
     * 
     * @param ID
     *     The ID
     */
    public void setID(Long ID) {
        this.ID = ID;
    }

}
