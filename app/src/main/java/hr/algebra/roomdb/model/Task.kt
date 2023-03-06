package hr.algebra.roomdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "task_items" )
data class Task(
    @PrimaryKey( autoGenerate = true ) var id          : Long,
    @ColumnInfo(name = "title")        var title       : String,
    @ColumnInfo(name = "description")  var description : String
)