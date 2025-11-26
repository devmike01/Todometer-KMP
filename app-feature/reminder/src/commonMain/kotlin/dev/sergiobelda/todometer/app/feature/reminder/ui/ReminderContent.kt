package dev.sergiobelda.todometer.app.feature.reminder.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.sergiobelda.fonament.presentation.ui.FonamentContent
import dev.sergiobelda.todometer.app.feature.reminder.model.DayOfWeekInitial
import dev.sergiobelda.todometer.app.feature.reminder.model.SnoozeTime
import dev.sergiobelda.todometer.app.feature.reminder.res.Dimens


class ReminderContent : FonamentContent<ReminderState, ReminderContentState>() {
    @Composable
    override fun createContentState(uiState: ReminderState): ReminderContentState {
        return rememberReminderContentState()
    }

    @Composable
    override fun Content(
        uiState: ReminderState,
        contentState: ReminderContentState,
        modifier: Modifier
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            LaunchedEffect(Unit){
                onEvent(ReminderEvent.LoadSnoozeTimes)
                onEvent(ReminderEvent.LoadRepeatDays)
            }
            ReminderScreen(uiState,
                onRepeatedDayClick ={ day ->
                    onEvent(ReminderEvent.SelectRepeatDay(day.id))
                },
                onSnoozeTime ={},
                modifier = Modifier.padding(it))
        }
    }


}


@Composable
fun rememberReminderContentState(): ReminderContentState = rememberSaveable(
    saver = ReminderContentState.Saver()){
    ReminderContentState()
}

@Composable
fun ReminderScreen(reminderUiState: ReminderState,
                   onRepeatedDayClick: (DayOfWeekInitial) -> Unit,
                   onSnoozeTime: (SnoozeTime) -> Unit,
                   modifier: Modifier = Modifier){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement
        .spacedBy(Dimens.Reminder.NormalItemVerticalSpacing.dp)){
        RowWithTitle("Repeat", items = reminderUiState.repeatedDayOfWeek,
            onItemClick = onRepeatedDayClick){
            DayItem(it, isSelected = it.checked,)
        }

        RowWithTitle("Snooze", items = reminderUiState.snoozeTimes,
            onItemClick = onSnoozeTime){
            SnoozeItem(it)
        }
    }
}

@Composable
fun AlarmTimeClock(){
    Canvas(modifier = Modifier.fillMaxWidth(.7f)
        .fillMaxHeight(.3f)){
        drawCircle(color = Color.Gray.copy(alpha = .6f),
            radius = size.width /2f
        )
    }
}

@Composable
fun <T> RowWithTitle(title: String, items: List<T>,
                     onItemClick: (T) -> Unit, listContent: @Composable (T) -> Unit){
    Text(title, style = MaterialTheme.typography.bodyLarge)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimens.Reminder.HorizontalSpace.dp),
        modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
        items.map {
            item { Box(modifier = Modifier.wrapContentSize()
                .padding(Dimens.Reminder.DayPadding.dp)
                .clickable{
                    onItemClick(it)
                }){
                listContent(it)
            } }
        }
    }
}

@Composable
fun SnoozeItem(snoozeTime: SnoozeTime){
    Box(modifier = Modifier
        .wrapContentWidth()
        .drawBehind{
            drawRoundRect(
                Color.Yellow.takeIf { snoozeTime.checked } ?:
                Color.Gray.copy(alpha = .2f),
                style =  Stroke(width = 1f).takeIf { snoozeTime.checked }
                    ?: Fill,
                cornerRadius = CornerRadius(size.minDimension / 2f))
        }
        .padding(vertical = Dimens.Reminder.HorizontalSpace.dp,
            horizontal = Dimens.Reminder.HorizontalSnoozeInnerItemPadding.dp)){
        Text(snoozeTime.title)
    }
}


@Composable
fun DayItem(day: DayOfWeekInitial, isSelected: Boolean){

    Text(day.title, textAlign = TextAlign.Center,
        modifier = Modifier.size(50.dp)
            .wrapContentHeight(align = Alignment.CenterVertically).drawBehind{
            drawCircle(
                Color.Yellow.takeIf { isSelected } ?: Color.Gray.copy(alpha = .2f),
                center = center,
                radius = size.minDimension / 2,
                style =  Stroke(width = 1f).takeIf { isSelected } ?: Fill)
        }.padding(10.dp))
}

//@Preview
//@Composable
//fun PreviewDayItem(){
//    listOf("S", "M","T", "W", "T", "F", "S").forEach {
//        DayItem(DayOfWeekInitial(it, it in listOf("M", "S", "W")), isSelected = f)
//    }
//}