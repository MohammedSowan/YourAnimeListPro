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
import com.example.youranimelist.databinding.DisplayListFragmentBinding
import com.example.youranimelist2pro.interfaces.AnimeListener
import com.example.youranimelist2pro.objects.Anime
import com.example.youranimelist2pro.recyclerviews.OnlineListAdapter
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private lateinit var binding: DisplayListFragmentBinding

private lateinit var dbref: DatabaseReference
private lateinit var onlineRecyclerView: RecyclerView
private lateinit var animeArrayList: ArrayList<Anime>

class DisplayListFragment: Fragment(R.layout.display_list_fragment), AnimeListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding =DisplayListFragmentBinding.inflate(inflater, container, false)

        onlineRecyclerView = binding.sharedListRecyclerView
        onlineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        onlineRecyclerView.setHasFixedSize(true)
        animeArrayList = arrayListOf<Anime>()

        binding.returnFAB.setOnClickListener{
            findNavController().popBackStack()
        }




        downloadAnimeData()

        return binding.root
    }


    fun downloadAnimeData() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                getAnimeData()
            } }
    }

    private fun getAnimeData() {
        val listName = requireArguments().getString("list_name")

        dbref = FirebaseDatabase.getInstance().getReference(listName as String)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    binding.noRecyclerTextView.visibility = View.GONE

                    for (animeSnapshot in snapshot.children) {

                        val anime = animeSnapshot.getValue(Anime::class.java)
                        animeArrayList.add(anime!!)
                    }

                    onlineRecyclerView.adapter = OnlineListAdapter(animeArrayList,this@DisplayListFragment )

                } else {
                    binding.noRecyclerTextView.setText("It looks like the list name you entered doesnt exist or that you made a mistake, check again or enter another list name")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented") }}) }


    private fun toDetailsFragment(anime: Anime?){

        val bundle = bundleOf("anime_name" to anime?.name.toString(),
            "anime_img" to anime?.imageUrl.toString(),
            "anime_dec" to anime?.description.toString())
          findNavController().navigate(R.id.action_displayListFragment_to_animeDetailsFragment, bundle)

    }

    override fun animeClicked(anime: Anime?) {
        toDetailsFragment(anime)
    }

}