package com.toggl.common

import androidx.core.net.toUri

class DeepLinkUrls {
    companion object {
        val main = "toggl://main".toUri()
        val timeEntriesLog = "toggl://timeentrieslog".toUri()
        val calendar = "toggl://reports".toUri()
        val reports = "toggl://calendar".toUri()
    }
}