package dev.sergiobelda.todometer.app.feature.reminder.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentEvent

sealed interface ReminderEvent : FonamentEvent {
    data object LoadRepeatDays : ReminderEvent
    data object LoadSnoozeTimes  : ReminderEvent
    data class SelectRepeatDay(val selection: Int): ReminderEvent
    data class SelectSnoozeTime(val selection: Int): ReminderEvent
    data class TimePeriodChanged(val period: String) : ReminderEvent
    data class AlarmTimeChanged(val alarmTime: String) : ReminderEvent
    data class MinuteTimeChanged(val minutes: Int, val isUpdate: Boolean): ReminderEvent
    data class SecondTimeChanged(val seconds: Int, val isUpdate: Boolean): ReminderEvent
    data class HourTimeChanged(val hour: Int, val isUpdate: Boolean): ReminderEvent
}