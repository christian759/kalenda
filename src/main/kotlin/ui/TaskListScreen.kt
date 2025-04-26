package ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import taskdb.*


@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onAddTask: (String) -> Unit,
    onToggleDone: (Task) -> Unit
) {
    var newTaskTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // New Task Input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("New Task") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newTaskTitle.isNotBlank()) {
                    onAddTask(newTaskTitle)
                    newTaskTitle = ""
                }
            }) {
                Text("+ Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (tasks.isEmpty()) {
            Text(
                text = "No tasks yet.",
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(tasks) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(if (task.isDone) Color(0xFFE0E0E0) else Color.Transparent)
                            .clickable { onToggleDone(task) }
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { onToggleDone(task) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.h2
                        )
                    }
                }
            }
        }
    }
}
