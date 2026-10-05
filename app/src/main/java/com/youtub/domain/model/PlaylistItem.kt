/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.model

import androidx.compose.runtime.Stable
import androidx.annotation.Keep

@Keep
@Stable
data class PlaylistItem(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val uploaderName: String,
    val uploaderUrl: String?,
    val streamCount: Long
)
