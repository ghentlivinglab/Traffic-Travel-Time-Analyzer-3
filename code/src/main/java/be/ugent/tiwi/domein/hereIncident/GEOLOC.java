
package be.ugent.tiwi.domein.hereIncident;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GEOLOC {

    @SerializedName("ORIGIN")
    @Expose
    private ORIGIN__ ORIGIN;
    @SerializedName("TO")
    @Expose
    private List<TO_> TO = new ArrayList<TO_>();

    /**
     * 
     * @return
     *     The ORIGIN
     */
    public ORIGIN__ getORIGIN() {
        return ORIGIN;
    }

    /**
     * 
     * @param ORIGIN
     *     The ORIGIN
     */
    public void setORIGIN(ORIGIN__ ORIGIN) {
        this.ORIGIN = ORIGIN;
    }

    /**
     * 
     * @return
     *     The TO
     */
    public List<TO_> getTO() {
        return TO;
    }

    /**
     * 
     * @param TO
     *     The TO
     */
    public void setTO(List<TO_> TO) {
        this.TO = TO;
    }

}
