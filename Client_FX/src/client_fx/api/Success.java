package client_fx.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;



/**
 *
 * @author Boris
 */
public class Success implements Serializable{
    @JsonDeserialize
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
