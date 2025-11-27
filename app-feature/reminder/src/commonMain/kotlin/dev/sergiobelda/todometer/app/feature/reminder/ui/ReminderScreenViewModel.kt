package dev.sergiobelda.todometer.app.feature.reminder.ui

import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
import dev.sergiobelda.todometer.app.feature.reminder.model.DayOfWeekInitial
import dev.sergiobelda.todometer.app.feature.reminder.model.SnoozeTime

class ReminderScreenViewModel : FonamentViewModel<ReminderState>(initialUIState = ReminderState()) {

    private val repeatDaysSelections = mutableMapOf<Int, Boolean>()

    override fun handleEvent(event: FonamentEvent) {
        when(event){
            is ReminderEvent.LoadRepeatDays -> loadRepeatDays()
            is ReminderEvent.LoadSnoozeTimes -> loadSnoozeTimes()
            is ReminderEvent.SelectRepeatDay -> toggleRepeatedDay(event.selection)
            is ReminderEvent.SelectSnoozeTime -> toggleSnoozeTime(event.selection)
        }
    }


    fun loadRepeatDays(){
        updateUIState { it.copy(
            repeatedDayOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")
                .mapIndexed { id,  initial ->  DayOfWeekInitial(initial, id, false) }
        ) }
    }

    fun toggleRepeatedDay(selection: Int){
        repeatDaysSelections[selection] = repeatDaysSelections[selection] == false || repeatDaysSelections[selection] == null
        updateUIState {
            val repeatedDays = uiState.repeatedDayOfWeek.map { day ->
                day.copy(checked = repeatDaysSelections[day.id] == true)
            }
            it.copy(repeatedDayOfWeek = repeatedDays)
        }

    }

    fun loadSnoozeTimes(){
        updateUIState {
            it.copy(snoozeTimes = listOf("5 minutes","10 minutes", "15 minutes")
                .mapIndexed { index,   snooze ->
                    SnoozeTime(title = snooze, false, index)
                }
        ) }
    }

    fun toggleSnoozeTime(selection: Int){
      //  snoozeSelections[selection] = snoozeSelections[selection] == false || snoozeSelections[selection] == null
        updateUIState {
            it.copy(snoozeTimes = it.snoozeTimes.map { st -> st.copy(checked = selection == st.id) })
        }
    }



}