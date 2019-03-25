package com.github.snuffix.suggestionsAdapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var suggestionsAdapter: SuggestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = List(300) {
            if (it % 2 == 0) {
                Animal("Animal $it")
            } else {
                Car("Car $it")
            }
        }

        suggestionsAdapter = SuggestionsAdapter(this, items)
        suggestionsAdapter.addDelegate(CAR_VIEW_TYPE, CarSuggestionsAdapterDelegate(this) as SuggestionsAdapterDelegate<Suggestion>)
        suggestionsAdapter.addDelegate(ANIMAL_VIEW_TYPE, AnimalSuggestionsAdapterDelegate(this) as SuggestionsAdapterDelegate<Suggestion>)

        autoCompleteInput.setAdapter(suggestionsAdapter)

        autoCompleteInput.setOnTouchListener { view, event ->
            autoCompleteInput.showDropDown()
            autoCompleteInput.requestFocus()
            false
        }
    }
}
