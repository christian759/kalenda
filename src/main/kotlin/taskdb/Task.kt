package taskdb

data class Task(
    val id: Int? = null,
    val title: String,
    val date: String,
    val isDone: Boolean = false
)