package com.example.admin.eulerityhackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.io.InputStream;

import org.json.JSONException;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private static final String REQUEST_URL =
            "https://eulerity-hackathon.appspot.com/image";

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loading asynchronous images
        LoadImagAsyncTask task = new LoadImagAsyncTask();
        task.execute();
    }



    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI
     */
    private class LoadImagAsyncTask extends AsyncTask<URL, Void, ArrayList<Event>> {


        @Override
        protected ArrayList<Event> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving the JSON response", e);
            }

            // Extract relevant fields from the JSON response and create an {@link Event} ob
            ArrayList<Event> image = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return image;
        }


        @Override
        protected void onPostExecute(ArrayList<Event> images) {
            if (images == null) {
                return;
            }

            updateUi(images);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            // If url is null, then return empty string.
            if (url == null) {
                return jsonResponse;
            }


            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                // If the request is a 200 response code then read the input
                // and parse the responce. Else return an empty string.
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error responce code: " + urlConnection.getResponseCode());
                }

            } catch (IOException e) {

                Log.e(LOG_TAG, "Problem retrieving making http request", e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                // From raw data to char
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }


        private ArrayList<Event> extractFeatureFromJson(String JSONimage) {

            // If the JSON is empty then return null.
            if (TextUtils.isEmpty(JSONimage)) {
                return null;
            }
            try {

                JSONArray featureArray = new JSONArray(JSONimage);
                ArrayList<Event> imageUrl = new ArrayList<>();


                // If there are results in the features array
                if (featureArray.length() > 0) {
                   for(int i = 0; i<featureArray.length();i++) {
                       JSONObject firstFeature = featureArray.getJSONObject(i);
                       // Extract out the url value
                       imageUrl.add(new Event(firstFeature.getString("url")));


                   }
                    // Create a new {@link Event} object

                    return imageUrl;
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }
            return null;
        }
    }

    /**
     * Update the screen to display information from the given {@link Event}.
     */


    private void updateUi(final ArrayList<Event> images) {



        // Find a reference to the {@link ListView} in the layout
        ListView imageListView =  findViewById(R.id.list);


        imagesAdapter adapter = new imagesAdapter(this, images);

        imageListView.setAdapter(adapter);

        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                Intent intent = new Intent(MainActivity.this, image_edition.class);
                intent.putExtra("img", images.get(position).getUrl());
                startActivity(intent);


            }
        });


    }

}
