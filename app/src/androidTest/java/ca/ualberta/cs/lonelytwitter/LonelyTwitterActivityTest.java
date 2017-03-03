package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2<LonelyTwitterActivity> {

    private Solo solo;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testTweet(){
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);

        solo.clickOnButton("Clear");
        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");

        solo.clickOnButton("Save");

        //To avoid waitForText searching the edittext field
        solo.clearEditText((EditText) solo.getView(R.id.body));

        assertTrue(solo.waitForText("Test Tweet!"));

        solo.clickOnButton("Clear");

        assertFalse(solo.searchText("Test Tweet!"));

    }

    public void testClickTweetList(){
        LonelyTwitterActivity activity =  (LonelyTwitterActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);

        solo.clickOnButton("Clear");
        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");

        solo.clickOnButton("Save");
        solo.waitForText("Test Tweet!");

        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);

        assertEquals("Test Tweet!", tweet.getMessage());

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);

        //assertTrue(solo.waitForText("TextView"));

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);

    }

    public void testUI(){
        LonelyTwitterActivity activity =  (LonelyTwitterActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);

        solo.clickOnButton("Clear");
        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");

        solo.clickOnButton("Save");
        solo.waitForText("Test Tweet!");

        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);

        solo.waitForText("Test Tweet!"); //Detects if text from tweet appears in the EditTweetActivity


    }

}














