package dev.sergiobelda.todometer.common.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual interface AppAlarmManager {
    actual fun set(timeInMilliseconds: Long)
}


class AppAlarmManagerImpl(private val context: Context) : AppAlarmManager{

    private val alarmManager = (context.getSystemService(Context.ALARM_SERVICE)) as AlarmManager

    override fun set(timeInMilliseconds: Long) {
        val intent = Intent(context, TodoAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val triggerTime = System.currentTimeMillis() + (timeInMilliseconds)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }
}
