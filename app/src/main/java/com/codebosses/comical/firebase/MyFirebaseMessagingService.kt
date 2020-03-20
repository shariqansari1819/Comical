package com.codebosses.comical.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.utils.PrefUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val map: Map<String, String> = remoteMessage.data
            val json = JSONObject(map)

            val description: String = json["description"].toString()
            val notificationType: String = json["notification_type"].toString()
            val imageUrl: String = json["image_url"].toString()
            val id: Int = json["id"].toString().toInt()
            val name: String = json["name"].toString()

            var notificationTitle = ""

            if (notificationType.equals("Comics")) {
                notificationTitle = "Hey! A new comic is waiting for you."
            } else {
                notificationTitle = "Hey! A new chapter is launched."
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", id)
            val pendingIntent = PendingIntent.getActivity(this, 1, intent, 0)

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
                    .setContentTitle(notificationTitle)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(description))
                    .setContentText(name)
                    .setSmallIcon(R.drawable.comical_logo)
                    .setPriority(importance)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            val largeImage = Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()

            notificationBuilder.setLargeIcon(largeImage.get())

            Glide.with(this).clear(largeImage)

            val notification = notificationBuilder.build()
            notificationManager.notify(System.currentTimeMillis().toInt(), notification)

        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PrefUtils.deviceToken = token
    }

}