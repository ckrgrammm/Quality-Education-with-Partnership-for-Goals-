package com.example.kleine.fragments.shopping

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kleine.databinding.MaterialAddBinding
import com.example.kleine.model.Material
import com.example.kleine.viewmodel.material.MaterialViewModel
import com.google.firebase.auth.FirebaseAuth


class AddMaterialFragment : Fragment() {

    private lateinit var binding: MaterialAddBinding // Corrected binding class name
    private lateinit var viewModel: MaterialViewModel


    private val REQUEST_CODE_IMAGE_PICK = 1
    private val REQUEST_CODE_DOCUMENT_PICK = 2

    private var selectedImageUri: Uri? = null
    private var selectedDocumentUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MaterialAddBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MaterialViewModel::class.java)
        // Retrieve the user's document ID (replace with your actual method)
        val userDocumentId = getUserDocumentId()
        binding.textViewPartnershipID.text = "Partnership ID: $userDocumentId"


        binding.buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK)
        }

        binding.buttonUploadDocument.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, REQUEST_CODE_DOCUMENT_PICK)
        }

        binding.buttonSubmit.setOnClickListener {
            // Validate and get data from UI elements
            val name = binding.editTextName.text.toString()
            val description = binding.editTextDesc.text.toString()
            val requirement = binding.editTextRequirement.text.toString()
            val status = binding.editTextStatus.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty() && requirement.isNotEmpty() && status.isNotEmpty()) {
                val material = Material(
                    name = name,
                    desc = description,
                    requirement = requirement,
                    status = status,
                    partnershipsID = userDocumentId // Set the user's document ID here

                )

                // Call ViewModel to add material and upload selected files
                viewModel.addMaterial(material, selectedImageUri, selectedDocumentUri)

                // Show a success message using a toast
                Toast.makeText(requireContext(), "Material submitted successfully", Toast.LENGTH_SHORT).show()

                // Navigate up (assuming you are using Navigation Component)
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_PICK -> {
                    selectedImageUri = data?.data
                    binding.imageViewCourseBanner.setImageURI(selectedImageUri)
                }
                REQUEST_CODE_DOCUMENT_PICK -> {
                    selectedDocumentUri = data?.data
                    // Show a message that the document has been uploaded
                    binding.textViewDocumentStatus.text = "Document has been uploaded."
                }
            }
        }
    }


    private fun getUserDocumentId(): String {
        // Initialize Firebase Authentication
        val auth = FirebaseAuth.getInstance()

        // Check if a user is currently authenticated
        val user = auth.currentUser

        // If a user is authenticated, you can retrieve their UID (user ID)
        return user?.uid ?: ""
    }



    companion object {
        private const val REQUEST_CODE_IMAGE_PICK = 1
        private const val REQUEST_CODE_DOCUMENT_PICK = 2
    }
}



