package com.myself223.ulanagaybotiknakotline
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.TokenWatcher
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.gson.Gson
import com.myself223.ulanagaybotiknakotline.databinding.FragmentNoteBinding


class NoteFragment : Fragment(), NoteAdapter.Clickable {

    private lateinit var binding: FragmentNoteBinding
    private var adapter: NoteAdapter? = null
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter(this)
        binding.rvNotes.adapter = adapter
        adapter?.addNote(App.db.getDao().getAllNotes() as ArrayList<Notes>)


        navController = findNavController()

        binding.btnAdd.setOnClickListener {
            navController?.navigate(R.id.addNoteFragment)
        }

        binding.btnSort.setOnClickListener {
            adapter?.sortNotes()
        }


        requireActivity().supportFragmentManager.setFragmentResultListener(
            "change_note", this
        ) { _, result ->
            val note: Notes? = result.getSerializable("edit_note") as? Notes
            note?.let {
                adapter?.changeNote(it, result.getInt("position"))
            }
        }
        binding.etSearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchNotes(newText!!)
                return false



            }

        })

    }
    private fun searchNotes(text:String){
        val searchNotes = arrayListOf<Notes>()
        if (searchNotes != null) {
            for (item in adapter?.getList()!!) {
                if (item.title?.toLowerCase()?.contains(text.toLowerCase())!!)
                    searchNotes.add(item)
            }
        }
        if (searchNotes.isEmpty()){
            Toast.makeText(requireContext(),"Tiloh",Toast.LENGTH_LONG)


        }
        else{
            adapter?.addNote(searchNotes)
        }
        if (text == ""){
            adapter?.addNote(MainActivity.getList())

        }

    }


    override fun edit(position: Int) {
        val noteList = adapter?.getList()
        if (noteList != null && position >= 0 && position < noteList.size) {
            val note: Notes = noteList[position]
            val bundle = Bundle()
            bundle.putString("changeTitle", note.title)
            bundle.putString("changeDesc", note.desc)
            bundle.putString("changeDate", note.date)
            bundle.putInt("position", position)
            navController?.navigate(R.id.addNoteFragment, bundle)
        }
    }

    override fun delete(position: Int) {

        val noteList = adapter?.getList()
        if (noteList != null && position >= 0 && position < noteList.size) {
            AlertDialog.Builder(requireContext())
                .setTitle("Удалить заметку?")
                .setMessage("Если удалить, никогда не вернешь!")
                .setNegativeButton("Нет", null)
                .setPositiveButton("Да, я уверен!") { _, _ ->
                }
                .show()
        }
    }

    private fun convertObjectToJson(note: Notes): String {
        val gson = Gson()
        return gson.toJson(note)
    }

    override fun share(note: Notes) {
        val gsonNote = convertObjectToJson(note)
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, gsonNote)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}