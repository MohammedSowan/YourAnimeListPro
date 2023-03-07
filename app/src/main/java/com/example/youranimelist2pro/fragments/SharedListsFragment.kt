package com.example.youranimelist2pro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youranimelist.R
import com.example.youranimelist.databinding.SharedListsFragmentBinding

import com.example.youranimelist2pro.interfaces.AnimeListener
import com.example.youranimelist2pro.objects.Anime
import com.example.youranimelist2pro.recyclerviews.OnlineListAdapter
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SharedListsFragment: Fragment(R.layout.shared_lists_fragment){

    private lateinit var binding: SharedListsFragmentBinding

    private lateinit var dbref: DatabaseReference
    private lateinit var onlineRecyclerView: RecyclerView
    private lateinit var animeArrayList: ArrayList<Anime>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding =SharedListsFragmentBinding.inflate(inflater, container, false)



        binding.displayButton.setOnClickListener{
            displayList()
        }


//        onlineRecyclerView = binding.sharedListRecyclerView
     //   onlineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        onlineRecyclerView.setHasFixedSize(true)
        animeArrayList = arrayListOf<Anime>()



        return binding.root
    }

    private fun displayList() {
        val listName = binding.sharedListNameEditTextView.text.toString()

        if (listName.isEmpty()) {
            sendToast("Please put a list name first")
        } else {

            val bundle = bundleOf("list_name" to listName.toString())

            findNavController().navigate(
                R.id.action_sharedListsFragment_to_displayListFragment,
                bundle
            )

        }
    }

    private fun sendToast(text: String){
        Toast.makeText(requireContext(),"$text", Toast.LENGTH_SHORT).show()
    }}






