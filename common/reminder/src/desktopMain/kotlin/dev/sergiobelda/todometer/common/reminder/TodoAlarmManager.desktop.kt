package dev.sergiobelda.todometer.common.reminder

actual interface AppAlarmManager  {
    actual fun set(timeInMilliseconds: Long)
}