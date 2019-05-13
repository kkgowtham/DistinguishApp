package fictionstudios.com.distinguishapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Html;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MyNotificationManager {

    public static final int BIG_NOTIFICATIONS = 234;
    public static final int SMALL_NOTIFICATIONS = 235;

    private Context mContext;

    public MyNotificationManager(Context context)
    {
        mContext=context;
    }

    public void showBigNotifcations(String name, String data, String url, Intent intent)
    {
        PendingIntent pendingIntent=PendingIntent.getActivity(mContext,BIG_NOTIFICATIONS,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigLargeIcon(getBitmap(url));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bigPictureStyle.setSummaryText(Html.fromHtml(data,Html.FROM_HTML_MODE_LEGACY).toString());
        }
        else {
            bigPictureStyle.setSummaryText(Html.fromHtml(data).toString());
        }
        bigPictureStyle.bigPicture(getBitmap(url));
        NotificationCompat.Builder mBuilder;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            mBuilder = new NotificationCompat.Builder(mContext, "Channel1");
        }
        else {
            mBuilder = new NotificationCompat.Builder(mContext);
        }
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(name).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(name)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentText(data)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(BIG_NOTIFICATIONS, notification);

    }

    public Bitmap getBitmap(String url)
    {
        Bitmap bitmap;
        try {
            bitmap=Picasso.get().load(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
