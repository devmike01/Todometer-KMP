package dev.sergiobelda.todometer.app.feature.reminder.ui

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotateRad
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.sergiobelda.fonament.presentation.ui.FonamentContent
import dev.sergiobelda.todometer.app.feature.reminder.exts.f
import dev.sergiobelda.todometer.app.feature.reminder.model.DayOfWeekInitial
import dev.sergiobelda.todometer.app.feature.reminder.model.SnoozeTime
import dev.sergiobelda.todometer.app.feature.reminder.res.Dimens
import dev.sergiobelda.todometer.app.feature.reminder.res.Strings
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.ArrowBack
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.ExpandLess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class ReminderContent : FonamentContent<ReminderState, ReminderContentState>() {
    @Composable
    override fun createContentState(uiState: ReminderState): ReminderContentState {
        return rememberReminderContentState()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        uiState: ReminderState,
        contentState: ReminderContentState,
        modifier: Modifier
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar({
                    Text("Set Alarm")
                }, navigationIcon = {
                    IconButton(onClick = {}){
                        Icon(Images.Icons.ArrowBack,
                            contentDescription = Strings.SetAlarm)
                    }
                })
            }) {
            LaunchedEffect(Unit){
                onEvent(ReminderEvent.LoadSnoozeTimes)
                onEvent(ReminderEvent.LoadRepeatDays)
            }
            ReminderScreen(uiState,
                contentState,
                onRepeatedDayClick ={ day ->
                    onEvent(ReminderEvent.SelectRepeatDay(day.id))
                },
                onSnoozeTime ={ snooze ->
                    onEvent(ReminderEvent.SelectSnoozeTime(snooze.id))
                },
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
                   contentState: ReminderContentState,
                   onRepeatedDayClick: (DayOfWeekInitial) -> Unit,
                   onSnoozeTime: (SnoozeTime) -> Unit,
                   modifier: Modifier = Modifier){

    Column(
        modifier = modifier.padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement
            .spacedBy(Dimens.Reminder.NormalItemVerticalSpacing.dp)){


        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 35.dp)){
            AlarmTimeClock(Modifier.align (Alignment.Center)){ time, period ->
                contentState.setTimePeriod(period)
                contentState.setTime(time)
            }
        }
        DigitalTimeClock(contentState.alarmTime, contentState.period)
        Spacer(Modifier.height(5.dp))
        RowWithTitle("Repeat", items = reminderUiState.repeatedDayOfWeek,){
            DayItem(it, isSelected = it.checked, onItemClick = onRepeatedDayClick)
        }
        Spacer(Modifier.height(5.dp))
        RowWithTitle(Strings.Snooze, items = reminderUiState.snoozeTimes){
            SnoozeItem(it, onSnoozeTime)
        }
    }
}

