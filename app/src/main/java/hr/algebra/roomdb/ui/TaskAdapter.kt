package hr.algebra.roomdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.roomdb.databinding.ItemBinding
import hr.algebra.roomdb.model.Task

interface OnItemClickListener {
    fun onItemClick( item : Task )
}

class TaskAdapter( val tasks : MutableList< Task >, val listener : OnItemClickListener ) : RecyclerView.Adapter< TasksViewHolder >( ){

    fun add( task : Task ) {
        tasks.add( task )
    }
    fun delete( task : Task ) {
        tasks.remove( task )
    }
    fun deleteAll(  ) {
        tasks.clear( )
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ) : TasksViewHolder {
        val taskBinding = ItemBinding
                            .inflate( LayoutInflater.from( parent.context ), parent, false )
        return TasksViewHolder( taskBinding )
    }

    override fun onBindViewHolder( holder: TasksViewHolder, position : Int ) {
        val task = tasks[position]
        holder.tvTitle.text       = task.title
        holder.tvDescription.text = task.description
        holder.itemView.setOnClickListener {
            listener.onItemClick( task )
        }
    }

    override fun getItemCount( ): Int = tasks.size

}

class TasksViewHolder( private val binding : ItemBinding ) : RecyclerView.ViewHolder( binding.root ) {
    val tvTitle       = binding.tvTitle
    val tvDescription = binding.tvDescription
}