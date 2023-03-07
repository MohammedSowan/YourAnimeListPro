package com.example.youranimelist2pro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.youranimelist.R
import com.example.youranimelist.databinding.AddDbanimeFragmentBinding
import com.example.youranimelist.databinding.CustomListsFragmentBinding
import com.example.youranimelist2pro.database.AnimeDatabase
import com.example.youranimelist2pro.database.DBAnimeViewModel
import com.example.youranimelist2pro.database.DBAnimeViewModelFactory
import com.example.youranimelist2pro.objects.DBAnime
import com.example.youranimelist2pro.recyclerviews.DBAnimesAdapter

class addDBAnimeFragment: Fragment(R.layout.add_dbanime_fragment) {

    private lateinit var binding: AddDbanimeFragmentBinding
    private lateinit var viewModel: DBAnimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        binding = AddDbanimeFragmentBinding.inflate(inflater, container, false)
        //DBAnimeRecyclerView = binding.rvStudent

        val dao = AnimeDatabase.getInstance(requireContext()).dbAnime()
        val factory = DBAnimeViewModelFactory(dao!!)
        viewModel = ViewModelProvider(requireActivity(), factory).get(DBAnimeViewModel::class.java)

       // initRecyclerView()

        binding.btnAddAnime.setOnClickListener{
            saveAnimeData()
        }

        binding.returnButton.setOnClickListener{
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    private fun saveAnimeData() {
        val animeName = binding.AnimeTitle.text.toString()
        if (animeName.isEmpty()){
            sendToast("Name can't be empty")
         }
       else {
           viewModel.insertAnime(
            DBAnime( 0,binding.AnimeTitle.text.toString()
                ,binding.animeEpisodes.text.toString(), binding.animeRating.text.toString(),
                binding.animeDescription.text.toString(), binding.animeGenre.text.toString(),binding.animeImg.text.toString()))
            sendToast("new Anime have been added to your list")
            findNavController().navigate(R.id.action_addDBAnimeFragment_to_customListsFragment)
        }
    }

    private fun sendToast(text: String){
        Toast.makeText(requireContext(),"$text", Toast.LENGTH_SHORT).show()
    }}


