/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx.api;

import com.fasterxml.jackson.databind.deser.Deserializers;
import java.io.Serializable;


/**
 *
 * @author roy
 */
public class LoginResponse implements Serializable{

    private Error error;
    private Success success;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public LoginResponse(Error error, Success success) {
        this.error = error;
        this.success = success;
    }

}
