/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 *
 * @author Boris_000
 */
public class DispenzorApiClient {
    public static String hostname = "http://10.0.1.1:8000/";
    
    public static boolean shootMoney(String slot) throws IOException{
        HttpURLConnection connection = new HttpURLConnection(new URL(hostname + slot), Proxy.NO_PROXY);
        return (connection.getResponseCode() == 200);
        
    }
}
