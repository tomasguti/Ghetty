package ghettyinc.ghetty.Utils;

import android.util.Log;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import im.delight.android.location.SimpleLocation;

/**
 * Created by Tomás on 10/10/2015.
 */
public class ParseUtils {

    public static ParseGeoPoint geoPoint;

    public static void createUser(JSONObject object){

        ParseUser user = ParseUser.getCurrentUser();

        try {
            user.put("name", object.getString("first_name"));
        } catch (JSONException e) {}

        try {
            user.put("email", object.getString("email"));
        } catch (JSONException e) {}

        try {
            String birthday = object.get("birthday").toString();

            Log.d("birthday", birthday.toString());
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date result =  df.parse(birthday);
            Log.d("result", result.toString());
            user.put("birthdate", result);
        } catch (JSONException e) {} catch (java.text.ParseException e) {
            Log.d("birthday", "object.get(\"user_birthday\") failed");
            e.printStackTrace();
        }

        user.saveInBackground();
    }

    public static void updateGeopoint(SimpleLocation location){

        ParseUser user = ParseUser.getCurrentUser();

        geoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        user.put("localization", geoPoint);

        user.saveInBackground();
    }
}
