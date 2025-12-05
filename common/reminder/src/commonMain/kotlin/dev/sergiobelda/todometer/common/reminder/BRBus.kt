package dev.sergiobelda.todometer.common.reminder

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow



sealed interface ReminderEvent{
    data object TimeElapsed : ReminderEvent
}

object BRBus{

    private val _value = Channel<ReminderEvent>(capacity = Channel.BUFFERED)

    fun send(value: ReminderEvent) = _value.trySend(value)

    suspend fun listen(collector: FlowCollector<ReminderEvent>){
        _value.receiveAsFlow().collect{
            collector.emit(it)
        }
    }
}