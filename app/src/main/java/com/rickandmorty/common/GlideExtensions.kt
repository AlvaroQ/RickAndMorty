package com.rickandmorty.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

private fun initUrlGlide(context: Context, url: String?) =
    Glide.with(context)
        .setDefaultRequestOptions(RequestOptions().timeout(30000))
        .load(url)
        .error(getCircularProgressDrawable(context))
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("Glide", "onLoadFailed", e)
                e?.logRootCauses("GLIDE")
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("Glide", "onResourceReady")
                return false
            }

        })

fun glideLoadURL(url: String?, where: ImageView) {
    initUrlGlide(where.context, url)
        .placeholder(getCircularProgressDrawable(where.context))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(where)
}
fun getCircularProgressDrawable(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}