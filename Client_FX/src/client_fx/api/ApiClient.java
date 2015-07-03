/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx.api;

import client_fx.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import sun.net.www.protocol.http.HttpURLConnection;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Roy
 */
public class ApiClient {

    private static ApiClient instance = null;
    private static String host = "http://145.24.222.156:8000/";
    private String token;

    public static ApiClient getApiClient() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public LoginResponse authorize(String rekeningnummer, String pincode) {
        try {
            String query = String.format("cardId=%s&pin=%s", URLEncoder.encode(rekeningnummer, "UTF-8"), URLEncoder.encode(pincode, "UTF-8"));
            HttpURLConnection connection;
            connection = new HttpURLConnection(new URL(String.format("%slogin", host)), Proxy.NO_PROXY);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);
            String bank = rekeningnummer.substring(0,4);
            if(!bank.equals("PROH")) {
                connection.setRequestProperty("bank", bank);
            }
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.getOutputStream().write(query.getBytes());
            int responsecode = connection.getResponseCode();
            if (responsecode == 200) {
                String response = readInputStream(connection.getInputStream());
                JSONObject responseObject = new JSONObject(response);
                JSONObject successObject = responseObject.getJSONObject("success");
                JSONObject errorObject = responseObject.getJSONObject("error");
                ErrorLogin err = null;
                Success success = null;
                if (successObject.length() > 0) {
                    success = new Success();
                    success.setToken(successObject.getString("token"));
                }
                if (errorObject.length() > 0) {
                    err = new ErrorLogin();
                    if(errorObject.has("code"))
                        err.setCode(errorObject.getInt("code"));
                    if (errorObject.has("failedAttempts")) {
                        err.setFailedAttempts(errorObject.getInt("failedAttempts"));
                    }
                    if(errorObject.has("message"))
                        err.setMessage(errorObject.getString("message"));
                }
                LoginResponse loginResponse = new LoginResponse(err, success);
                if(success != null) {
                    token = success.getToken();
                }
                return loginResponse;
            }
            System.out.println(responsecode);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public long getBalance() throws Exception {
        {
            HttpURLConnection connection;
            try {
                connection = new HttpURLConnection(new URL(String.format("%sbalance", host)), Proxy.NO_PROXY);
                connection.setRequestMethod("POST");
                connection.setUseCaches(true);
                connection.setDoInput(true);
                connection.setRequestProperty("token", token);
 
                int responsecode = connection.getResponseCode();
                if (responsecode == 200) {
                    String response = readInputStream(connection.getInputStream());
                    JSONObject responseObject = new JSONObject(response);
                    JSONObject successObject = responseObject.getJSONObject("success");
                    JSONObject errorObject = responseObject.getJSONObject("error");
                    Error err = null;
                    SuccessSaldo success = null;
                    if (successObject.length() > 0) {
                        success = new SuccessSaldo();
                        success.setSaldo(successObject.getLong("saldo"));
                    }
                    if (errorObject.length() > 0) {
                        err = new Error();
                        err.setCode(errorObject.getInt("code"));
                        err.setMessage(errorObject.getString("message"));
                    }
                    SaldoResponse saldoResponse = new SaldoResponse(success,err);
                    return saldoResponse.getSuccess().getSaldo();
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        throw new Exception("Problem communicating with the server...");
    }

    public long getMaximumWithdraw(String rekeningnummer) throws Exception {
        {
            HttpURLConnection connection;
            try {
                connection = new HttpURLConnection(new URL(String.format("%smaximum_withdraw/%s", host, rekeningnummer)), Proxy.NO_PROXY);
                if (connection.getResponseCode() == 200) {
                    String response = readInputStream(connection.getInputStream());
                    return Long.parseLong(response);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        throw new Exception("Problem communicating with the server...");
    }

    public WithdrawResponse withdraw(long amount) throws IOException, Exception {
        try {
            String query = String.format("amount=%d", amount);
            HttpURLConnection connection;
            connection = new HttpURLConnection(new URL(String.format("%swithdraw", host)), Proxy.NO_PROXY);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);
            String bank = Transaction.getCurrentTransaction().getAccountID().substring(0,4);
            if(!bank.equals("PROH")) {
                connection.setRequestProperty("bank", bank);
            }            
            connection.setRequestProperty("token", token);
       
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.getOutputStream().write(query.getBytes());
            
            int responsecode = connection.getResponseCode();

            if (responsecode == 200) {
                String response = readInputStream(connection.getInputStream());
                JSONObject responseObject = new JSONObject(response);
                JSONObject successObject = responseObject.getJSONObject("success");
                JSONObject errorObject = responseObject.getJSONObject("error");
                Error err = null;
                SuccessWithdraw success = null;
                if (successObject.length() > 0) {
                    success = new SuccessWithdraw();
                    success.setCode(successObject.getInt("code"));
                }
                if (errorObject.length() > 0) {
                    err = new ErrorLogin();
                    if(errorObject.has("code"))
                        err.setCode(errorObject.getInt("code"));
                    
                    if(errorObject.has("message"))
                        err.setMessage(errorObject.getString("message"));
                }
                WithdrawResponse withdrawResponse = new WithdrawResponse();
                withdrawResponse.setError(err);
                withdrawResponse.setSuccessWithdraw(success);
                return withdrawResponse;
                    
                
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean logout() throws Exception {
        try {
            HttpURLConnection connection;
            connection = new HttpURLConnection(new URL(String.format("%swithdraw", host)), Proxy.NO_PROXY);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);
            String bank = Transaction.getCurrentTransaction().getAccountID().substring(0,4);
            if(!bank.equals("PROH")) {
                connection.setRequestProperty("bank", bank);
            }       
            connection.setRequestProperty("token", token);
            connection.setUseCaches(false);
            connection.setDoInput(true);
           
            int responsecode = connection.getResponseCode();

            return (responsecode == 200) ;
                
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private String readInputStream(InputStream is) throws IOException {
        String response = "";
        byte[] buffer = new byte[1024];
        while (is.available() > 0) {
            int read = is.read(buffer);
            for (int i = 0; i < read; i++) {
                response += (char) buffer[i];
            }
        }
        return response;
    }
}
