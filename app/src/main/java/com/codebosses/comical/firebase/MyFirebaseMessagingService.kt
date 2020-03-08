package com.codebosses.comical.firebase

import com.codebosses.comical.utils.PrefUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PrefUtils.deviceToken = token
    }

}