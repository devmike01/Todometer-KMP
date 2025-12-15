package dev.sergiobelda.todometer.common.di

import dev.sergiobelda.todometer.common.reminder.AndroidAppAlarmManager
import dev.sergiobelda.todometer.common.reminder.AndroidNotificationHandler
import dev.sergiobelda.todometer.common.reminder.AppAlarmManager
import dev.sergiobelda.todometer.common.reminder.NotificationHandler
import dev.sergiobelda.todometer.common.usecase.SetReminderUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val reminderModule: Module = module {
    single<NotificationHandler> { AndroidNotificationHandler(androidContext()) }
    single <AppAlarmManager>{ AndroidAppAlarmManager(androidContext()) }
    factory<SetReminderUseCase> { SetReminderUseCase(get()) }
}