@Composable
fun DigitalTimeClock(timeInStr: String, period: String){
    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Reminder.DigitalTimeSpace.dp)) {
        Column {
            Text("Time")
            Text(timeInStr, style = MaterialTheme.typography.headlineMedium)
        }
        Column {
            Text("Period")
            Text(period, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun AlarmTimeClock(modifier: Modifier, onHumanTime: (String, String) -> Unit){
    val handColor = Color(0XFF263238)
    val now = Clock.System.now()
    val localTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val period = "AM".takeIf { localTime.hour in 0..11 } ?: "PM"
    //val twelveHour = m localTime.hour

    LaunchedEffect(Unit){
        onHumanTime("${localTime.hour.toString()
            .padEnd(1, '0')}:${localTime.minute
                .toString().padEnd(1, '0')}", period)
    }

    // Move to content state
    var secondsCounter by remember { mutableStateOf( localTime.second) }
    var minutesCounter by remember { mutableStateOf(localTime.minute) }
    var hourCounter by remember { mutableStateOf(localTime.hour) }

    LaunchedEffect(secondsCounter){
       // var seconds = 0
        launch(Dispatchers.IO) {
            while (true){
                delay(1_000)
                if (secondsCounter >= 60){
                    secondsCounter = 0
                    if (minutesCounter >= 60){
                        minutesCounter = 0
                        hourCounter += 1
                    }else{
                        minutesCounter += 1
                    }

                }else{
                    secondsCounter += 1
                }


                if (hourCounter >= 24){
                    hourCounter = 0
                }
            }
        }

    }
    Canvas(modifier = modifier.fillMaxWidth(.7f)
        .fillMaxHeight(.3f)){
        val rootRadius = 5.dp.toPx()
        val radius = size.minDimension / 2f
        val clockHand = 5.dp.toPx()
        val handCap = Stroke(width = clockHand, cap = StrokeCap.Butt)

        drawCircle(color = Color.White.copy(alpha = .4f),
            radius = radius,
            center = center
        )

        val secondsHandPath = Path().apply {
            moveTo(center.x, center.y)
            lineTo(center.x, center.y + 12.dp.toPx())
            addOval(Rect(
                center = center,
                radius = rootRadius * .6f
            ))

            addOval(Rect(
                center = center,
                radius = rootRadius * .3f
            ))
            lineTo(center.x , center.y- (radius - 10.dp.toPx()))
            close()
        }

        val minuteHandPath = Path().apply {
            val startY = center.y + rootRadius
            moveTo(center.x, startY)
            lineTo(center.x, startY -  (radius * .8f))
            close()
        }

        val hourHandPath = Path().apply {
            val startY = center.y + rootRadius
            moveTo(center.x, startY)
            lineTo(center.x, startY - (radius * .5f))
            close()
        }

        for (i in 0 until 12) {
            val angle = (i * 30).toRadians() // 12 dots = 30° steps

            // distance from center
            val offsetRadius = radius - 20.dp.toPx()

            val x = center.x + offsetRadius * cos(angle)
            val y = center.y + offsetRadius * sin(angle)

            drawCircle(
                color = handColor,
                radius =( 4.dp.takeIf { i % 3 == 0 } ?: 2.dp).toPx(),
                center = Offset(x, y)
            )
        }


        drawCircle(handColor, radius = rootRadius,
            style = Stroke(width = clockHand))

        rotateRad((minutesCounter * 6).toFloat().toRadians(), center){
            drawPath(minuteHandPath, handColor,
                style = handCap)
        }

        rotateRad((hourCounter % 12 * 30 + minutesCounter * .5f).toRadians(), center){
            drawPath(hourHandPath, handColor,
                style = handCap)
        }


        rotateRad(radians =(secondsCounter * 6).toFloat().toRadians(), center){
            drawPath(secondsHandPath, Color.Magenta,
                style = Stroke(width = clockHand * .2f, cap = StrokeCap.Round))
        }

    }
}


fun Float.toRadians(): Float = (this * PI / 180).toFloat()
fun Int.toRadians(): Float = (this * PI / 180).toFloat()

@Composable
fun <T> RowWithTitle(title: String, items: List<T>, listContent: @Composable (T) -> Unit){
    Text(title, style = MaterialTheme.typography.bodyLarge)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimens.Reminder.HorizontalSpace.dp),
        modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
        items.map {
            item { Box(modifier = Modifier.wrapContentSize()){
                listContent(it)
            } }
        }
    }
}

@Composable
fun SnoozeItem(snoozeTime: SnoozeTime, onItemClick: (SnoozeTime) -> Unit){
    TextButton(onClick = {
        onItemClick(snoozeTime)
    }, modifier = Modifier
        .wrapContentWidth()
        .drawBehind{
            drawRoundRect(
                Color.Yellow.takeIf { snoozeTime.checked } ?:
                Color.Gray.copy(alpha = .2f),
                style =  Stroke(width = 2f).takeIf { snoozeTime.checked }
                    ?: Fill,
                cornerRadius = CornerRadius(size.minDimension / 2f))
        }){
        Text(snoozeTime.title)
    }
}


@Composable
fun DayItem(day: DayOfWeekInitial, isSelected: Boolean, onItemClick: (DayOfWeekInitial) -> Unit){
    TextButton(onClick = { onItemClick(day) },
        modifier = Modifier.size(50.dp)
            .wrapContentHeight(align = Alignment.CenterVertically).drawBehind{
                drawCircle(
                    Color.Yellow.takeIf { isSelected } ?: Color.Gray.copy(alpha = .2f),
                    center = center,
                    radius = size.minDimension / 2,
                    style =  Stroke(width = 2f).takeIf { isSelected } ?: Fill)
            }){
        Text(day.title, textAlign = TextAlign.Center)
    }
}

//@Preview
//@Composable
//fun PreviewDayItem(){
//    listOf("S", "M","T", "W", "T", "F", "S").forEach {
//        DayItem(DayOfWeekInitial(it, it in listOf("M", "S", "W")), isSelected = f)
//    }
//}