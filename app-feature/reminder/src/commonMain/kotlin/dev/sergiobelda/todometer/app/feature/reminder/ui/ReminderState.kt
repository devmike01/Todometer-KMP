package dev.sergiobelda.todometer.app.feature.reminder.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentUIState
import dev.sergiobelda.todometer.app.feature.reminder.model.DayOfWeekInitial
import dev.sergiobelda.todometer.app.feature.reminder.model.SnoozeTime
import dev.sergiobelda.todometer.common.resources.NavBundle
import kotlinx.collections.immutable.ImmutableList

data class ReminderState(
    val repeatedDayOfWeek: List<DayOfWeekInitial> = emptyList(),
    val snoozeTimes: List<SnoozeTime> = emptyList(),
    val reminderBundle: NavBundle = NavBundle.empty,
    val titleBody: Pair<String, String> = Pair("", "")

): FonamentUIState

