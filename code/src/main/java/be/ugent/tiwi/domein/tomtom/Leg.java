
package be.ugent.tiwi.domein.tomtom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Leg {

    @SerializedName("summary")
    @Expose
    private Summary_ summary;
    @SerializedName("points")
    @Expose
    private List<Point> points = new ArrayList<Point>();

    /**
     * 
     * @return
     *     The summary
     */
    public Summary_ getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(Summary_ summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The points
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * 
     * @param points
     *     The points
     */
    public void setPoints(List<Point> points) {
        this.points = points;
    }

}
