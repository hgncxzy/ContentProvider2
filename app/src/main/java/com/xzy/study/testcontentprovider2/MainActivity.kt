package com.xzy.study.testcontentprovider2

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

import android.widget.TextView
import android.database.ContentObserver
import android.os.*

import com.xzy.study.testcontentprovider2.config.Configs.CONTENT_URI
import com.xzy.study.testcontentprovider2.config.Configs.DATA_CHANGED


class MainActivity : AppCompatActivity() {
    private lateinit var contentObserver: MyContentObserver
    private lateinit var tvText1: TextView
    private lateinit var tvText2: TextView

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.obj as String) {
                // todo
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contentObserver = MyContentObserver(handler)
        contentResolver.registerContentObserver(CONTENT_URI, true, contentObserver)
        tvText1 = findViewById(R.id.tv_test1)
        tvText2 = findViewById(R.id.tv_test2)
        tvText1.setOnClickListener {
            query()
        }
        tvText2.setOnClickListener {
            insert()
        }
    }

    private fun query() {
        val cursor = contentResolver.query(CONTENT_URI, arrayOf("username", "password"), null, null, null)
        if (cursor != null) {
            val bundle = cursor.extras
            val username = bundle.getString("username")
            val password = bundle.getString("password")
            Log.d("test 001 ", "username:$username, password:$password")
            cursor.close()
        }
    }

    private fun insert() {
        val values = ContentValues()
        values.put("username", "client-01")
        values.put("password", "client-pwd")
        contentResolver.insert(CONTENT_URI, values)
    }

    // 参考 http://www.javashuo.com/article/p-utqyaxla-ds.html
    inner class MyContentObserver(handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            val msg = Message()
            msg.obj = uri?.authority + uri?.path
            msg.what = DATA_CHANGED
            handler.sendMessage(msg)
            Log.d("test 002", "--uri:${uri?.authority + uri?.path}")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(contentObserver)
    }
}