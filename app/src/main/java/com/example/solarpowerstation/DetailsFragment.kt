package com.example.solarpowerstation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.solarpowerstation.databinding.FragmentDetailsBinding
import com.google.firebase.database.FirebaseDatabase

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)

        val productId = arguments?.getString("productId")
        if (productId != null) {
            val database = FirebaseDatabase.getInstance().getReference("products").child(productId)
            database.get().addOnSuccessListener { snapshot ->
                val product = snapshot.getValue(Product::class.java)
                product?.let {
                    binding.productName.text = it.name
                    binding.productDescription.text = it.description ?: "No description available"
                    binding.productPrice.text = it.price?.toString() ?: "Price unavailable"
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch product details.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Error: Product ID is missing.", Toast.LENGTH_SHORT).show()
        }

        binding.buyNowButton.setOnClickListener {
            findNavController().navigate(R.id.action_DetailsFragment_to_CheckoutFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}