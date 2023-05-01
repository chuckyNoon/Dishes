package com.example.dishes.domain.priceformatter

interface PriceFormatter {
    fun format(value: Int): String
}