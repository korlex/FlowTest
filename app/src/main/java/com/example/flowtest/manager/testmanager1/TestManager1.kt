package com.example.flowtest.manager.testmanager1

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import org.threeten.bp.LocalDateTime

class TestManager1 private constructor(){

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var job: Job? = null

    private val channel = Channel<String>(Channel.UNLIMITED)
    val testManager1StateFlow: MutableStateFlow<TestManager1Data> = MutableStateFlow(TestManager1Data(false, false, "TestManager1 initial msg"))

    init {
        Log.d("TAG", "TestManager1 created")
        startTest()
    }

    private fun startTest() {
        job = scope.launch {
            while(job?.isActive == true) {
                delay(1000)

                val oldData = testManager1StateFlow.value
                val newData = oldData.copy(message = "TestManager1 new msg, ${LocalDateTime.now()}")
                testManager1StateFlow.emit(newData)
            }
        }
    }

    fun finishTest() {
        job?.cancel()
    }

    companion object { val instance = TestManager1() }
}