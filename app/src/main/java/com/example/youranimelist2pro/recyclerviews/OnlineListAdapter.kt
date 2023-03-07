package com.example.youranimelist2pro.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.youranimelist.R
import com.example.youranimelist2pro.interfaces.AnimeListener
import com.example.youranimelist2pro.objects.Anime
import com.example.youranimelist2pro.objects.DBAnime


class OnlineListAdapter(
    private val animeList: ArrayList<Anime>, private val animeListener: AnimeListener
): ListAdapter<DBAnime, OnlineListAdapter.myViewHolder>(AnimesItemCallback())
   // RecyclerView.Adapter<OnlineListAdapter.myViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return myViewHolder(itemview)



    }


    override fun getItemCount(): Int {

        return animeList.size
    }



    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val currentItem = animeList[position]

        holder.name.text = currentItem.name
        holder.eps.text = "Episodes: " + currentItem.episodes.toString()
        holder.rating.text = currentItem.rating.toString() + "/10"
        holder.genre.text ="generes: " + currentItem.genre.toString()

        setFadeAnimation(holder.itemView)

        val manager: RequestManager? = holder.image?.let { Glide.with(it) }
        if (manager != null) {
            manager.load(currentItem.imageUrl)
                .thumbnail(0.05f)
                .transition(
                    DrawableTransitionOptions.withCrossFade()
                )
                .into(holder.image as ImageView)



        }


        holder.itemView.setOnClickListener {
            val anime = animeList.get(position)
            animeListener.animeClicked(anime)
        }
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)    }

    class myViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val name: TextView = itemview.findViewById(R.id.animeTitle)
        val eps: TextView = itemview.findViewById(R.id.animeEpisodes)
        val rating: TextView = itemview.findViewById(R.id.animeRating)
        val genre: TextView = itemview.findViewById(R.id.animeGenre)
        val image: View? = itemview.findViewById(R.id.AnimeImageView)

    }

    class AnimesItemCallback : DiffUtil.ItemCallback<DBAnime>() {

        override fun areItemsTheSame(oldItem: DBAnime, newItem: DBAnime): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: DBAnime, newItem: DBAnime): Boolean {
            return oldItem == newItem
        }



    }


    }
