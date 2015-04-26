/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import org.json.JSONObject;

/**
 *
 * @author roy
 */
public class WithdrawResponse {
    private String response;
    private long transactionNumber;

    public WithdrawResponse(JSONObject withdrawObject){
        this.response = withdrawObject.getString("response");
        this.transactionNumber = withdrawObject.getLong("transactionNumber");
    }
    
    public String getResponse() {
        return response;
    }

    public long getTransactionNumber() {
        return transactionNumber;
    }


    
}
