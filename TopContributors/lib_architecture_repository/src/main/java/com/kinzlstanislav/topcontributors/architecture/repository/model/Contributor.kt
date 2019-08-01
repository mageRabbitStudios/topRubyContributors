package com.kinzlstanislav.topcontributors.architecture.repository.model

data class Contributor(
    val loginName: String,
    val avatarUrl: String,
    val numberOfCommits: Int
)