package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EditTweetActivity extends Activity {
    public TextView textview;
    //public Tweet sentTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);

        Intent intent = getIntent();

        textview = (TextView) findViewById(R.id.textView);
        Tweet sentTweet = (Tweet) intent.getSerializableExtra("sendTweet");
        String newText = sentTweet.getMessage();



        textview.setText(newText);


    }

}
