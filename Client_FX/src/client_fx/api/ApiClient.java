/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 *
 * @author Roy
 */
public class ApiClient {

    private static ApiClient instance = null;
    private static String host = "http://localhost/";

    public static ApiClient getApiClient() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public Boolean authorize(String rekeningnummer, String pincode) {
        try {
            HttpURLConnection connection;
            connection = new HttpURLConnection(new URL(String.format("%slogin", host)), Proxy.NO_PROXY);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            LoginRequest request = new LoginRequest();
            request.setPin(pincode);
            request.setCardId(rekeningnummer);

            JSONObject object = new JSONObject(request);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(object.toString());
            writer.close();
            int responsecode = connection.getResponseCode();
            if (responsecode == 200) {
                InputStream is = connection.getInputStream();
                String response = "";
                byte[] buffer = new byte[1024];
                while (is.available() > 0) {
                    int read = is.read(buffer);
                    for (int i = 0; i < read; i++) {
                        response += (char) buffer[i];
                    }
                }

                LoginResponse responseObject = new ObjectMapper().readValue(response, LoginResponse.class);
                return (responseObject.getSuccess() != null);
            }
            System.out.println(responsecode);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

    public long getBalance(String rekeningnummer) throws Exception {
        {
            HttpURLConnection connection;
            try {
                connection = new HttpURLConnection(new URL(String.format("%sbalance/%s", host, rekeningnummer)), Proxy.NO_PROXY);
                if (connection.getResponseCode() == 200) {
                    InputStream is = connection.getInputStream();
                    String response = "";
                    byte[] buffer = new byte[1024];
                    while (is.available() > 0) {
                        int read = is.read(buffer);
                        for (int i = 0; i < read; i++) {
                            response += (char) buffer[i];
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

    public long getMaximumWithdraw(String rekeningnummer) throws Exception {
        {
            HttpURLConnection connection;
            try {
                connection = new HttpURLConnection(new URL(String.format("%smaximum_withdraw/%s", host, rekeningnummer)), Proxy.NO_PROXY);
                if (connection.getResponseCode() == 200) {
                    InputStream is = connection.getInputStream();
                    String response = "";
                    byte[] buffer = new byte[1024];
                    while (is.available() > 0) {
                        int read = is.read(buffer);
                        for (int i = 0; i < read; i++) {
                            response += (char) buffer[i];
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

    public boolean withdraw(String token, long amount) throws IOException {
        try {
            HttpURLConnection connection;
            connection = new HttpURLConnection(new URL(String.format("%swithdraw", host)), Proxy.NO_PROXY);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            WithdrawRequest request = new WithdrawRequest();
            request.setToken(token);
            request.setAmount(amount);

            JSONObject object = new JSONObject(request);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(object.toString());
            writer.close();
            int responsecode = connection.getResponseCode();

            if (responsecode == 200) {
                InputStream is = connection.getInputStream();
                String response = "";
                byte[] buffer = new byte[1024];
                while (is.available() > 0) {
                    int read = is.read(buffer);
                    for (int i = 0; i < read; i++) {
                        response += (char) buffer[i];
                    }
                }
                WithdrawResponse responseObject = new ObjectMapper().readValue(response, WithdrawResponse.class);
                return (responseObject.getSuccess() != null);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean logout(String token){
        try {
            HttpURLConnection connection;
            connection = new HttpURLConnection(new URL(String.format("%swithdraw", host)), Proxy.NO_PROXY);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            LogoutRequest request = new LogoutRequest();
            request.setToken(token);

            JSONObject object = new JSONObject(request);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(object.toString());
            writer.close();
            int responsecode = connection.getResponseCode();

            if (responsecode == 200) {
                InputStream is = connection.getInputStream();
                String response = "";
                byte[] buffer = new byte[1024];
                while (is.available() > 0) {
                    int read = is.read(buffer);
                    for (int i = 0; i < read; i++) {
                        response += (char) buffer[i];
                    }
                }
                WithdrawResponse responseObject = new ObjectMapper().readValue(response, WithdrawResponse.class);
                return responseObject.getSuccess()!=null;
            }
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
