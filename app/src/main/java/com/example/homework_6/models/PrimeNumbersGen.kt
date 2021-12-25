package com.example.homework_6.models

import java.util.LinkedList

val primeNumList: List<Int> = PrimeNumbersGen().primeNumbersGenerator(1000)


class PrimeNumbersGen {
    private val even = 2
    fun primeNumbersGenerator(n: Int): List<Int> {
        val primeNumbers: MutableList<Int> = LinkedList()
        primeNumbers.add(0, 0)
        for (i in even..n) {
            if (isPrimeBruteForce(i)) {
                primeNumbers.add(i)
            }
        }
        return primeNumbers
    }

    private fun isPrimeBruteForce(number: Int): Boolean {
        for (i in even until number) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }
}