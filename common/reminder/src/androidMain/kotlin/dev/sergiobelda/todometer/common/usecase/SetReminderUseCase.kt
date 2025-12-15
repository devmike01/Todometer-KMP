package dev.sergiobelda.todometer.common.usecase

import dev.sergiobelda.todometer.common.reminder.AppAlarmManager

class SetReminderUseCase(val alarmManager: AppAlarmManager) {

    operator fun invoke(timeInMilliseconds: Long, title: String, description: String){
        alarmManager.set(timeInMilliseconds, title, description)
    }
}