package dev.sergiobelda.todometer.app.feature.reminder.exts

import androidx.compose.runtime.MutableState

fun <T> MutableState<T>.update(block: (T) -> T){
    this.value = block(this.value)
}