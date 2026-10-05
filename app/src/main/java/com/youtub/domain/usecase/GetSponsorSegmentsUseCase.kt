/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.usecase

import com.youtub.domain.model.SponsorSegment
import com.youtub.domain.repository.SponsorBlockRepository
import javax.inject.Inject

class GetSponsorSegmentsUseCase @Inject constructor(
    private val repository: SponsorBlockRepository
) {
    suspend operator fun invoke(videoId: String): Result<List<SponsorSegment>> {
        return repository.getSponsorSegments(videoId)
    }
}
