package taskdb

import org.jetbrains.exposed.sql.Table

object TaskTable : Table("tasks") {
    val id = integer("id").autoIncrement()
    var title = varchar("title", 255)
    val date = varchar("date", 10)
    val isDone = bool("is_done").default(false)

    override val primaryKey = PrimaryKey(id)
}