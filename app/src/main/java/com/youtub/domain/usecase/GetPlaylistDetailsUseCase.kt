/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.usecase

import com.youtub.domain.model.PlaylistDetails
import com.youtub.domain.repository.VideoRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class GetPlaylistDetailsUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(playlistUrl: String): Result<PlaylistDetails> {
        return try {
            Result.success(repository.getPlaylistDetails(playlistUrl))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }
}
