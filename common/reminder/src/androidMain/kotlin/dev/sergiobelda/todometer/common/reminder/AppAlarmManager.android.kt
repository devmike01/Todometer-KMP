package dev.sergiobelda.todometer.common.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import org.koin.core.module.Module
import org.koin.dsl.module

//@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object AppAlarmManager {

    lateinit var appContext: Context

    actual fun set(timeInMilliseconds: Long) {
        val alarmManager = (appContext.getSystemService(Context.ALARM_SERVICE)) as AlarmManager

        val intent = Intent(appContext, TodoAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        //val triggerTime = System.currentTimeMillis() + ()

        Log.d("[TodoAndroid]", "timeInMilliseconds -> $timeInMilliseconds")
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMilliseconds,
            pendingIntent)

    }
}
