package com.gvelesiani.movieapp

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/*
 Failed to find GeneratedAppGlideModule. You should include an annotationProcessor
 compile dependency on com.github.bumptech.glide:compiler in your application and
 a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored
*/
@GlideModule
class CustomGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.apply { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) }
    }
}