package dev.sergiobelda.todometer.app.feature.reminder.model

data class DayOfWeekInitial(val title: String,
                            val id: Int,
                            val checked: Boolean)

data class SnoozeTime(val title: String, val checked: Boolean,
                      val id: Int)