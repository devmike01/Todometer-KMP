package dev.sergiobelda.todometer.app.feature.reminder.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
import dev.sergiobelda.todometer.app.feature.reminder.model.DayOfWeekInitial
import dev.sergiobelda.todometer.app.feature.reminder.model.SnoozeTime

class ReminderScreenViewModel : FonamentViewModel<ReminderState>(initialUIState = ReminderState()) {

    override fun handleEvent(event: FonamentEvent) {
        when(event){
            is ReminderEvent.LoadRepeatDays -> loadRepeatDays()
            is ReminderEvent.LoadSnoozeTimes -> loadSnoozeTimes()
            is ReminderEvent.SelectRepeatDay -> selectRepeatedDay(event.selection)
        }
    }


    fun loadRepeatDays(){
        updateUIState { it.copy(
            repeatedDayOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")
                .mapIndexed { id,  initial ->  DayOfWeekInitial(initial, id, false) }
        ) }
    }

    fun selectRepeatedDay(selection: Int){
        updateUIState {
            val repeatedDays = uiState.repeatedDayOfWeek.map { day ->
                day.copy(checked = day.id == selection)
            }
            it.copy(repeatedDayOfWeek = repeatedDays)
        }
    }

    fun loadSnoozeTimes(){
        updateUIState { it.copy(snoozeTimes = mapOf("5 minutes" to 5, "10 minutes" to 10, "15 minutes" to 15)
                .map {  snooze ->
                    SnoozeTime(title = snooze.key, false)
                }
        ) }
    }



}