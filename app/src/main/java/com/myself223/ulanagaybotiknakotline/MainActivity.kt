package com.myself223.ulanagaybotiknakotline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val mainList = arrayListOf<Notes>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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



