package com.gvelesiani.movieapp.other.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.movieapp.R
import kotlinx.android.synthetic.main.load_state_footer_view_item.view.*

class MovieLoadStateAdapter(
    private val context: Context,
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.itemView.pfProgressState
        val btnRetry = holder.itemView.pfRetryState
        val txtErrorMessage = holder.itemView.pfErrorState

        btnRetry.isVisible = loadState !is LoadState.Loading
        txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            txtErrorMessage.text = context.getString(R.string.network_erorr_message)
        }

        btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer_view_item, parent, false)
        )
    }

    class LoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

