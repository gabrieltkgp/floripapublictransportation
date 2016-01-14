package com.arctouch.floripapublictransportation.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class RestConnection extends AsyncTask<String, Void, String>{
    private Context context;
    private String user;
    private String password;
    private String textSearch;

    //constructor
    public RestConnection(Context context, String user, String password, String textSearch) {
        this.context = context;
        this.user = user;
        this.password = password;
        this.textSearch = textSearch;
    }

    //private methods
    private String getAuthorization(){
        String usernamePassword = this.user + ":" + this.password;
        return "Basic " + Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);
    }

    private String downloadContent(String myUrl) throws IOException {
        InputStream is = null;
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(myUrl);

            connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setRequestProperty("Authorization", getAuthorization());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-AppGlu-Environment", "staging");

            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream (connection.getOutputStream());

            wr.writeBytes (getJsonParams());
            wr.flush();
            wr.close();

            is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return response.toString();

        } finally {
            if (is != null) {
                is.close();
            }

            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    //protected methods
    @Override
    protected String doInBackground(String... url) {
        try {
            return downloadContent(url[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    @Override
    protected void onPostExecute(String res) {
        ArrayList items = parseJson(res);
        executeAfterParseJson(items);
    }

    //this method has to be inherited on child classes.
    protected String getJsonParams(){
        return "";
    }

    //this method has to be inherited on child classes.
    protected ArrayList parseJson(String jsonResult){
        return null;
    }

    //this method has to be inherited on child classes.
    protected void executeAfterParseJson(ArrayList items) {
        return;
    }

    //public methods
    public Context getContext() {
        return context;
    }

    public String getTextSearch() {
        return textSearch;
    }
}
