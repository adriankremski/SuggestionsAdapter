package com.github.snuffix.suggestionsAdapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_animal.view.*

const val ANIMAL_VIEW_TYPE = 1

class AnimalSuggestionsAdapterDelegate(private val context: Context) : BaseSuggestionsAdapterDelegate<Animal, AnimalView>() {
    override fun onCreateView(animal: Animal) = AnimalView(animal, context)

    override fun onBindView(animal: Animal, view: AnimalView) = view.bind(animal = animal)

    override fun isViewType(convertView: View) = convertView is AnimalView
}

class AnimalView @JvmOverloads constructor(private var animal: Animal, context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_animal, this)
        orientation = VERTICAL
        bind(animal)
    }

    fun bind(animal: Animal) : AnimalView {
        this.animal = animal
        nameLabel.text = animal.name
        return this
    }
}

data class Animal(override val name: String, override val viewType: Int = ANIMAL_VIEW_TYPE) : Suggestion {
    override fun isMatch(query: String) = name.contains(query)
}

