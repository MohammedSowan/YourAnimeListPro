package com.example.youranimelist2pro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youranimelist.R
import com.example.youranimelist.databinding.CustomListsFragmentBinding
import com.example.youranimelist2pro.SwipeToDeleteCallback
import com.example.youranimelist2pro.database.AnimeDatabase
import com.example.youranimelist2pro.database.DBAnimeDao
import com.example.youranimelist2pro.database.DBAnimeViewModel
import com.example.youranimelist2pro.database.DBAnimeViewModelFactory
import com.example.youranimelist2pro.interfaces.DBAnimeListener
import com.example.youranimelist2pro.objects.Anime
import com.example.youranimelist2pro.objects.DBAnime
import com.example.youranimelist2pro.recyclerviews.DBAnimesAdapter
import kotlinx.coroutines.runBlocking


class CustomListsFragment: Fragment(R.layout.custom_lists_fragment), DBAnimeListener {

    private lateinit var binding: CustomListsFragmentBinding
    private lateinit var viewModel: DBAnimeViewModel
    private lateinit var DBAnimeRecyclerView: RecyclerView
    private lateinit var adapter: DBAnimesAdapter
    private lateinit var db: AnimeDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = CustomListsFragmentBinding.inflate(inflater, container, false)
        DBAnimeRecyclerView = binding.recyclerAnimes


        val dao = AnimeDatabase.getInstance(requireContext()).dbAnime()
        val factory = dao?.let { DBAnimeViewModelFactory(it) }
        viewModel =
            ViewModelProvider(requireActivity(), factory!!).get(DBAnimeViewModel::class.java)


        initRecyclerView()

        binding.fabAddAnime.setOnClickListener {
            it.findNavController().navigate(R.id.action_customListsFragment_to_addDBAnimeFragment)
        }

        binding.UploadListFap.setOnClickListener{
            it.findNavController().navigate(R.id.action_customListsFragment_to_uploadListFragment)
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)

        itemTouchHelper.attachToRecyclerView(DBAnimeRecyclerView)

        return binding.root
    }


    private fun initRecyclerView() {

        DBAnimeRecyclerView.layoutManager = LinearLayoutManager(view?.getContext())
        adapter = DBAnimesAdapter(this,viewModel)
        DBAnimeRecyclerView.adapter = adapter

        displayStudentsList()
    }

    private fun displayStudentsList() {
        viewModel.dbAnimes.observe(requireActivity()) {
            adapter.setList(it)
            adapter.notifyDataSetChanged() }
    }


    val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            runBlocking {
                adapter.deleteAnime(position) } }
    }

    override fun animeClicked(anime: DBAnime?) {
       // Toast.makeText(requireContext(),"$anime", Toast.LENGTH_SHORT).show()
        toDetailsFragment(anime)
        }

    private fun toDetailsFragment(anime: DBAnime?){

        val bundle = bundleOf("anime_name" to anime?.name.toString(),
            "anime_img" to anime?.imgUri.toString(),
            "anime_dec" to anime?.description.toString())
        findNavController().navigate(R.id.action_customListsFragment_to_animeDetailsFragment, bundle)
    }

}



    //  DBAnimeRecyclerView.adapter?.notifyItemRemoved(position)





