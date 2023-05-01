package com.example.dishes.common.adapter

interface DelegateDiffable<in C> {

    fun isSame(other: DelegateDiffable<*>): Boolean

    fun getChangePayload(newCell: C): Any? = null
}
