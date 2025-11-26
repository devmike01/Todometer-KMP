package dev.sergiobelda.todometer.app.feature.reminder.ui

import androidx.compose.runtime.annotation.RememberInComposition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import dev.sergiobelda.fonament.presentation.ui.FonamentContentState

class ReminderContentState @RememberInComposition constructor(): FonamentContentState {

    var hasAlarm by mutableStateOf(false)
        private set

    var selectedRepeatDay by mutableStateOf(0)
        private set

    var selectedSnooze by mutableStateOf(0)
        private set

    var alarmTime by mutableStateOf("")
        private set

    companion object {

        private const val SelectedRepeatDay: String = "selected_repeat_days"
        private const val SelectedSnooze : String = "selected_snooze_time"
        private const val AlarmTime : String = "selected_time"

        internal fun Saver(): Saver<ReminderContentState, *> = mapSaver(
            save = {
                mapOf(
                    SelectedRepeatDay to it.selectedRepeatDay,
                    SelectedSnooze to it.selectedSnooze,
                    AlarmTime to it.alarmTime
                )
            },
            restore = { map ->
                ReminderContentState().apply {
                    selectedSnooze = map[SelectedRepeatDay] as Int
                    selectedRepeatDay = map[SelectedSnooze] as Int
                    alarmTime = map[AlarmTime] as String
                }
            }
        )
    }


}