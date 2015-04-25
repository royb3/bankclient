/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.www.protocol.http.HttpURLConnection;


/**
 *
 * @author Roy
 */
public class ApiClient {
    private static ApiClient instance = null;
    private static String host = "http://localhost:8080/";
    
    public static ApiClient getApiClient(){
        if(instance == null)
            instance = new ApiClient();
        return instance;
    }
    
    public Boolean authorize(String rekeningnummer, String passnummer, String pincode){
        try{
            HttpURLConnection connection;
            connection = new HttpURLConnection(new URL(String.format("%sauth/%s/%s/%s", host, rekeningnummer, passnummer, pincode)),Proxy.NO_PROXY);
            return (connection.getResponseCode() == 200);
        }catch(MalformedURLException e){
            e.printStackTrace();
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public long getBalance(String rekeningnummer) throws Exception{
        {
            HttpURLConnection connection;
            try {
                connection = new HttpURLConnection(new URL(String.format("%sbalance/%s", host, rekeningnummer)), Proxy.NO_PROXY);
                if(connection.getResponseCode() == 200){
                    InputStream is = connection.getInputStream();
                    String response = "";
                    byte[] buffer = new byte[1024];
                    while(is.available() > 0)
                    {
                        int read = is.read(buffer);
                        for(int i = 0; i < read; i++){
                            response += (char)buffer[i];
                        }
                    }
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
    public long getMaximumWithdraw(String rekeningnummer) throws Exception{
        {
            HttpURLConnection connection;
            try {
                connection = new HttpURLConnection(new URL(String.format("%smaximum_withdraw/%s", host, rekeningnummer)), Proxy.NO_PROXY);
                if(connection.getResponseCode() == 200){
                    InputStream is = connection.getInputStream();
                    String response = "";
                    byte[] buffer = new byte[1024];
                    while(is.available() > 0)
                    {
                        int read = is.read(buffer);
                        for(int i = 0; i < read; i++){
                            response += (char)buffer[i];
                        }
                    }
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
}
