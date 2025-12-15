package dev.sergiobelda.todometer.common.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf

//@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
class AndroidAppAlarmManager(val appContext: Context): AppAlarmManager {

    companion object{
        const val EXTRA_ALARM_DATA = "AndroidAppAlarmManager.EXTRA_ALARM_DATA"
    }

    override fun set(timeInMilliseconds: Long, title: String, description: String) {
        val alarmManager = (appContext.getSystemService(Context.ALARM_SERVICE)) as AlarmManager

        val intent = Intent(appContext, TodoAlarmReceiver::class.java).apply {
            putExtra(EXTRA_ALARM_DATA, bundleOf(title to description))
        }

        val pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        //val triggerTime = System.currentTimeMillis() + ()

        Log.d("[TodoAndroid]", "timeInMilliseconds -> $timeInMilliseconds")
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMilliseconds,
            pendingIntent)

    }
}
