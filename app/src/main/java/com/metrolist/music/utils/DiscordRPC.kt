package com.metrolist.music.utils

import android.content.Context
import com.metrolist.music.R
import com.metrolist.music.db.entities.Song
import com.my.kizzy.rpc.KizzyRPC
import com.my.kizzy.rpc.RpcImage
import java.util.concurrent.TimeUnit

class DiscordRPC(
    val context: Context,
    token: String,
) : KizzyRPC(token) {
    suspend fun updateSong(song: Song) = runCatching {
        setActivity(
            name = context.getString(R.string.app_name).removeSuffix(" Debug"),
            details = song.song.title,
            state = song.artists.joinToString { it.name },
            largeImage = song.song.thumbnailUrl?.let { RpcImage.ExternalImage(it) },
            smallImage = song.artists.firstOrNull()?.thumbnailUrl?.let { RpcImage.ExternalImage(it) },
            largeText = song.album?.title,
            smallText = song.artists.firstOrNull()?.name,
            buttons = listOf(
                "Listen on Yt Music" to "https://music.youtube.com/watch?v=${song.song.id}"
            ),
            type = Type.LISTENING,
            startTime = startTime,
            endTime = startTime + TimeUnit.SECONDS.toMillis(song.song.duration.toLong()),
            applicationId = APPLICATION_ID
        )
    }

    companion object {
        private const val startTime = System.currentTimeMillis()
        private const val APPLICATION_ID = "1271273225120125040"
    }
}
