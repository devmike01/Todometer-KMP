package dev.sergiobelda.todometer.common.reminder

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TodoAlarmManager{
    fun set(timeInMilliseconds: Long)
}