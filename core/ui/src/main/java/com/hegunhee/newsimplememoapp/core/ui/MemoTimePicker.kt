package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemoTimePickerDialog(
    onDismissDialog: () -> Unit,
    timePickerState: TimePickerState,
    onSelectTimeClick : (LocalTime) -> Unit)
{
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        onDismissRequest = onDismissDialog
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // time picker
            TimePicker(state = timePickerState)

            // buttons
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // dismiss button
                TextButton(onClick = onDismissDialog) {
                    Text(text = "취소")
                }

                // confirm button
                TextButton(
                    onClick = {
                        val hour = if(timePickerState.hour > 12)  {
                            timePickerState.hour - 12
                        }else {
                            timePickerState.hour
                        }
                        val ampm = if(timePickerState.hour > 12) {
                            "오후"
                        }else {
                            "오전"
                        }
                        val time = LocalTime.of(hour, timePickerState.minute)
                        onSelectTimeClick(time)
                        onDismissDialog()
                    }
                ) {
                    Text(text = "선택")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoDatePickerDialog(
    onDismissDialog : () -> Unit,
    datePickerState : DatePickerState,
    onSelectDateClick : (LocalDate) -> Unit
) {
    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = onDismissDialog,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { timeStamp ->
                    SimpleDateFormat("YYYY/MM/dd").let { dateFormat ->
                        val date = Date(timeStamp)
                        val (year, month, day) = dateFormat.format(date).split("/")
                            .map { it.toInt() }
                        onSelectDateClick(LocalDate.of(year,month,day))
                    }
                    onDismissDialog()
                }
            }) {
                Text(text = "선택")
            }
        },
    ) {
        DatePicker(datePickerState)
    }
}