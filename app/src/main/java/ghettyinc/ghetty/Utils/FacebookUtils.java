package ghettyinc.ghetty.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import org.json.JSONObject;

/**
 * Created by Tomás on 10/10/2015.
 */
public class FacebookUtils {

    public static void getFriends(){

        AccessToken access_token = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(
                access_token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        if(object != null){
                            Log.d("JSON", object.toString());
                        }
                        if(response != null){
                            Log.d("JSON", response.toString());
                        }
                        ParseUtils.createUser(object);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "friends");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public static void requestFacebookUserInfo(){

        AccessToken access_token = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(
                access_token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        if(object != null){
                            Log.d("JSON", object.toString());
                        }
                        if(response != null){
                            Log.d("JSON", response.toString());
                        }
                        ParseUtils.createUser(object);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,email,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public static void inviteFriendsDialog(Activity activity){
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://play.google.com";
        previewImageUrl = "";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(activity, content);
        }
    }


}
