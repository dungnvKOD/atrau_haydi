package com.atrau.guider_haydi.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.view.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

@SuppressLint("Registered")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        val TAG = "MyFirebaseMessagingService"
    }

    private val NOTIFICATION_ID_EXTRA = "notificationId"
    private val IMAGE_URL_EXTRA = "imageUrl"
    private val ADMIN_CHANNEL_ID = "admin_channel"
    private var notificationManager: NotificationManager? = null

    override fun onNewToken(token: String?) {

    }

    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "okkkkkkkk")
        val notificationIntent = Intent(this, HomeActivity::class.java)
        if (HomeActivity.isAppRunning) {
            //Some action
        } else {
            //Show notification as usual
        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notifyIntent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        //You should use an actual ID instead
        val notificationId = Random().nextInt(60000)


        val bitmap = getBitmapfromUrl(remoteMessage!!.data["image-url"])

//        val likeIntent = Intent(this, LikeService::class.java)
//        likeIntent.putExtra(NOTIFICATION_ID_EXTRA, notificationId)
//        likeIntent.putExtra(IMAGE_URL_EXTRA, remoteMessage.data["image-url"])
//        val likePendingIntent = PendingIntent.getService(
//            this,
//            notificationId + 1, likeIntent, PendingIntent.FLAG_ONE_SHOT
//        )


        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels()
        }

        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
            .setLargeIcon(bitmap)
            .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
            .setContentTitle(remoteMessage.notification!!.title)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .setSummaryText(remoteMessage.data["message"])
                    .bigPicture(bitmap)
            )/*Notification with Image*/
            .setContentText(remoteMessage.notification!!.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
//            .addAction(
//                R.drawable.ic_delete_black_24dp,
//                "dung123...", likePendingIntent
//            )
            .setContentIntent(pendingIntent)


        notificationManager!!.notify(notificationId, notificationBuilder.build())

    }

    fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannelName = "dung1234"
        val adminChannelDescription = "dung12345"

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        if (notificationManager != null) {
            notificationManager!!.createNotificationChannel(adminChannel)
        }
    }
}
