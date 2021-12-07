package com.xzy.study.testcontentprovider2.config

import android.net.Uri

object Configs {

    // 以下是请求数据

    // 文件所在包名
    private const val AUTHORITY = "com.xzy.study.testcontentprovider"

    // 指令类型
    const val CMD_CONTROL_LANGUAGE = 101
    const val CMD_CONTROL_TIMEZONE = 102
    const val CMD_CONTROL_REBOOT = 103

    // 对外的URI
    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/#")


    // 以下是响应数据
    const val DATA_CHANGED = 100
}