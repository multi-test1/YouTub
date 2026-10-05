/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.usecase

import com.youtub.data.local.SubscriptionEntity
import com.youtub.domain.repository.LibraryRepository
import com.youtub.domain.repository.VideoRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncSubscriptionMetadataUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke() {
        val subscriptions = libraryRepository.getSubscriptions().first()
        
        // Sync channels that have missing metadata or seem to have ID as names
        subscriptions.filter { 
            it.subscriberCount == null || 
            it.subscriberCount == 0L || 
            it.thumbnailUrl == null ||
            it.name.startsWith("UC")
        }.forEach { sub ->
            try {
                // Add a small delay to be "good citizens" and avoid throttling
                kotlinx.coroutines.delay(500)
                
                val details = videoRepository.getChannelDetails(sub.channelId)
                libraryRepository.subscribe(
                    SubscriptionEntity(
                        channelId = sub.channelId,
                        name = details.name,
                        thumbnailUrl = details.avatarUrl ?: sub.thumbnailUrl,
                        subscriberCount = details.subscriberCount
                    )
                )
            } catch (e: Exception) {
                // Skip failed syncs to avoid blocking others
                e.printStackTrace()
            }
        }
    }
}
