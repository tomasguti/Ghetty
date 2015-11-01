package ghettyinc.ghetty.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
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
        query.include("image");
        query.setLimit(10);
        //query.whereNear("location", ParseUtils.geoPoint);
        query.findInBackground(callback);
    }

    public static void getImage(Group group, final ImageView imageView){
        if(group.getParseObject("image") != null){
            ParseFile imageFile = (ParseFile) group.getParseObject("image").getParseFile("image");
            if (imageFile != null) {
                imageFile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] dataArray, ParseException e) {
                        if (e == null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(dataArray, 0, dataArray.length);
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        }

       /*

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GroupImages");

        if(group.getParse("image") != null){

        }

        query.whereEqualTo("objectId", .getObjectId());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
                                       @Override
                                       public void done(ParseObject object, ParseException e) {
                                           if (e == null) {
                                               ParseFile imageFile = (ParseFile) object.getParseFile("image");
                                               if (imageFile != null) {
                                                   Log.d("URL", imageFile.getUrl());

                                                   imageFile.getDataInBackground(new GetDataCallback() {
                                                       @Override
                                                       public void done(byte[] dataArray, ParseException e) {
                                                           if (e == null) {
                                                               Bitmap bitmap = BitmapFactory.decodeByteArray(dataArray, 0, dataArray.length);
                                                               imageView.setImageBitmap(bitmap);
                                                           }
                                                       }
                                                   });
                                               }
                                           }
                                       }
                                   }
        );
        */
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
