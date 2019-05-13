package fictionstudios.com.distinguishapp;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String,String> map=remoteMessage.getData();
            JSONObject object=new JSONObject(map);
            Log.d(TAG, "onMessageReceived: "+object.toString());
            sendPushNotification(object);

        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendPushNotification(JSONObject json) {
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                mNotificationManager.showBigNotifcations(title, message, imageUrl, intent);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}
