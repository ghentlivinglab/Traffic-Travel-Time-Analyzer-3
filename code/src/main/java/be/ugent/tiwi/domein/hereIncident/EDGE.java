
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class EDGE {

    @SerializedName("EDGEID")
    @Expose
    private List<String> EDGEID = new ArrayList<String>();

    /**
     * 
     * @return
     *     The EDGEID
     */
    public List<String> getEDGEID() {
        return EDGEID;
    }

    /**
     * 
     * @param EDGEID
     *     The EDGEID
     */
    public void setEDGEID(List<String> EDGEID) {
        this.EDGEID = EDGEID;
    }

}
