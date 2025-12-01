package dev.sergiobelda.todometer.common.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val reminder = module {
    singleOf(::TodoAlarmManager)
}