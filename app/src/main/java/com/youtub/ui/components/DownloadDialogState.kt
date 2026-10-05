/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.ui.components

import com.youtub.domain.model.VideoItem
import com.youtub.domain.model.StreamBundle

sealed class DownloadDialogState {
    object Idle : DownloadDialogState()
    data class Loading(val video: VideoItem) : DownloadDialogState()
    data class ShowDialog(val video: VideoItem, val bundle: StreamBundle) : DownloadDialogState()
}
