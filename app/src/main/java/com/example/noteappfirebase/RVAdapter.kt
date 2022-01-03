package com.example.noteappfirebase
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappfirebase.databinding.ItemRowBinding
class RVAdapter(val activity: MainActivity, var notesList: List<Notes>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(var binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val aNote = notesList[position]

        holder.binding.apply {
            tvNote.text = aNote.note

            edtNote.setOnClickListener{
                activity.DialogEdit(aNote)
            }

            delNote.setOnClickListener{
                activity.DialogDel(aNote)
            }
        }
    }

    override fun getItemCount(): Int = notesList.size
}