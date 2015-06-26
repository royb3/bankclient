package client_fx.api;

import java.io.Serializable;


/**
 *
 * @author Boris
 */
public class ErrorLogin extends Error implements Serializable{

    int failedAttempts;

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

}
