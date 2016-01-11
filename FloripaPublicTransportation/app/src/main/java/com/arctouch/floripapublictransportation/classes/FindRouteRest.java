package com.arctouch.floripapublictransportation.classes;

import android.os.AsyncTask;
import android.util.Base64;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class FindRouteRest{

    private String user = "WKD4N7YMA1uiM8V";
    private String password = "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68";
    private String header =  "X-AppGlu-Environment: staging";
    private String urlRest = "https://api.appglu.com/v1/queries/findRoutesByStopName/run";

    public String executePost()
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(urlRest);

            String param="stopName=" + URLEncoder.encode("%lauro linhares%","UTF-8");

            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setFixedLengthStreamingMode(param.getBytes().length);
            connection.setRequestProperty("Content-Type", header);
            connection.setRequestProperty("Authorization", getAuthorization());

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.close();

            String response= "";
            Scanner inStream = new Scanner(connection.getInputStream());

            while(inStream.hasNextLine())
                response+=(inStream.nextLine());

            return response;

            // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            //connection.setRequestProperty("Content-Language", "en-US");

            //connection.setUseCaches(false);
            //connection.setDoInput(true);


            //Send request
//            DataOutputStream wr = new DataOutputStream (
//                    connection.getOutputStream ());
//            wr.writeBytes (urlParameters);
//            wr.flush ();
//            wr.close ();
//
//            //Get Response
//            InputStream is = connection.getInputStream();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//            String line;
//            StringBuffer response = new StringBuffer();
//            while((line = rd.readLine()) != null) {
//                response.append(line);
//                response.append('\r');
//            }
//            rd.close();
//            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

//    private String getBody(){
//        return "{ 'params': { 'stopName': '%lauro linhares%'} }";
//    }

    private String getAuthorization(){
        String usernamePassword = user + ":" + password;
        String basicAuth = "Basic " + Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);

        return basicAuth;
    }
}
