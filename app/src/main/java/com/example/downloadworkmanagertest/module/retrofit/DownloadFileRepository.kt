package com.example.downloadworkmanagertest.module.retrofit

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

object DownloadFileRepository {
    fun downloadFile(fileUrl: String): ResponseBody? {
        return try {
            val service = DownloadFileRetrofit
                .provideRetrofit(DownloadFileRetrofit.provideOkHttpClient())
                .create(DownloadFileService::class.java)
            val response = service.downloadFile(fileUrl).execute()
            if (response.isSuccessful) response.body()
            else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}