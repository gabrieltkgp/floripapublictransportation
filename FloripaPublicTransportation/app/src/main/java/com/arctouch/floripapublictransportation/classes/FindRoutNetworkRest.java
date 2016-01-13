package com.arctouch.floripapublictransportation.classes;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 12/01/2016.
 */
public class FindRoutNetworkRest extends AsyncTask<String, Void, String> {
    private Context context;
    private String user;
    private String password;

    private ListView listView;
    private ListViewResultAdapter listViewResultAdapter;
    private ArrayList<ItemListViewResult> items;

    private String address;

    public FindRoutNetworkRest(Context context, String user, String password, ListView listView, BaseAdapter listViewResultAdapter, String address) {
        this.context = context;
        this.user = user;
        this.password = password;
        this.listView = listView;
        this.listViewResultAdapter = (ListViewResultAdapter)listViewResultAdapter;
        this.address = address;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }
    @Override
    protected void onPostExecute(String res) {
        try {
            JSONObject json = new JSONObject(res);
            JSONArray rows = json.getJSONArray("rows");

            items = new ArrayList<ItemListViewResult>();

            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);

                ItemListViewResult item = new ItemListViewResult(obj.getInt("id"), obj.getString("longName"));

                items.add(item);
            }

            listViewResultAdapter = new ListViewResultAdapter(this.context, items);

            listView.setAdapter(this.listViewResultAdapter);

            listView.setCacheColorHint(Color.TRANSPARENT);

        } catch (JSONException e) {
            Toast.makeText(this.context, "Error to parse json.", Toast.LENGTH_LONG).show();
        }
    }

    private String getJson(){
        return "{ \"params\": { \"stopName\": \"%" + this.address+ "%\" } }";
    }

    private String getAuthorization(){
        String usernamePassword = this.user + ":" + this.password;
        return "Basic " + Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);
    }

    private String downloadUrl(String myUrl) throws IOException {
        InputStream is = null;
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(myUrl);

            String param = getJson();

            connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setRequestProperty("Authorization", getAuthorization());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-AppGlu-Environment", "staging");

            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream (connection.getOutputStream());

            wr.writeBytes (param);
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
}