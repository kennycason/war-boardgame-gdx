package com.kennycason.war.util

import java.util.*

object Dice {
    val random = Random()

    fun d(n: Int) = random.nextInt(n) + 1
    fun d(min: Int, max: Int) = d(max - min) + min
    fun d2() = d(2)
    fun d6() = d(6)
    fun d10() = d(10)
    fun d100() = d(100)
    fun d20() = d(20)
    fun posNeg() = if (random.nextBoolean()) 1 else -1
    fun bool() = random.nextBoolean()
    fun double() = random.nextDouble()
    fun float() = random.nextFloat()
}