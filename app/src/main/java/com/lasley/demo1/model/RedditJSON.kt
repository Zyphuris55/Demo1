package com.lasley.demo1.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class RedditJSON(
        val kind: String?, //Listing
        val data: KindData?
) {
    class Deserializer : ResponseDeserializable<RedditJSON> {
        override fun deserialize(content: String) = Gson().fromJson(content, RedditJSON::class.java)
    }
}

data class KindData(
        val modhash: String?,
        val whitelistStatus: String?,
        val children: ArrayList<Children>?,
        val after: String?,
        val before: Any?
)

data class Children(
        val kind: String?,
        val data: ChildrenData?
)

data class ChildrenData(
        val domain: String?, //reddit.com
        val approvedAtUtc: Any?,
        val bannedBy: Any?,
        val thumbnailWidth: Int?, //140
        val subreddit: String?,
        val selftextHtml: Any?,
        val selftext: String?,
        val likes: Any?,
        val suggestedSort: Any?,
        val userReports: List<Any?>?,
        val secureMedia: Any?,
        val isRedditMediaDomain: Boolean?,
        val linkFlairText: Any?,
        val id: String?,
        val bannedAtUtc: Any?,
        val viewCount: Any?,
        val archived: Boolean?,
        val clicked: Boolean?,
        val reportReasons: Any?,
        val title: String?,
        val numCrossposts: Int?,
        val saved: Boolean?,
        val modReports: List<Any?>?,
        val canModPost: Boolean?,
        val isCrosspostable: Boolean?,
        val pinned: Boolean?,
        val score: Int?,
        val approvedBy: Any?,
        val over18: Boolean?,
        val hidden: Boolean?,
        val preview: Preview?,
        val thumbnail: String?,
        val subredditId: String?,
        val edited: Any?,
        val linkFlairCssClass: Any?,
        val authorFlairCssClass: Any?,
        val contestMode: Boolean?,
        val gilded: Int?,
        val downs: Int?,
        val brandSafe: Boolean?,
        val removalReason: Any?,
        val postHint: String?,
        val authorFlairText: String?,
        val stickied: Boolean?,
        val canGild: Boolean?,
        val thumbnailHeight: Int?, //140
        val parentWhitelistStatus: String?,
        val name: String?,
        val spoiler: Boolean?,
        val permalink: String?,
        val subredditType: String?,
        val locked: Boolean?,
        val hideScore: Boolean?,
        val created: Int?,
        val url: String?,
        val whitelistStatus: String?,
        val quarantine: Boolean?,
        val author: String?,
        val createdUtc: Int?,
        val subredditNamePrefixed: String?,
        val ups: Int?,
        val media: Any?,
        val numComments: Int?,
        val isSelf: Boolean?,
        val visited: Boolean?,
        val numReports: Any?,
        val isVideo: Boolean?,
        val distinguished: String?
)

data class Preview(
        val images: List<PreviewImage?>?,
        val enabled: Boolean?
)

data class PreviewImage(
        val source: Source?,
        val resolutions: List<Resolution?>?,
        val id: String?
)

data class Source(
        val url: String?,
        val width: Int?, //256
        val height: Int? //256
)

data class Resolution(
        val url: String?,
        val width: Int?, //108
        val height: Int? //108
)
