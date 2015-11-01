package ghettyinc.ghetty.Utils;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomás on 10/10/2015.
 */
@ParseClassName("Invitation")
public class Invitation extends ParseObject{

    public static ArrayList<Invitation> pending;
    public static ArrayList<Invitation> accepted;

    public static void getUserInvitations(FindCallback<Invitation> callback){

        ParseQuery<Invitation> query = ParseQuery.getQuery(Invitation.class);
        query.setLimit(10);

        //query.whereNear("location", ParseUtils.geoPoint);
        query.findInBackground(callback);
    }

}
