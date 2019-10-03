package com.example.cr7.mobieshop;

import android.app.Application;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class hawkx extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            createChannel();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel(){
        String groupId = "some_group_id";
        CharSequence groupName = "Some_Group";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId,groupName));
    }
}
