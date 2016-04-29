
package be.ugent.tiwi.domein.hereIncident;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TRAFFICITEMDESCRIPTION {

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("TYPE")
    @Expose
    private String TYPE;

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The TYPE
     */
    public String getTYPE() {
        return TYPE;
    }

    /**
     * 
     * @param TYPE
     *     The TYPE
     */
    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

}
