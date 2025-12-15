package dev.sergiobelda.todometer.common.data.reminder

import dev.sergiobelda.todometer.common.domain.reminder.ITodoReminder
import dev.sergiobelda.todometer.common.reminder.AppAlarmManager

class TodoReminder(private val appAlarmManager: AppAlarmManager) : ITodoReminder {

    override fun remind(
        timeInMilliseconds: Long,
        title: String,
        description: String
    ) {
        appAlarmManager.set(timeInMilliseconds, title, description)
    }
}