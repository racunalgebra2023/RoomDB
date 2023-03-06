package hr.algebra.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hr.algebra.roomdb.model.Task

@Dao
interface TaskDao {

    @Query( "SELECT * FROM task_items" )
    fun getAll( ) : List< Task >

    @Query( "SELECT * FROM task_items WHERE title=:name" )
    fun loadByName( name : String ): List< Task >

    @Insert
    fun insertAll( vararg tasks : Task )

    @Delete
    fun delete( task: Task )

    @Query( "DELETE FROM task_items" )
    fun deleteAll( )
}
