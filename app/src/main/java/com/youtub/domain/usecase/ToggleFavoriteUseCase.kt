/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.usecase

import com.youtub.data.local.FavoriteEntity
import com.youtub.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: LibraryRepository
) {
    suspend operator fun invoke(favorite: FavoriteEntity) {
        val isFavorite = repository.isFavorite(favorite.videoId).first()
        if (isFavorite) {
            repository.removeFromFavorites(favorite)
        } else {
            repository.addToFavorites(favorite)
        }
    }
}
