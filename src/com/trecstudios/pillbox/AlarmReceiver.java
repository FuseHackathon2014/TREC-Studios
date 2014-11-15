package com.trecstudios.pillbox;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        
            Intent resultIntent = new Intent(Intent.ACTION_QUICK_CLOCK);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent2 = PendingIntent.getService(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //Notification
            NotificationCompat.Builder mBuilder =
            	    new NotificationCompat.Builder(context)
            	    .setSmallIcon(R.drawable.ic_launcher)
            	    .setContentTitle("Pill Notification")
            	    .setContentText("Take your pill!!! \n Have you taken it?")
            	    .addAction(R.drawable.check2, new String("Yes"), pendingIntent)
            	    .addAction(R.drawable.clock, new String("Remind Later"), pendingIntent2);
            
            	
            
            // Because clicking the notification opens a new ("special") activity, there's
            // no need to create an artificial back stack.
            PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr = 
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
    

}