package dev.sergiobelda.todometer.common.reminder

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.IconCompat
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.AlarmOn
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.math.roundToInt

object ReminderPermission{
    const val ACTION_SNOOZE = "action-snooze-reminder"
}


class AndroidNotificationHandler(private val context: Context): NotificationHandler {

    companion object{
        const val NOTIFICATION_ID = 311
        const val EXTRA_NOTIFICATION_ID = "dev.sergiobelda.todometer.common.reminder.EXTRA_NOTIFICATION_ID"
        const val CHANNEL_ID = "dev.sergiobelda.todometer.common.reminder.NotificationHandler.CHANNEL_ID"
    }

    init {
        if(Build.VERSION.SDK_INT >= 26){
            context.createNotificationChannel()
        }
    }

    override fun setUpNotification(title: String, body: String){

        val snoozeIntent = Intent(context, TodoAlarmReceiver::class.java).apply {
            action = ReminderPermission.ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, NOTIFICATION_ID)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent,
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(IconCompat.createWithBitmap(Images.Icons.AlarmOn.asBitmap()))
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.outline_alarm_on, "Snooze", snoozePendingIntent)

        with(NotificationManagerCompat.from(context)){
            // todo: request permission
            if(ActivityCompat
                    .checkSelfPermission(context,
                        Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED){
                return@with
            }
             notify(NOTIFICATION_ID, builder.build())
        }

    }

    @RequiresApi(26)
    private fun Context.createNotificationChannel(){
        val name = "Reminder_Notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = "Notification test"
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun ImageVector.asBitmap(): Bitmap{

        return createBitmap(defaultWidth.value.roundToInt(), defaultHeight.value.roundToInt())
    }
}


