package com.example.youranimelist2pro.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.youranimelist.R
import com.example.youranimelist.databinding.AnimeDetailsFragmentBinding

class AnimeDetailsFragment: Fragment(R.layout.anime_details_fragment) {


    private lateinit var binding: AnimeDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?{
        binding = AnimeDetailsFragmentBinding.inflate(inflater,container,false)
        binding.animeDec.movementMethod = ScrollingMovementMethod()
        val detailsImg = binding.animeImg

        val name = requireArguments().getString("anime_name")
        val dec = requireArguments().getString("anime_dec")
        val img = requireArguments().getString("anime_img")

        val manager: RequestManager? = detailsImg.let { Glide.with(it) }
        if (manager != null) {
            manager.load(img)
                .thumbnail(0.05f)
                .transition(
                    DrawableTransitionOptions.withCrossFade()
                )
                .into(detailsImg)
        }

        binding.apply {
            animeTitle.text = name
            animeDec.text = dec
        }

        binding.returnButton.setOnClickListener{
            it.findNavController().popBackStack()
        }

        return binding.root
    }

}