package com.nassdk.wallapp.feature.newsfeed.domain.model

sealed class PostType(val name: String, val index: Int) {
    object Plain : PostType(name = "PLAIN", index = 1)
    object Video : PostType(name = "VIDEO", index = 2)
    object AudioCover : PostType(name = "AUDIO_COVER", index = 3)
    object PlainCover : PostType(name = "PLAIN_COVER", index = 4)
}
