package com.example.guessboxgame

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Options(
    val boxCount: Int
) : Parcelable {
    companion object {
        val DEFAULT = Options(boxCount = 6)
    }
}
