package taskdb

import org.jetbrains.exposed.sql.Database
import java.io.File

object DataBaseFactory {
    fun init(){
        val dbFile = File("kalenda.db")
        Database.connect(
            url = "jdbc:sqlite:${dbFile.absolutePath}",
            driver = "org.sqlite.JDBC"
        )
    }
}