package com.hellguy39.hellnotes.core.domain.system_features

interface Downloader {

    fun downloadFile(url: String): Long

}