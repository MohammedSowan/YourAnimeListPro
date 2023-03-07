package com.example.youranimelist2pro.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.youranimelist.R
import com.example.youranimelist.databinding.UploadFragmentBinding
import com.example.youranimelist2pro.database.AnimeDatabase
import com.example.youranimelist2pro.database.DBAnimeViewModel
import com.example.youranimelist2pro.database.DBAnimeViewModelFactory
import com.example.youranimelist2pro.objects.Anime
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UploadListFragment: Fragment(R.layout.upload_fragment) {
    private lateinit var viewModel: DBAnimeViewModel
    private lateinit var binding: UploadFragmentBinding
    private var Boolean_Key = "BooleanKey"
    private var String_Key = "isListUploaded"
    private var contextAttached = true


    private val NAME = "uploadListSharedPreferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = UploadFragmentBinding.inflate(inflater, container, false)


        val dao = AnimeDatabase.getInstance(requireContext()).dbAnime()
        val factory = dao?.let { DBAnimeViewModelFactory(it) }
        viewModel =
            ViewModelProvider(requireActivity(), factory!!).get(DBAnimeViewModel::class.java)

        setText()

        binding.uploadButton.setOnClickListener {

            if (binding.listNameEditText.text.toString().isEmpty()) {
                sendToast("Please put a list name first")
            } else {
                uploadList()
                val BooleansharedPreferences =
                    requireContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)
                var editor = BooleansharedPreferences.edit()
                editor.putBoolean(Boolean_Key, true)
                editor.putString(String_Key, binding.listNameEditText.text.toString())
                editor.apply()
            }
        }

        binding.returnButton.setOnClickListener{

            it.findNavController().popBackStack()
        }

        return binding.root
    }

    private fun uploadList() {
        val ListName = binding.listNameEditText.text.toString()

        viewModel.dbAnimes.observe(requireActivity()) {
            val database = Firebase.database
            val myRef = database.getReference(ListName)
            val animeList = arrayListOf<Anime>()

            for (i in it.indices) {
                var dbAnime = it.get(i)

                val animeEps: Int
                val animeRating: Double

                if (dbAnime.episodes == ""){
                    animeEps = 0
                } else {animeEps = dbAnime.episodes.toInt() }
                if (dbAnime.rating == ""){
                    animeRating = 0.0
                }  else {animeRating = dbAnime.rating.toDouble() }


                var anime: Anime = Anime(
                    dbAnime.name,
                    dbAnime.imgUri,
                    animeEps,
                    animeRating,
                    dbAnime.description,
                    dbAnime.genre
                )
                animeList.add(anime)
                myRef.setValue(animeList)
            }

            setText()

            sendToast("List uploaded")

        }

    }

    private fun setText() {

        if (contextAttached){
        val sharedPreferences = requireContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)
        val isListUploaded: Boolean = sharedPreferences.getBoolean(Boolean_Key, false)
        val ListName = sharedPreferences.getString(String_Key,"")

        if(isListUploaded){
            binding.listNameEditText.setText(ListName)
            binding.uploadTextview.setText("your list was uploaded! you can change the name or upgrade it anytime")
        }}

        else {

        }
    }

    private fun sendToast(text: String){
        if (contextAttached){
           Toast.makeText(requireContext(),"$text", Toast.LENGTH_SHORT).show()
    }
    }

    override fun onDetach() {
        super.onDetach()
        contextAttached = false
    }


}