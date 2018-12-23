package com.example.andrei.mysapper

import java.util.*

fun IntRange.random() : Int {
    return Random().nextInt((endInclusive + 1 ) - start) + start
}

fun <T : Any?> MutableList<T>.pickRandomElement() : T? {
    if(isEmpty())
        return null

    val index = (indices.start until indices.endInclusive).random()

    if(index < 0)
        return null

    val element = this[index]
    removeAt(index)

    return element
}