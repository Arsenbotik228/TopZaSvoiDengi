package com.myself223.ulanagaybotiknakotline
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.myself223.ulanagaybotiknakotline.databinding.FragmentNoteBinding
import java.io.Serializable


class NoteFragment : Fragment(), NoteAdapter.Clickable {
    private lateinit var binding: FragmentNoteBinding
    private var adapter: NoteAdapter? = null
    private var NavHostFragment: NavHostFragment? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding1 = FragmentNoteBinding.inflate(inflater, container, false)
        binding = binding1

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NoteAdapter(this)
        binding.rvNotes?.adapter = adapter
        adapter!!.addNote(MainActivity.getList())
        NavHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as
                    NavHostFragment
        val navController = NavHostFragment!!.navController

        binding.btnAdd?.setOnClickListener {
            navController.navigate(R.id.addNoteFragment)
        }
        binding.btnSort?.setOnClickListener{adapter!!.sortNotes() }
    requireActivity().supportFragmentManager.setFragmentResultListener(
            "change_note", this
        ) { _, result ->
        val note: Notes? = result.getSerializable("edit_note") as Notes?
        note?.let { adapter!!.changeNote(it, result.getInt("position")) }


    }

    }

    override fun edit(position: Int) {
        val note: Notes = adapter!!.getList()!![position]
        val bundle = Bundle()
        note.title?.let {  Log.e("ololo",it )}
        bundle.putString("changeTitle", note.title)
        bundle.putString("changeDesc", note.desc)
        bundle.putString("changeDate", note.date)
        bundle.putInt("position", position)

        val navController = NavHostFragment?.navController
        navController?.navigate(R.id.addNoteFragment, bundle)
    }

    override fun delete(position: Int) {
        TODO("Not yet implemented")
    }


}