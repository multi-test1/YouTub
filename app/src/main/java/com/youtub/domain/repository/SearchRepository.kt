/*
 * YouTub Project Original (2026)
 * arslandaim-hub (GitHub.com/arslandaim-hub)
 * Licenced Under GPL-3.0+
*/
package com.youtub.domain.repository

import com.youtub.domain.model.PaginatedList
import com.youtub.domain.model.SearchSort
import com.youtub.domain.model.SearchItem
import com.youtub.domain.model.UploadDateFilter
import com.youtub.domain.model.DurationFilter
import org.schabi.newpipe.extractor.Page

interface SearchRepository {
    suspend fun search(
        query: String,
        sort: SearchSort = SearchSort.RELEVANCE,
        uploadDate: UploadDateFilter = UploadDateFilter.ALL,
        duration: DurationFilter = DurationFilter.ALL
    ): PaginatedList<SearchItem>

    suspend fun fetchNextPage(
        query: String,
        sort: SearchSort,
        uploadDate: UploadDateFilter = UploadDateFilter.ALL,
        duration: DurationFilter = DurationFilter.ALL,
        page: Page
    ): PaginatedList<SearchItem>

    suspend fun getSearchSuggestions(query: String): List<String>
}
