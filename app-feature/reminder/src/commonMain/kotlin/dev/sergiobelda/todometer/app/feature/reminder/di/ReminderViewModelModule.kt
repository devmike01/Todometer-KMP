package dev.sergiobelda.todometer.app.feature.reminder.di


import dev.sergiobelda.fonament.di.koin.fonamentViewModel
import dev.sergiobelda.fonament.di.koin.fonamentViewModelOf
import dev.sergiobelda.todometer.app.feature.reminder.ui.ReminderScreenViewModel
import org.koin.dsl.module


val reminderViewModelModule = module{
    fonamentViewModelOf(::ReminderScreenViewModel)
}