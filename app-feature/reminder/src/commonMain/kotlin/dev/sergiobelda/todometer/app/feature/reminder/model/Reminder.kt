package dev.sergiobelda.todometer.app.feature.reminder.model

data class DayOfWeekInitial(val title: Char,
                            val key: String,
                            val checked: Boolean)

data class SnoozeTime(val title: String, val checked: Boolean,
                      val id: Int)