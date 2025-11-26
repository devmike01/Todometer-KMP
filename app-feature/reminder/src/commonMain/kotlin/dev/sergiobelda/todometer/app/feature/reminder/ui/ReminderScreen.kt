package dev.sergiobelda.todometer.app.feature.reminder.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentContent
import dev.sergiobelda.fonament.presentation.ui.FonamentUI
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination

@NavDestination(name = "reminder",
    destinationId = "reminder",
    deepLinkUris = ["app://open.add.reminder"])
data object ReminderScreen : FonamentUI<ReminderState>(){
    override val content: FonamentContent<ReminderState, *>
        get() = ReminderContent()

}