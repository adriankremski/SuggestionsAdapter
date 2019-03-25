package com.github.snuffix.suggestionsAdapter

import android.view.View

abstract class BaseSuggestionsAdapterDelegate<SuggestionItem: Suggestion, DelegateView : View> : SuggestionsAdapterDelegate<SuggestionItem> {
    override fun getView(suggestion: SuggestionItem, convertView: View?): View {
        return if (convertView == null) {
            onCreateView(suggestion)
        } else {
            onBindView(suggestion, convertView as DelegateView)
        }
    }

    abstract fun onCreateView(suggestion: SuggestionItem): View

    abstract fun onBindView(suggestion: SuggestionItem, view: DelegateView): View

    abstract fun isViewType(convertView: View): Boolean
}

