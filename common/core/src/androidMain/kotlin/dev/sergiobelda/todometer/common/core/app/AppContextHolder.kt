package dev.sergiobelda.todometer.common.core.app

import android.app.Application
import dev.sergiobelda.todometer.common.database.DriverFactory
import dev.sergiobelda.todometer.common.preferences.PreferencesFactory
import dev.sergiobelda.todometer.common.reminder.AppAlarmManager

object AppContextHolder{

    fun init(context: Application){
        PreferencesFactory.appContext = context
        DriverFactory.appContext = context
        AppAlarmManager.appContext = context
    }

}