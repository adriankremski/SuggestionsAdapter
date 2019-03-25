package com.github.snuffix.suggestionsAdapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_car.view.*

const val CAR_VIEW_TYPE = 0

class CarSuggestionsAdapterDelegate(private val context: Context) : BaseSuggestionsAdapterDelegate<Car, CarView>() {
    override fun onCreateView(car: Car) = CarView(car, context)

    override fun onBindView(car: Car, view: CarView) = view.bind(car = car)

    override fun isViewType(convertView: View) = convertView is CarView
}

class CarView @JvmOverloads constructor(private var car: Car, context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_car, this)
        orientation = VERTICAL
        bind(car)
    }

    fun bind(car: Car) : CarView {
        this.car = car
        nameLabel.text = car.name
        return this
    }
}

data class Car(override val name: String, override val viewType: Int = CAR_VIEW_TYPE) : Suggestion {
    override fun isMatch(query: String) = name.contains(query)
}


