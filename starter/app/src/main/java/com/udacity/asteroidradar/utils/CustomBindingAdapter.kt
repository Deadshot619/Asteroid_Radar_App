package com.udacity.asteroidradar.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.ui.main.AsteroidListAdapter

@BindingAdapter("rvAsteroidData")
fun bindRvAsteroidData(recyclerView: RecyclerView, data: List<Asteroid>?){
    val adapter = recyclerView.adapter as AsteroidListAdapter
    adapter.submitList(data)
}

@BindingAdapter("ivAsteroidImage")
fun bindIvAsteroidImage(imageView: ImageView, url: String?){
    Picasso.with(imageView.context).load(url).error(R.drawable.wallpapers).into(imageView)
}

@BindingAdapter("ivAsteroidHazardousIndicator")
fun bindIvAsteroidHazardousIndicator(imageView: ImageView, isHazardous: Boolean?) {
    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, if (isHazardous == true) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal))
}


@BindingAdapter("loadingStatus")
fun bindLoadingStatus(progressBar: ProgressBar, status: Status?) {
    when (status) {
        Status.LOADING -> {
            progressBar.visible()
        }
        Status.ERROR -> {
            progressBar.gone()
        }
        Status.SUCCESS -> {
            progressBar.gone()
        }
        else -> {
            progressBar.gone()
        }
    }
}