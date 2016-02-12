package ghettyinc.ghetty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.http.ParseHttpResponse;
import com.parse.http.ParseNetworkInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ghettyinc.ghetty.Utils.FacebookUtils;
import ghettyinc.ghetty.Utils.Group;
import ghettyinc.ghetty.Utils.Invitation;
import ghettyinc.ghetty.Utils.Message;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(Invitation.class);

        //Parse.enableLocalDatastore(this);

        //Parse.initialize(this); //Old Parse

        String parse_app_id = "superGhetty";
        String parse_client_key = "esloquehay";
        String heroku_server = "https://ghetty.herokuapp.com/parse/";

        Parse.setLogLevel(Parse.LOG_LEVEL_ERROR);

        //Heroku
        Parse.initialize(new Parse.Configuration.Builder(this).applicationId(parse_app_id).clientKey(parse_client_key).server(heroku_server).build());
        ParseFacebookUtils.initialize(this);

        if(ParseUser.getCurrentUser()!=null){

            Log.d("USER", ParseUser.getCurrentUser().getUsername());
            Log.d("MAIL", ParseUser.getCurrentUser().getEmail());
            launchMain();
        }else{
            setContentView(R.layout.activity_login);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        //login();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void logOut(View view) {
        Log.d("Button", "LOGOUT");
        ParseUser.logOut();
    }

    public void loginButtonHandler(View view) {
        //Log.d("Button", "touched");
        facebookLogin();
    }

    public void launchMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void facebookLogin() {
        ArrayList<String> permissions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.my_facebook_permissions)));
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if(e!=null){
                    Log.d("ParseException", e.getCode() + " " + e.getMessage());
                }

                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    FacebookUtils.requestFacebookUserInfo();
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    launchMain();
                }
            }
        });
    }

}
