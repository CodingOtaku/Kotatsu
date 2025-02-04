package org.koitharu.kotatsu.filter.ui

import kotlinx.coroutines.flow.StateFlow
import org.koitharu.kotatsu.core.model.GenericSortOrder
import org.koitharu.kotatsu.core.model.SortDirection
import org.koitharu.kotatsu.filter.ui.model.FilterHeaderModel
import org.koitharu.kotatsu.filter.ui.model.FilterProperty
import org.koitharu.kotatsu.list.ui.model.ListModel
import org.koitharu.kotatsu.parsers.model.ContentRating
import org.koitharu.kotatsu.parsers.model.MangaState
import org.koitharu.kotatsu.parsers.model.MangaTag
import java.util.Locale

interface MangaFilter : OnFilterChangedListener {

	val allTags: StateFlow<List<ListModel>>

	val filterTags: StateFlow<FilterProperty<MangaTag>>

	val filterTagsExcluded: StateFlow<FilterProperty<MangaTag>>

	val filterSortOrder: StateFlow<FilterProperty<GenericSortOrder>>

	val filterSortDirection: StateFlow<FilterProperty<SortDirection>>

	val filterState: StateFlow<FilterProperty<MangaState>>

	val filterContentRating: StateFlow<FilterProperty<ContentRating>>

	val filterLocale: StateFlow<FilterProperty<Locale?>>

	val header: StateFlow<FilterHeaderModel>

	fun applyFilter(tags: Set<MangaTag>)
}
