package dev.sergiobelda.todometer.app.feature.reminder.navigation

import dev.sergiobelda.fonament.presentation.navigation.FonamentNavigationEvent

sealed interface ReminderNavigationEvent : FonamentNavigationEvent {

    data object NavigateBack : ReminderNavigationEvent
}