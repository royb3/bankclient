/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx.api;

/**
 *
 * @author joey
 */
public class SaldoResponse {

    private Error error;
    private SuccessSaldo success;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
    public SaldoResponse() {
        super();
    }

    public SuccessSaldo getSuccess() {
        return success;
    }

    public void setSuccess(SuccessSaldo success) {
        this.success = success;
    }

    public SaldoResponse( SuccessSaldo success,  Error error) {
        super();
        this.success = success;
        this.error = error;
    }

}
