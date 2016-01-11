package com.arctouch.floripapublictransportation;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ListViewResultAdapter listViewResultAdapter;
    private ArrayList<ItemListViewResult> items;
    private EditText editText;

    private String user = "WKD4N7YMA1uiM8V";
    private String password = "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68";
    private String urlRest = "https://api.appglu.com/v1/queries/findRoutesByStopName/run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initializeComponents();

        //createListView();
    }

    private void initializeComponents(){
        listView = (ListView) findViewById(R.id.listViewResult);
        listView.setOnItemClickListener(this);
        listView.setCacheColorHint(Color.TRANSPARENT);

        editText = (EditText) findViewById(R.id.editTextSearch);
    }

//    private void createListView() {
//        items = new ArrayList<ItemListViewResult>();
//        ItemListViewResult item1 = new ItemListViewResult("First line");
//        ItemListViewResult item2 = new ItemListViewResult("Second line");
//        ItemListViewResult item3 = new ItemListViewResult("Third line");
//
//        items.add(item1);
//        items.add(item2);
//        items.add(item3);
//
//        listViewResultAdapter = new ListViewResultAdapter(this, items);
//
//        listView.setAdapter(listViewResultAdapter);
//
//        listView.setCacheColorHint(Color.TRANSPARENT);
//
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemListViewResult item = (ItemListViewResult) listViewResultAdapter.getItem(position);
        Toast.makeText(this, "Click: " + item.getId() + " = " + item.getText(), Toast.LENGTH_LONG).show();
    }

    public void onButtonClick(View v){
        executeTestNetwork();
    }

    public void executeTestNetwork(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new FindRoutNetworkRest().execute(urlRest);
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }

    private class FindRoutNetworkRest extends AsyncTask<String, Void, String> {
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

                listViewResultAdapter = new ListViewResultAdapter(ListActivity.this, items);

                listView.setAdapter(listViewResultAdapter);

                listView.setCacheColorHint(Color.TRANSPARENT);

            } catch (JSONException e) {
                Toast.makeText(ListActivity.this, "Error to parse json.", Toast.LENGTH_LONG).show();
            }
        }

        private String getJson(){
            String address = editText.getText().toString();

            return "{ \"params\": { \"stopName\": \"%" + address + "%\" } }";
        }

        private String getAuthorization(){
            String usernamePassword = user + ":" + password;
            String basicAuth = "Basic " + Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);

            return basicAuth;
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
}
