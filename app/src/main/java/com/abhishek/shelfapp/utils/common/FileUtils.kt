package com.abhishek.shelfapp.utils.common

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.annotation.RawRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringWriter
import java.util.logging.Logger

object FileUtils {

    fun readRawFile(context: Context, @RawRes rawResId: Int): Flow<String> {

        return flow {
            val inputStream = context.resources.openRawResource(rawResId)
            val writer = StringWriter()
            val buffer = CharArray(1024)
            try {
                val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                var readBuffer = reader.read(buffer)
                while (readBuffer != -1) {
                    writer.write(buffer, 0, readBuffer)
                    readBuffer = reader.read(buffer)
                }
                emit(writer.toString())
            } finally {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    Log.e(TAG, e.toString())
                }
            }
        }
    }
}