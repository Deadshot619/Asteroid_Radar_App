package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidListAdapter(private val asteroidClickListener: AsteroidClickListener): ListAdapter<Asteroid, AsteroidListAdapter.AsteroidListViewHolder>(AsteroidListDiffCallback) {

    companion object AsteroidListDiffCallback: DiffUtil.ItemCallback<Asteroid>(){
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidListViewHolder {
        return AsteroidListViewHolder(ItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AsteroidListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, asteroidClickListener)
    }


    class AsteroidListViewHolder(private var mBinding: ItemAsteroidBinding): RecyclerView.ViewHolder(mBinding.root){
        fun bind(data: Asteroid, asteroidClickListener: AsteroidClickListener){
            mBinding.data = data

            mBinding.clickListener = asteroidClickListener

            mBinding.ivAsteroid.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.wallpapers))

            mBinding.executePendingBindings()
        }
    }

    interface AsteroidClickListener{
        fun onItemClick(asteroidData: Asteroid)
    }
}