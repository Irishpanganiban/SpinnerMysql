package com.example.spinnermysql;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    List<String> itemList; // List to store fetched items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        itemList = new ArrayList<>(); // Initialize list for items

        // Initialize adapter with an empty list
        adapterItems = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemList);
        autoCompleteTextView.setAdapter(adapterItems);

        // Fetch data from PHP script
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        String url = "http://lesterintheclouds.com/crud-android/get_spinner_data.php"; // Replace with your PHP script URL

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a JSON array request
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Clear existing items
                            itemList.clear();

                            // Parse JSON response and add items to itemList
                            for (int i = 0; i < response.length(); i++) {
                                String item = response.getString(i);
                                itemList.add(item);
                            }

                            // Notify adapter of data change
                            adapterItems.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                });

        // Add the request to the queue
        queue.add(request);
    }
}
