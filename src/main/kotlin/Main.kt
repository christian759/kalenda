import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.CalendarScreen
import java.time.LocalDate
import taskdb.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }

        CalendarScreen(
            onDateSelected = { date ->
                selectedDate = date
                println("Selected Date: $date") // You can later load tasks from DB here
            }
        )
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
