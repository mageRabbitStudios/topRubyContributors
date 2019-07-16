package com.kinzlstanislav.topcontributors.feature.list.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import com.kinzlstanislav.topcontributors.ui.viewholder.ShrinkOnTouchTileViewHolder
import com.kinzlstanislav.topcontributors.ui.viewholder.ShrinkOnTouchTileViewHolderImpl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contributor_tile.*

class ContributorsViewHolder(
    override val containerView: View,
    private val imageLoader: GlideImageLoader,
    private val onItemClickAction: (Contributor) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer,
    ShrinkOnTouchTileViewHolder by ShrinkOnTouchTileViewHolderImpl(containerView) {

    fun bind(contributor: Contributor) {
        item_contributor_name.text = contributor.loginName
        item_contributor_commits.text =
            itemView.resources.getString(R.string.item_contributor_commits, contributor.numberOfCommits.toString())
        imageLoader.loadFromUrl(containerView.context, contributor.avatarUrl, contributor_list_item_img_contributor)
        touchUpAction = {
            onItemClickAction(contributor)
        }
    }
}