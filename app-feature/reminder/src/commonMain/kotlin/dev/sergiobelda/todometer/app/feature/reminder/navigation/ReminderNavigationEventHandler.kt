package dev.sergiobelda.todometer.app.feature.reminder.navigation

import dev.sergiobelda.fonament.presentation.navigation.FonamentNavigationEventHandler

fun reminderNavigationEventHandler(
    navigateBack: () -> Unit,
): FonamentNavigationEventHandler<ReminderNavigationEvent> = FonamentNavigationEventHandler{
    when(it){
        ReminderNavigationEvent.NavigateBack -> navigateBack()
    }
}