package com.myself223.ulanagaybotiknakotline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.myself223.ulanagaybotiknakotline.databinding.ItemNoteBinding




// NoteAdapter
class NoteAdapter(
    private val listener: Clickable
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var list = arrayListOf<Notes>()


    fun addNote(list: ArrayList<Notes>) {
        this.list = list
        notifyDataSetChanged()
    }
    fun changeNote(note: Notes, position: Int) {
        list[position] = note
        notifyItemChanged(position)
    }

    fun getList(): List<Notes>? {
        return list
    }

    fun sortNotes() {
        list.sortBy { it.title }
        notifyDataSetChanged()
    }

    fun removeNotes(position: Int) {
        if (position in 0 until list.size) {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.binding.itemBtnEdit.setOnClickListener {
            listener.edit(holder.adapterPosition)
        }
        holder.binding.itemBtnDelete.setOnClickListener {
            listener.delete(holder.adapterPosition)
        }
        holder.binding.itemBtnShare.setOnClickListener {
            listener.share(list[position])
        }
    }

    class NoteViewHolder constructor(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(note: Notes) {
            binding.itemTvTitle.text = note.title
            binding.itemTvDes.text = note.desc
            binding.itemTvDate.text = note.date
        }
    }

    interface Clickable {
        fun edit(position: Int)
        fun delete(position: Int)
        fun share(note: Notes)
    }
}
