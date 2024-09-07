package com.example.racetrackerapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*

class RaceParticipant(
    val name:String,
    val maxProgress:Int = 100,
    val progressDelay:Long = 500L,
    private val progressIncrement:Int = 1,
    private val initialProgress:Int = 0
) {
    init{
        require(maxProgress > 0) {"maxProgress=$maxProgress; must be a positive number"}
        require(progressIncrement > 0) {"progressIncrement=$progressIncrement; must be a positive number"}
    }

    var currentProgress by mutableIntStateOf(initialProgress)
        private set

    suspend fun run() {
        try {
            while (currentProgress < maxProgress) {
                delay(progressDelay)
                currentProgress += progressIncrement
            }
        } catch (e: CancellationException) {
            Log.e("RaceParticipant", "$name: ${e.message}")
            throw e
        }
    }

    fun reset(){
        currentProgress = 0
    }

}

val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()