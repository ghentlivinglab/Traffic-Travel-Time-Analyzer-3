
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class POINT {

    @SerializedName("DESCRIPTION")
    @Expose
    private List<DESCRIPTION_> DESCRIPTION = new ArrayList<DESCRIPTION_>();
    @SerializedName("ID")
    @Expose
    private Long ID;

    /**
     * 
     * @return
     *     The DESCRIPTION
     */
    public List<DESCRIPTION_> getDESCRIPTION() {
        return DESCRIPTION;
    }

    /**
     * 
     * @param DESCRIPTION
     *     The DESCRIPTION
     */
    public void setDESCRIPTION(List<DESCRIPTION_> DESCRIPTION) {
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
