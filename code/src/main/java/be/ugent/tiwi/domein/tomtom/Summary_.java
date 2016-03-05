
package be.ugent.tiwi.domein.tomtom;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "lengthInMeters",
    "travelTimeInSeconds",
    "trafficDelayInSeconds",
    "departureTime",
    "arrivalTime"
})
public class Summary_ {

    @JsonProperty("lengthInMeters")
    private Integer lengthInMeters;
    @JsonProperty("travelTimeInSeconds")
    private Integer travelTimeInSeconds;
    @JsonProperty("trafficDelayInSeconds")
    private Integer trafficDelayInSeconds;
    @JsonProperty("departureTime")
    private String departureTime;
    @JsonProperty("arrivalTime")
    private String arrivalTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The lengthInMeters
     */
    @JsonProperty("lengthInMeters")
    public Integer getLengthInMeters() {
        return lengthInMeters;
    }

    /**
     * 
     * @param lengthInMeters
     *     The lengthInMeters
     */
    @JsonProperty("lengthInMeters")
    public void setLengthInMeters(Integer lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    /**
     * 
     * @return
     *     The travelTimeInSeconds
     */
    @JsonProperty("travelTimeInSeconds")
    public Integer getTravelTimeInSeconds() {
        return travelTimeInSeconds;
    }

    /**
     * 
     * @param travelTimeInSeconds
     *     The travelTimeInSeconds
     */
    @JsonProperty("travelTimeInSeconds")
    public void setTravelTimeInSeconds(Integer travelTimeInSeconds) {
        this.travelTimeInSeconds = travelTimeInSeconds;
    }

    /**
     * 
     * @return
     *     The trafficDelayInSeconds
     */
    @JsonProperty("trafficDelayInSeconds")
    public Integer getTrafficDelayInSeconds() {
        return trafficDelayInSeconds;
    }

    /**
     * 
     * @param trafficDelayInSeconds
     *     The trafficDelayInSeconds
     */
    @JsonProperty("trafficDelayInSeconds")
    public void setTrafficDelayInSeconds(Integer trafficDelayInSeconds) {
        this.trafficDelayInSeconds = trafficDelayInSeconds;
    }

    /**
     * 
     * @return
     *     The departureTime
     */
    @JsonProperty("departureTime")
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * 
     * @param departureTime
     *     The departureTime
     */
    @JsonProperty("departureTime")
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 
     * @return
     *     The arrivalTime
     */
    @JsonProperty("arrivalTime")
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * 
     * @param arrivalTime
     *     The arrivalTime
     */
    @JsonProperty("arrivalTime")
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
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
