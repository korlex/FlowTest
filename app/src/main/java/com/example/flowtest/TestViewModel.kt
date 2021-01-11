package com.example.flowtest

import androidx.lifecycle.MutableLiveData
import com.example.flowtest.manager.testmanager3.TestManager3
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.flow.collect

class TestViewModel {

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var testJob: Job? = null
    private val testManager: TestManager = TestManager()
    val msgLiveData: MutableLiveData<String> = MutableLiveData()

    fun listen() {
        testJob = scope.launch {
            msgLiveData.value = "Start listen"
            TestManager3.instance.testManager3StateFlow.collect { msgLiveData.value = it.message }
        }
    }

    fun unListen() {
        msgLiveData.value = "Finish listen"
        testJob?.cancel()
    }



//    fun startTest() {
//        msgLiveData.value = "------"
//        startListen()
//        testManager.startTest()
//    }
//
//    fun finishTest() {
//        finishListen()
//        testManager.finishTest()
//        msgLiveData.value = "------"
//    }
//
//    private fun startListen() {
//        testJob = scope.launch {
//            testManager.msgFlow
//                .collect {
//                msgLiveData.value = it
//            }
//        }
//    }
//
//    private fun finishListen() {
//        testJob?.cancel()
//    }

}