package com.dexterous.flutterlocalnotifications.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.dexterous.flutterlocalnotifications.FlutterLocalNotificationsPlugin;
import com.dexterous.flutterlocalnotifications.models.NotificationDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ScheduleNotificationWorker extends Worker {

    public ScheduleNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Gson gson = FlutterLocalNotificationsPlugin.buildGson();
        Type type = new TypeToken<NotificationDetails>() {
        }.getType();
        NotificationDetails notificationDetails = gson.fromJson(getInputData().getString("notificationDetailsJson"), type);
        FlutterLocalNotificationsPlugin.showNotification(getApplicationContext(), notificationDetails);
        FlutterLocalNotificationsPlugin.removeNotificationFromCache(notificationDetails.id, getApplicationContext());

        return Result.success();
    }
}