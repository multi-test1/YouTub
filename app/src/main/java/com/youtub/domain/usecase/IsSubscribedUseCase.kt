/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.usecase

import com.youtub.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsSubscribedUseCase @Inject constructor(
    private val repository: LibraryRepository
) {
    operator fun invoke(channelId: String): Flow<Boolean> = repository.isSubscribed(channelId)
}
