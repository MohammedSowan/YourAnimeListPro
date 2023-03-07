package com.example.youranimelist2pro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youranimelist2pro.objects.Anime
import com.example.youranimelist2pro.recyclerviews.OnlineListAdapter
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.youranimelist.R
import com.example.youranimelist.databinding.OnlineListFragmentBinding

import com.example.youranimelist2pro.interfaces.AnimeListener
import kotlinx.coroutines.runBlocking

class OnlineListFragment: Fragment(R.layout.online_list_fragment), AnimeListener {

    private lateinit var binding: OnlineListFragmentBinding

    private lateinit var dbref: DatabaseReference
    private lateinit var onlineRecyclerView: RecyclerView
    private lateinit var animeArrayList: ArrayList<Anime>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = OnlineListFragmentBinding.inflate(inflater, container, false)


        onlineRecyclerView = binding.onlineRecyclerView
        onlineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        onlineRecyclerView.setHasFixedSize(true)
        animeArrayList = arrayListOf<Anime>()

        downloadAnimeData()

        return binding.root
    }

    fun downloadAnimeData() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                    getAnimeData()
            } } }

    private fun getAnimeData() {

        dbref = FirebaseDatabase.getInstance().getReference("AnimeListPro")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (animeSnapshot in snapshot.children) {

                        val anime = animeSnapshot.getValue(Anime::class.java)
                        animeArrayList.add(anime!!)
                    }

                    onlineRecyclerView.adapter = OnlineListAdapter(animeArrayList,this@OnlineListFragment )
                    onlineRecyclerView.startLayoutAnimation()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented") }}) }

    override fun animeClicked(anime: Anime?) {
     //   Toast.makeText(requireContext(),"$anime", Toast.LENGTH_SHORT).show()
    toDetailsFragment(anime)

    }

    private fun toDetailsFragment(anime: Anime?){

        val bundle = bundleOf("anime_name" to anime?.name.toString(),
            "anime_img" to anime?.imageUrl.toString(),
            "anime_dec" to anime?.description.toString())
        findNavController().navigate(R.id.action_onlineListFragment_to_animeDetailsFragment, bundle)

    }

}




