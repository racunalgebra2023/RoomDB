package hr.algebra.roomdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.roomdb.databinding.ItemBinding
import hr.algebra.roomdb.model.Task

class TaskAdapter( val tasks : MutableList< Task > ) : RecyclerView.Adapter< TasksViewHolder >( ){

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ) : TasksViewHolder {
        val taskBinding = ItemBinding
                            .inflate( LayoutInflater.from( parent.context ), parent, false )
        return TasksViewHolder( taskBinding )
    }

    override fun onBindViewHolder( holder: TasksViewHolder, position : Int ) {
        val todo = tasks[position]
        holder.tvTitle.text       = todo.title
        holder.tvDescription.text = todo.description
    }

    override fun getItemCount( ): Int = tasks.size

}

class TasksViewHolder( private val binding : ItemBinding ) : RecyclerView.ViewHolder( binding.root ) {
    val tvTitle       = binding.tvTitle
    val tvDescription = binding.tvDescription
}