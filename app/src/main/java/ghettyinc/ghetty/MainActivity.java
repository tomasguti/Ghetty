package ghettyinc.ghetty;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ghettyinc.ghetty.Utils.FacebookUtils;
import ghettyinc.ghetty.Utils.Group;
import ghettyinc.ghetty.Utils.ParseUtils;
import im.delight.android.location.SimpleLocation;

public class MainActivity extends Activity {

    protected Chats chat;
    protected Notifications notifications;
    protected PagerContainer mContainer;
    protected ViewPager pager;
    protected MyPagerAdapter adapter;
    protected SimpleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //setTitle(R.string.attach);
        // set the content view
        setContentView(R.layout.activity_main);
        // configure the SlidingMenu

        chat = new Chats(this);
        chat.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);

        notifications = new Notifications(this);
        notifications.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);

        //Pager
        mContainer = (PagerContainer) findViewById(R.id.pager_container);

        pager = mContainer.getViewPager();
        adapter = new MyPagerAdapter(this);

        //A little space between pages
        pager.setPageMargin(10);
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);

        location = new SimpleLocation(this);
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }
        ParseUtils.updateGeopoint(location);

        Group.getUserWithCurrentGroup(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject user, ParseException e) {

                TextView myGroupName = (TextView) findViewById(R.id.myGroupName);
                ParseObject group = (ParseObject) user.get("currentGroup");

                Log.d("GroupName", group.get("name").toString());
                myGroupName.setText(group.get("name").toString());
            }
        });

        Group.getMoreGroups(new FindCallback<Group>() {
            @Override
            public void done(List<Group> objects, ParseException e) {

                Log.d("objects", objects.toString());

                if(objects.size() > 0){
                    adapter.setData(objects);
                    pager.setAdapter(adapter);
                    //Necessary or the pager will only have one extra page to show
                    // make this at least however many pages you can see
                    pager.setOffscreenPageLimit(adapter.getCount());
                }
            }
        });
    }

    public void showRight(View view) {
        chat.showMenu(true);
    }

    public void showLeft(View view) {
        //FacebookUtils.inviteFriendsDialog(this);
        FacebookUtils.getFriends();
       // notifications.showMenu(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // make the device update its location
        location.beginUpdates();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}
