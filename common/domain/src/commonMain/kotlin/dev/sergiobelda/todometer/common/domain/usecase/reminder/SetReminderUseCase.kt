package dev.sergiobelda.todometer.common.domain.usecase.reminder

import dev.sergiobelda.todometer.common.domain.reminder.ITodoReminder

class SetReminderUseCase(val ITodoReminder: ITodoReminder) {

    operator fun invoke(timeInMilliseconds: Long, title: String, description: String){
        ITodoReminder.remind(timeInMilliseconds, title, description)
    }
}