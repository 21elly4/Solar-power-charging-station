package com.example.solarpowerstation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.solarpowerstation.databinding.FragmentProductBinding
import com.google.firebase.database.*

class ProductFragment : Fragment(R.layout.fragment_product) {
    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("products")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)

        productAdapter = ProductAdapter { productId ->
            val action = ProductFragmentDirections.actionProductFragmentToDetailsFragment(productId)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        // Fetch product data from Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
                productAdapter.submitList(products)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
