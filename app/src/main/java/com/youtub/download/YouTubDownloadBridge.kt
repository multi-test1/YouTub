package com.youtub.download

import android.content.Context
import android.content.Intent
import android.util.Log
import com.youtub.data.local.DownloadEntity
import com.youtub.data.local.DownloadMissionEntity
import com.youtub.data.local.DownloadStatus

/**
 * Bridge between YouTub's Hilt/Room download architecture and the device
 * download services.
 *
 * NOTE: The original PipePipe giga download engine (us.shandian.giga) was
 * excluded from the build because its vendored port references APIs that do
 * not exist in this project's dependencies (PipePipe-only SABR extractor API
 * and legacy ExoPlayer 2 HLS internals). Downloads are handled by YouTub's
 * own Room-backed VideoDownloadService instead.
 */
object YouTubDownloadBridge {

    private const val TAG = "YouTubDownloadBridge"

    private const val DOWNLOAD_SERVICE_CLASS = "com.youtub.services.VideoDownloadService"

    /** Best available stream URL for a download entity (video preferred, then audio). */
    fun bestStreamUrl(entity: DownloadEntity): String? =
        entity.videoUrl ?: entity.audioUrl

    /** Whether this entity represents an audio-only download. */
    fun isAudio(entity: DownloadEntity): Boolean =
        entity.format?.contains("audio", ignoreCase = true) == true

    /** Human-readable summary for logs/UI. */
    fun describe(entity: DownloadEntity, mission: DownloadMissionEntity): String =
        buildString {
            append(entity.title)
            append(" | ")
            append(entity.quality ?: "auto")
            append(" | ")
            append(entity.format ?: "unknown")
            append(" | ")
            append(mission.downloadedBytes)
            append('/')
            append(mission.totalBytes)
            append(" bytes")
        }

    /**
     * Ask the app's own download service to process a mission.
     * Uses an explicit class name so this object has no compile-time
     * dependency on the service implementation.
     */
    fun startDownload(context: Context, mission: DownloadMissionEntity) {
        try {
            val intent = Intent().setClassName(context, DOWNLOAD_SERVICE_CLASS)
            context.startForegroundService(intent)
            val preview = DownloadEntity(
                videoId = mission.videoId,
                title = mission.title,
                thumbnailUrl = "",
                uploaderName = "",
                filePath = mission.outputFilePath ?: "",
                totalSize = mission.totalBytes,
                downloadedSize = mission.downloadedBytes,
                status = DownloadStatus.DOWNLOADING,
                quality = mission.quality,
                format = mission.format
            )
            Log.i(TAG, "Requested download start: ${describe(preview, mission)}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start download for ${mission.videoId}", e)
        }
    }
}
