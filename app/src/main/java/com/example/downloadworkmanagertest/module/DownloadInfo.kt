package com.example.downloadworkmanagertest.module

import com.google.gson.annotations.SerializedName


const val DOWNLOAD_INFO = "download_info"
data class DownloadInfo(
    @SerializedName("file_name") val filePath: String,
    @SerializedName("url") val url: String
)