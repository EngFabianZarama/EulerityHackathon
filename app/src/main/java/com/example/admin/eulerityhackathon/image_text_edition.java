package com.example.admin.eulerityhackathon;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;

public class image_text_edition extends AppCompatActivity {

    ImageView imageClicked;
    String urlToSend;

    private static final String REQUEST_URL =
            "https://eulerity-hackathon.appspot.com/upload";

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_text_edition);

        Intent intent = getIntent();


        if (intent.getStringExtra("imageClicked1") != null) {

            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked1")).transform(new GrayscaleTransformation()).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked1");
        } else if (intent.getStringExtra("imageClicked2") != null) {

            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked2")).transform(new ColorFilterTransformation(Color.parseColor("#539b59b6"))).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked2");

        } else if (intent.getStringExtra("imageClicked3") != null) {

            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked3")).transform(new BrightnessFilterTransformation(imageClicked.getContext(), 0.5f)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked3");

        } else if (intent.getStringExtra("imageClicked4") != null) {

            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked4")).transform(new BrightnessFilterTransformation(imageClicked.getContext(), -0.5f)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked4");

        } else if (intent.getStringExtra("imageClicked5") != null) {

            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked5")).transform(new ContrastFilterTransformation(imageClicked.getContext(), 34)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked5");

        } else if (intent.getStringExtra("imageClicked6") != null) {

            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked6")).transform(new ContrastFilterTransformation(imageClicked.getContext(), -34)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked6");

        }


    }

    public void setTextOnImage(View view) {

        TextView textView = findViewById(R.id.myImageViewText);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonPost);
        EditText editText = findViewById(R.id.editTextImage);

        textView.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        textView.setText(editText.getText().toString());
    }


    public void starClick(View view) {

        image_text_edition.LoadUrl task = new image_text_edition.LoadUrl();
        task.execute();
        Toast.makeText(getApplicationContext(),"Uploading image",Toast.LENGTH_LONG).show();
    }

    public void post(String JSONurl) {

        // appid: a string unique to your app (e.g. your email address)
        // original: the url of the original image the user edited
        // file: the image file being uploaded


        String dataString = "engfabianzarama@gmail.com";
        String urlString = urlToSend ;
        //File file = Picasso

        OutputStream out = null;
        try {

            URL url = new URL(JSONurl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // I couldnt finish getting BufferedOutputStream(urlConnection.getOutputStream())
            out = new BufferedOutputStream(urlConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));

            writer.write(dataString);
            writer.write(urlString);

            writer.flush();

            writer.close();

            out.close();

            urlConnection.connect();


        } catch (Exception e) {

            Log.e(LOG_TAG, "Problem on POST", e);

        }

    }


    private class LoadUrl extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... urls) {
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
            String jsonUrl = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return jsonUrl;
        }


        @Override
        protected void onPostExecute(String url) {
            if (url == null) {
                return;
            }

            post(url);
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

        /**
         * Return an {@link String} object by parsing out information string
         */
        private String extractFeatureFromJson(String extractString) {

            // If the JSON is empty then return null.
            if (TextUtils.isEmpty(extractString)) {
                return null;
            }
            try {
                JSONObject jSONesponse = new JSONObject(extractString);

                String getUrl = jSONesponse.getString("url");

                return getUrl;

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing url JSON results", e);
            }
            return null;
        }
    }


}
