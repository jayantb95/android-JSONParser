
package jayantb95.jsonparser.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jayantb95.jsonparser.R;
import jayantb95.jsonparser.adapter.RecyclerViewAdapter;
import jayantb95.jsonparser.dataModel.FeedItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;

    //    String hostUrl = "https://raw.githubusercontent.com/jayantb95/android-JSONParser/master/SampleData/SampleData.json";
    String hostUrl = "https://raw.githubusercontent.com/jayantb95/android-JSONParser/master/SampleData/SampleData2.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        adapter = new RecyclerViewAdapter(MainActivity.this, feedsList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        new DownloadTask().execute(hostUrl);

    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressDialog.dismiss();

            if (result == 1) {
                adapter = new RecyclerViewAdapter(MainActivity.this, feedsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
//            JSONObject response = new JSONObject(result);
//            JSONArray posts = response.optJSONArray("posts");
            JSONArray posts = new JSONArray(result);
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                item.setDescription(post.optString("description"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
