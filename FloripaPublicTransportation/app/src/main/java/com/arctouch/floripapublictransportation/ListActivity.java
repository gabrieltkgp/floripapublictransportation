package com.arctouch.floripapublictransportation;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.classes.ItemListViewResult;
import com.arctouch.floripapublictransportation.classes.ListViewResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ListViewResultAdapter listViewResultAdapter;
    private ArrayList<ItemListViewResult> items;

    private String user = "WKD4N7YMA1uiM8V";
    private String password = "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68";
    private String urlRest = "https://api.appglu.com/v1/queries/findRoutesByStopName/run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listViewResult);

        listView.setOnItemClickListener(this);

        //createListView();
    }

    private void createListView() {
        items = new ArrayList<ItemListViewResult>();
        ItemListViewResult item1 = new ItemListViewResult("First line");
        ItemListViewResult item2 = new ItemListViewResult("Second line");
        ItemListViewResult item3 = new ItemListViewResult("Third line");

        items.add(item1);
        items.add(item2);
        items.add(item3);

        listViewResultAdapter = new ListViewResultAdapter(this, items);

        listView.setAdapter(listViewResultAdapter);

        listView.setCacheColorHint(Color.TRANSPARENT);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemListViewResult item = (ItemListViewResult) listViewResultAdapter.getItem(position);
        Toast.makeText(this, "Click: " + item.getText(), Toast.LENGTH_LONG).show();
    }

    public void onButtonClick(View v){
        Toast.makeText(this, "Calling rest ", Toast.LENGTH_LONG).show();

        //FindRouteRest findRouteRest = new FindRouteRest();

        //executePost();
        executeTestNetwork();

        //Toast.makeText(this, jsonReturn, Toast.LENGTH_LONG).show();
    }

    public void executeTestNetwork(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Toast.makeText(this, "true", Toast.LENGTH_LONG).show();
            new FindRoutNetworkRest().execute(urlRest);
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }

    private class FindRoutNetworkRest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String res) {
            //videos = new ArrayList<Video>();

            try {
                JSONObject json = new JSONObject(res);
                JSONArray rows = json.getJSONArray("rows");

                items = new ArrayList<ItemListViewResult>();

                for (int i = 0; i < rows.length(); i++) {
                    JSONObject obj = rows.getJSONObject(i);

                    ItemListViewResult item = new ItemListViewResult(obj.getString("longName"));

                    items.add(item);

                }

                listViewResultAdapter = new ListViewResultAdapter(ListActivity.this, items);

                listView.setAdapter(listViewResultAdapter);

                listView.setCacheColorHint(Color.TRANSPARENT);

            } catch (JSONException e) {
                // handle exception
            }
        }

        private String getJson(){
            return "{ \"params\": { \"stopName\": \"%lauro linhares%\" } }";

        }

        private String getAuthorization(){
            String usernamePassword = user + ":" + password;
            String basicAuth = "Basic " + Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);
            //String basicAuth = "Basic " + usernamePassword;

            return basicAuth;
        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            URL url;
            HttpURLConnection connection = null;

            try {
                url = new URL(myurl);

                String param = getJson();

                connection = (HttpURLConnection)url.openConnection();

                connection.setRequestMethod("POST");
//            connection.setFixedLengthStreamingMode(param.getBytes().length);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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

                Log.i("json",  response.toString());

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
}
