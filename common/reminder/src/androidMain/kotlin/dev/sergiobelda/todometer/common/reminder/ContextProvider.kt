package dev.sergiobelda.todometer.common.reminder

import android.app.Activity
import android.app.Application
import android.content.Context

interface ContextProvider{
    fun onCreateContext()
}

class ContextProviderImpl(val appContext: Application): ContextProvider {

    override fun onCreateContext() {
      //  AppAlarmManager.appContext = appContext
    }

}

