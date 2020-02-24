package org.koitharu.kotatsu.ui.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.local.Cache
import org.koitharu.kotatsu.ui.common.BasePreferenceFragment
import org.koitharu.kotatsu.utils.CacheUtils
import org.koitharu.kotatsu.utils.FileSizeUtils
import org.koitharu.kotatsu.utils.ext.getDisplayMessage

class HistorySettingsFragment : BasePreferenceFragment(R.string.history_and_cache) {

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		addPreferencesFromResource(R.xml.pref_history)
		findPreference<Preference>(R.string.key_pages_cache_clear)?.let { pref ->
			lifecycleScope.launchWhenStarted {
				val size = withContext(Dispatchers.IO) {
					CacheUtils.computeCacheSize(pref.context, Cache.PAGES.dir)
				}
				pref.summary = FileSizeUtils.formatBytes(pref.context, size)
			}
		}
	}

	override fun onPreferenceTreeClick(preference: Preference): Boolean {
		return when(preference.key) {
			getString(R.string.key_pages_cache_clear) -> {
				clearCache(preference, Cache.PAGES)
				true
			}
			else -> super.onPreferenceTreeClick(preference)
		}
	}

	private fun clearCache(preference: Preference, cache: Cache) {
		val ctx = preference.context.applicationContext
		lifecycleScope.launch {
			try {
				preference.isEnabled = false
				val size = withContext(Dispatchers.IO) {
					CacheUtils.clearCache(ctx, cache.dir)
					CacheUtils.computeCacheSize(ctx, cache.dir)
				}
				preference.summary = FileSizeUtils.formatBytes(ctx, size)
			} catch (e: Exception) {
				preference.summary = e.getDisplayMessage(ctx.resources)
			} finally {
				preference.isEnabled = true
			}
		}
	}
}