package hr.algebra.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.algebra.roomdb.dao.TaskDao
import hr.algebra.roomdb.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao( ) : TaskDao

    companion object {
        @Volatile private var instance : AppDatabase? = null
                  private val LOCK = Any( )

        operator fun invoke( context: Context ) = instance ?: synchronized( LOCK ) {
            instance ?: buildDatabase( context ).also { instance = it }
        }

        private fun buildDatabase( context : Context) = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "tasks.db"
            )
//          .allowMainThreadQueries( )
            .build( )
    }
}
