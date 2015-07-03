/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx.api;

import org.json.JSONObject;

/**
 *
 * @author roy
 */
public class WithdrawResponse {
    private Error error;
    private SuccessWithdraw success;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public SuccessWithdraw getSuccessWithdraw() {
        return success;
    }

    public void setSuccessWithdraw(SuccessWithdraw success) {
        this.success = success;
    }

    public WithdrawResponse() {
        super();
    }


    
}
