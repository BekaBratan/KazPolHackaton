package com.example.kazpol.presentation.report

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.kazpol.R
import com.example.kazpol.databinding.FragmentReportBinding
import com.example.kazpol.presentation.news.NewsFragment
import com.example.unihub.data.api.ServiceBuilder
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private var selectedImageUri: Uri? = null
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { binding.ivReportImage.setImageURI(it); selectedImageUri = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnUploadMedia.setOnClickListener {
                pickImage.launch("image/*")
            }
        }

        binding.btnSubmit.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }

        val stream = requireContext().contentResolver.openInputStream(selectedImageUri!!) ?: return
        val requestBody = stream.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData(
            "image", "upload.jpg", requestBody
        )

        val description = binding.etReportText.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        lifecycleScope.launch {
            try {
                val response = ServiceBuilder.api.uploadImage(imagePart, description)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Upload Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Upload Failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}