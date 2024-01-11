package com.myself223.ulanagaybotiknakotline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.myself223.ulanagaybotiknakotline.databinding.ItemNoteBinding


class NoteAdapter(private val  listener: Clickable) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var list = arrayListOf<Notes>()
    private var inflater: LayoutInflater? = null

    fun addNote(list: ArrayList<Notes>) {
        this.list = list
        notifyDataSetChanged()
    }
    fun changeNote(note: Notes, position: Int){
        list[position] = note
        notifyItemChanged(position)
    }
    fun getList(): List<Notes>?{
        return list
    }

    fun sortNotes() {
        list.sortBy { it.title }


        notifyDataSetChanged()
    }

    fun remoteNotes(){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.btnEdit?.setOnClickListener{
            listener.edit(holder.adapterPosition)
        }
    }

    class NoteViewHolder constructor(val binding: ItemNoteBinding) : ViewHolder(binding.root) {

        private var title: TextView? = null
        private var desc:TextView? = null
        private var date:TextView? = null
         var btnEdit: ImageView? = null
        fun onBind(note: Notes) {
            btnEdit = itemView.findViewById(R.id.item_btn_edit)
            title = itemView.findViewById(R.id.item_tv_title)
            desc = itemView.findViewById(R.id.item_tv_des)
            date = itemView.findViewById(R.id.item_tv_date)


            title?.text = note.title
            desc?.text = note.desc
            date?.text = note.date

        }
    }
    interface  Clickable{
        fun edit(position: Int)
    }
    }



