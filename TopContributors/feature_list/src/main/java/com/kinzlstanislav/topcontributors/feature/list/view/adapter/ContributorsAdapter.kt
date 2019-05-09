package com.kinzlstanislav.topcontributors.feature.list.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.base.static.inflateViewForHolder
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader

class ContributorsAdapter(
    private val imageLoader: GlideImageLoader,
    private val itemClickListener: ContributorItemClickListener
) : RecyclerView.Adapter<ContributorsViewHolder>() {

    private var contributorsList: MutableList<Contributor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorsViewHolder
        = ContributorsViewHolder(inflateViewForHolder(parent, R.layout.item_contributor_tile), imageLoader, itemClickListener)

    override fun getItemCount() = contributorsList.size

    override fun onBindViewHolder(holder: ContributorsViewHolder, position: Int) {
        holder.bind(contributorsList[position])
    }

    fun updateItems(contributors: List<Contributor>) {
        contributorsList.clear()
        contributorsList.addAll(contributors)
        notifyDataSetChanged()
    }

}