package com.example.kazpol.presentation.sos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.example.kazpol.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.example.kazpol.databinding.FragmentSOSBinding
import com.example.kazpol.utils.SharedProvider
import com.google.android.gms.location.LocationServices

class SOSFragment : Fragment() {

    private lateinit var binding: FragmentSOSBinding
    private val viewModel: SOSViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val phoneNumber = "+77078737458"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSOSBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.btnUploadMedia.setOnClickListener {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            makePhoneCall()
        }
    }

    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val sharedProvider = SharedProvider(requireContext())
                binding.tvLocation.visibility = View.VISIBLE
                binding.tvLocation.text = "Lat: $latitude\nLng: $longitude"

                Toast.makeText(requireContext(), sharedProvider.getToken(), Toast.LENGTH_SHORT).show()
                viewModel.SOS(token = sharedProvider.getToken(), longitude = longitude.toInt(), latitude = latitude.toInt())
                viewModel.sosResponse.observe(viewLifecycleOwner, {
                    Toast.makeText(requireContext(), "SOS sent", Toast.LENGTH_SHORT).show()
                })
                viewModel.errorResponse.observe(viewLifecycleOwner, {
                    Toast.makeText(requireContext(), it?.error, Toast.LENGTH_SHORT).show()
                })
            } else {
                Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makePhoneCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent)
        } else {
            Toast.makeText(requireContext(), "Permission required", Toast.LENGTH_SHORT).show()
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
    }
}