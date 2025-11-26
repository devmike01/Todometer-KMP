package dev.sergiobelda.todometer.app.feature.reminder.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentEvent

sealed interface ReminderEvent : FonamentEvent {
    data object LoadRepeatDays : ReminderEvent
    data object LoadSnoozeTimes  : ReminderEvent
    data class SelectRepeatDay(val selection: Int): ReminderEvent

}