package com.arctouch.floripapublictransportation.classes;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class FindRouteRest {

    private String user = "WKD4N7YMA1uiM8V";
    private String password = "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68";
    private String header =  "X-AppGlu-Environment: staging";

    public String excutePost(String targetURL, String urlParameters)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", header);
            String usernamePassword = user + ":" + password;
            String basicAuth = "Basic " + Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);
            connection.setRequestProperty("Authorization", basicAuth);
            // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            //connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
