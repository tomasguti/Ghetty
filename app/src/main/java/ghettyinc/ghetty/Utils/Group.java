package ghettyinc.ghetty.Utils;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Tomás on 10/10/2015.
 */
@ParseClassName("Group")
public class Group extends ParseObject{

    public static List<Group> groupCache;
    public static List<Group> myGroups;

    public static void getMoreGroups(FindCallback<Group> callback){
        ParseQuery<Group> query = ParseQuery.getQuery(Group.class);
        query.setLimit(10);
        query.include("image");
        //query.whereNear("location", ParseUtils.geoPoint);
        query.findInBackground(callback);
    }

    public static void getImage(GetCallback<Group> callback){
        ParseQuery<Group> query = ParseQuery.getQuery(Group.class);
        query.setLimit(10);
        //query.include("image");
        //query.whereNear("location", ParseUtils.geoPoint);
        query.getFirstInBackground(callback);
    }

    public static void getUserWithCurrentGroup(GetCallback<ParseObject> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.include("currentGroup");
        query.getFirstInBackground(callback);
    }

    public static List<Group> getMyGroups(FindCallback<Group> callback){
        ParseQuery<Group> query = ParseQuery.getQuery(Group.class);
        query.setLimit(10);

        //query.whereNear("location", ParseUtils.geoPoint);
        query.findInBackground(callback);
        /*
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Post");
        innerQuery.whereExists("image");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
        query.whereMatchesQuery("post", innerQuery);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> commentList, ParseException e) {
                // comments now contains the comments for posts with images.
            }
        });
        */
        return myGroups;
    }

}
