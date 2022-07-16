package com.feyzaurkut.todoapp.presentation.home

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.data.model.Note
import com.feyzaurkut.todoapp.databinding.ItemHomeBinding
import com.feyzaurkut.todoapp.utils.OnClickListener
import com.feyzaurkut.todoapp.utils.SharedPreferences
import dev.sasikanth.colorsheet.ColorSheet

class ToDoListRecyclerAdapter(
    private val context: Context,
    private val activity: FragmentActivity,
    private val notesList: ArrayList<Note>,
    private val onClickListener: OnClickListener
) : ListAdapter<Note, ToDoListRecyclerAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHomeBinding.bind(itemView)
        fun bind(note: Note) {
            with(binding) {
                tvTitle.text = note.title
                tvDescription.text = note.description
                clItem.setOnClickListener {
                    onClickListener.onClick(adapterPosition)
                }

                initColorPickerListener(note)
                val sharedColor = note.id?.let { SharedPreferences(context).getColorInt(it) }
                val gd = GradientDrawable()
                gd.cornerRadius = 17f
                if (sharedColor != null) {
                    gd.setStroke(3, sharedColor)
                    tvTitle.setTextColor(sharedColor)
                    tvDescription.setTextColor(sharedColor)
                }
                clItem.background = gd


                initCheckboxListener(note)
                val isChecked = note.id?.let { SharedPreferences(context).getCheckboxBoolean("$it*") }
                if (isChecked != null) {
                    checkbox.isChecked = isChecked
                }
            }
        }

        private fun initColorPickerListener(note: Note){
            with(binding) {
                ivColorSelector.setOnClickListener {
                    val colors = context.resources.getIntArray(R.array.colors)
                    activity.let {
                        ColorSheet().colorPicker(
                            colors = colors,
                            noColorOption = true,
                            listener = { color ->
                                val gd = GradientDrawable()
                                gd.cornerRadius = 17f
                                gd.setStroke(3, color)
                                clItem.background = gd
                                tvTitle.setTextColor(color)
                                tvDescription.setTextColor(color)
                                note.id?.let { it -> SharedPreferences(context).putColorInt(it, color) }
                            })
                            .show(it.supportFragmentManager)
                    }
                }
            }
        }

        private fun initCheckboxListener(note: Note) {
            binding.checkbox.setOnClickListener {
                if (binding.checkbox.isChecked)
                    note.id?.let { it -> SharedPreferences(context).putCheckboxBoolean("$it*", true) }
                else
                    note.id?.let { it -> SharedPreferences(context).putCheckboxBoolean("$it*", false) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notesList[position])

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

}

class DiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}