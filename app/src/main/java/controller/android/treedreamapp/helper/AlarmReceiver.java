package controller.android.treedreamapp.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.activity.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras=intent.getExtras();
        String title=extras.getString("title");
        //here we get the title and description of our Notification

        String note=extras.getString("note");
        NotificationManager manger = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.tree_icon, "Combi Note", System.currentTimeMillis());

        // api 11

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(false);
        builder.setTicker("Event Notifier");
        builder.setContentTitle(title);
        builder.setContentText(note);
        builder.setSmallIcon(R.drawable.tree_icon);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("This is subtext...");   //API level 16
        builder.setNumber(100);
        builder.build();

        notification = builder.getNotification();
        manger.notify(NOTIFICATION_ID++, notification);









    }
}