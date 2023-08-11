package com.lucas.petros.commons.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

val requestOptions = RequestOptions()
    .diskCacheStrategy(DiskCacheStrategy.ALL)

/**
 * Download the image into the ImageView
 * @param url the string that contains a valid http address
 */
@BindingAdapter("loadUrlImage")
fun ImageView.loadUrlImage(url: String?) {
    if (url == null) return

    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .into(this)
}