package hr.algebra.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.roomdb.database.AppDatabase
import hr.algebra.roomdb.database.AppExecutors
import hr.algebra.roomdb.databinding.ActivityMainBinding
import hr.algebra.roomdb.model.Task
import hr.algebra.roomdb.ui.OnItemClickListener
import hr.algebra.roomdb.ui.TaskAdapter


class MainActivity : AppCompatActivity( ) {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db      : AppDatabase
    private lateinit var adapter : TaskAdapter

    private val TAG = "MainActivity"

    override fun onCreate( savedInstanceState : Bundle? ) {
        super.onCreate( savedInstanceState )
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        Log.i( TAG, "Idem po bazu podataka!" )
        db = AppDatabase( this )
        Log.i( TAG, "Dohvatio sam bazu podataka!" )
        setupRecyclerView( )

        observeDataChanged( )

        binding.bAdd.setOnClickListener {
            val taskName = binding.etTaskName.text.toString( )
            val taskDesc = binding.etTaskDescription.text.toString( )
            if( validateFields( taskName, taskDesc ) ) {
                Log.i( TAG, "Inserting row to the database." )
                val task = Task( 0, taskName,taskDesc )
                AppExecutors.instance?.diskIO( )?.execute {
                    db.taskDao( ).insertAll( task )
//                    adapter.add( task )
//                    updateList( )
                    clearFields( )
                }
            }
        }
    }

    private fun observeDataChanged( ) {
        db.taskDao( ).getAll( ).observe( this ) {
            Log.i( TAG, "Something has been changed" )
            adapter.tasks = it
        }
    }

    private fun setupRecyclerView( ) {
        binding.rvTasks.layoutManager = LinearLayoutManager( this )
        adapter = TaskAdapter( object : OnItemClickListener {
                        override fun onItemClick( task : Task ) {
                            AppExecutors.instance?.diskIO( )?.execute {
                                Log.i( TAG, "Ubijam task: $task" )
                                db.taskDao( ).delete( task )
                            }
                        }
                    } )
        binding.rvTasks.adapter = adapter
    }

    override fun onCreateOptionsMenu( menu: Menu? ): Boolean {
        menuInflater.inflate( R.menu.menu, menu )
        return true
    }

    override fun onOptionsItemSelected( item : MenuItem): Boolean {
        if( item.itemId==R.id.menuDeleteAll ) {
            AppExecutors.instance?.diskIO( )?.execute {
                db.taskDao( ).deleteAll( )
//              adapter.deleteAll( )
//              updateList( )
            }
        }
        return true
    }

    private fun validateFields( title : String, description : String ) : Boolean {
        if( title.isEmpty( ) )
            binding.etTaskName.error = "Title is missing"
        if( description.isEmpty( ) )
            binding.etTaskDescription.error = "Description is missing"

        return !( title.isEmpty( )||description.isEmpty( ) )
    }

    private fun clearFields( ) {
        AppExecutors.instance?.mainThread( )?.execute {
            binding.etTaskName.setText("")
            binding.etTaskDescription.setText("")
            binding.etTaskName.requestFocus()
        }
    }
/*
    private fun updateList( ) {
        AppExecutors.instance?.mainThread( )?.execute {
            adapter.notifyDataSetChanged( )
        }
    }

    private fun setupRecyclerView( ) {
        binding.rvTasks.layoutManager = LinearLayoutManager( this )
        AppExecutors.instance?.diskIO( )?.execute {
            val tasks = db.taskDao( ).getAll( ).toMutableList( )
            adapter = TaskAdapter(
                tasks,
                object : OnItemClickListener {
                    override fun onItemClick( task : Task ) {
                        AppExecutors.instance?.diskIO( )?.execute {
                            Log.i( TAG, "Ubijam task: $task" )
                            db.taskDao( ).delete( task )
                            adapter.delete( task )
                            updateList( )
                        }
                    }
                }
            )
            AppExecutors.instance?.mainThread( )?.execute {
                binding.rvTasks.adapter = adapter
            }
        }
    }
 */
}