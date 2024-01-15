package com.myself223.ulanagaybotiknakotline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.myself223.ulanagaybotiknakotline.databinding.ActivityMainBinding
import com.myself223.ulanagaybotiknakotline.databinding.ItemNoteBinding

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.setFragmentResultListener(
            "new_note", this
        ) { requestKey, result ->
            val note: Notes? = result.getSerializable("note") as Notes?
            note?.let { mainList.add(it) }
        }

    }
    companion object{
        private val mainList = arrayListOf<Notes>()

    fun getList(): ArrayList<Notes>{
        return mainList
    }
    }
}



