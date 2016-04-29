
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class POINT_ {

    @SerializedName("DESCRIPTION")
    @Expose
    private List<DESCRIPTION____> DESCRIPTION = new ArrayList<DESCRIPTION____>();
    @SerializedName("ID")
    @Expose
    private Integer ID;

    /**
     * 
     * @return
     *     The DESCRIPTION
     */
    public List<DESCRIPTION____> getDESCRIPTION() {
        return DESCRIPTION;
    }

    /**
     * 
     * @param DESCRIPTION
     *     The DESCRIPTION
     */
    public void setDESCRIPTION(List<DESCRIPTION____> DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    /**
     * 
     * @return
     *     The ID
     */
    public Integer getID() {
        return ID;
    }

    /**
     * 
     * @param ID
     *     The ID
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

}
