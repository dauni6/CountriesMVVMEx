package com.dontsu.countriesmvvmex.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dontsu.countriesmvvmex.R

fun getProgressDrawable(context: Context): CircularProgressDrawable { //이미지 안에 작은 스피너 만들기(이미지가 로딩중임을 표시)
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
        val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.mipmap.ic_launcher_round)

        Glide.with(this.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)
}