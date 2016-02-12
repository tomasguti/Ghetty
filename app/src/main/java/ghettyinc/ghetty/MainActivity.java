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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    protected CardPagerAdapter adapter;
    protected SimpleLocation location;
    protected RelativeLayout myGroups;
    protected ListView myGroupsListView;
    protected boolean animatingMyGroup;

    protected boolean myGroupsSlided;

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
        adapter = new CardPagerAdapter(this);

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
                if (e == null && objects.size() > 0) {
                    //Log.d("objects", objects.toString());
                    adapter.setData(objects);
                    pager.setAdapter(adapter);
                    //Necessary or the pager will only have one extra page to show
                    // make this at least however many pages you can see
                    pager.setOffscreenPageLimit(adapter.getCount());
                }
            }
        });

        //Mis grupos sliding
        myGroupsSlided = false;
        myGroups = (RelativeLayout) findViewById(R.id.myGroups);
        myGroupsListView = (ListView) myGroups.findViewById(R.id.myGroupsListView);
        animatingMyGroup = false;
        final MyGroupsAdapter myGroupsAdapter = new MyGroupsAdapter(this);

        Group.getMyGroups(new FindCallback<Group>() {
            @Override
            public void done(List<Group> objects, ParseException e) {
                if(e == null && objects.size() > 0){
                    //Log.d("objects", objects.toString());
                    myGroupsAdapter.setData(objects);
                    myGroupsListView.setAdapter(myGroupsAdapter);
                }
            }
        });
    }

    public void groupClick(View v) {

        if(!animatingMyGroup){
            if(myGroups.getVisibility() == View.GONE){
                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                slideDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        myGroups.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animatingMyGroup = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animatingMyGroup = true;
                myGroups.startAnimation(slideDown);
            }else{
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                slideUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        myGroups.setVisibility(View.GONE);
                        animatingMyGroup = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animatingMyGroup = true;
                myGroups.startAnimation(slideUp);
            }
        }
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
