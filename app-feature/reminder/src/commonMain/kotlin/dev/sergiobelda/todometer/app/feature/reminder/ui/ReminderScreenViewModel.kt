package dev.sergiobelda.todometer.app.feature.reminder.ui

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
import dev.sergiobelda.todometer.app.feature.reminder.model.DayOfWeekInitial
import dev.sergiobelda.todometer.app.feature.reminder.model.SnoozeTime
import dev.sergiobelda.todometer.common.domain.usecase.reminder.SetReminderUseCase
import dev.sergiobelda.todometer.common.reminder.AppAlarmManager
import dev.sergiobelda.todometer.common.reminder.BRBus
import dev.sergiobelda.todometer.common.resources.NavBundle
import dev.sergiobelda.todometer.common.resources.asNavBundle
import kotlinx.coroutines.launch
import kotlin.collections.set

class ReminderScreenViewModel(
    private val navBundleString: String,
    private val todoSetReminderUseCase: SetReminderUseCase
)
    : FonamentViewModel<ReminderState>(initialUIState = ReminderState()) {

   // private val navBundle = navBundleString.asNavBundle()
    private val repeatDaysSelections = mutableMapOf<String, Boolean>()


    private val days = mapOf("monday"  to 'M', "tuesday" to 'T',
        "wednesday" to 'W', "thursday" to 'T', "friday" to 'F', "saturday" to 'S',
        "sunday" to 'S').also { days ->
            days.keys.forEach { day ->
                repeatDaysSelections[day] = false
            }
    }

    init {
        println("navBundleString: $navBundleString")
        val navBundle = navBundleString.asNavBundle()
        // Alarm time passed from the add to-do screen
        updateUIState {
            it.copy(reminderBundle = navBundle)
        }
    }

    override fun handleEvent(event: FonamentEvent) {
        val navBundle = navBundleString.asNavBundle()
        when(event){
            is ReminderEvent.SetReminder -> setReminder(
                navBundle.getOrDefault("dueDate", 0L),
                navBundle.getOrDefault("title", "N/A"),
                navBundle.getOrDefault("description", "N/A"))
            is ReminderEvent.LoadRepeatDays -> loadRepeatDays()
            is ReminderEvent.LoadSnoozeTimes -> loadSnoozeTimes()
            is ReminderEvent.SelectRepeatDay -> toggleRepeatedDay(event.selection)
            is ReminderEvent.SelectSnoozeTime -> toggleSnoozeTime(event.selection)
            is ReminderEvent.PreSelectRepeatDays -> preAddSelectedDay(event.selection)
        }
    }


    fun loadRepeatDays(){
        updateUIState { it.copy(
            repeatedDayOfWeek = days.map { entity ->   DayOfWeekInitial(
                entity.value,
                entity.key,
                false)}.toList()
        ) }
    }

    fun preAddSelectedDay(selection: String){
        println("selection -> $selection")
        repeatDaysSelections[selection.lowercase()] = true
        updateRepeatAlarmDayUiState()
    }

    private fun updateRepeatAlarmDayUiState(){
        updateUIState {
            val repeatedDays = uiState.repeatedDayOfWeek.map { day ->
                day.copy(checked = repeatDaysSelections[day.key] == true)
            }
            it.copy(repeatedDayOfWeek = repeatedDays)
        }
    }

    private fun setReminder(timeInMilliseconds: Long, title: String, description: String){
        todoSetReminderUseCase.invoke(timeInMilliseconds, title, description)
       // AppAlarmManager.set(timeInMilliseconds)
    }

    fun toggleRepeatedDay(selection: String){
        repeatDaysSelections[selection] = repeatDaysSelections[selection] == false //|| repeatDaysSelections[selection] == null
        updateRepeatAlarmDayUiState()

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