package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LonelyTwitterActivity extends Activity {

    private LonelyTwitterActivity activity = this;

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView oldTweetsList;

    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    private ArrayAdapter<Tweet> adapter;

    public ListView getOldTweetsList(){ return oldTweetsList;}

    public ArrayAdapter<Tweet> getAdapter() {
        return adapter;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bodyText = (EditText) findViewById(R.id.body);
        Button saveButton = (Button) findViewById(R.id.save);
        Button clearButton = (Button) findViewById(R.id.clear);

        oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String text = bodyText.getText().toString();
                Tweet latestTweet = new NormalTweet(text);

                tweets.add(latestTweet);
                adapter.notifyDataSetChanged();

                // TODO: Replace with Elasticsearch
                saveInFile();

                setResult(RESULT_OK);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                tweets.clear();
                adapter.notifyDataSetChanged();

                // TODO: Replace with Elasticsearch
                saveInFile();

                setResult(RESULT_OK);
            }
        });

        oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, EditTweetActivity.class);

                Tweet sendTweet = (Tweet) oldTweetsList.getItemAtPosition(i);

                intent.putExtra("sendTweet", sendTweet);


                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get latest tweets
        // TODO: Replace with Elasticsearch
        loadFromFile();

        // Binds tweet list with view, so when our array updates, the view updates with it
        adapter = new ArrayAdapter<Tweet>(this, R.layout.list_item, tweets);
        oldTweetsList.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<NormalTweet>>() {
            }.getType();
            tweets = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            tweets = new ArrayList<Tweet>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(tweets, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}