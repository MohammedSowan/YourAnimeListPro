package com.example.youranimelist2pro.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.youranimelist.R
import com.example.youranimelist2pro.database.DBAnimeDao
import com.example.youranimelist2pro.database.DBAnimeViewModel
import com.example.youranimelist2pro.interfaces.DBAnimeListener
import com.example.youranimelist2pro.objects.DBAnime


class DBAnimesAdapter(
    private val animeListener: DBAnimeListener,
    private val viewModel: DBAnimeViewModel

):RecyclerView.Adapter<DBAnimesAdapter.DBAnimeViewHolder>(){

    private val dbAnimesList = ArrayList<DBAnime>()
    private lateinit var dbAnimeDao: DBAnimeDao


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBAnimeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.row_item,parent,false)
        return DBAnimeViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: DBAnimeViewHolder, position: Int) {
        holder.bind(dbAnimesList[position])

        holder.itemView.setOnClickListener{
            val anime = dbAnimesList.get(position)
            animeListener.animeClicked(anime)
        }

        setFadeAnimation(holder.itemView)


    }


    override fun getItemCount(): Int {
        return dbAnimesList.size
    }

    fun setList(dbAnimes:List<DBAnime>){
        dbAnimesList.clear()
        dbAnimesList.addAll(dbAnimes)
    }


    class DBAnimeViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(dbAnime: DBAnime){
            val animeTitle = view.findViewById<TextView>(R.id.animeTitle)
            val animeEps = view.findViewById<TextView>(R.id.animeEpisodes)
            val animeRating = view.findViewById<TextView>(R.id.animeRating)
            val animeGenre = view.findViewById<TextView>(R.id.animeGenre)
        //    val animeDec = view.findViewById<TextView>(R.id.animeDescription)
            val image: View? = view.findViewById(R.id.AnimeImageView)

            animeTitle.text = dbAnime.name
            animeEps.text = dbAnime.episodes
            animeRating.text = dbAnime.rating
            animeGenre.text = dbAnime.genre

            val manager: RequestManager? = image ?.let { Glide.with(it) }
            if (manager != null) {
                manager.load(dbAnime.imgUri)
                    .thumbnail(0.05f)
                    .transition(
                        DrawableTransitionOptions.withCrossFade()
                    )
                    .into(image as ImageView)
            }
        }
    }

     fun deleteAnime(position: Int) {
        val item = dbAnimesList[position]
        viewModel.deleteAnime(item)
        notifyItemChanged(position)
    }

    fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }

}

