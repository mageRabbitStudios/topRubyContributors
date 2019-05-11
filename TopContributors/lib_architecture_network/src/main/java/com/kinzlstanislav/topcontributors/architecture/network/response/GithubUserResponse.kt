package com.kinzlstanislav.topcontributors.architecture.network.response

import com.squareup.moshi.Json

data class GithubUserResponse(
    @field:Json(name = "login") val loginName: String? = null,
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
    @field:Json(name = "site_admin") val isSiteAdmin: Boolean? = null,
    @field:Json(name = "name") val realName: String? = null,
    @field:Json(name = "company") val company: String? = null,
    @field:Json(name = "blog") val blogUrl: String? = null,
    @field:Json(name = "location") val location: String? = null,
    @field:Json(name = "email") val email: String? = null,
    @field:Json(name = "hireable") val hireable: Boolean? = null,
    @field:Json(name = "bio") val bio: String? = null,
    @field:Json(name = "public_repos") val numberOfPublicRepositories: Int? = null,
    @field:Json(name = "public_gists") val numberOfPublicGists: Int? = null,
    @field:Json(name = "followers") val numberOfFollowers: Int? = null,
    @field:Json(name = "following") val numberOfFollowing: Int? = null,
    @field:Json(name = "created_at") val timeCreated: String? = null,
    @field:Json(name = "updated_at") val timeUpdated: String? = null
)