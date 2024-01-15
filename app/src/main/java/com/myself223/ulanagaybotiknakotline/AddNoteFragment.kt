package com.myself223.ulanagaybotiknakotline

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.myself223.ulanagaybotiknakotline.databinding.FragmentAddNoteBinding
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        val bundle = Bundle()

        if (arguments != null) {
            binding.etTitle.setText(requireArguments().getString("changeTitle"))
            binding.etDes.setText(requireArguments().getString("changeDesc"))
            binding.etDate.setText(requireArguments().getString("changeDate"))

            binding.btnSave.setOnClickListener {
                val changeTitle = binding.etTitle.text.toString()
                val changeDesc = binding.etDes.text.toString()
                val position = requireArguments().getInt("position")
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val changeDate = sdf.format(Date())
                val note = Notes(null, changeTitle, changeDate, changeDesc)
                bundle.putSerializable("edit_note", note)
                bundle.putInt("position", position)
                requireActivity().supportFragmentManager.setFragmentResult("change_note", bundle)
                requireActivity().supportFragmentManager.popBackStack()
            }
        } else {
            binding.btnSave.setOnClickListener {
                val title = binding.etTitle.text.toString()
                val desc = binding.etDes.text.toString()
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val date = sdf.format(Date())

                val notes = Notes(title = title, date = date, desc = desc)
                App.db.getDao().insertNotes(notes)
                navController?.navigateUp()
            }
        }

        binding.imgAdd.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                if (data?.data != null) {
                    val imageUri = data.data
                    val imageStream = requireActivity().contentResolver.openInputStream(imageUri!!)
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    binding.imgAdd?.setImageBitmap(selectedImage)
                } else {
                    Toast.makeText(requireActivity(), "No image selected", Toast.LENGTH_LONG).show()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }
}


