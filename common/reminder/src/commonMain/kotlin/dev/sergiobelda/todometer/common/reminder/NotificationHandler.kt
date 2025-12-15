package dev.sergiobelda.todometer.common.reminder

import org.koin.core.module.Module


interface NotificationHandler {

    fun setUpNotification(title: String, body: String)

}

