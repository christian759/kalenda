package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun CalendarScreen(
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    // Current date and state management for the selected month/year
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val currentMonth = currentDate.month
    val currentYear = currentDate.year
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    val firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Adjust to start on Sunday

    // Generate the calendar grid
    val totalDaysInMonth = firstDayOfMonth.lengthOfMonth()
    val daysInCalendar = MutableList(42) { "" }

    for (i in 0 until totalDaysInMonth) {
        daysInCalendar[i + firstDayOfWeek] = (i + 1).toString()
    }

    // Month and year format
    val monthYearText = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))

    Column(
        modifier = modifier
    ) {
        // Month and Year Header with navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Previous month button
            IconButton(
                onClick = {
                    currentDate = currentDate.minusMonths(1)
                },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray.copy(alpha = 0.2f), CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous Month",
                    tint = Color.Black
                )
            }

            // Month and Year Text
            Text(
                text = monthYearText,
                style = MaterialTheme.typography.h6.copy(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            // Next month button
            IconButton(
                onClick = {
                    currentDate = currentDate.plusMonths(1)
                },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray.copy(alpha = 0.2f), CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next Month",
                    tint = Color.Black
                )
            }
        }

        // Days of the week header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        color = Color.Gray,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        // Calendar days grid
        val rows = daysInCalendar.chunked(7)
        rows.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEachIndexed { index, day ->
                    if (day.isNotEmpty()) {
                        val currentDay = LocalDate.of(currentYear, currentMonth, day.toInt())
                        val isToday = currentDay == LocalDate.now()

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .height(40.dp)
                                .background(
                                    if (currentDay == selectedDate)
                                        Color.Blue.copy(alpha = 0.3f)
                                    else if (isToday)
                                        Color.Yellow.copy(alpha = 0.5f) // Highlight today's date with yellow
                                    else Color.Transparent,
                                    shape = CircleShape
                                )
                                .border(1.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
                                .clickable {
                                    // Handle the date click
                                    selectedDate = currentDay
                                    onDateSelected(selectedDate)
                                }
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                // Day number text
                                Text(
                                    text = day,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = if (currentDay == selectedDate)
                                            Color.White
                                        else if (isToday) Color.Black else Color.Black
                                    )
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
