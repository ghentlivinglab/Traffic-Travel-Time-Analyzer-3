
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
        "formatVersion",
        "copyright",
        "privacy",
        "routes"
})
public class TomTom {

    @JsonProperty("formatVersion")
    private String formatVersion;
    @JsonProperty("copyright")
    private String copyright;
    @JsonProperty("privacy")
    private String privacy;
    @JsonProperty("routes")
    private List<Route> routes = new ArrayList<Route>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The formatVersion
     */
    @JsonProperty("formatVersion")
    public String getFormatVersion() {
        return formatVersion;
    }

    /**
     * @param formatVersion The formatVersion
     */
    @JsonProperty("formatVersion")
    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    /**
     * @return The copyright
     */
    @JsonProperty("copyright")
    public String getCopyright() {
        return copyright;
    }

    /**
     * @param copyright The copyright
     */
    @JsonProperty("copyright")
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * @return The privacy
     */
    @JsonProperty("privacy")
    public String getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy The privacy
     */
    @JsonProperty("privacy")
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    /**
     * @return The routes
     */
    @JsonProperty("routes")
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * @param routes The routes
     */
    @JsonProperty("routes")
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
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
