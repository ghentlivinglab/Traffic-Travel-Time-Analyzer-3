
package be.ugent.tiwi.domein.tomtom;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "startPointIndex",
        "endPointIndex",
        "travelMode"
})
public class Section {

    @JsonProperty("startPointIndex")
    private Integer startPointIndex;
    @JsonProperty("endPointIndex")
    private Integer endPointIndex;
    @JsonProperty("travelMode")
    private String travelMode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The startPointIndex
     */
    @JsonProperty("startPointIndex")
    public Integer getStartPointIndex() {
        return startPointIndex;
    }

    /**
     * @param startPointIndex The startPointIndex
     */
    @JsonProperty("startPointIndex")
    public void setStartPointIndex(Integer startPointIndex) {
        this.startPointIndex = startPointIndex;
    }

    /**
     * @return The endPointIndex
     */
    @JsonProperty("endPointIndex")
    public Integer getEndPointIndex() {
        return endPointIndex;
    }

    /**
     * @param endPointIndex The endPointIndex
     */
    @JsonProperty("endPointIndex")
    public void setEndPointIndex(Integer endPointIndex) {
        this.endPointIndex = endPointIndex;
    }

    /**
     * @return The travelMode
     */
    @JsonProperty("travelMode")
    public String getTravelMode() {
        return travelMode;
    }

    /**
     * @param travelMode The travelMode
     */
    @JsonProperty("travelMode")
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
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
