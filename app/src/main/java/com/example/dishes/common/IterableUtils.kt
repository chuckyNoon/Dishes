package com.example.dishes.common

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }