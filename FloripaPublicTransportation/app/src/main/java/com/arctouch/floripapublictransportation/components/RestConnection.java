package com.arctouch.floripapublictransportation.components;

import android.os.AsyncTask;
import android.util.Base64;

import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

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
public class RestConnection extends AsyncTask<String, Void, String> {
    private String user;
    private String password;
    private String query;
    private AsyncResponse delegate;

    //constructor
    public RestConnection(AsyncResponse delegate, String user, String password, String query) {
        this.delegate = delegate;
        this.user = user;
        this.password = password;
        this.query = query;
    }

    //private methods
    private String formatAuthorization() {
        String usernamePassword = this.user + ":" + this.password;
        return "Basic " + Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);
    }

    private String receiveConnectionContent(HttpURLConnection connection) throws IOException {
        InputStream is = null;
        StringBuffer response = new StringBuffer();
        try {
            is = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

        } finally {
            if (is != null) {
                is.close();
            }
        }

        return response.toString();
    }

    private void sendConnectionParams(HttpURLConnection connection) throws IOException {
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(getJsonParams());
        wr.flush();
        wr.close();
    }

    private HttpURLConnection createConnection(String myUrl) throws IOException{
        URL url = new URL(myUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setRequestProperty("Authorization", formatAuthorization());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-AppGlu-Environment", "staging");

        connection.setDoOutput(true);

        return connection;
    }

    private String executeRequest(String myUrl) throws IOException {
        HttpURLConnection connection = null;

        try {
            connection = createConnection(myUrl);

            sendConnectionParams(connection);

            return receiveConnectionContent(connection);

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    //protected methods
    @Override
    protected String doInBackground(String... url) {
        try {
            return executeRequest(url[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    @Override
    protected void onPostExecute(String res) {
        ArrayList items = parseJson(res);

        processFinish(items);
    }

    //this method has to be inherited on child classes.
    protected String getJsonParams() {
        return "";
    }

    //this method has to be inherited on child classes.
    protected ArrayList parseJson(String jsonResult) {
        return null;
    }

    //this method has to be inherited on child classes.
    protected void processFinish(ArrayList items){
        return;
    }

    //public methods
    public String getQuery() {
        return this.query;
    }

    public AsyncResponse getDelegate() {
        return this.delegate;
    }
}
