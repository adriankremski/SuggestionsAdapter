package com.github.snuffix.suggestionsAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.collection.SparseArrayCompat

class SuggestionsAdapter(context: Context, private val allItems: List<Suggestion>, private val suggestionsTypeCount : Int = 2) :
    ArrayAdapter<Suggestion>(context, android.R.layout.activity_list_item, allItems) {

    private val delegateAdapters = SparseArrayCompat<SuggestionsAdapterDelegate<Suggestion>>()
    private val resultItems = mutableListOf<Suggestion>().apply { addAll(allItems) }

    fun addDelegate(viewType: Int, delegate: SuggestionsAdapterDelegate<Suggestion>) {
        delegateAdapters.put(viewType, delegate)
    }

    private val filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String {
            return (resultValue as Suggestion).name
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()

            if (!constraint.isNullOrEmpty()) {
                val suggestions = allItems.filter { it.isMatch(constraint!!.toString()) }

                results.values = suggestions.toTypedArray()
                results.count = suggestions.size
            } else {
                results.values = allItems.toTypedArray()
                results.count = allItems.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {
                resultItems.clear()
                resultItems.addAll(it.values as Array<out Suggestion>)
            }

            notifyDataSetChanged()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val suggestion = resultItems[position]
        return delegateAdapters[suggestion.viewType]!!.getView(suggestion, convertView)
    }

    override fun getViewTypeCount() = suggestionsTypeCount

    override fun getItemViewType(position: Int) = resultItems[position].viewType

    override fun getItem(position: Int): Suggestion {
        return resultItems[position]
    }

    override fun getCount(): Int {
        return resultItems.count()
    }

    override fun getFilter(): Filter {
        return filter
    }
}

interface SuggestionsAdapterDelegate<T : Suggestion> {
    fun getView(suggestion: T, convertView: View?): View
}

interface Suggestion {
    val name: String
    val viewType: Int
    fun isMatch(query: String): Boolean
}