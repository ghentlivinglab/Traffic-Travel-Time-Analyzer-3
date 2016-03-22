
package be.ugent.tiwi.domein.tomtom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class TomTom {

    @SerializedName("formatVersion")
    @Expose
    private String formatVersion;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("privacy")
    @Expose
    private String privacy;
    @SerializedName("routes")
    @Expose
    private List<Route> routes = new ArrayList<Route>();

    /**
     * 
     * @return
     *     The formatVersion
     */
    public String getFormatVersion() {
        return formatVersion;
    }

    /**
     * 
     * @param formatVersion
     *     The formatVersion
     */
    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    /**
     * 
     * @return
     *     The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * 
     * @param copyright
     *     The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * 
     * @return
     *     The privacy
     */
    public String getPrivacy() {
        return privacy;
    }

    /**
     * 
     * @param privacy
     *     The privacy
     */
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    /**
     * 
     * @return
     *     The routes
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * 
     * @param routes
     *     The routes
     */
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
