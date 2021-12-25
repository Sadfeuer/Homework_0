package com.example.homework_6.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CounterViewModel : ViewModel() {
    val history = mutableListOf<Int>()
    var countIsRunning = false
    var actualNumber = 0
    var threadSleepTime = 1
    var number = 0
    var summ = 0

    private val protCountLiveData = MutableLiveData<String>()
    val countLiveData: LiveData<String> get() = protCountLiveData

    fun startCounting() {
        countIsRunning = true
        val interval = (threadSleepTime * 1000).toLong()
        var thread = Thread {
            while (countIsRunning) {
                number++
                if (primeNumList != null) {
                    actualNumber = primeNumList[number]
                    protCountLiveData.postValue("$actualNumber")
                }
                Thread.sleep(interval)
            }
        }
        thread.start()
    }

    fun pauseCounting() {
        countIsRunning = false
    }

    fun summCounting(): Int {
        for (i in 0..number) {
            summ += primeNumList[i]
        }
        history.add(summ)
        val showsumm = summ
        summ = 0
        return showsumm

    }

    fun resetCounting() {
        countIsRunning = false
        number = 0
        summ = 0
        actualNumber = 0
    }

    fun changeIntervalTime(usersNum: Int?) {
        if (usersNum != null) {
            threadSleepTime = usersNum
        }
    }

}