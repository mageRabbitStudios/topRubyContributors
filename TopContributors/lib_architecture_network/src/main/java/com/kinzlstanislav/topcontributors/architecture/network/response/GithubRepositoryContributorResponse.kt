package com.kinzlstanislav.topcontributors.architecture.network.response

import com.squareup.moshi.Json

data class GithubRepositoryContributorResponse(
    @field:Json(name = "author") val contributor: GithubRepositoryContributionAuthorResponse? = null,
    @field:Json(name = "total") val numberOfCommits: Int? = null,
    @field:Json(name = "weeks") val weeks: List<GithubRepositoryContributorWeeksResponse>? = null
)

data class GithubRepositoryContributionAuthorResponse(
    @field:Json(name = "login") val name: String? = null,
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "node_id") val nodeId: String? = null,
    @field:Json(name = "avatar_url") val avatarUrl: String? = null,
    @field:Json(name = "gravatar_url") val gravatarUrl: String? = null,
    @field:Json(name = "url") val url: String? = null,
    @field:Json(name = "html_url") val htmlUrl: String? = null,
    @field:Json(name = "followers_url") val followersUrl: String? = null,
    @field:Json(name = "following_url") val followingUrl: String? = null,
    @field:Json(name = "gists_url") val gistsUrl: String? = null,
    @field:Json(name = "starred_url") val starredUrl: String? = null,
    @field:Json(name = "subscriptions_url") val subscriptionsUrl: String? = null,
    @field:Json(name = "organizations_url") val organizationsUrl: String? = null,
    @field:Json(name = "repos_url") val reposUrl: String? = null,
    @field:Json(name = "events_url") val eventsUrl: String? = null,
    @field:Json(name = "received_events_url") val receivedEventsUrl: String? = null,
    @field:Json(name = "type") val type: String? = null,
    @field:Json(name = "site_admin") val isSiteAdmin: Boolean? = null
)

data class GithubRepositoryContributorWeeksResponse(
    @field:Json(name = "w") val startOfTheWeek: Long? = null,
    @field:Json(name = "a") val numberOfAdditions: Long? = null,
    @field:Json(name = "d") val numberOfDeletions: Long? = null,
    @field:Json(name = "c") val numberOfCommits: Int? = null
)