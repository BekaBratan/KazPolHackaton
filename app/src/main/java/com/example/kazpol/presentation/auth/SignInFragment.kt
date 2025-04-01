package com.example.kazpol.presentation.auth

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kazpol.R
import com.example.kazpol.databinding.FragmentSignInBinding
import com.example.kazpol.utils.SharedProvider

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedProvider = SharedProvider(requireContext())

        if (sharedProvider.isAuthorized()) {
//            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToReportFragment())
            sharedProvider.clearShared()
        }

        binding.run {
            btnGoToSignUp.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
            }

            btnSave.setOnClickListener {
                val phone = etPhone.text.toString()
                val password = etPassword.text.toString()
                viewModel.signIn(password = password, phone = phone)
                viewModel.signInResponse.observe(viewLifecycleOwner, {
                    sharedProvider.saveUser(it)
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToReportFragment())
                })
                viewModel.errorBody.observe(viewLifecycleOwner, {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                })
            }
        }
    }

}