package com.ahmety.movieapplicationproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmety.movieapplicationproject.R
import com.ahmety.movieapplicationproject.databinding.ItemActivityMainBinding
import com.ahmety.movieapplicationproject.model.Movie
import com.bumptech.glide.Glide

class HomeAdapter(private val onClickItem: (Movie) -> Unit) : ListAdapter<Movie, HomeAdapter.HomeViewHolder>(
    HomeAdapterComparator
) {
    inner class HomeViewHolder(private val binding: ItemActivityMainBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: Movie, onClickItem: (Movie) -> Unit) {
            binding.apply {
                textViewNoMovie.isVisible = item.Title.isNullOrEmpty()
                Glide.with(imageViewMovie).load(item.Poster).error(R.drawable.ic_baseline_warning_24).into(imageViewMovie)
                textViewMovieName.text = item.Title
                textViewMovieType.text = item.Genre
                textViewMovieDesc.text = item.Plot
                groupMovieInfo.isVisible = !item.Title.isNullOrEmpty()

                root.setOnClickListener {
                    if (!item.Title.isNullOrEmpty()){
                        onClickItem.invoke(item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val mView = ItemActivityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(mView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), onClickItem)
    }

    companion object {
        private val HomeAdapterComparator = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.Title == newItem.Title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}