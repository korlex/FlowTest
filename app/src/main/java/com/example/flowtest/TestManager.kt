package com.example.flowtest

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.threeten.bp.LocalDateTime

class TestManager {

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var testJob: Job? = null

    var msgFlow: MutableSharedFlow<String> = MutableSharedFlow(5, 5, BufferOverflow.DROP_LATEST)

    fun startTest() {
        testJob = scope.launch {
            while (testJob?.isActive == true) {
                delay(1000)
                msgFlow.emit("Tick , ${LocalDateTime.now()}")
            }
        }
    }

    fun finishTest() {
        testJob?.cancel()
    }


}