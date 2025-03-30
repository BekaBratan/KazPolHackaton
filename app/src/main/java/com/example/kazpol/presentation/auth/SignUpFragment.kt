package com.example.kazpol.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kazpol.R
import com.example.kazpol.databinding.FragmentSignUpBinding
import com.example.kazpol.utils.SharedProvider
import kotlin.getValue

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedProvider = SharedProvider(requireContext())

        binding.run {
            btnGoToSignIn.setOnClickListener {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
            }

            btnSave.setOnClickListener {
                val name = etName.text.toString()
                val dateOfBirth = etBirthDate.text.toString()
                val iin = etIIN.text.toString()
                val currentLocation = etLocation.text.toString()
                val phone = etPhone.text.toString()
                val password = etPassword.text.toString()
                viewModel.signUp(
                    currentLocation = currentLocation,
                    name = name,
                    iin = iin,
                    dateOfBirth = dateOfBirth,
                    password = password,
                    phone = phone
                )
                viewModel.signUpResponse.observe(viewLifecycleOwner, {
                    viewModel.signIn(
                        phone = phone,
                        password = password
                    )
                })
                viewModel.signInResponse.observe(viewLifecycleOwner, {
                    sharedProvider.saveUser(it)
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToReportFragment())
                })
                viewModel.errorBody.observe(viewLifecycleOwner, {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}