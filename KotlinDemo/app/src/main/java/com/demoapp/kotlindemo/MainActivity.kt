package com.demoapp.kotlindemo

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button1).setOnClickListener { v: View? ->
            println("Start")

            // 启动一个协程
            GlobalScope.launch {
                val result = doSomething()
                println("Result: $result")
            }

            // 主线程继续执行其他操作
            println("End")

            // 阻塞主线程，等待协程执行完成
            runBlocking {
                delay(2000) // 等待协程执行完成
            }
        }
    }

    suspend fun doSomething(): String {
        delay(1000) // 模拟耗时操作
        return "Done"
    }
}
