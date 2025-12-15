package dev.sergiobelda.todometer.common.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.compose.getKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.mp.KoinPlatformTools

class TodoAlarmReceiver : BroadcastReceiver() {

    val notificationHandler : NotificationHandler by inject(AndroidNotificationHandler::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {

        when(intent?.action){
            ReminderPermission.ACTION_SNOOZE ->{
                // SNOOZE Reminder
            }
            else ->{
                // show notification
                notificationHandler.setUpNotification("", "")
                // AndroidNotificationHandler
            }
        }
    }
}
