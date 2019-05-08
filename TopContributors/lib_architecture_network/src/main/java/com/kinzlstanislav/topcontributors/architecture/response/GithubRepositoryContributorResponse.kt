package com.kinzlstanislav.topcontributors.architecture.response

import com.squareup.moshi.Json

data class GithubRepositoryContributorResponse(
    @field:Json(name = "") val contributors: String?
)