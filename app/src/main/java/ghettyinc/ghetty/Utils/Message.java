package ghettyinc.ghetty.Utils;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Tomás on 10/10/2015.
 */
@ParseClassName("Message")
public class Message extends ParseObject{

    public static void getInvitationMessages(FindCallback<Message> callback){

        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.setLimit(10);

        //query.whereNear("location", ParseUtils.geoPoint);
        query.findInBackground(callback);
    }

}
