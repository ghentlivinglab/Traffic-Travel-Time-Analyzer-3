
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ROADWAY {

    @SerializedName("DESCRIPTION")
    @Expose
    private List<be.ugent.tiwi.domein.hereIncident.DESCRIPTION> DESCRIPTION = new ArrayList<be.ugent.tiwi.domein.hereIncident.DESCRIPTION>();
    @SerializedName("ID")
    @Expose
    private Integer ID;

    /**
     * 
     * @return
     *     The DESCRIPTION
     */
    public List<be.ugent.tiwi.domein.hereIncident.DESCRIPTION> getDESCRIPTION() {
        return DESCRIPTION;
    }

    /**
     * 
     * @param DESCRIPTION
     *     The DESCRIPTION
     */
    public void setDESCRIPTION(List<be.ugent.tiwi.domein.hereIncident.DESCRIPTION> DESCRIPTION) {
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
