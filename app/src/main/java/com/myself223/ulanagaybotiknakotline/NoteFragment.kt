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
import java.io.Serializable


class NoteFragment : Fragment(), NoteAdapter.Clickable {
    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null
    private var btnAdd: Button? = null
    private var btnSort: Button? = null
    private var navHostFragment: NavHostFragment? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd = view.findViewById(R.id.btn_add)
        btnSort = view.findViewById(R.id.btn_sort)
        //Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.rv_notes)
        adapter = NoteAdapter(this)
        recyclerView?.adapter = adapter
        adapter!!.addNote(MainActivity.getList())
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as
                    NavHostFragment
        val navController = navHostFragment!!.navController

        btnAdd?.setOnClickListener {
            navController.navigate(R.id.addNoteFragment)
        }
        btnSort?.setOnClickListener{adapter!!.sortNotes() }
        /*   requireActivity().getSupportFragmentManager().setFragmentResultListener("new_note", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable("note");
                adapter.addNote(note);
            }
        });*/requireActivity().supportFragmentManager.setFragmentResultListener(
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

        val navController = navHostFragment?.navController
        navController?.navigate(R.id.addNoteFragment, bundle)
    }




}