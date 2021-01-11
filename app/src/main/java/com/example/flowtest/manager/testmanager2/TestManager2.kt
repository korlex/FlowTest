package com.example.flowtest.manager.testmanager2


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.threeten.bp.LocalDateTime

class TestManager2 private constructor(){

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var job: Job? = null

    val testManager2StateFlow: MutableStateFlow<TestManager2Data> = MutableStateFlow(TestManager2Data(0, 0, "TestManager2 initial msg"))

    init {
        startTest()
    }

    private fun startTest() {
        job = scope.launch {
            while(job?.isActive == true) {
                delay(1000)

                val oldData = testManager2StateFlow.value
                val newData = oldData.copy(message = "TestManager2 new msg, ${LocalDateTime.now()}")
                testManager2StateFlow.emit(newData)
            }
        }
    }

    companion object { val instance = TestManager2() }
}