
package be.ugent.tiwi.domein.tomtom;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "summary",
        "points"
})
public class Leg {

    @JsonProperty("summary")
    private Summary_ summary;
    @JsonProperty("points")
    private List<Point> points = new ArrayList<Point>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The summary
     */
    @JsonProperty("summary")
    public Summary_ getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    @JsonProperty("summary")
    public void setSummary(Summary_ summary) {
        this.summary = summary;
    }

    /**
     * @return The points
     */
    @JsonProperty("points")
    public List<Point> getPoints() {
        return points;
    }

    /**
     * @param points The points
     */
    @JsonProperty("points")
    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
