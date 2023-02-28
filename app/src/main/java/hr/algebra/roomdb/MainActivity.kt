package hr.algebra.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.roomdb.database.AppDatabase
import hr.algebra.roomdb.databinding.ActivityMainBinding
import hr.algebra.roomdb.model.Task
import hr.algebra.roomdb.ui.TaskAdapter

class MainActivity : AppCompatActivity( ) {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db      : AppDatabase
    private lateinit var adapter : TaskAdapter

    override fun onCreate( savedInstanceState : Bundle? ) {
        super.onCreate( savedInstanceState )
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        db = AppDatabase( this )
        setupRecyclerView( )

        binding.bAdd.setOnClickListener {
            val taskName = binding.etTaskName.text.toString( )
            val taskDesc = binding.etTaskDescription.text.toString( )
            if( validateFields( taskName, taskDesc ) ) {
                db.taskDao( ).insertAll( Task( 0, taskName,taskDesc ) )
            }
        }
    }

    private fun setupRecyclerView( ) {
        binding.rvTasks.layoutManager = LinearLayoutManager( this )
        adapter = TaskAdapter( db.taskDao( ).getAll( ).toMutableList( ) )
        binding.rvTasks.adapter = adapter
    }

    override fun onCreateOptionsMenu( menu: Menu? ): Boolean {
        menuInflater.inflate( R.menu.menu, menu )
        return true
    }

    override fun onOptionsItemSelected( item : MenuItem): Boolean {
        if( item.itemId==R.id.menuDeleteAll ) {
            Toast
                .makeText( this, "Brišem sve zadatke!", Toast.LENGTH_SHORT )
                .show( )
        }
        return true
    }

    private fun validateFields( title : String, description : String ) : Boolean {
        if( title.isEmpty( ) )
            binding.etTaskName.error = "Title is missing"
        if( description.isEmpty( ) )
            binding.etTaskDescription.error = "Description is missing"

        return title.isEmpty( )||description.isEmpty( )
    }
}
