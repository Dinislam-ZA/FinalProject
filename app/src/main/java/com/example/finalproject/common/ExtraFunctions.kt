package com.example.finalproject.common

object ExtraFunctions {

    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }
}