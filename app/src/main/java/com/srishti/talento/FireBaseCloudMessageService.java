package com.srishti.talento;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class FireBaseCloudMessageService extends FirebaseMessagingService {


    private String category, subject, courseId, company, vacancy, placement, job, post, examCategory,name;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        appPreferences = AppPreferences.getInstance(this, getResources().getString(R.string.app_name));

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i("Notification", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
//            Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                // handleNow();
            }

        }


        try {
            issueNotification(remoteMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void scheduleJob() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance) {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true); // set false to disable badges, Oreo exclusive

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void issueNotification(RemoteMessage remoteMessage) throws JSONException {


        // make the channel. The method has been discussed before.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("CHANNEL_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT);
        }
        // the check ensures that the channel will only be made
        // if the device is running Android 8+

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, "CHANNEL_1");
        // the second parameter is the channel id.
        // it should be the same as passed to the makeNotificationChannel() method


        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            //JSONObject Customer_details = object.getJSONObject("Customer_details");

            if (object.getString("Category").equals("Exam")) {
                subject = object.getString("Subject");
                courseId = object.getString("Technology");
                examCategory = object.getString("From");

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category_name",examCategory);
                editor.putString("courseId",courseId);
                editor.apply();


                Intent resultIntent = new Intent(this, SmartClassHomeActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntentWithParentStack(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.notification_small_icon);
                notification
                        .setLargeIcon(icon)
                        .setSmallIcon(R.drawable.notification_small_icon) // can use any other icon
                        .setContentTitle("New Exam ")
                        //change this to order received.
                        .setContentText("Subject : " + subject)
                        //setContentText("ORder List")
                        //.addAction(R.drawable.ic_launcher_background,"qwertyuio")
                        .setContentIntent(resultPendingIntent)
                        .setNumber(3) // this shows a number in the notification dots
                        .setAutoCancel(true);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                assert notificationManager != null;
                notificationManager.notify(1, notification.build());

            } else if (object.getString("Category").equals("Job")) {
                company = object.getString("Company");
                vacancy = object.getString("vacancy");

                Intent resultIntent = new Intent(this, SmartClassHomeActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntentWithParentStack(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.notification_small_icon);
                notification
                        .setLargeIcon(icon)
                        .setSmallIcon(R.drawable.notification_small_icon) // can use any other icon
                        .setContentTitle("New Vacancy ")
                        //change this to order received.
                        .setContentText("Company Name : " + company + " " + "Vacancy available for " + vacancy)
                        //setContentText("ORder List")
                        //.addAction(R.drawable.ic_launcher_background,"qwertyuio")
                        .setContentIntent(resultPendingIntent)
                        .setNumber(3) // this shows a number in the notification dots
                        .setAutoCancel(true);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                assert notificationManager != null;
                notificationManager.notify(1, notification.build());


            } else if (object.getString("Category").equals("Placement")) {
                job = object.getString("Job");
                post = object.getString("Post");
                name=object.getString("name");

                Intent resultIntent = new Intent(this, SmartClassHomeActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntentWithParentStack(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.notification_small_icon);
                Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.placed_notification);

                notification
                        .setLargeIcon(icon)
                        .setSmallIcon(R.drawable.notification_small_icon) // can use any other icon
                        .setLargeIcon(myBitmap)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(myBitmap)
                                .bigLargeIcon(null))

                        .setContentTitle(name+" got placed")
                        //change this to order received.
                        .setContentText("Placed as " + post)
                        //setContentText("ORder List")
                        //.addAction(R.drawable.ic_launcher_background,"qwertyuio")
                        .setContentIntent(resultPendingIntent)
                        .setNumber(3) // this shows a number in the notification dots
                        .setAutoCancel(true);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                assert notificationManager != null;
                notificationManager.notify(1, notification.build());
            }


//            Log.e("subject", subject);
//            Log.e("technology", technology);


// Create an Intent for the activity you want to start

// Create the TaskStackBuilder and add the intent, which inflates the back stack

// Get the PendingIntent containing the entire back stack


//intent for accept button

            // Create an Intent for the activity you want to start
            //   Intent aprove = new Intent(this, ApprovalActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
            ////  TaskStackBuilder astackBuilder = TaskStackBuilder.create(this);
            //    astackBuilder.addNextIntentWithParentStack(aprove);
// Get the PendingIntent containing the entire back stack
//        PendingIntent approvepending =
//                astackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


// Create an Intent for the activity you want to start
//                Intent resultIntent = new Intent(this, StaffOrderList.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
//                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//                stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
//                PendingIntent resultPendingIntent =
//                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


//intent for accept button

            // Create an Intent for the activity you want to start
            //   Intent aprove = new Intent(this, ApprovalActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
            ////  TaskStackBuilder astackBuilder = TaskStackBuilder.create(this);
            //    astackBuilder.addNextIntentWithParentStack(aprove);
// Get the PendingIntent containing the entire back stack
//        PendingIntent approvepending =
//                astackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                        R.drawable.groceri);
//                notification
//                        .setLargeIcon(icon)
//                        .setSmallIcon(R.drawable.groceridribbble) // can use any other icon
//                        .setContentTitle("Order To be delivered ")
//                        .setContentText("Staff push ")
//                        //setContentText("ORder List")
//                        //.addAction(R.drawable.ic_launcher_background,"qwertyuio")
//                        .setContentIntent(resultPendingIntent)
//                        .setNumber(3); // this shows a number in the notification dots
//
//                NotificationManager notificationManager =
//                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//                assert notificationManager != null;
//                notificationManager.notify(1, notification.build());


        } catch (Exception e) {
            Log.e("ERROR IN PUSH", String.valueOf(e));
        }


    }


    @Override
    public void onNewToken(String token) {
        Log.i("TAG", "Refreshed token: " + token);


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
        Log.i("token",token);
    }

    private void sendRegistrationToServer(String token) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("token", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

}
