package dev.sergiobelda.todometer.common.domain.reminder

interface ITodoReminder{

    fun remind(timeInMilliseconds: Long, title: String, description: String)
}


