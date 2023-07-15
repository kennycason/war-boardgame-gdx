package com.kennycason.war.util

fun Int?.orDefault(default: Int): Int {
    return this ?: default
}