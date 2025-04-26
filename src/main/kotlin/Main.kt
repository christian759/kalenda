import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.CalendarScreen
import java.time.LocalDate
import taskdb.*
import ui.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var tasks by remember { mutableStateOf(TaskRepository.getTasksByDate(selectedDate.toString())) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Calendar
            CalendarScreen(
                onDateSelected = { date ->
                    selectedDate = date
                    tasks = TaskRepository.getTasksByDate(selectedDate.toString())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Text
            Text(
                text = "Tasks for $selectedDate",
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Task Input
            TaskInput(
                onAddTask = { title ->
                    TaskRepository.addTask(Task(title = title, date = selectedDate.toString()))
                    tasks = TaskRepository.getTasksByDate(selectedDate.toString())
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tasks List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggleDone = {
                            TaskRepository.updateTask(task.copy(isDone = !task.isDone))
                            tasks = TaskRepository.getTasksByDate(selectedDate.toString())
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggleDone: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onToggleDone() }
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { onToggleDone() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = task.title,
            style = if (task.isDone) MaterialTheme.typography.body1.copy(textDecoration = TextDecoration.LineThrough)
            else MaterialTheme.typography.body2
        )
    }
}


@Composable
fun TaskInput(onAddTask: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Enter task...") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    onAddTask(text)
                    text = ""
                }
            }
        ) {
            Text("Add")
        }
    }
}


fun main() {
    DataBaseFactory.init()
    TaskRepository.createTable()

    application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}
