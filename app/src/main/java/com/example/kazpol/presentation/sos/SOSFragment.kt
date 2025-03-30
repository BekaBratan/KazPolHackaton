package com.example.kazpol.presentation.sos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kazpol.R
import com.example.kazpol.databinding.FragmentSOSBinding

class SOSFragment : Fragment() {

    private lateinit var binding: FragmentSOSBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSOSBinding.inflate(inflater, container, false)
        return binding.root
    }

}