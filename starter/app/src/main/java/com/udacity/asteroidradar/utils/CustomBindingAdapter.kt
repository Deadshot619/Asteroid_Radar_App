package com.udacity.asteroidradar.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.ui.main.AsteroidListAdapter

@BindingAdapter("rvAsteroidData")
fun bindRvAsteroidData(recyclerView: RecyclerView, data: List<Asteroid>){
    val adapter = recyclerView.adapter as AsteroidListAdapter
    adapter.submitList(data)
}