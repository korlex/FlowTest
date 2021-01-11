package com.example.flowtest.manager.testmanager3

import android.util.Log
import com.example.flowtest.manager.testmanager1.TestManager1
import com.example.flowtest.manager.testmanager1.TestManager1Data
import com.example.flowtest.manager.testmanager2.TestManager2
import com.example.flowtest.manager.testmanager2.TestManager2Data
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

class TestManager3 {

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var job1: Job? = null
    private var job2: Job? = null
    private var job3: Job? = null

    val testManager3Channel: Channel<Any> = Channel(Channel.UNLIMITED)
    val testManager3StateFlow: MutableStateFlow<TestManager3Data> = MutableStateFlow(TestManager3Data("", ""))


    init {
        Log.d("TAG", "TestManager3 created")
        startListen1()
        startListen2()
        startOperate()
    }


    fun startListen1() {
        job1 = scope.launch {
            TestManager1.instance.testManager1StateFlow.collect { testManager3Channel.send(it) }
        }
    }

    fun startListen2() {
        job2 = scope.launch {
            TestManager2.instance.testManager2StateFlow.collect { testManager3Channel.send(it) }
        }
    }

    fun startOperate() {
        job3 = scope.launch {
            while (job3?.isActive == true) {
                when(val data = testManager3Channel.receive()) {
                    is TestManager1Data -> updateData(data)
                    is TestManager2Data -> updateData(data)
                }
            }
        }
    }

    fun stopListen() {
        job1?.cancel()
        job2?.cancel()
        job3?.cancel()
    }

    private suspend fun updateData(testManager1Data: TestManager1Data) {
        //delay(2000)

        val oldData = testManager3StateFlow.value
        val newData = oldData.copy(message = "TestManager3: ${testManager1Data.message} \n ---")
        testManager3StateFlow.emit(newData)
    }

    private suspend fun updateData(testManager2Data: TestManager2Data) {
        //delay(2000)

        val oldData = testManager3StateFlow.value
        val newData = oldData.copy(message = "TestManager3: ${testManager2Data.message} \n ---")
        testManager3StateFlow.emit(newData)
    }

    companion object { val instance = TestManager3() }
}