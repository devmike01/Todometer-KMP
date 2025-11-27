package dev.sergiobelda.todometer.app.feature.reminder.ui

import androidx.compose.runtime.annotation.RememberInComposition
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import dev.sergiobelda.fonament.presentation.ui.FonamentContentState

class ReminderContentState @RememberInComposition constructor(): FonamentContentState {

    var period by mutableStateOf("--")
        private set

    var selectedRepeatDay by mutableStateOf(0)
        private set

    var selectedSnooze by mutableStateOf(0)
        private set

    var alarmTime by mutableStateOf("")
        private set

    var secondsCounter by mutableStateOf( 0)
        private set

    var minutesCounter by mutableStateOf(0)
        private set

    var hourCounter by mutableStateOf(0)
        private set

    var twelveHour = derivedStateOf { hourCounter.takeIf { it <= 12 } ?: (hourCounter - 12) }


    companion object {

        private const val SelectedRepeatDay: String = "selected_repeat_days"
        private const val SelectedSnooze : String = "selected_snooze_time"
        private const val AlarmTime : String = "selected_time"
        private const val Period : String ="time_period"

        internal fun Saver(): Saver<ReminderContentState, *> = mapSaver(
            save = {
                mapOf(
                    SelectedRepeatDay to it.selectedRepeatDay,
                    SelectedSnooze to it.selectedSnooze,
                    AlarmTime to it.alarmTime,
                    Period to it.period
                )
            },
            restore = { map ->
                ReminderContentState().apply {
                    selectedSnooze = map[SelectedRepeatDay] as Int
                    selectedRepeatDay = map[SelectedSnooze] as Int
                    alarmTime = map[AlarmTime] as String
                    period = map[Period] as String
                }
            }
        )
    }

    fun setTimePeriod(period: String){
        this.period = period
    }

    fun setTime(time: String){
        this.alarmTime = time
    }

    fun setSeconds(seconds: Int){
        this.secondsCounter = seconds
    }

    fun setMinute(minute: Int){
        this.minutesCounter = minute
    }

    fun updateMinute(updateMinute: (Int) -> Int){
        this.minutesCounter = updateMinute(this.minutesCounter)
    }


    fun updateHour(updateHour: (Int) -> Int){
        this.hourCounter = updateHour(this.hourCounter)
    }

    fun updateSecond(onUpdateSeconds: (Int) -> Int){
        this.secondsCounter = onUpdateSeconds(this.secondsCounter)
    }

    fun setHours(hours: Int){
        this.hourCounter = hours
    }

}