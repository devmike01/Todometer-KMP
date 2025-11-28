package dev.sergiobelda.todometer.app.feature.reminder.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentUIState
import dev.sergiobelda.todometer.app.feature.reminder.model.DayOfWeekInitial
import dev.sergiobelda.todometer.app.feature.reminder.model.SnoozeTime
import kotlinx.collections.immutable.ImmutableList

data class ReminderState(
    val repeatedDayOfWeek: List<DayOfWeekInitial> = emptyList(),
    val snoozeTimes: List<SnoozeTime> = emptyList(),
    val alarmTimeInMillis: Long = 0L

): FonamentUIState

