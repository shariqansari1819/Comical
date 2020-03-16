package com.codebosses.comical.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codebosses.comical.utils.PrefUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.size > 0) {
            val map: Map<String, String> = remoteMessage.data
            val json = JSONObject(map)

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channelId = "Comical_CH_Alarm"
            val channelName = "Comical_Alarm"
            val importance = NotificationManager.IMPORTANCE_HIGH

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(channelId, channelName, importance)
                notificationChannel.enableLights(true)
                notificationChannel.enableVibration(true)
            }

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setContentTitle("Hey! A new comic is waiting.")
                    .setStyle(NotificationCompat.BigTextStyle().bigText(json.getString("description")))
                    .setContentText(json.getString("name"))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)

            val notification = notificationBuilder.build()
            notificationManager.notify(1, notification)

        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PrefUtils.deviceToken = token
    }

}