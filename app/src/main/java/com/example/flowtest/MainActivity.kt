package com.example.flowtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flowtest.manager.testmanager1.TestManager1
import com.example.flowtest.manager.testmanager2.TestManager2
import com.example.flowtest.manager.testmanager3.TestManager3
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // testManagers init
    val testManager1 = TestManager1.instance
    val testManager2 = TestManager2.instance
    val testManager3 = TestManager3.instance

    private val testViewModel: TestViewModel = TestViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testViewModel.msgLiveData.observe(this) {
            val oldText = tvTextPanel.text.toString()
            tvTextPanel.text = "$oldText\n$it"
        }

        btn1.setOnClickListener { testViewModel.listen() }
        btn2.setOnClickListener { testViewModel.unListen() }
        btn3.setOnClickListener { tvTextPanel.text = "" }
    }
}