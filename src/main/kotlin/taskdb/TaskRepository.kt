package taskdb

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TaskRepository {

    fun createTable(){
        transaction {
            SchemaUtils.create(TaskTable)
        }
    }

    fun addTask(task: Task){
        transaction {
            TaskTable.insert {
                it[title] = task.title
                it[date] = task.date
                it[isDone] = task.isDone
            }
        }
    }

    fun getTasksByDone(dateString: String): List<Task> {
        return transaction {
            TaskTable.selectAll()
                .where { TaskTable.date eq dateString }
                .map {
                    Task(
                        id = it[TaskTable.id],
                        title = it[TaskTable.title],
                        date = it[TaskTable.date],
                        isDone = it[TaskTable.isDone]
                    )
                }
        }
    }

    fun updateTask(task: Task){
        transaction {
            task.id?.let { id ->
                TaskTable.update({ TaskTable.id  eq id}){
                    it[title] = task.title
                    it[date] = task.date
                    it[isDone] = task.isDone
                }
            }
        }
    }

    fun deleteTask(taskId: Int){
        transaction {
            TaskTable.deleteWhere { TaskTable.id eq taskId }
        }
    }
}