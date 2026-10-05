/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.usecase

import com.youtub.domain.model.PaginatedList
import com.youtub.domain.model.VideoItem
import com.youtub.domain.repository.VideoRepository
import kotlinx.coroutines.CancellationException
import org.schabi.newpipe.extractor.Page
import javax.inject.Inject

class GetTrendingVideosUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(): Result<PaginatedList<VideoItem>> {
        return try {
            // NewPipe usually fetches trending/kiosk as the initial page.
            // We'll need a way in Repository to fetch the initial trending page with its Page token.
            Result.success(repository.getTrendingVideos())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }

    suspend fun fetchNextPage(page: Page): Result<PaginatedList<VideoItem>> {
        return try {
            Result.success(repository.fetchNextTrendingPage(page))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }
}
