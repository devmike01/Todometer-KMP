package dev.sergiobelda.todometer.common.reminder

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val modules = module {
    single<AppAlarmManager>{ AppAlarmManagerImpl(context = androidContext()) }
}