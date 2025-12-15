package dev.sergiobelda.todometer.common.core.di

import dev.sergiobelda.todometer.common.data.reminder.TodoReminder
import dev.sergiobelda.todometer.common.di.reminderModule
import dev.sergiobelda.todometer.common.domain.reminder.ITodoReminder
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val todoReminderModule = module {
    single<ITodoReminder>{
        TodoReminder(get())
    }
} + reminderModule