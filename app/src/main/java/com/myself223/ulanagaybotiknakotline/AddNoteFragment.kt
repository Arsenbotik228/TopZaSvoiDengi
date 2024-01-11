package com.myself223.ulanagaybotiknakotline

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteFragment : Fragment() {
    private var etTitle: EditText? = null
    private  var etDesc:EditText? = null
    private  var etDate:EditText? = null
    private var btnSave: Button? = null
    private var imgUser:ImageView? = null
    private var navHostFragment:NavHostFragment? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etTitle = view.findViewById(R.id.et_title)
        etDesc = view.findViewById(R.id.et_des)
        btnSave = view.findViewById(R.id.btn_save)
        etDate = view.findViewById(R.id.et_date)
        imgUser = view.findViewById(R.id.img_add)
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as
                    NavHostFragment
        val navController = navHostFragment!!.navController
        val bundle = Bundle()
        if (arguments != null) {
            etTitle?.setText(requireArguments().getString("changeTitle"))
            etDesc?.setText(requireArguments().getString("changeDesc"))
            etDate?.setText(requireArguments().getString("changeDate"))
            //Инициализация изменения заметок
            btnSave?.setOnClickListener(View.OnClickListener {
                val changeTitle = etTitle?.text.toString()
                val changeDesck = etDesc?.text.toString()
                val position = requireArguments().getInt("position")
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val changeDate = sdf.format(Date())
                val note = Notes(changeTitle, changeDesck, changeDate, "")
                bundle.putSerializable("edit_note", note)
                bundle.putInt("position", position)
                requireActivity().supportFragmentManager.setFragmentResult("change_note", bundle)
                requireActivity().supportFragmentManager.popBackStack()
            })
        } else {
            //Инициализация сохранения новых заметок
            btnSave?.setOnClickListener(View.OnClickListener {
                val title = etTitle?.text.toString()
                val desc = etDesc?.text.toString()
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val date = sdf.format(Date())
                val note = Notes(title, desc, date, "")
                bundle.putSerializable("note", note)
                requireActivity().supportFragmentManager.setFragmentResult("new_note", bundle)
                requireActivity().supportFragmentManager.popBackStack()
            })
        }
        imgUser?.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data?.data
                val imageStream = requireActivity().contentResolver.openInputStream(
                    imageUri!!
                )
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                imgUser!!.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    }

