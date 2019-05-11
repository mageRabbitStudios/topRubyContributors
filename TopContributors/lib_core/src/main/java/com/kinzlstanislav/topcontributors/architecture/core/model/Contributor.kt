package com.kinzlstanislav.topcontributors.architecture.core.model

data class Contributor(
    val loginName: String,
    val avatarUrl: String,
    val numberOfCommits: Int
)