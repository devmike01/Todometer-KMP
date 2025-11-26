package dev.sergiobelda.todometer.app.feature.reminder.ui

import androidx.compose.runtime.Immutable
import dev.sergiobelda.fonament.presentation.ui.FonamentUIState

@Immutable
data class ReminderUiState(val hasSetAlarm: Boolean = false): FonamentUIState