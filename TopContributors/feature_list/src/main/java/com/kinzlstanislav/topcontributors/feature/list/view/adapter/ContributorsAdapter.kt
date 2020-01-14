package com.kinzlstanislav.topcontributors.feature.list.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import com.kinzlstanislav.topcontributors.base.extensions.inflateViewHolder
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.base.imageloading.GlideImageLoader

class ContributorsAdapter(
    private val imageLoader: GlideImageLoader,
    private val onItemClickAction: (Contributor) -> Unit
) : RecyclerView.Adapter<ContributorsViewHolder>() {

    private var contributorsList: MutableList<Contributor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContributorsViewHolder(
        parent.inflateViewHolder(R.layout.item_contributor_tile), imageLoader, onItemClickAction
    )

    override fun getItemCount() = contributorsList.size

    override fun onBindViewHolder(holder: ContributorsViewHolder, position: Int) {
        holder.bind(contributorsList[position])
    }

    fun updateItems(contributors: List<Contributor>) {
        contributorsList.apply {
            clear()
            addAll(contributors)
        }
        notifyDataSetChanged()
    }
}