
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TRAFFICITEMS {

    @SerializedName("TRAFFICITEM")
    @Expose
    private List<be.ugent.tiwi.domein.hereIncident.TRAFFICITEM> TRAFFICITEM = new ArrayList<be.ugent.tiwi.domein.hereIncident.TRAFFICITEM>();

    /**
     * 
     * @return
     *     The TRAFFICITEM
     */
    public List<be.ugent.tiwi.domein.hereIncident.TRAFFICITEM> getTRAFFICITEM() {
        return TRAFFICITEM;
    }

    /**
     * 
     * @param TRAFFICITEM
     *     The TRAFFICITEM
     */
    public void setTRAFFICITEM(List<be.ugent.tiwi.domein.hereIncident.TRAFFICITEM> TRAFFICITEM) {
        this.TRAFFICITEM = TRAFFICITEM;
    }

}
