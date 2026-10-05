/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.ui.screens.search

import com.youtub.domain.repository.SearchRepository
import javax.inject.Inject

class SearchSuggestionProvider @Inject constructor(
    private val searchRepository: SearchRepository
) {
    // This could be used for search suggestions as the user types
    suspend fun getSuggestions(query: String): List<String> {
        // NewPipe Extractor doesn't have a direct suggestion API easily accessible 
        // without more deep extraction, but we could implement it later.
        return emptyList()
    }
}
