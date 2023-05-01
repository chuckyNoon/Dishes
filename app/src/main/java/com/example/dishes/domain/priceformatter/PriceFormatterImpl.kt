package com.example.dishes.domain.priceformatter

import javax.inject.Inject

class PriceFormatterImpl @Inject constructor() : PriceFormatter {

    override fun format(value: Int): String =
        value.toString()
}