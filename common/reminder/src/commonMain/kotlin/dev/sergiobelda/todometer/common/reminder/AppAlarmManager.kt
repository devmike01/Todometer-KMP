package dev.sergiobelda.todometer.common.reminder

import org.koin.core.module.Module

//@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppAlarmManager{

    fun set(timeInMilliseconds: Long)
}


