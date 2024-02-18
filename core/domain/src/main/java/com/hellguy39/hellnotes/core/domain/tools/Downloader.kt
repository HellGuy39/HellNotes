package com.hellguy39.hellnotes.core.domain.tools

interface Downloader {
    fun downloadFile(url: String): Long
}
