package com.kinzlstanislav.topcontributors.architecture.network.response

import com.squareup.moshi.Json

data class GithubUserResponse(
    @field:Json(name = "login") val loginName: String?,
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "node_id") val nodeId: String?,
    @field:Json(name = "avatar_url") val avatarUrl: String?,
    @field:Json(name = "gravatar_url") val gravatarUrl: String?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "html_url") val htmlUrl: String?,
    @field:Json(name = "followers_url") val followersUrl: String?,
    @field:Json(name = "following_url") val followingUrl: String?,
    @field:Json(name = "gists_url") val gistsUrl: String?,
    @field:Json(name = "starred_url") val starredUrl: String?,
    @field:Json(name = "subscriptions_url") val subscriptionsUrl: String?,
    @field:Json(name = "organizations_url") val organizationsUrl: String?,
    @field:Json(name = "repos_url") val reposUrl: String?,
    @field:Json(name = "events_url") val eventsUrl: String?,
    @field:Json(name = "received_events_url") val receivedEventsUrl: String?,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "site_admin") val isSiteAdmin: Boolean?,
    @field:Json(name = "name") val realName: String?,
    @field:Json(name = "company") val company: String?,
    @field:Json(name = "blog") val blogUrl: String?,
    @field:Json(name = "location") val location: String?,
    @field:Json(name = "email") val email: String?,
    @field:Json(name = "hireable") val hireable: Boolean?,
    @field:Json(name = "bio") val bio: String?,
    @field:Json(name = "public_repos") val numberOfPublicRepositories: Int?,
    @field:Json(name = "public_gists") val numberOfPublicGists: Int?,
    @field:Json(name = "followers") val numberOfFollowers: Int?,
    @field:Json(name = "following") val numberOfFollowing: Int?,
    @field:Json(name = "created_at") val timeCreated: String?,
    @field:Json(name = "updated_at") val timeUpdated: String?
)