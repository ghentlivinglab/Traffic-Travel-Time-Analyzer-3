package be.ugent.tiwi.domein.bing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Detail {

    @SerializedName("compassDegrees")
    @Expose
    private Integer compassDegrees;
    @SerializedName("endPathIndices")
    @Expose
    private List<Integer> endPathIndices = new ArrayList<Integer>();
    @SerializedName("locationCodes")
    @Expose
    private List<String> locationCodes = new ArrayList<String>();
    @SerializedName("maneuverType")
    @Expose
    private String maneuverType;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("names")
    @Expose
    private List<String> names = new ArrayList<String>();
    @SerializedName("roadType")
    @Expose
    private String roadType;
    @SerializedName("startPathIndices")
    @Expose
    private List<Integer> startPathIndices = new ArrayList<Integer>();

    /**
     * @return The compassDegrees
     */
    public Integer getCompassDegrees() {
        return compassDegrees;
    }

    /**
     * @param compassDegrees The compassDegrees
     */
    public void setCompassDegrees(Integer compassDegrees) {
        this.compassDegrees = compassDegrees;
    }

    /**
     * @return The endPathIndices
     */
    public List<Integer> getEndPathIndices() {
        return endPathIndices;
    }

    /**
     * @param endPathIndices The endPathIndices
     */
    public void setEndPathIndices(List<Integer> endPathIndices) {
        this.endPathIndices = endPathIndices;
    }

    /**
     * @return The locationCodes
     */
    public List<String> getLocationCodes() {
        return locationCodes;
    }

    /**
     * @param locationCodes The locationCodes
     */
    public void setLocationCodes(List<String> locationCodes) {
        this.locationCodes = locationCodes;
    }

    /**
     * @return The maneuverType
     */
    public String getManeuverType() {
        return maneuverType;
    }

    /**
     * @param maneuverType The maneuverType
     */
    public void setManeuverType(String maneuverType) {
        this.maneuverType = maneuverType;
    }

    /**
     * @return The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return The names
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * @param names The names
     */
    public void setNames(List<String> names) {
        this.names = names;
    }

    /**
     * @return The roadType
     */
    public String getRoadType() {
        return roadType;
    }

    /**
     * @param roadType The roadType
     */
    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    /**
     * @return The startPathIndices
     */
    public List<Integer> getStartPathIndices() {
        return startPathIndices;
    }

    /**
     * @param startPathIndices The startPathIndices
     */
    public void setStartPathIndices(List<Integer> startPathIndices) {
        this.startPathIndices = startPathIndices;
    }

}
