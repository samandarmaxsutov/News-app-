package uz.gita.readnews.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import uz.gita.readnews.R
import uz.gita.readnews.ui.activity.MainActivity

const val channelId = "notification_channel"
const val channelName = "uz.gita.readnews"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("receivekel:  ","Kelmadi")
        if (remoteMessage.getNotification() != null){
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }else{
            Log.d("ReceivedMessage:  ","Kelmadi")
        }
    }

    override fun onNewToken(token: String) {

        Log.d("token:  ","Kelmadi")
        super.onNewToken(token)
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemote(title: String, description: String,): RemoteViews? {
        Log.d("getRemote:  ","Kelmadi")
        val remoteView=RemoteViews("uz.gita.readnews.service",R.layout.message_notification)

        remoteView.setTextViewText(R.id.title_notif,title)
        remoteView.setTextViewText(R.id.desc_notif,description)

        return remoteView

    }



    fun generateNotification(title: String, description: String) {
        Log.d("generateNot:  ","Kelmadi")
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val paddingIntent = PendingIntent.getActivity(this, 8, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(paddingIntent)

        builder = builder.setContent(getRemote(title, description))

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if ( Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificaitonChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificaitonChannel)
        }

        notificationManager.notify(0,builder.build())
    }


}
