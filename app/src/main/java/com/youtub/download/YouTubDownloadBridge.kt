package com.youtub.download

import android.content.Context
import android.content.Intent
import android.util.Log
import com.youtub.data.local.DownloadEntity
import com.youtub.data.local.DownloadMissionEntity
import us.shandian.giga.get.DownloadMission
import us.shandian.giga.get.MissionRecoveryInfo
import us.shandian.giga.service.DownloadManager
import us.shandian.giga.service.DownloadManagerService

/**
 * Bridge between YouTub's Hilt/Room download architecture 
 * and PipePipe's giga download engine.
 * 
 * This allows YouTub to use giga's advanced features:
 * - Multi-threaded downloads with configurable thread count
 * - SABR protocol support (via PipePipeExtractor)
 * - HLS manifest download with parallel segments
 * - Advanced resume/recovery from expired URLs
 * - FFmpeg-based post-processing/muxing
 */
object YouTubDownloadBridge {
    
    private const val TAG = "YouTubDownloadBridge"
    
    /**
     * Convert YouTub DownloadEntity to giga DownloadMission
     */
    fun toMission(entity: DownloadEntity, missionEntity: DownloadMissionEntity): DownloadMission {
        val mission = DownloadMission(
            name = entity.title,
            url = arrayOf(entity.url),
            kind = if (entity.format.contains("audio", ignoreCase = true)) 'a' else 'v',
            threads = missionEntity.threadCount.coerceIn(1, 5),
            postprocessingName = null,
            postprocessingArgs = null,
            source = entity.url,
            recoveryInfo = arrayOf(
                MissionRecoveryInfo(
                    serviceId = 0, // YouTube
                    url = entity.url,
                    position = 0
                )
            )
        )
        
        mission.timestamp = entity.createdAt
        mission.threadCount = missionEntity.threadCount.coerceIn(1, 5)
        
        return mission
    }
    
    /**
     * Start giga download service for a mission
     */
    fun startGigaDownload(context: Context, mission: DownloadMission) {
        try {
            val intent = Intent(context, DownloadManagerService::class.java).apply {
                action = DownloadManagerService.ACTION_START
                putExtra(DownloadManagerService.EXTRA_MISSION, mission)
            }
            context.startForegroundService(intent)
            Log.i(TAG, "Started giga download for: ${mission.name}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start giga download", e)
        }
    }
    
    /**
     * Pause a giga download mission
     */
    fun pauseGigaDownload(context: Context, missionId: String) {
        val intent = Intent(context, DownloadManagerService::class.java).apply {
            action = DownloadManagerService.ACTION_PAUSE
            putExtra(DownloadManagerService.EXTRA_MISSION_ID, missionId)
        }
        context.startService(intent)
    }
    
    /**
     * Resume a paused giga download mission
     */
    fun resumeGigaDownload(context: Context, missionId: String) {
        val intent = Intent(context, DownloadManagerService::class.java).apply {
            action = DownloadManagerService.ACTION_RESUME
            putExtra(DownloadManagerService.EXTRA_MISSION_ID, missionId)
        }
        context.startService(intent)
    }
    
    /**
     * Cancel and remove a giga download mission
     */
    fun cancelGigaDownload(context: Context, missionId: String) {
        val intent = Intent(context, DownloadManagerService::class.java).apply {
            action = DownloadManagerService.ACTION_CANCEL
            putExtra(DownloadManagerService.EXTRA_MISSION_ID, missionId)
        }
        context.startService(intent)
    }
}
