/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.usecase

import com.youtub.data.local.HistoryEntity
import com.youtub.data.local.PreferencesManager
import com.youtub.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddToHistoryUseCase @Inject constructor(
    private val repository: LibraryRepository,
    private val preferencesManager: PreferencesManager
) {
    suspend operator fun invoke(history: HistoryEntity) {
        if (preferencesManager.isIncognitoMode.first()) return
        repository.addToHistory(history)
    }
}
