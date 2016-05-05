
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RDSTMCLOCATIONS {

    @SerializedName("RDSTMC")
    @Expose
    private List<be.ugent.tiwi.domein.hereIncident.RDSTMC> RDSTMC = new ArrayList<be.ugent.tiwi.domein.hereIncident.RDSTMC>();

    /**
     * 
     * @return
     *     The RDSTMC
     */
    public List<be.ugent.tiwi.domein.hereIncident.RDSTMC> getRDSTMC() {
        return RDSTMC;
    }

    /**
     * 
     * @param RDSTMC
     *     The RDSTMC
     */
    public void setRDSTMC(List<be.ugent.tiwi.domein.hereIncident.RDSTMC> RDSTMC) {
        this.RDSTMC = RDSTMC;
    }

}
